package soft.co.books.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soft.co.books.configuration.database.CustomBaseService;
import soft.co.books.configuration.storage.StorageService;
import soft.co.books.domain.collection.Author;
import soft.co.books.domain.collection.Book;
import soft.co.books.domain.collection.Editor;
import soft.co.books.domain.collection.Topic;
import soft.co.books.domain.repository.BookRepository;
import soft.co.books.domain.service.dto.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

    private final ClassificationService classificationService;

    private final StorageService storageService;

    private final BookTraceService bookTraceService;

    private MongoTemplate mongoTemplate;

    public BookService(BookRepository bookRepository,
                       MongoTemplate mongoTemplate,
                       TopicService topicService,
                       AuthorService authorService,
                       StorageService storageService,
                       BookTraceService bookTraceService,
                       EditorService editorService, ClassificationService classificationService) {
        super(bookRepository);
        this.bookRepository = bookRepository;
        this.mongoTemplate = mongoTemplate;
        this.authorService = authorService;
        this.editorService = editorService;
        this.topicService = topicService;
        this.classificationService = classificationService;
        this.storageService = storageService;
        this.bookTraceService = bookTraceService;
    }

    public PageResultDTO findAll(BookDTO bookDTO, Pageable pageable) {
        PageResultDTO<BookDTO> resultDTO = new PageResultDTO<>();

        Query query = new Query();
        query.limit(pageable.getPageSize());
        query.skip(pageable.getPageNumber() * pageable.getPageSize());
        query.with(pageable.getSort());

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

        List<Editor> editorList = bookDTO.getEditorList().stream()
                .map(editorDTO -> editorService.findOne(editorDTO.getId()).get())
                .collect(Collectors.toList());
        book.setEditorList(editorList);

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

        log.debug("Created Information for Book: {}", book);
        Book result = bookRepository.save(book);

        bookTraceService.createBookTrace(result, "Create", null);
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

                    List<Topic> topicList = bookDTO.getTopicList().stream()
                            .map(topicDTO -> topicService.findOne(topicDTO.getId()).get())
                            .collect(Collectors.toList());
                    book.setTopicList(topicList);

                    List<Editor> editorList = bookDTO.getEditorList().stream()
                            .map(editorDTO -> editorService.findOne(editorDTO.getId()).get())
                            .collect(Collectors.toList());
                    book.setEditorList(editorList);

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
                    Book result = bookRepository.save(book);
                    log.debug("Changed Information for Book: {}", book);

                    bookTraceService.createBookTrace(result, "Update", null);
                    return book;
                })
                .map(BookDTO::new);
    }

    public void delete(String id) {
        bookRepository.findById(id).ifPresent(book -> {
            storageService.deleteById(book.getId());
            bookRepository.delete(book);
            log.debug("Deleted Book: {}", book);

            bookTraceService.createBookTrace(book, "Delete", null);
        });
    }
}
