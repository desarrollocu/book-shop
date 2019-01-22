package soft.co.books.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soft.co.books.configuration.database.CustomBaseService;
import soft.co.books.configuration.security.other.SecurityUtils;
import soft.co.books.domain.collection.Book;
import soft.co.books.domain.collection.BookTrace;
import soft.co.books.domain.collection.User;
import soft.co.books.domain.collection.data.UserDetail;
import soft.co.books.domain.repository.BookTraceRepository;


/**
 * Service class for managing bookTrace.
 */
@Service
@Transactional
public class BookTraceService extends CustomBaseService<BookTrace, String> {

    private final Logger log = LoggerFactory.getLogger(BookTraceService.class);

    private final BookTraceRepository bookTraceRepository;

    private final UserService userService;

    public BookTraceService(BookTraceRepository bookTraceRepository, UserService userService) {
        super(bookTraceRepository);
        this.bookTraceRepository = bookTraceRepository;
        this.userService = userService;
    }

    public void createBookTrace(Book book, String action, UserDetail userDetail) {
        BookTrace bookTrace = new BookTrace();
        bookTrace.setAction(action);
        bookTrace.setBookId(book.getId());
        String userName = SecurityUtils.getCurrentUserLogin().get();
        if (userName != null) {
            User user = userService.findOneByUserName(userName).get();
            if (user != null) {
                bookTrace.setUserName(userName);
                bookTrace.setFullName(user.getFirstName() + " " + user.getLastName());
            }
        } else {
            bookTrace.setUserName(userDetail != null ? userDetail.getUserName() : "Not user!");
            bookTrace.setFullName(userDetail != null ? userDetail.getFirstName() + userDetail.getLastName() : "Not user!");
        }
        this.bookTraceRepository.save(bookTrace);
    }
}
