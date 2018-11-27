package soft.co.books.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import soft.co.books.configuration.database.CustomBaseService;
import soft.co.books.domain.collection.Book;
import soft.co.books.domain.repository.BookRepository;


/**
 * Service class for managing books.
 */
@Service
public class BookService extends CustomBaseService<Book, String> {

    private final Logger log = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        super(bookRepository);
        this.bookRepository = bookRepository;
    }
}
