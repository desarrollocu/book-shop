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
import soft.co.books.domain.collection.Magazine;
import soft.co.books.domain.repository.MagazineRepository;
import soft.co.books.domain.service.dto.MagazineDTO;
import soft.co.books.domain.service.dto.PageResultDTO;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * Service class for managing magazines.
 */
@Service
@Transactional
public class MagazineService extends CustomBaseService<Magazine, String> {

    private final Logger log = LoggerFactory.getLogger(MagazineService.class);

    private final MagazineRepository magazineRepository;

    private final TopicService topicService;

    private final EditorService editorService;

    private MongoTemplate mongoTemplate;

    public MagazineService(MagazineRepository magazineRepository,
                           MongoTemplate mongoTemplate,
                           TopicService topicService, EditorService editorService) {
        super(magazineRepository);
        this.magazineRepository = magazineRepository;
        this.mongoTemplate = mongoTemplate;
        this.topicService = topicService;
        this.editorService = editorService;
    }

    public PageResultDTO findAll(MagazineDTO magazineDTO, Pageable pageable) {
        PageResultDTO<MagazineDTO> resultDTO = new PageResultDTO<>();

        Query query = new Query();
        query.limit(pageable.getPageSize());
        query.skip(pageable.getPageNumber() * pageable.getPageSize());
        query.with(pageable.getSort());

        if (magazineDTO.getToShow() != null) {
            if (magazineDTO.getToShow().isVal())
                query.addCriteria(where("toShow").is(true));
        }
        if (magazineDTO.getTitle() != null && !magazineDTO.getTitle().isEmpty())
            query.addCriteria(where("title").regex(magazineDTO.getTitle(), "i"));
        if (magazineDTO.getEditor() != null)
            query.addCriteria(where("editor.id").is(magazineDTO.getEditor().getId()));
        if (magazineDTO.getTopic() != null)
            query.addCriteria(where("topic.id").is(magazineDTO.getTopic().getId()));

        Page<Magazine> magazines = new PageImpl<>(mongoTemplate.find(query, Magazine.class));
        resultDTO.setElements(magazines.stream().map(MagazineDTO::new).collect(Collectors.toList()));
        resultDTO.setTotal(mongoTemplate.count(query, Magazine.class));
        return resultDTO;
    }

    public Optional<MagazineDTO> createMagazine(MagazineDTO magazineDTO) {
        Magazine magazine = new Magazine();
        magazine.setTitle(magazineDTO.getTitle());
        magazine.setCity(magazineDTO.getCity());
        if (magazineDTO.getEditor() != null) {
            magazine.setEditor(editorService.findOne(magazineDTO.getEditor().getId()).get());
        }
        if (magazineDTO.getTopic() != null) {
            magazine.setTopic(topicService.findOne(magazineDTO.getTopic().getId()).get());
        }
        magazine.setPublishYear(magazineDTO.getPublishYear());
        magazine.setToShow(magazineDTO.getToShow().isVal());
        magazine.setStockNumber(magazineDTO.getStockNumber());
        magazine.setFrequency(magazineDTO.getFrequency());
        magazine.setIsbn(magazineDTO.getIsbn());
        magazine.setSalePrice(magazineDTO.getSalePrice());
        magazine.setCoin(magazineDTO.getCoin());
        magazine.setImageUrl(magazineDTO.getImage());
        magazine.setVisit(magazineDTO.getVisit());

        log.debug("Created Information for Magazine: {}", magazine);
        return Optional.of(magazineRepository.save(magazine))
                .map(MagazineDTO::new);
    }

    /**
     * Update all information for a specific magazine, and return the modified magazine.
     *
     * @param magazineDTO magazine to update
     * @return updated magazine
     */
    public Optional<MagazineDTO> updateMagazine(MagazineDTO magazineDTO) {
        return Optional.of(magazineRepository
                .findById(magazineDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(magazine -> {
                    magazine.setTitle(magazineDTO.getTitle());
                    magazine.setCity(magazineDTO.getCity());
                    if (magazineDTO.getEditor() != null) {
                        magazine.setEditor(editorService.findOne(magazineDTO.getEditor().getId()).get());
                    }
                    if (magazineDTO.getTopic() != null) {
                        magazine.setTopic(topicService.findOne(magazineDTO.getTopic().getId()).get());
                    }
                    magazine.setPublishYear(magazineDTO.getPublishYear());
                    magazine.setToShow(magazineDTO.getToShow().isVal());
                    magazine.setStockNumber(magazineDTO.getStockNumber());
                    magazine.setFrequency(magazineDTO.getFrequency());
                    magazine.setIsbn(magazineDTO.getIsbn());
                    magazine.setSalePrice(magazineDTO.getSalePrice());
                    magazine.setCoin(magazineDTO.getCoin());
                    magazine.setImageUrl(magazineDTO.getImage());
                    magazine.setVisit(magazineDTO.getVisit());

                    magazineRepository.save(magazine);
                    log.debug("Changed Information for Magazine: {}", magazine);
                    return magazine;
                })
                .map(MagazineDTO::new);
    }

    public void delete(String id) {
        magazineRepository.findById(id).ifPresent(magazine -> {
            magazineRepository.delete(magazine);
            log.debug("Deleted Magazine: {}", magazine);
        });
    }
}
