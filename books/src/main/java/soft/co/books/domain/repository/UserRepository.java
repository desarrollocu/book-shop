package soft.co.books.domain.repository;


import soft.co.books.configuration.database.CustomBaseRepository;
import soft.co.books.domain.collection.User;

import java.util.Optional;

/**
 * Spring Data MongoDB repository for the User entity.
 */
public interface UserRepository extends CustomBaseRepository<User, String> {

    Optional<User> findOneByUserName(String userName);
}
