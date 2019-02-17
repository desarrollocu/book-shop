package soft.co.books.domain.service.dto;

import soft.co.books.configuration.Constants;
import soft.co.books.domain.collection.Book;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A DTO representing a book.
 */
public class BookDTO {

    private String id;

    @NotNull(message = Constants.ERR_NOT_NULL)
    @NotEmpty(message = Constants.ERR_NOT_NULL)
    private List<AuthorDTO> authorList = new ArrayList<>();

    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(min = 1, max = 150, message = Constants.ERR_MIN1_MAX150)
    private String title;

    @Size(min = 1, max = 150, message = Constants.ERR_MIN1_MAX150)
    private String subTitle;

    private List<EditorDTO> editorList = new ArrayList<>();

    @NotNull(message = Constants.ERR_NOT_NULL)
    private ClassificationDTO classification;

    private String editionYear;

    private String pages;

    private String size;

    private String isbn;

    private String comments;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private double weight;

    @NotNull(message = Constants.ERR_NOT_NULL)
    @NotEmpty(message = Constants.ERR_NOT_NULL)
    private List<TopicDTO> topicList = new ArrayList<>();

    @NotNull(message = Constants.ERR_NOT_NULL)
    private double salePrice;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private String coin;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private int stockNumber = 0;

    private String image;

    private String descriptors;

    private Long visit = 0L;

    private ShowDTO toShow;

    private String topicsEnglish;

    private String topicsSpanish;

    private String editors;

    private String authors;

    private String cities;

    private String countriesEnglish;

    private String countriesSpanish;

    private String createdDate;

    private String createdBy;

    private String lastModifiedDate;

    private String lastModifiedBy;

    public BookDTO() {
        // Empty constructor needed for Jackson.
    }

    public BookDTO(Book book) {
        this.id = book.getId();
        this.authorList = book.getAuthorList().stream().map(AuthorDTO::new).collect(Collectors.toList());
        this.authors = "";
        for (int i = 0; i < this.authorList.size(); i++) {
            if (this.authors.equals(""))
                this.authors = this.authorList.get(i).getFullName();
            else
                this.authors += ", " + this.authorList.get(i).getFullName();
        }

        this.title = book.getTitle();
        this.subTitle = book.getSubTitle();
        this.editorList = book.getEditorList().stream().map(EditorDTO::new).collect(Collectors.toList());
        this.editors = "";
        this.cities = "";
        this.countriesEnglish = "";
        this.countriesSpanish = "";
        for (int i = 0; i < this.editorList.size(); i++) {
            if (this.editors.equals(""))
                this.editors = this.editorList.get(i).getName();
            else
                this.editors += ", " + this.editorList.get(i).getName();

            if (this.cities.equals(""))
                this.cities = this.editorList.get(i).getCity();
            else
                this.cities += ", " + this.editorList.get(i).getCity();

            if (this.countriesSpanish.equals(""))
                this.countriesSpanish = this.editorList.get(i).getCountry().getSpanishName();
            else
                this.countriesSpanish += ", " + this.editorList.get(i).getCountry().getSpanishName();

            if (this.countriesEnglish.equals(""))
                this.countriesEnglish = this.editorList.get(i).getCountry().getEnglishName();
            else
                this.countriesEnglish += ", " + this.editorList.get(i).getCountry().getEnglishName();
        }

        this.classification = book.getClassificationOrNull().map(ClassificationDTO::new).orElse(null);
        this.editionYear = book.getEditionYear();
        this.pages = book.getPages();
        this.size = book.getSize();
        this.isbn = book.getIsbn();
        this.comments = book.getComments();
        this.topicList = book.getTopicList().stream().map(TopicDTO::new).collect(Collectors.toList());
        this.topicsEnglish = "";
        this.topicsSpanish = "";
        for (int i = 0; i < this.topicList.size(); i++) {
            if (this.topicsEnglish.equals(""))
                this.topicsEnglish = this.topicList.get(i).getEnglishName();
            else
                this.topicsEnglish += ", " + this.topicList.get(i).getEnglishName();

            if (this.topicsSpanish.equals(""))
                this.topicsSpanish = this.topicList.get(i).getSpanishName();
            else
                this.topicsSpanish += ", " + this.topicList.get(i).getSpanishName();
        }

        this.weight = book.getWeight();
        this.salePrice = book.getSalePrice();
        this.coin = book.getCoin();
        this.stockNumber = book.getStockNumber();
        this.image = book.getImageUrl();
        ShowDTO showDTO = new ShowDTO();
        showDTO.setElem(book.isToShow() == true ? "user.yes" : "user.no");
        showDTO.setVal(book.isToShow());
        this.toShow = showDTO;

        for (int i = 0; i < book.getDescriptorList().size(); i++) {
            if (i == 0)
                this.descriptors = book.getDescriptorList().get(i);
            else
                this.descriptors += "," + book.getDescriptorList().get(i);
        }

        this.visit = book.getVisit();
        this.createdDate = new SimpleDateFormat("yyyy-MM-dd").format(Date.from(book.getCreatedDate()));
        this.createdBy = book.getCreatedBy() != null ? book.getCreatedBy().getUserName() : "";
        this.lastModifiedDate = book.getLastModifiedDate() != null ? book.getLastModifiedDate() : "";
        this.lastModifiedBy = book.getLastModifiedBy() != null ? book.getLastModifiedBy().getUserName() : "";
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public ShowDTO getToShow() {
        return toShow;
    }

    public void setToShow(ShowDTO toShow) {
        this.toShow = toShow;
    }

    public ClassificationDTO getClassification() {
        return classification;
    }

    public void setClassification(ClassificationDTO classification) {
        this.classification = classification;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public List<EditorDTO> getEditorList() {
        return editorList;
    }

    public void setEditorList(List<EditorDTO> editorList) {
        this.editorList = editorList;
    }

    public List<TopicDTO> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<TopicDTO> topicList) {
        this.topicList = topicList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public int getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(int stockNumber) {
        this.stockNumber = stockNumber;
    }

    public List<AuthorDTO> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<AuthorDTO> authorList) {
        this.authorList = authorList;
    }

    public Long getVisit() {
        return visit;
    }

    public void setVisit(Long visit) {
        this.visit = visit;
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

    public String getTopicsEnglish() {
        return topicsEnglish;
    }

    public void setTopicsEnglish(String topicsEnglish) {
        this.topicsEnglish = topicsEnglish;
    }

    public String getTopicsSpanish() {
        return topicsSpanish;
    }

    public void setTopicsSpanish(String topicsSpanish) {
        this.topicsSpanish = topicsSpanish;
    }

    public String getEditors() {
        return editors;
    }

    public void setEditors(String editors) {
        this.editors = editors;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getCities() {
        return cities;
    }

    public void setCities(String cities) {
        this.cities = cities;
    }

    public String getCountriesEnglish() {
        return countriesEnglish;
    }

    public void setCountriesEnglish(String countriesEnglish) {
        this.countriesEnglish = countriesEnglish;
    }

    public String getCountriesSpanish() {
        return countriesSpanish;
    }

    public void setCountriesSpanish(String countriesSpanish) {
        this.countriesSpanish = countriesSpanish;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "title='" + title + '\'' +
                ", pages=" + pages +
                ", coin='" + coin + '\'' +
                ", image='" + image + '\'' +
                ", salePrice=" + salePrice +
                ", comments=" + comments +
                ", editionYear='" + editionYear + '\'' +
                ", authorList=" + authorList +
                ", descriptors=" + descriptors +
                ", visit=" + visit +
                '}';
    }
}
