package soft.co.books.resource;

import io.swagger.annotations.Api;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import soft.co.books.configuration.security.other.AuthoritiesConstants;
import soft.co.books.domain.service.BookService;
import soft.co.books.domain.service.dto.BookDTO;
import soft.co.books.domain.service.dto.PageResultDTO;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@Api(description = "Book operations")
public class BookResource {

    private final BookService bookService;

    public BookResource(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/books")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.BOOK_LIST + "\")")
    public PageResultDTO<BookDTO> getAllBooks(@RequestBody BookDTO bookDTO, Pageable pageable) {
        return bookService.findAll(bookDTO, pageable);
    }

    @PostMapping("/book")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.BOOK_MANAGEMENT + "\")")
    public BookDTO getBook(@RequestBody BookDTO bookDTO) {
        if (bookDTO.getId() != null)
            return bookService.findOne(bookDTO.getId())
                    .map(BookDTO::new).get();
        else
            return null;
    }

    @PostMapping("/saveBook")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.BOOK_MANAGEMENT + "\")")
    public BookDTO saveBook(@Valid @RequestBody BookDTO bookDTO) {
        if (bookDTO.getId() == null || bookDTO.getId().isEmpty())
            return bookService.createBook(bookDTO).get();
        else
            return bookService.updateBook(bookDTO).get();
    }

    @DeleteMapping("/deleteBook/{bookId}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.BOOK_MANAGEMENT + "\")")
    public ResponseEntity<Void> deleteBook(@PathVariable String bookId) {
        bookService.delete(bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
