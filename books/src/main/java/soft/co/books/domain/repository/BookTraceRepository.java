package soft.co.books.domain.repository;


import soft.co.books.configuration.database.CustomBaseRepository;
import soft.co.books.domain.collection.BookTrace;

/**
 * Spring Data MongoDB repository for the BookTrace entity.
 */
public interface BookTraceRepository extends CustomBaseRepository<BookTrace, String> {
}
