package soft.co.books.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soft.co.books.configuration.database.CustomBaseService;
import soft.co.books.domain.collection.CustomError;
import soft.co.books.domain.repository.ErrorRepository;

/**
 * Service class for managing errors.
 */
@Service
@Transactional
public class ErrorService extends CustomBaseService<CustomError, String> {

    private final Logger log = LoggerFactory.getLogger(ErrorService.class);

    private final ErrorRepository errorRepository;

    private MongoTemplate mongoTemplate;

    public ErrorService(ErrorRepository errorRepository, MongoTemplate mongoTemplate) {
        super(errorRepository);
        this.errorRepository = errorRepository;
        this.mongoTemplate = mongoTemplate;
    }
}
