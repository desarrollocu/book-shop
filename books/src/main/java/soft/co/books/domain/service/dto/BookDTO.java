package soft.co.books.domain.service.dto;

import soft.co.books.configuration.Constants;
import soft.co.books.domain.collection.Book;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A DTO representing a book.
 */
public class BookDTO {

    private String id;

    private List<AuthorDTO> authorList = new ArrayList<>();

    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(min = 1, max = 150, message = Constants.ERR_MIN1_MAX150)
    private String title;

    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(min = 1, max = 150, message = Constants.ERR_MIN1_MAX150)
    private String subTitle;

    private String city;

    private EditorDTO editor;

    private String editionDate;

    private int pages = 0;

    private String size;

    private String isbn;

    private TopicDTO topic;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private double salePrice;

    private String coin;

    private String image;

    private String edition;

    private EditorialDTO editorial;

    private List<DescriptorDTO> descriptorList = new ArrayList<>();

    private Long visit = 0L;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    public BookDTO() {
        // Empty constructor needed for Jackson.
    }

    public BookDTO(Book book) {
        this.id = book.getId();
        this.authorList = book.getAuthorList().stream().map(AuthorDTO::new).collect(Collectors.toList());
        this.title = book.getTitle();
        this.subTitle = book.getSubTitle();
        this.city = book.getCity();
        this.editor = book.getEditorOrNull().map(EditorDTO::new).orElse(null);
        this.editionDate = book.getEditionDate();
        this.pages = book.getPages();
        this.size = book.getSize();
        this.isbn = book.getIsbn();
        this.topic = book.getTopicOrNull().map(TopicDTO::new).orElse(null);
        this.salePrice = book.getSalePrice();
        this.coin = book.getCoin();

        this.image = book.getImage();
        this.edition = book.getEdition();
        this.editorial = book.getEditorialOrNull().map(EditorialDTO::new).orElse(null);
        this.descriptorList = book.getDescriptorList().stream().map(DescriptorDTO::new).collect(Collectors.toList());

        this.visit = book.getVisit();
        this.createdBy = book.getCreatedBy();
        this.createdDate = book.getCreatedDate();
        this.lastModifiedBy = book.getLastModifiedBy();
        this.lastModifiedDate = book.getLastModifiedDate();
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public EditorDTO getEditor() {
        return editor;
    }

    public void setEditor(EditorDTO editor) {
        this.editor = editor;
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

    public TopicDTO getTopic() {
        return topic;
    }

    public void setTopic(TopicDTO topic) {
        this.topic = topic;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
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

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
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

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public String getEditionDate() {
        return editionDate;
    }

    public void setEditionDate(String editionDate) {
        this.editionDate = editionDate;
    }

    public EditorialDTO getEditorial() {
        return editorial;
    }

    public void setEditorial(EditorialDTO editorial) {
        this.editorial = editorial;
    }

    public List<AuthorDTO> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<AuthorDTO> authorList) {
        this.authorList = authorList;
    }

    public List<DescriptorDTO> getDescriptorList() {
        return descriptorList;
    }

    public void setDescriptorList(List<DescriptorDTO> descriptorList) {
        this.descriptorList = descriptorList;
    }

    public Long getVisit() {
        return visit;
    }

    public void setVisit(Long visit) {
        this.visit = visit;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "title='" + title + '\'' +
                ", pages=" + pages +
                ", coin='" + coin + '\'' +
                ", image='" + image + '\'' +
                ", edition='" + edition + '\'' +
                ", salePrice=" + salePrice +
                ", editionDate='" + editionDate + '\'' +
                ", editorial=" + editorial +
                ", authorList=" + authorList +
                ", descriptorList=" + descriptorList +
                ", visit=" + visit +
                '}';
    }
}
