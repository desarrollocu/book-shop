package soft.co.books.domain.repository;


import soft.co.books.configuration.database.CustomBaseRepository;
import soft.co.books.domain.collection.CustomError;

/**
 * Spring Data MongoDB repository for the Error entity.
 */
public interface ErrorRepository extends CustomBaseRepository<CustomError, String> {
}
