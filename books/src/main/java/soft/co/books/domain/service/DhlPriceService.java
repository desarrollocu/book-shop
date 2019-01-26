package soft.co.books.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soft.co.books.configuration.database.CustomBaseService;
import soft.co.books.domain.collection.DhlPrice;
import soft.co.books.domain.repository.DhlPriceRepository;


/**
 * Service class for managing DhlPrice.
 */
@Service
@Transactional
public class DhlPriceService extends CustomBaseService<DhlPrice, String> {

    private final Logger log = LoggerFactory.getLogger(DhlPriceService.class);

    private final DhlPriceRepository dhlPriceRepository;

    private MongoTemplate mongoTemplate;

    public DhlPriceService(DhlPriceRepository dhlPriceRepository,
                           MongoTemplate mongoTemplate) {
        super(dhlPriceRepository);
        this.dhlPriceRepository = dhlPriceRepository;
        this.mongoTemplate = mongoTemplate;
    }
}
