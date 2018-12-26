package soft.co.books.domain.repository;


import soft.co.books.configuration.database.CustomBaseRepository;
import soft.co.books.domain.collection.Country;

/**
 * Spring Data MongoDB repository for the Country entity.
 */
public interface CountryRepository extends CustomBaseRepository<Country, String> {
}
