package soft.co.books.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soft.co.books.configuration.database.CustomBaseService;
import soft.co.books.domain.collection.Country;
import soft.co.books.domain.repository.CountryRepository;


/**
 * Service class for managing countries.
 */
@Service
@Transactional
public class CountryService extends CustomBaseService<Country, String> {

    private final Logger log = LoggerFactory.getLogger(CountryService.class);

    private final CountryRepository countryRepository;

    private MongoTemplate mongoTemplate;

    public CountryService(CountryRepository countryRepository, MongoTemplate mongoTemplate) {
        super(countryRepository);
        this.countryRepository = countryRepository;
        this.mongoTemplate = mongoTemplate;
    }
}
