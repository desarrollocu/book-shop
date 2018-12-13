package soft.co.books.domain.repository;


import soft.co.books.configuration.database.CustomBaseRepository;
import soft.co.books.domain.collection.Classification;

/**
 * Spring Data MongoDB repository for the Classification entity.
 */
public interface ClassificationRepository extends CustomBaseRepository<Classification, String> {
}
