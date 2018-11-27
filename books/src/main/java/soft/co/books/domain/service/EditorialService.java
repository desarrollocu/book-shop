package soft.co.books.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import soft.co.books.configuration.database.CustomBaseService;
import soft.co.books.domain.collection.Editorial;
import soft.co.books.domain.repository.EditorialRepository;


/**
 * Service class for managing editorials.
 */
@Service
public class EditorialService extends CustomBaseService<Editorial, String> {

    private final Logger log = LoggerFactory.getLogger(EditorialService.class);

    private final EditorialRepository editorialRepository;

    public EditorialService(EditorialRepository editorialRepository) {
        super(editorialRepository);
        this.editorialRepository = editorialRepository;
    }
}
