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
import soft.co.books.configuration.Constants;
import soft.co.books.configuration.database.CustomBaseService;
import soft.co.books.configuration.error.CustomizeException;
import soft.co.books.domain.collection.Book;
import soft.co.books.domain.collection.Editor;
import soft.co.books.domain.collection.Magazine;
import soft.co.books.domain.repository.EditorRepository;
import soft.co.books.domain.service.dto.EditorDTO;
import soft.co.books.domain.service.dto.PageResultDTO;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * Service class for managing editors.
 */
@Service
@Transactional
public class EditorService extends CustomBaseService<Editor, String> {

    private final Logger log = LoggerFactory.getLogger(EditorService.class);

    private final EditorRepository editorRepository;

    private final CountryService countryService;

    private MongoTemplate mongoTemplate;

    public EditorService(EditorRepository editorRepository,
                         CountryService countryService,
                         MongoTemplate mongoTemplate) {
        super(editorRepository);
        this.countryService = countryService;
        this.editorRepository = editorRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public PageResultDTO findAll(EditorDTO editorDTO, Pageable pageable) {
        PageResultDTO<EditorDTO> resultDTO = new PageResultDTO<>();

        Query query = new Query();
        query.limit(pageable.getPageSize());
        query.skip(pageable.getPageNumber() * pageable.getPageSize());
        query.with(pageable.getSort());

        if (editorDTO.getName() != null && !editorDTO.getName().isEmpty())
            query.addCriteria(where("name").regex(editorDTO.getName(), "i"));
        if (editorDTO.getCountry() != null)
            query.addCriteria(where("country.id").is(editorDTO.getCountry().getId()));
        if (editorDTO.getCity() != null && !editorDTO.getCity().isEmpty())
            query.addCriteria(where("city").regex(editorDTO.getCity(), "i"));

        Page<Editor> editors = new PageImpl<>(mongoTemplate.find(query, Editor.class));
        resultDTO.setElements(editors.stream().map(EditorDTO::new).collect(Collectors.toList()));
        resultDTO.setTotal(mongoTemplate.count(query, Editor.class));
        return resultDTO;
    }

    public Optional<EditorDTO> createEditor(EditorDTO editorDTO) {
        Editor editor = new Editor();
        editor.setName(editorDTO.getName());
        editor.setCity(editorDTO.getCity());
        editor.setCountry(countryService.findOne(editorDTO.getCountry().getId()).get());
        editor.setId(editorDTO.getId());

        log.debug("Created Information for Editor: {}", editor);
        return Optional.of(editorRepository.save(editor))
                .map(EditorDTO::new);
    }

    /**
     * Update all information for a specific editor, and return the modified user.
     *
     * @param editorDTO editor to update
     * @return updated editor
     */
    public Optional<EditorDTO> updateEditor(EditorDTO editorDTO) {
        return Optional.of(editorRepository
                .findById(editorDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(editor -> {
                    editor.setName(editorDTO.getName());
                    editor.setCity(editorDTO.getCity());
                    editor.setCountry(countryService.findOne(editorDTO.getCountry().getId()).get());
                    editorRepository.save(editor);
                    log.debug("Changed Information for Editor: {}", editor);
                    return editor;
                })
                .map(EditorDTO::new);
    }

    public void delete(String id) {
        Query query = new Query();
        query.addCriteria(where("editor.id").is(id));
        if (mongoTemplate.count(query, Book.class) > 0 || mongoTemplate.count(query, Magazine.class) > 0) {
            throw new CustomizeException(Constants.ERR_DELETE);
        } else {
            editorRepository.findById(id).ifPresent(editor -> {
                editorRepository.delete(editor);
                log.debug("Deleted Editor: {}", editor);
            });
        }
    }
}
