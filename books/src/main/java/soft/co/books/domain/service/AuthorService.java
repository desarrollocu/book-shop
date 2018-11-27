package soft.co.books.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import soft.co.books.configuration.database.CustomBaseService;
import soft.co.books.domain.collection.Author;
import soft.co.books.domain.repository.AuthorRepository;


/**
 * Service class for managing authors.
 */
@Service
public class AuthorService extends CustomBaseService<Author, String> {

    private final Logger log = LoggerFactory.getLogger(AuthorService.class);

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        super(authorRepository);
        this.authorRepository = authorRepository;
    }
}
