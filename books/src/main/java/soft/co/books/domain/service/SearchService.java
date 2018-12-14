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
import soft.co.books.domain.collection.Book;
import soft.co.books.domain.repository.BookRepository;
import soft.co.books.domain.service.dto.BookDTO;
import soft.co.books.domain.service.dto.PageResultDTO;

import java.util.stream.Collectors;


/**
 * Service class for searchs.
 */
@Service
public class SearchService extends CustomBaseService<Book, String> {

    private final Logger log = LoggerFactory.getLogger(SearchService.class);

    private final BookRepository bookRepository;

    private final AuthorService authorService;

    private final EditorService editorService;

    private final TopicService topicService;

    private final ClassificationService classificationService;

    private MongoTemplate mongoTemplate;

    public SearchService(BookRepository bookRepository,
                         MongoTemplate mongoTemplate,
                         TopicService topicService,
                         AuthorService authorService,
                         EditorService editorService, ClassificationService classificationService) {
        super(bookRepository);
        this.bookRepository = bookRepository;
        this.mongoTemplate = mongoTemplate;
        this.authorService = authorService;
        this.editorService = editorService;
        this.topicService = topicService;
        this.classificationService = classificationService;
    }

    public PageResultDTO generalSearch(BookDTO bookDTO, Pageable pageable) {
        PageResultDTO<BookDTO> resultDTO = new PageResultDTO<>();

        Query query = new Query();
        query.limit(pageable.getPageSize());
        query.skip(pageable.getPageNumber() * pageable.getPageSize());
        query.with(pageable.getSort());

        Page<Book> books = new PageImpl<>(mongoTemplate.find(query, Book.class));
        resultDTO.setElements(books.stream().map(BookDTO::new).collect(Collectors.toList()));
        resultDTO.setTotal(mongoTemplate.count(query, Book.class));
        return resultDTO;
    }
}
