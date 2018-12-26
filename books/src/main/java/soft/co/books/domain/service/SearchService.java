package soft.co.books.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soft.co.books.domain.collection.Author;
import soft.co.books.domain.collection.Book;
import soft.co.books.domain.collection.Editor;
import soft.co.books.domain.collection.Magazine;
import soft.co.books.domain.service.dto.BookDTO;
import soft.co.books.domain.service.dto.MagazineDTO;
import soft.co.books.domain.service.dto.PageResultDTO;
import soft.co.books.domain.service.dto.SearchDTO;

import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;


/**
 * Service class for search.
 */
@Service
@Transactional
public class SearchService {

    private MongoTemplate mongoTemplate;

    public SearchService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public PageResultDTO bookSearch(SearchDTO searchDTO, Pageable pageable) {
        PageResultDTO<BookDTO> resultDTO = new PageResultDTO<>();

        Query query = new Query();
        query.limit(pageable.getPageSize());
        query.skip(pageable.getPageNumber() * pageable.getPageSize());
        query.with(pageable.getSort());

        if (searchDTO.getAuthor() != null && !searchDTO.getAuthor().isEmpty()) {
            Query authorQuery = new Query(Criteria.where("fullName").regex(searchDTO.getAuthor().toLowerCase(), "i"));
            authorQuery.fields().include("_id");
            query.addCriteria(where("authorList").in(mongoTemplate.find(authorQuery, Author.class)));
        }
        if (searchDTO.getEditor() != null && !searchDTO.getEditor().isEmpty()) {
            Query editorQuery = new Query(Criteria.where("name").regex(searchDTO.getEditor().toLowerCase(), "i"));
            editorQuery.fields().include("_id");
            query.addCriteria(where("editor").in(mongoTemplate.find(editorQuery, Editor.class)));
        }
        if (searchDTO.getTitle() != null && !searchDTO.getTitle().isEmpty()) {
            query.addCriteria(where("title").regex(searchDTO.getTitle().toLowerCase(), "i"));
        }
        if (searchDTO.getCity() != null && !searchDTO.getCity().isEmpty()) {
            query.addCriteria(where("city").regex(searchDTO.getCity().toLowerCase(), "i"));
        }
        if (searchDTO.getEditionYear() != null && !searchDTO.getEditionYear().isEmpty()) {
            query.addCriteria(where("editionYear").is(searchDTO.getEditionYear()));
        }
        if (searchDTO.getDescriptors() != null && !searchDTO.getDescriptors().isEmpty()) {
            query.addCriteria(where("descriptorList").in(searchDTO.getDescriptors().split(",")));
        }
        if (searchDTO.getClassification() != null) {
            query.addCriteria(where("classification.id").is(searchDTO.getClassification().getId()));
        }
        if (searchDTO.getTopic() != null) {
            query.addCriteria(where("topic.id").is(searchDTO.getTopic().getId()));
        }

        Page<Book> books = new PageImpl<>(mongoTemplate.find(query, Book.class));
        resultDTO.setElements(books.stream().map(BookDTO::new).collect(Collectors.toList()));
        resultDTO.setTotal(mongoTemplate.count(query, Book.class));
        return resultDTO;
    }

    public PageResultDTO magazineSearch(SearchDTO searchDTO, Pageable pageable) {
        PageResultDTO<MagazineDTO> resultDTO = new PageResultDTO<>();

        Query query = new Query();
        query.limit(pageable.getPageSize());
        query.skip(pageable.getPageNumber() * pageable.getPageSize());
        query.with(pageable.getSort());

        if (searchDTO.getEditor() != null && !searchDTO.getEditor().isEmpty()) {
            Query editorQuery = new Query(Criteria.where("name").regex(searchDTO.getEditor().toLowerCase(), "i"));
            editorQuery.fields().include("_id");
            query.addCriteria(where("editor").in(mongoTemplate.find(editorQuery, Editor.class)));
        }
        if (searchDTO.getTitle() != null && !searchDTO.getTitle().isEmpty()) {
            query.addCriteria(where("title").regex(searchDTO.getTitle().toLowerCase(), "i"));
        }
        if (searchDTO.getCity() != null && !searchDTO.getCity().isEmpty()) {
            query.addCriteria(where("city").regex(searchDTO.getCity().toLowerCase(), "i"));
        }
        if (searchDTO.getTopic() != null) {
            query.addCriteria(where("topic.id").is(searchDTO.getTopic().getId()));
        }

        Page<Magazine> magazines = new PageImpl<>(mongoTemplate.find(query, Magazine.class));
        resultDTO.setElements(magazines.stream().map(MagazineDTO::new).collect(Collectors.toList()));
        resultDTO.setTotal(mongoTemplate.count(query, Book.class));
        return resultDTO;
    }
}
