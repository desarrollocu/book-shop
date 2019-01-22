package soft.co.books.domain.service.dto;

import soft.co.books.configuration.Constants;
import soft.co.books.domain.collection.Country;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * A DTO representing a search param.
 */
public class SearchDTO {

    private String author;

    @Size(min = 1, max = 150, message = Constants.ERR_MIN1_MAX150)
    private String title;

    @Size(min = 1, max = 150, message = Constants.ERR_MIN1_MAX150)
    private String subTitle;

    private String editor;

    private String city;

    private String editionCity;

    private Country editionCountry;

    private String editionYear;

    private String descriptors;

    private String isbn;

    private List<TopicDTO> topicList = new ArrayList<>();

    private ClassificationDTO classification;

    public SearchDTO() {
        // Empty constructor needed for Jackson.
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEditionYear() {
        return editionYear;
    }

    public void setEditionYear(String editionYear) {
        this.editionYear = editionYear;
    }

    public String getDescriptors() {
        return descriptors;
    }

    public void setDescriptors(String descriptors) {
        this.descriptors = descriptors;
    }

    public List<TopicDTO> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<TopicDTO> topicList) {
        this.topicList = topicList;
    }

    public ClassificationDTO getClassification() {
        return classification;
    }

    public void setClassification(ClassificationDTO classification) {
        this.classification = classification;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getEditionCity() {
        return editionCity;
    }

    public void setEditionCity(String editionCity) {
        this.editionCity = editionCity;
    }

    public Country getEditionCountry() {
        return editionCountry;
    }

    public void setEditionCountry(Country editionCountry) {
        this.editionCountry = editionCountry;
    }
}
