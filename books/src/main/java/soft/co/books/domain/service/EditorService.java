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
import soft.co.books.domain.collection.Editor;
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
public class EditorService extends CustomBaseService<Editor, String> {

    private final Logger log = LoggerFactory.getLogger(EditorService.class);

    private final EditorRepository editorRepository;

    private MongoTemplate mongoTemplate;

    public EditorService(EditorRepository editorRepository, MongoTemplate mongoTemplate) {
        super(editorRepository);
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
            query.addCriteria(where("name").regex(editorDTO.getName()));
        if (editorDTO.getCity() != null && !editorDTO.getCity().isEmpty())
            query.addCriteria(where("city").regex(editorDTO.getCity()));

        Page<Editor> editors = new PageImpl<>(mongoTemplate.find(query, Editor.class));
        resultDTO.setElements(editors.stream().map(EditorDTO::new).collect(Collectors.toList()));
        resultDTO.setTotal(mongoTemplate.count(query, Editor.class));
        return resultDTO;
    }

    public Optional<EditorDTO> createEditor(EditorDTO editorDTO) {
        Editor editor = new Editor();
        editor.setName(editorDTO.getName());
        editor.setCity(editorDTO.getCity());
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
                    editorRepository.save(editor);
                    log.debug("Changed Information for Editor: {}", editor);
                    return editor;
                })
                .map(EditorDTO::new);
    }

    public void delete(String id) {
        editorRepository.findById(id).ifPresent(editor -> {
            editorRepository.delete(editor);
            log.debug("Deleted Editor: {}", editor);
        });
    }
}
