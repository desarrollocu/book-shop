package soft.co.books.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soft.co.books.configuration.database.CustomBaseService;
import soft.co.books.domain.collection.Authority;
import soft.co.books.domain.repository.AuthorityRepository;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Service class for managing authorities.
 */
@Service
@Transactional
public class AuthorityService extends CustomBaseService<Authority, String> {

    private final Logger log = LoggerFactory.getLogger(AuthorityService.class);

    private final AuthorityRepository authorityRepository;

    public AuthorityService(AuthorityRepository authorityRepository) {
        super(authorityRepository);
        this.authorityRepository = authorityRepository;
    }

    /**
     * @return a list of all the authorities
     */
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }

    public Authority getAuthority(String name) {
        return authorityRepository.findByName(name);
    }
}
