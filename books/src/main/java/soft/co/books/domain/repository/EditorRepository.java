package soft.co.books.domain.repository;


import soft.co.books.configuration.database.CustomBaseRepository;
import soft.co.books.domain.collection.Editor;

/**
 * Spring Data MongoDB repository for the Editor entity.
 */
public interface EditorRepository extends CustomBaseRepository<Editor, String> {
}
