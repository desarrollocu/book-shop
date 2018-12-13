package soft.co.books.domain.repository;


import soft.co.books.configuration.database.CustomBaseRepository;
import soft.co.books.domain.collection.Magazine;

/**
 * Spring Data MongoDB repository for the Magazine entity.
 */
public interface MagazineRepository extends CustomBaseRepository<Magazine, String> {
}
