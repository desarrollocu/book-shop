package soft.co.books.domain.repository;


import soft.co.books.configuration.database.CustomBaseRepository;
import soft.co.books.domain.collection.UIData;

/**
 * Spring Data MongoDB repository for the UIData entity.
 */
public interface UIDataRepository extends CustomBaseRepository<UIData, String> {
}
