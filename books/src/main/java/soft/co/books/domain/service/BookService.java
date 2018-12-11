package soft.co.books.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import soft.co.books.configuration.database.CustomBaseService;
import soft.co.books.domain.collection.Author;
import soft.co.books.domain.collection.Book;
import soft.co.books.domain.repository.BookRepository;
import soft.co.books.domain.service.dto.BookDTO;
import soft.co.books.domain.service.dto.PageResultDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;


/**
 * Service class for managing books.
 */
@Service
public class BookService extends CustomBaseService<Book, String> {

    private final Logger log = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;

    private final AuthorService authorService;

    private final EditorService editorService;

    private final TopicService topicService;

    private MongoTemplate mongoTemplate;

    public BookService(BookRepository bookRepository,
                       MongoTemplate mongoTemplate,
                       TopicService topicService,
                       AuthorService authorService, EditorService editorService) {
        super(bookRepository);
        this.bookRepository = bookRepository;
        this.mongoTemplate = mongoTemplate;
        this.authorService = authorService;
        this.editorService = editorService;
        this.topicService = topicService;
    }

    public PageResultDTO findAll(BookDTO bookDTO, Pageable pageable) {
        PageResultDTO<BookDTO> resultDTO = new PageResultDTO<>();

        Query query = new Query();
        query.limit(pageable.getPageSize());
        query.skip(pageable.getPageNumber() * pageable.getPageSize());
        query.with(pageable.getSort());

        if (bookDTO.getTitle() != null && !bookDTO.getTitle().isEmpty())
            query.addCriteria(where("title").regex(bookDTO.getTitle()));
        if (bookDTO.getSubTitle() != null && !bookDTO.getSubTitle().isEmpty())
            query.addCriteria(where("subTitle").regex(bookDTO.getSubTitle()));
        if (bookDTO.getCity() != null && !bookDTO.getCity().isEmpty())
            query.addCriteria(where("city").regex(bookDTO.getCity()));
        if (bookDTO.getEditionDate() != null && !bookDTO.getEditionDate().isEmpty())
            query.addCriteria(where("editionDate").is(bookDTO.getEditionDate()));
        if (bookDTO.getEditor() != null)
            query.addCriteria(where("editor.name").regex(bookDTO.getEditor().getName()));

        Page<Book> books = new PageImpl<>(mongoTemplate.find(query, Book.class));
        resultDTO.setElements(books.stream().map(BookDTO::new).collect(Collectors.toList()));
        resultDTO.setTotal(mongoTemplate.count(query, Book.class));
        return resultDTO;
    }

    public Optional<BookDTO> createBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setSubTitle(bookDTO.getSubTitle());
        book.setCity(bookDTO.getCity());

        List<Author> authorList = bookDTO.getAuthorList().stream()
                .map(authorDTO -> authorService.findOne(authorDTO.getId()).get())
                .collect(Collectors.toList());
        book.setAuthorList(authorList);

        if (bookDTO.getEditor() != null) {
            book.setEditor(editorService.findOne(bookDTO.getEditor().getId()).get());
        }

        book.setEditionDate(bookDTO.getEditionDate());
        book.setPages(bookDTO.getPages());
        book.setSize(bookDTO.getSize());
        book.setIsbn(bookDTO.getIsbn());

        if (bookDTO.getTopic() != null) {
            book.setTopic(topicService.findOne(bookDTO.getTopic().getId()).get());
        }

        book.setSalePrice(bookDTO.getSalePrice());
        book.setCoin(bookDTO.getCoin());
        book.setImage(bookDTO.getImage());
        book.setEdition(bookDTO.getEdition());
//        book.setEditorial();
//        book.setDescriptorList();
        book.setVisit(bookDTO.getVisit());
        book.setCreatedBy(bookDTO.getCreatedBy());
        book.setCreatedDate(bookDTO.getCreatedDate());
        book.setLastModifiedBy(bookDTO.getLastModifiedBy());
        book.setLastModifiedDate(bookDTO.getLastModifiedDate());

        log.debug("Created Information for Book: {}", book);
        return Optional.of(bookRepository.save(book))
                .map(BookDTO::new);
    }

    /**
     * Update all information for a specific book, and return the modified book.
     *
     * @param bookDTO book to update
     * @return updated book
     */
    public Optional<BookDTO> updateBook(BookDTO bookDTO) {
        return Optional.of(bookRepository
                .findById(bookDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(book -> {
                    book.setTitle(bookDTO.getTitle());
                    book.setSubTitle(bookDTO.getSubTitle());
                    book.setCity(bookDTO.getCity());

                    List<Author> authorList = bookDTO.getAuthorList().stream()
                            .map(authorDTO -> authorService.findOne(authorDTO.getId()).get())
                            .collect(Collectors.toList());
                    book.setAuthorList(authorList);

                    if (bookDTO.getEditor() != null) {
                        book.setEditor(editorService.findOne(bookDTO.getEditor().getId()).get());
                    }
                    book.setEditionDate(bookDTO.getEditionDate());
                    book.setPages(bookDTO.getPages());
                    book.setSize(bookDTO.getSize());
                    book.setIsbn(bookDTO.getIsbn());

                    if (bookDTO.getTopic() != null) {
                        book.setTopic(topicService.findOne(bookDTO.getTopic().getId()).get());
                    }

                    book.setSalePrice(bookDTO.getSalePrice());
                    book.setCoin(bookDTO.getCoin());
                    book.setImage(bookDTO.getImage());
                    book.setEdition(bookDTO.getEdition());
//                  book.setEditorial();
//                  book.setDescriptorList();
                    book.setVisit(bookDTO.getVisit());
                    bookRepository.save(book);
                    log.debug("Changed Information for Book: {}", book);
                    return book;
                })
                .map(BookDTO::new);
    }

    public void delete(String id) {
        bookRepository.findById(id).ifPresent(book -> {
            bookRepository.delete(book);
            log.debug("Deleted Book: {}", book);
        });
    }
}
