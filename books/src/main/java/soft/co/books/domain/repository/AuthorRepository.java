package soft.co.books.domain.repository;


import soft.co.books.configuration.database.CustomBaseRepository;
import soft.co.books.domain.collection.Author;

import java.util.List;

/**
 * Spring Data MongoDB repository for the Author entity.
 */
public interface AuthorRepository extends CustomBaseRepository<Author, String> {

    List<Author> findByFullNameIsLike(String fullName);
}
