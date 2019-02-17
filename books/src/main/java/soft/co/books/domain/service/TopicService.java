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
import soft.co.books.configuration.Constants;
import soft.co.books.configuration.database.CustomBaseService;
import soft.co.books.configuration.error.CustomizeException;
import soft.co.books.domain.collection.Book;
import soft.co.books.domain.collection.Magazine;
import soft.co.books.domain.collection.Topic;
import soft.co.books.domain.repository.TopicRepository;
import soft.co.books.domain.service.dto.PageResultDTO;
import soft.co.books.domain.service.dto.TopicDTO;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * Service class for managing topics.
 */
@Service
@Transactional
public class TopicService extends CustomBaseService<Topic, String> {

    private final Logger log = LoggerFactory.getLogger(TopicService.class);

    private final TopicRepository topicRepository;

    private MongoTemplate mongoTemplate;

    public TopicService(TopicRepository topicRepository, MongoTemplate mongoTemplate) {
        super(topicRepository);
        this.topicRepository = topicRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public PageResultDTO findAll(TopicDTO topicDTO, Pageable pageable) {
        PageResultDTO<TopicDTO> resultDTO = new PageResultDTO<>();

        Query query = new Query();
        query.limit(pageable.getPageSize());
        query.skip(pageable.getPageNumber() * pageable.getPageSize());
        query.with(pageable.getSort());

        if (topicDTO.getName() != null && !topicDTO.getName().isEmpty()) {
            Criteria criteria = new Criteria();
            criteria.orOperator(where("spanishName").regex(topicDTO.getName(), "i"),
                    where("englishName").regex(topicDTO.getName(), "i"));
            query.addCriteria(criteria);
        }

        Page<Topic> topics = new PageImpl<>(mongoTemplate.find(query, Topic.class));
        resultDTO.setElements(topics.stream().map(TopicDTO::new).collect(Collectors.toList()));
        resultDTO.setTotal(mongoTemplate.count(query, Topic.class));
        return resultDTO;
    }

    public Optional<TopicDTO> createTopic(TopicDTO topicDTO) {
        Topic topic = new Topic();
        topic.setEnglishName(topicDTO.getEnglishName());
        topic.setSpanishName(topicDTO.getSpanishName());
        topic.setId(topicDTO.getId());

        log.debug("Created Information for Topic: {}", topic);
        return Optional.of(topicRepository.save(topic))
                .map(TopicDTO::new);
    }

    /**
     * Update all information for a specific topic, and return the modified topic.
     *
     * @param topicDTO topic to update
     * @return updated topic
     */
    public Optional<TopicDTO> updateTopic(TopicDTO topicDTO) {
        return Optional.of(topicRepository
                .findById(topicDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(topic -> {
                    topic.setEnglishName(topicDTO.getEnglishName());
                    topic.setSpanishName(topicDTO.getSpanishName());
                    topicRepository.save(topic);
                    log.debug("Changed Information for Topic: {}", topic);
                    return topic;
                })
                .map(TopicDTO::new);
    }

    public void delete(String id) {
        Query query = new Query();
        query.addCriteria(where("topicList.id").is(id));
        if (mongoTemplate.count(query, Book.class) > 0 || mongoTemplate.count(query, Magazine.class) > 0) {
            throw new CustomizeException(Constants.ERR_DELETE);
        } else {
            topicRepository.findById(id).ifPresent(topic -> {
                topicRepository.delete(topic);
                log.debug("Deleted Topic: {}", topic);
            });
        }
    }
}
