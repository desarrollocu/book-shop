package soft.co.books.domain.repository;


import soft.co.books.configuration.database.CustomBaseRepository;
import soft.co.books.domain.collection.Topic;

/**
 * Spring Data MongoDB repository for the Topic entity.
 */
public interface TopicRepository extends CustomBaseRepository<Topic, String> {
}
