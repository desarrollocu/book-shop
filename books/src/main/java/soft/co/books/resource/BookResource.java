package soft.co.books.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import soft.co.books.configuration.security.other.AuthoritiesConstants;
import soft.co.books.configuration.storage.StorageService;
import soft.co.books.domain.service.BookService;
import soft.co.books.domain.service.dto.BookDTO;
import soft.co.books.domain.service.dto.BookTraceDTO;
import soft.co.books.domain.service.dto.PageResultDTO;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@Api(description = "Book operations")
public class BookResource {

    @Autowired
    StorageService storageService;

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
    public BookDTO getBook(@RequestBody BookDTO bookDTO) {
        if (bookDTO.getId() != null)
            return bookService.findOne(bookDTO.getId())
                    .map(BookDTO::new).get();
        else
            return null;
    }

    @PostMapping("/saveBook")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.BOOK_MANAGEMENT + "\")")
    public BookDTO saveBook(@RequestPart("bookDTO") String bookDTOString, MultipartFile file)
            throws IOException {
        BookDTO bookDTO = new ObjectMapper().readValue(bookDTOString, BookDTO.class);
        String subDirectory;
        if (bookDTO.getId() == null || bookDTO.getId().isEmpty()) {
            subDirectory = UUID.randomUUID().toString();
        } else
            subDirectory = bookDTO.getId();

        if (file != null) {
            storageService.store(file, subDirectory);
            bookDTO.setImage(MvcUriComponentsBuilder
                    .fromMethodName(UploadResource.class, "getFile", file.getOriginalFilename(), subDirectory)
                    .build().toString());
        }

        if (bookDTO.getId() == null || bookDTO.getId().isEmpty()) {
            bookDTO.setId(subDirectory);
            return bookService.createBook(bookDTO).get();
        } else
            return bookService.updateBook(bookDTO).get();
    }

    @DeleteMapping("/deleteBook/{bookId}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.BOOK_MANAGEMENT + "\")")
    public ResponseEntity<Void> deleteBook(@PathVariable String bookId) {
        bookService.delete(bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/bookTrace")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.BOOK_TRACE + "\")")
    public List<BookTraceDTO> bookTrace(@RequestBody BookDTO bookDTO) {
        return bookService.traceList(bookDTO.getId());
    }
}
