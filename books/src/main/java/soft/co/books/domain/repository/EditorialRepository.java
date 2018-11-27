package soft.co.books.domain.repository;


import soft.co.books.configuration.database.CustomBaseRepository;
import soft.co.books.domain.collection.Editorial;

/**
 * Spring Data MongoDB repository for the Authority entity.
 */
public interface EditorialRepository extends CustomBaseRepository<Editorial, String> {
}
