package soft.co.books.resource;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soft.co.books.domain.collection.Country;
import soft.co.books.domain.service.CountryService;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api(description = "Country operations")
public class CountryResource {

    private final CountryService countryService;

    public CountryResource(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping("/allCountries")
    public List<Country> allCountries() {
        return countryService.findAll();
    }
}
