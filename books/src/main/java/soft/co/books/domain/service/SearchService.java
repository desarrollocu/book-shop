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
import soft.co.books.domain.service.dto.*;
import soft.co.books.domain.service.temporal.Temp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        query.fields().exclude("traceList");
        query.fields().exclude("createdBy");
        query.fields().exclude("lastModifiedBy");

        if (searchDTO.getAuthor() != null && !searchDTO.getAuthor().isEmpty()) {
            Query authorQuery = new Query(Criteria.where("fullName").regex(searchDTO.getAuthor().toLowerCase(), "i"));
            authorQuery.fields().include("_id");
            query.addCriteria(where("authorList").in(mongoTemplate.find(authorQuery, Author.class)));
        }

        boolean editorFlag = false;
        Set<Editor> editorList = new HashSet<>();
        Set<Editor> editorNameList = null;
        Set<Editor> editorCityList = null;
        Set<Editor> editorCountryList = null;
        if (searchDTO.getEditor() != null && !searchDTO.getEditor().isEmpty()) {
            Query editorQuery = new Query(Criteria.where("name").regex(searchDTO.getEditor().toLowerCase(), "i"));
            editorQuery.fields().include("_id");
            editorNameList = new HashSet<>();
            editorNameList.addAll(mongoTemplate.find(editorQuery, Editor.class));
            editorList.addAll(editorNameList);
            editorFlag = true;
        }
        if (searchDTO.getEditionCity() != null && !searchDTO.getEditionCity().isEmpty()) {
            Query editionCityQuery = new Query(Criteria.where("city").regex(searchDTO.getEditionCity().toLowerCase(), "i"));
            editionCityQuery.fields().include("_id");
            editorCityList = new HashSet<>();
            editorCityList.addAll(mongoTemplate.find(editionCityQuery, Editor.class));
            editorList.addAll(editorCityList);
            editorFlag = true;
        }
        if (searchDTO.getEditionCountry() != null) {
            Query editionCountryQuery = new Query(Criteria.where("country.id").is(searchDTO.getEditionCountry().getId()));
            editionCountryQuery.fields().include("_id");
            editorCountryList = new HashSet<>();
            editorCountryList.addAll(mongoTemplate.find(editionCountryQuery, Editor.class));
            editorList.addAll(editorCountryList);
            editorFlag = true;
        }

        if (editorNameList != null) {
            Set<Editor> finalEditorNameList = editorNameList;
            editorList.removeIf(editor -> !finalEditorNameList.contains(editor));
        }
        if (editorCityList != null) {
            Set<Editor> finalEditorCityList = editorCityList;
            editorList.removeIf(editor -> !finalEditorCityList.contains(editor));
        }
        if (editorCountryList != null) {
            Set<Editor> finalEditorCountryList = editorCountryList;
            editorList.removeIf(editor -> !finalEditorCountryList.contains(editor));
        }

        if (editorFlag)
            query.addCriteria(where("editorList").in(editorList));

        if (searchDTO.getTitle() != null && !searchDTO.getTitle().isEmpty()) {
            query.addCriteria(where("title").regex(searchDTO.getTitle().toLowerCase(), "i"));
        }
        if (searchDTO.getIsbn() != null && !searchDTO.getIsbn().isEmpty()) {
            query.addCriteria(where("isbn").regex(searchDTO.getIsbn().toLowerCase(), "i"));
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
        if (searchDTO.getTopicList() != null) {
            if (!searchDTO.getTopicList().isEmpty()) {
                query.addCriteria(where("topicList").in(searchDTO.getTopicList().stream()
                        .map(TopicDTO::getId)
                        .collect(Collectors.toList())));
            }
        }

        query.addCriteria(where("stockNumber").gt(0).and("visible").is(true));
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
        query.fields().exclude("traceList");
        query.fields().exclude("createdBy");
        query.fields().exclude("lastModifiedBy");

        boolean editorFlag = false;
        Set<Editor> editorList = new HashSet<>();
        Set<Editor> editorNameList = null;
        Set<Editor> editorCityList = null;

        if (searchDTO.getEditor() != null && !searchDTO.getEditor().isEmpty()) {
            Query editorQuery = new Query(Criteria.where("name").regex(searchDTO.getEditor().toLowerCase(), "i"));
            editorQuery.fields().include("_id");
            editorNameList = new HashSet<>();
            editorNameList.addAll(mongoTemplate.find(editorQuery, Editor.class));
            editorList.addAll(editorNameList);
            editorFlag = true;
        }
        if (searchDTO.getEditionCity() != null && !searchDTO.getEditionCity().isEmpty()) {
            Query editionCityQuery = new Query(Criteria.where("city").regex(searchDTO.getEditionCity().toLowerCase(), "i"));
            editionCityQuery.fields().include("_id");
            editorCityList = new HashSet<>();
            editorCityList.addAll(mongoTemplate.find(editionCityQuery, Editor.class));
            editorList.addAll(editorCityList);
            editorFlag = true;
        }
        if (searchDTO.getTopicList() != null) {
            if (!searchDTO.getTopicList().isEmpty()) {
                query.addCriteria(where("topicList").in(searchDTO.getTopicList().stream()
                        .map(TopicDTO::getId)
                        .collect(Collectors.toList())));
            }
        }
        if (searchDTO.getTitle() != null && !searchDTO.getTitle().isEmpty()) {
            query.addCriteria(where("title").regex(searchDTO.getTitle().toLowerCase(), "i"));
        }
        if (searchDTO.getIsbn() != null && !searchDTO.getIsbn().isEmpty()) {
            query.addCriteria(where("isbn").regex(searchDTO.getIsbn().toLowerCase(), "i"));
        }

        if (editorNameList != null) {
            Set<Editor> finalEditorNameList = editorNameList;
            editorList.removeIf(editor -> !finalEditorNameList.contains(editor));
        }
        if (editorCityList != null) {
            Set<Editor> finalEditorCityList = editorCityList;
            editorList.removeIf(editor -> !finalEditorCityList.contains(editor));
        }

        if (editorFlag)
            query.addCriteria(where("editorList").in(editorList));

        query.addCriteria(where("stockNumber").gt(0).and("visible").is(true));
        Page<Magazine> magazines = new PageImpl<>(mongoTemplate.find(query, Magazine.class));
        resultDTO.setElements(magazines.stream().map(MagazineDTO::new).collect(Collectors.toList()));
        resultDTO.setTotal(mongoTemplate.count(query, Book.class));
        return resultDTO;
    }

    public List<CarouselDTO> searchCarouselBooks() {
        Query query = new Query();
        query.fields().include("image_url");
        query.addCriteria(where("stockNumber").gt(0).and("toShow").is(true).and("visible").is(true));

        List<Book> books = mongoTemplate.find(query, Book.class);
        List<Magazine> magazines = mongoTemplate.find(query, Magazine.class);

        List<Temp> urls = new ArrayList<>();
        for (int i = 0; i < books.size(); i++) {
            Temp bookTemp = new Temp();
            bookTemp.setUrl(books.get(i).getImageUrl());
            bookTemp.setDocumentId(books.get(i).getId());
            bookTemp.setBook(true);
            urls.add(bookTemp);
        }
        for (int i = 0; i < magazines.size(); i++) {
            Temp magazineTemp = new Temp();
            magazineTemp.setUrl(magazines.get(i).getImageUrl());
            magazineTemp.setDocumentId(magazines.get(i).getId());
            magazineTemp.setBook(false);
            urls.add(magazineTemp);
        }

        List<CarouselDTO> carouselDTOS = new ArrayList<>();
        while (urls.size() > 0) {
            List<ElementDTO> toPush = new ArrayList<>();
            List<Temp> temp = new ArrayList<>(urls.subList(0, urls.size() < 4 ? urls.size() : 4));
            urls.removeAll(temp);

            for (int i = 0; i < temp.size(); i++) {
                ElementDTO elementDTO = new ElementDTO();
                elementDTO.setUrl(temp.get(i).getUrl());
                elementDTO.setDocumentId(temp.get(i).getDocumentId());
                elementDTO.setBook(temp.get(i).getBook());
                elementDTO.setPosition(i > 0 ? "absolute" : null);
                elementDTO.setLeft(i > 0 ? 167 * i + "px" : null);
                toPush.add(elementDTO);
            }

            int val = 4 - toPush.size();
            int pos = toPush.size();
            for (int i = 0; i < val; i++) {
                ElementDTO elementDTO = new ElementDTO();
                elementDTO.setUrl("assets/images/image.gif");
                elementDTO.setPosition("absolute");
                elementDTO.setDocumentId(null);
                elementDTO.setLeft(167 * pos + "px");
                toPush.add(elementDTO);
                pos++;
            }
            CarouselDTO carouselDTO = new CarouselDTO();
            carouselDTO.setElementList(toPush);
            carouselDTOS.add(carouselDTO);

        }
        return carouselDTOS;
    }
}
