package soft.co.books.configuration.database;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Spring Data MongoDB custom class for the all repositories.
 */
@NoRepositoryBean
//public interface CustomBaseRepository<T, ID> extends MongoRepository<T, ID> {
public interface CustomBaseRepository<T, ID> extends MongoRepository<T, ID> {
}
