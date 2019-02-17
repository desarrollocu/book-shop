package soft.co.books.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soft.co.books.configuration.database.CustomBaseService;
import soft.co.books.configuration.security.other.SecurityUtils;
import soft.co.books.configuration.storage.StorageService;
import soft.co.books.domain.collection.*;
import soft.co.books.domain.collection.data.Trace;
import soft.co.books.domain.repository.BookRepository;
import soft.co.books.domain.service.dto.*;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;


/**
 * Service class for managing books.
 */
@Service
@Transactional
public class BookService extends CustomBaseService<Book, String> {

    private final Logger log = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;

    private final AuthorService authorService;

    private final EditorService editorService;

    private final TopicService topicService;

    private final UserService userService;

    private final ClassificationService classificationService;

    private final StorageService storageService;

    private MongoTemplate mongoTemplate;

    public BookService(BookRepository bookRepository,
                       MongoTemplate mongoTemplate,
                       TopicService topicService,
                       AuthorService authorService,
                       StorageService storageService,
                       EditorService editorService,
                       UserService userService,
                       ClassificationService classificationService) {
        super(bookRepository);
        this.bookRepository = bookRepository;
        this.mongoTemplate = mongoTemplate;
        this.authorService = authorService;
        this.editorService = editorService;
        this.topicService = topicService;
        this.userService = userService;
        this.classificationService = classificationService;
        this.storageService = storageService;
    }

    public Book findByIdAndVisible(String id, boolean visible) {
        return bookRepository.findByIdAndVisible(id, visible);
    }

    public PageResultDTO findAll(BookDTO bookDTO, Pageable pageable) {
        PageResultDTO<BookDTO> resultDTO = new PageResultDTO<>();

        Query query = new Query();
        query.limit(pageable.getPageSize());
        query.skip(pageable.getPageNumber() * pageable.getPageSize());
        query.with(pageable.getSort());
        query.fields().exclude("traceList");

        if (bookDTO.getToShow() != null) {
            if (bookDTO.getToShow().isVal())
                query.addCriteria(where("toShow").is(true));
        }
        if (bookDTO.getTitle() != null && !bookDTO.getTitle().isEmpty())
            query.addCriteria(where("title").regex(bookDTO.getTitle(), "i"));
        if (bookDTO.getSubTitle() != null && !bookDTO.getSubTitle().isEmpty())
            query.addCriteria(where("subTitle").regex(bookDTO.getSubTitle(), "i"));
        if (bookDTO.getEditorList() != null) {
            if (!bookDTO.getEditorList().isEmpty()) {
                query.addCriteria(where("editorList").in(bookDTO.getEditorList().stream()
                        .map(EditorDTO::getId)
                        .collect(Collectors.toList())));
            }
        }
        if (bookDTO.getTopicList() != null) {
            if (!bookDTO.getTopicList().isEmpty()) {
                query.addCriteria(where("topicList").in(bookDTO.getTopicList().stream()
                        .map(TopicDTO::getId)
                        .collect(Collectors.toList())));
            }
        }
        if (bookDTO.getAuthorList() != null) {
            if (!bookDTO.getAuthorList().isEmpty()) {
                query.addCriteria(where("authorList").in(bookDTO.getAuthorList().stream()
                        .map(AuthorDTO::getId)
                        .collect(Collectors.toList())));
            }
        }

        query.addCriteria(where("visible").is(true));

        Page<Book> books = new PageImpl<>(mongoTemplate.find(query, Book.class));
        resultDTO.setElements(books.stream().map(BookDTO::new).collect(Collectors.toList()));
        resultDTO.setTotal(mongoTemplate.count(query, Book.class));
        return resultDTO;
    }

    public Optional<BookDTO> createBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setTitle(bookDTO.getTitle());
        book.setSubTitle(bookDTO.getSubTitle());
        book.setComments(bookDTO.getComments());
        book.setWeight(bookDTO.getWeight());
        book.setVisible(true);

        if (bookDTO.getDescriptors() != null) {
            if (bookDTO.getDescriptors() != "") {
                String[] temp = bookDTO.getDescriptors().split(",");
                book.setDescriptorList(Arrays.stream(temp).collect(Collectors.toList()));
            }
        }

        List<Author> authorList = bookDTO.getAuthorList().stream()
                .map(authorDTO -> authorService.findOne(authorDTO.getId()).get())
                .collect(Collectors.toList());
        book.setAuthorList(authorList);
        if (authorList.size() > 0) {
            Author author = authorList.get(0);
            AuthorBook authorBook = new AuthorBook();
            authorBook.setId(author.getId());
            authorBook.setAuthorName(author.getFirstName() + " " + author.getLastName());
            book.setAuthorBook(authorBook);
        }

        List<Editor> editorList = bookDTO.getEditorList().stream()
                .map(editorDTO -> editorService.findOne(editorDTO.getId()).get())
                .collect(Collectors.toList());
        book.setEditorList(editorList);
        if (editorList.size() > 0) {
            Editor editor = editorList.get(0);
            EditorDocument editorDocument = new EditorDocument();
            editorDocument.setId(editor.getId());
            editorDocument.setName(editor.getName());
            book.setEditorDocument(editorDocument);
        }

        List<Topic> topicList = bookDTO.getTopicList().stream()
                .map(topicDTO -> topicService.findOne(topicDTO.getId()).get())
                .collect(Collectors.toList());
        book.setTopicList(topicList);

        if (bookDTO.getClassification() != null) {
            book.setClassification(classificationService.findOne(bookDTO.getClassification().getId()).get());
        }

        book.setEditionYear(bookDTO.getEditionYear());
        book.setPages(bookDTO.getPages());
        book.setSize(bookDTO.getSize());
        book.setIsbn(bookDTO.getIsbn());
        book.setSalePrice(bookDTO.getSalePrice());
        if (bookDTO.getToShow() != null)
            book.setToShow(bookDTO.getToShow().isVal());
        else
            book.setToShow(false);
        book.setStockNumber(bookDTO.getStockNumber());
        book.setCoin(bookDTO.getCoin());
        book.setImageUrl(bookDTO.getImage());
        book.setVisit(bookDTO.getVisit());

        Trace trace = new Trace();
        String userName = SecurityUtils.getCurrentUserLogin().get();
        if (userName != null) {
            User user = userService.findOneByUserName(userName).get();
            book.setCreatedBy(user);
            trace.setUser(user);
        }

        trace.setAction("Create");
        trace.setDate(new SimpleDateFormat("yyyy-MM-dd").format(Date.from(Instant.now())));
        book.getTraceList().add(trace);

        Book result = bookRepository.save(book);
        log.debug("Created Information for Book: {}", result);
        return Optional.of(result)
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
                    book.setComments(bookDTO.getComments());
                    book.setWeight(bookDTO.getWeight());

                    List<Author> authorList = bookDTO.getAuthorList().stream()
                            .map(authorDTO -> authorService.findOne(authorDTO.getId()).get())
                            .collect(Collectors.toList());
                    book.setAuthorList(authorList);
                    if (authorList.size() > 0) {
                        Author author = authorList.get(0);
                        AuthorBook authorBook = new AuthorBook();
                        authorBook.setId(author.getId());
                        authorBook.setAuthorName(author.getFirstName() + " " + author.getLastName());
                        book.setAuthorBook(authorBook);
                    }

                    List<Topic> topicList = bookDTO.getTopicList().stream()
                            .map(topicDTO -> topicService.findOne(topicDTO.getId()).get())
                            .collect(Collectors.toList());
                    book.setTopicList(topicList);

                    List<Editor> editorList = bookDTO.getEditorList().stream()
                            .map(editorDTO -> editorService.findOne(editorDTO.getId()).get())
                            .collect(Collectors.toList());
                    book.setEditorList(editorList);
                    if (editorList.size() > 0) {
                        Editor editor = editorList.get(0);
                        EditorDocument editorDocument = new EditorDocument();
                        editorDocument.setId(editor.getId());
                        editorDocument.setName(editor.getName());
                        book.setEditorDocument(editorDocument);
                    }

                    if (bookDTO.getClassification() != null) {
                        book.setClassification(classificationService.findOne(bookDTO.getClassification().getId()).get());
                    }

                    book.setEditionYear(bookDTO.getEditionYear());
                    book.setPages(bookDTO.getPages());
                    book.setSize(bookDTO.getSize());
                    book.setIsbn(bookDTO.getIsbn());
                    book.setSalePrice(bookDTO.getSalePrice());
                    book.setCoin(bookDTO.getCoin());
                    book.setStockNumber(bookDTO.getStockNumber());
                    book.setVisit(bookDTO.getVisit());
                    book.setImageUrl(bookDTO.getImage());
                    if (bookDTO.getImage() == null)
                        storageService.deleteById(book.getId());

                    if (bookDTO.getToShow() != null)
                        book.setToShow(bookDTO.getToShow().isVal());
                    else
                        book.setToShow(false);

                    if (bookDTO.getDescriptors() != null) {
                        if (bookDTO.getDescriptors() != "") {
                            String[] temp = bookDTO.getDescriptors().split(",");
                            book.setDescriptorList(Arrays.stream(temp).collect(Collectors.toList()));
                        }
                    }

                    Trace trace = new Trace();
                    String userName = SecurityUtils.getCurrentUserLogin().get();
                    if (userName != null) {
                        book.setLastModifiedDate(new SimpleDateFormat("yyyy-MM-dd")
                                .format(Date.from(Instant.now())));

                        User user = userService.findOneByUserName(userName).get();
                        book.setLastModifiedBy(user);
                        trace.setUser(user);
                    }

                    trace.setAction("Update");
                    trace.setDate(new SimpleDateFormat("yyyy-MM-dd").format(Date.from(Instant.now())));
                    book.getTraceList().add(trace);

                    bookRepository.save(book);
                    log.debug("Changed Information for Book: {}", book);
                    return book;
                })
                .map(BookDTO::new);
    }

    public void delete(String id) {
        bookRepository.findById(id).ifPresent(book -> {
            book.setVisible(false);
//            storageService.deleteById(book.getId());
            Trace trace = new Trace();
            trace.setAction("Delete");
            trace.setDate(new SimpleDateFormat("yyyy-MM-dd").format(Date.from(Instant.now())));
            String userName = SecurityUtils.getCurrentUserLogin().get();
            if (userName != null) {
                book.setLastModifiedDate(new SimpleDateFormat("yyyy-MM-dd")
                        .format(Date.from(Instant.now())));

                User user = userService.findOneByUserName(userName).get();
                book.setLastModifiedBy(user);
                trace.setUser(user);
            }

            book.getTraceList().add(trace);
            bookRepository.save(book);
            log.debug("Deleted Book: {}", book);
        });
    }

    public List<BookTraceDTO> traceList(String id) {
        List<BookTraceDTO> dtoList = new ArrayList<>();
        Query query = new Query();
        query.fields().include("traceList");
        query.addCriteria(Criteria.where("id").is(id));
        Book book = mongoTemplate.findOne(query, Book.class);
        book.getTraceList().forEach(trace -> dtoList.add(new BookTraceDTO(trace)));
        return dtoList;
    }
}
