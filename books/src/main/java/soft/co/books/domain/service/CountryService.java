package soft.co.books.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import soft.co.books.configuration.database.CustomBaseService;
import soft.co.books.domain.collection.Country;
import soft.co.books.domain.repository.CountryRepository;


/**
 * Service class for managing Countries.
 */
@Service
public class CountryService extends CustomBaseService<Country, String> {

    private final Logger log = LoggerFactory.getLogger(CountryService.class);

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        super(countryRepository);
        this.countryRepository = countryRepository;
    }
}
