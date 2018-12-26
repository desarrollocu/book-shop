package soft.co.books.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soft.co.books.configuration.Constants;
import soft.co.books.configuration.database.CustomBaseService;
import soft.co.books.configuration.error.CustomizeException;
import soft.co.books.configuration.security.other.SecurityUtils;
import soft.co.books.domain.collection.User;
import soft.co.books.domain.repository.UserRepository;
import soft.co.books.domain.service.dto.PageResultDTO;
import soft.co.books.domain.service.dto.UserDTO;

import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;


/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService extends CustomBaseService<User, String> {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityService authorityService;

    private final CountryService countryService;

    private MongoTemplate mongoTemplate;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       AuthorityService authorityService,
                       MongoTemplate mongoTemplate,
                       CountryService countryService) {
        super(userRepository);
        this.userRepository = userRepository;
        this.countryService = countryService;
        this.passwordEncoder = passwordEncoder;
        this.authorityService = authorityService;
        this.mongoTemplate = mongoTemplate;
    }

    public PageResultDTO findAll(UserDTO userDTO, Pageable pageable) {
        PageResultDTO<UserDTO> resultDTO = new PageResultDTO<>();

        Query query = new Query();
        query.limit(pageable.getPageSize());
        query.skip(pageable.getPageNumber() * pageable.getPageSize());
        query.with(pageable.getSort());

        if (userDTO.getFirstName() != null && !userDTO.getFirstName().isEmpty())
            query.addCriteria(where("firstName").regex(userDTO.getFirstName(), "i"));
        if (userDTO.getLastName() != null && !userDTO.getLastName().isEmpty())
            query.addCriteria(where("lastName").regex(userDTO.getLastName(), "i"));
        if (userDTO.getUserName() != null && !userDTO.getUserName().isEmpty())
            query.addCriteria(where("userName").regex(userDTO.getUserName().toLowerCase(), "i"));
        if (userDTO.getEmail() != null && !userDTO.getEmail().isEmpty())
            query.addCriteria(where("email").regex(userDTO.getEmail(), "i"));

        Page<User> users = new PageImpl<>(mongoTemplate.find(query, User.class));
        resultDTO.setElements(users.stream().map(UserDTO::new).collect(Collectors.toList()));
        resultDTO.setTotal(mongoTemplate.count(query, User.class));
        return resultDTO;
    }

    public Optional<UserDTO> createUser(UserDTO userDTO) {
        User user = new User();
        user.setPassword("$2a$10$Iwn.w7FA7780RmxOBQKFQ.gV0QjFTYQCyEoAmZsYWSIr9rlHEiR7y");
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUserName(userDTO.getUserName().toLowerCase());
        user.setId(userDTO.getId());
        user.setActivated(true);
        user.setEmail(userDTO.getEmail());

        if (userDTO.getIsAdmin().equals("true")) {
            user.setAuthorities(new HashSet<>(authorityService.findAll()));
        }

        if (userDTO.getLangKey() == null) {
            user.setLangKey(Constants.DEFAULT_LANGUAGE);
        } else {
            user.setLangKey(userDTO.getLangKey());
        }

        log.debug("Created Information for User: {}", user);
        return Optional.of(userRepository.save(user))
                .map(UserDTO::new);
    }

    public void registerUser(UserDTO userDTO) {
        User user = new User();
        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encryptedPassword);
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUserName(userDTO.getUserName().toLowerCase());
        user.setId(userDTO.getId());
        user.setCity(userDTO.getCity());
        user.setCountry(countryService.findOne(userDTO.getCountry().getId()).get());
        user.setState(userDTO.getState());
        user.setCp(userDTO.getCp());
        user.setPhone(userDTO.getPhone());
        user.setActivated(true);
        user.setEmail(userDTO.getEmail());

        if (userDTO.getLangKey() == null) {
            user.setLangKey(Constants.DEFAULT_LANGUAGE);
        } else {
            user.setLangKey(userDTO.getLangKey());
        }

        log.debug("Created Information for User: {}", user);
        userRepository.save(user);
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update
     * @return updated user
     */
    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        return Optional.of(userRepository
                .findById(userDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(user -> {
                    user.setUserName(userDTO.getUserName().toLowerCase());
                    user.setFirstName(userDTO.getFirstName());
                    user.setLastName(userDTO.getLastName());
                    user.setEmail(userDTO.getEmail());
                    user.setCity(userDTO.getCity());
                    user.setCountry(countryService.findOne(userDTO.getCountry().getId()).get());
                    user.setState(userDTO.getState());
                    user.setCp(userDTO.getCp());
                    user.setPhone(userDTO.getPhone());
                    user.setActivated(userDTO.isActivated());
                    user.setLangKey(userDTO.getLangKey());

                    if (userDTO.getIsAdmin().equals("true")) {
                        user.setAuthorities(new HashSet<>(authorityService.findAll()));
                    }

                    userRepository.save(user);
                    log.debug("Changed Information for User: {}", user);
                    return user;
                })
                .map(UserDTO::new);
    }

    public void updateLang(String lang) {
        SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findOneByUserName)
                .ifPresent(user -> {
                    user.setLangKey(lang);
                    userRepository.save(user);
                    log.debug("Changed language for User: {}", user);
                });
    }

    public void deleteUser(String login) {
        if (SecurityUtils.getCurrentUserLogin().equals(login)) {
            throw new CustomizeException(Constants.ERR_USER);
        } else {
            userRepository.findOneByUserName(login).ifPresent(user -> {
                userRepository.delete(user);
                log.debug("Deleted User: {}", user);
            });
        }
    }

    public void delete(String id) {
        SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findOneByUserName)
                .ifPresent(user -> {
                    if (user.getId().equals(id)) {
                        throw new CustomizeException(Constants.ERR_USER);
                    } else {
                        userRepository.findById(id).ifPresent(userById -> {
                            userRepository.delete(userById);
                            log.debug("Deleted User: {}", userById);
                        });
                    }
                });
    }

    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findOneByUserName)
                .ifPresent(user -> {
                    String currentEncryptedPassword = user.getPassword();
                    if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                        throw new CustomizeException(Constants.ERR_INCORRECT_PASSWORD);
                    }
                    String encryptedPassword = passwordEncoder.encode(newPassword);
                    user.setPassword(encryptedPassword);
                    userRepository.save(user);
                    log.debug("Changed password for User: {}", user);
                });
    }

    public Optional<User> findOneByUserName(String userName) {
        return this.userRepository.findOneByUserName(userName);
    }

    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneByUserName);
    }
}
