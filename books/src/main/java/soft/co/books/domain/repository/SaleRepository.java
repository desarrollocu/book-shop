package soft.co.books.domain.repository;


import soft.co.books.configuration.database.CustomBaseRepository;
import soft.co.books.domain.collection.Sale;

/**
 * Spring Data MongoDB repository for the Sale entity.
 */
public interface SaleRepository extends CustomBaseRepository<Sale, String> {
}
