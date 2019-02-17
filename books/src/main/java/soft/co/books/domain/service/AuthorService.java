package soft.co.books.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soft.co.books.configuration.Constants;
import soft.co.books.configuration.database.CustomBaseService;
import soft.co.books.configuration.error.CustomizeException;
import soft.co.books.domain.collection.Author;
import soft.co.books.domain.collection.AuthorBook;
import soft.co.books.domain.collection.Book;
import soft.co.books.domain.repository.AuthorRepository;
import soft.co.books.domain.service.dto.AuthorDTO;
import soft.co.books.domain.service.dto.PageResultDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;


/**
 * Service class for managing authors.
 */
@Service
@Transactional
public class AuthorService extends CustomBaseService<Author, String> {

    private final Logger log = LoggerFactory.getLogger(AuthorService.class);

    private final AuthorRepository authorRepository;

    private final CountryService countryService;

    private MongoTemplate mongoTemplate;

    public AuthorService(AuthorRepository authorRepository,
                         CountryService countryService,
                         MongoTemplate mongoTemplate) {
        super(authorRepository);
        this.authorRepository = authorRepository;
        this.countryService = countryService;
        this.mongoTemplate = mongoTemplate;
    }

    public PageResultDTO findAll(AuthorDTO authorDTO, Pageable pageable) {
        PageResultDTO<AuthorDTO> resultDTO = new PageResultDTO<>();

        Query query = new Query();
        query.limit(pageable.getPageSize());
        query.skip(pageable.getPageNumber() * pageable.getPageSize());
        query.with(pageable.getSort());

        if (authorDTO.getFirstName() != null && !authorDTO.getFirstName().isEmpty())
            query.addCriteria(where("firstName").regex(authorDTO.getFirstName(), "i"));
        if (authorDTO.getLastName() != null && !authorDTO.getLastName().isEmpty())
            query.addCriteria(where("lastName").regex(authorDTO.getLastName(), "i"));
        if (authorDTO.getCity() != null && !authorDTO.getCity().isEmpty())
            query.addCriteria(where("city").regex(authorDTO.getCity(), "i"));
        if (authorDTO.getState() != null && !authorDTO.getState().isEmpty())
            query.addCriteria(where("state").regex(authorDTO.getState(), "i"));
        if (authorDTO.getBornDate() != null && !authorDTO.getBornDate().isEmpty())
            query.addCriteria(where("bornDate").is(authorDTO.getBornDate()));
        if (authorDTO.getDeathDate() != null && !authorDTO.getDeathDate().isEmpty())
            query.addCriteria(where("deathDate").is(authorDTO.getDeathDate()));
        if (authorDTO.getCountry() != null)
            query.addCriteria(where("country.id").is(authorDTO.getCountry().getId()));

        Page<Author> authors = new PageImpl<>(mongoTemplate.find(query, Author.class));
        resultDTO.setElements(authors.stream().map(AuthorDTO::new).collect(Collectors.toList()));
        resultDTO.setTotal(mongoTemplate.count(query, Author.class));
        return resultDTO;
    }

    public Optional<AuthorDTO> createAuthor(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setFirstName(authorDTO.getFirstName());
        author.setLastName(authorDTO.getLastName());
        author.setFullName(authorDTO.getFirstName().toLowerCase() + " " + authorDTO.getLastName().toLowerCase());
        author.setId(authorDTO.getId());
        author.setCity(authorDTO.getCity());
        author.setState(authorDTO.getState());
        author.setCountry(countryService.findOne(authorDTO.getCountry().getId()).get());
        author.setBornDate(authorDTO.getBornDate());
        author.setDeathDate(authorDTO.getDeathDate());

        log.debug("Created Information for Author: {}", author);
        return Optional.of(authorRepository.save(author))
                .map(AuthorDTO::new);
    }

    /**
     * Update all information for a specific author, and return the modified author.
     *
     * @param authorDTO author to update
     * @return updated author
     */
    public Optional<AuthorDTO> updateAuthor(AuthorDTO authorDTO) {
        return Optional.of(authorRepository
                .findById(authorDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(author -> {
                    author.setFirstName(authorDTO.getFirstName());
                    author.setLastName(authorDTO.getLastName());
                    author.setFullName(authorDTO.getFirstName().toLowerCase() + " " + authorDTO.getLastName().toLowerCase());
                    author.setCity(authorDTO.getCity());
                    author.setState(authorDTO.getState());
                    author.setCountry(countryService.findOne(authorDTO.getCountry().getId()).get());
                    author.setDeathDate(authorDTO.getDeathDate());
                    author.setBornDate(authorDTO.getBornDate());
                    Author authorTemp = authorRepository.save(author);
                    /**
                     * Update all books*/
                    AuthorBook authorBook = new AuthorBook();
                    authorBook.setId(authorTemp.getId());
                    authorBook.setAuthorName(authorTemp.getFirstName() + " " + authorTemp.getLastName());

                    Query query = new Query();
                    query.addCriteria(Criteria.where("authorBook.id").is(authorTemp.getId()));
                    Update update = new Update();
                    update.set("authorBook", authorBook);
                    mongoTemplate.updateMulti(query, update, Book.class);
                    log.debug("Changed Information for Author: {}", author);
                    return author;
                })
                .map(AuthorDTO::new);
    }

    public void delete(String id) {
        Query query = new Query();
        query.addCriteria(where("authorList.id").is(id));
        if (mongoTemplate.count(query, Book.class) > 0) {
            throw new CustomizeException(Constants.ERR_DELETE);
        } else {
            authorRepository.findById(id).ifPresent(author -> {
                authorRepository.delete(author);
                log.debug("Deleted Author: {}", author);
            });
        }
    }

    public List<Author> findByFullNameIsLike(String fullName) {
        return authorRepository.findByFullNameIsLike(fullName);
    }
}
