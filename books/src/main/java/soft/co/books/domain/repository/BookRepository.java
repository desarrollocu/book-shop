package soft.co.books.domain.repository;


import soft.co.books.configuration.database.CustomBaseRepository;
import soft.co.books.domain.collection.Authority;
import soft.co.books.domain.collection.Book;

/**
 * Spring Data MongoDB repository for the Book entity.
 */
public interface BookRepository extends CustomBaseRepository<Book, String> {
}
