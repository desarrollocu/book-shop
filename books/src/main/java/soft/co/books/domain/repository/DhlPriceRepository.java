package soft.co.books.domain.repository;


import soft.co.books.configuration.database.CustomBaseRepository;
import soft.co.books.domain.collection.DhlPrice;

/**
 * Spring Data MongoDB repository for the DhlPrice entity.
 */
public interface DhlPriceRepository extends CustomBaseRepository<DhlPrice, String> {
}
