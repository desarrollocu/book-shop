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
import soft.co.books.domain.collection.Classification;
import soft.co.books.domain.repository.ClassificationRepository;
import soft.co.books.domain.service.dto.ClassificationDTO;
import soft.co.books.domain.service.dto.PageResultDTO;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * Service class for managing classifications.
 */
@Service
@Transactional
public class ClassificationService extends CustomBaseService<Classification, String> {

    private final Logger log = LoggerFactory.getLogger(ClassificationService.class);

    private final ClassificationRepository classificationRepository;

    private MongoTemplate mongoTemplate;

    public ClassificationService(ClassificationRepository classificationRepository, MongoTemplate mongoTemplate) {
        super(classificationRepository);
        this.classificationRepository = classificationRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public PageResultDTO findAll(ClassificationDTO classificationDTO, Pageable pageable) {
        PageResultDTO<ClassificationDTO> resultDTO = new PageResultDTO<>();

        Query query = new Query();
        query.limit(pageable.getPageSize());
        query.skip(pageable.getPageNumber() * pageable.getPageSize());
        query.with(pageable.getSort());

        if (classificationDTO.getName() != null && !classificationDTO.getName().isEmpty())
            query.addCriteria(where("name").regex(classificationDTO.getName(), "i"));

        Page<Classification> classifications = new PageImpl<>(mongoTemplate.find(query, Classification.class));
        resultDTO.setElements(classifications.stream().map(ClassificationDTO::new).collect(Collectors.toList()));
        resultDTO.setTotal(mongoTemplate.count(query, Classification.class));
        return resultDTO;
    }

    public Optional<ClassificationDTO> createClassification(ClassificationDTO classificationDTO) {
        Classification classification = new Classification();
        classification.setName(classificationDTO.getName());
        classification.setId(classificationDTO.getId());

        log.debug("Created Information for Classification: {}", classification);
        return Optional.of(classificationRepository.save(classification))
                .map(ClassificationDTO::new);
    }

    /**
     * Update all information for a specific classification, and return the modified classification.
     *
     * @param classificationDTO classification to update
     * @return updated classification
     */
    public Optional<ClassificationDTO> updateClassification(ClassificationDTO classificationDTO) {
        return Optional.of(classificationRepository
                .findById(classificationDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(classification -> {
                    classification.setName(classificationDTO.getName());
                    classificationRepository.save(classification);
                    log.debug("Changed Information for Classification: {}", classification);
                    return classification;
                })
                .map(ClassificationDTO::new);
    }

    public void delete(String id) {
        Query query = new Query();
        query.addCriteria(where("classification.id").is(id));
        if (mongoTemplate.count(query, Book.class) > 0) {
            throw new CustomizeException(Constants.ERR_DELETE);
        } else {
            classificationRepository.findById(id).ifPresent(topic -> {
                classificationRepository.delete(topic);
                log.debug("Deleted Editor: {}", topic);
            });
        }
    }
}
