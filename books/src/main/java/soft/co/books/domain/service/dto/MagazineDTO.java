package soft.co.books.domain.service.dto;

import soft.co.books.configuration.Constants;
import soft.co.books.domain.collection.Magazine;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

/**
 * A DTO representing an magazine.
 */
public class MagazineDTO {

    private String id;

    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(min = 1, max = 150, message = Constants.ERR_MIN1_MAX150)
    private String title;

    @Size(max = 50, message = Constants.ERR_MAX50)
    private String city;

    private EditorDTO editor;

    @Size(max = 50, message = Constants.ERR_MAX50)
    private String isbn;

    private String frequency;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private TopicDTO topic;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private double salePrice;

    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(max = 50, message = Constants.ERR_MAX50)
    private String coin;

    private String image;

    private String publishYear;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private int stockNumber;

    private Long visit = 0L;

    private ShowDTO toShow;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    public MagazineDTO() {
        // Empty constructor needed for Jackson.
    }

    public MagazineDTO(Magazine magazine) {
        this.id = magazine.getId();
        this.title = magazine.getTitle();
        this.city = magazine.getCity();
        this.editor = magazine.getEditorOrNull().map(EditorDTO::new).orElse(null);
        this.publishYear = magazine.getPublishYear();
        this.frequency = magazine.getFrequency();
        this.isbn = magazine.getIsbn();
        this.topic = magazine.getTopicOrNull().map(TopicDTO::new).orElse(null);
        this.salePrice = magazine.getSalePrice();
        this.stockNumber = magazine.getStockNumber();
        ShowDTO showDTO = new ShowDTO();
        showDTO.setElem(magazine.isToShow() == true ? "user.yes" : "user.no");
        showDTO.setVal(magazine.isToShow());
        this.toShow = showDTO;
        this.coin = magazine.getCoin();
        this.image = magazine.getImageUrl();
        this.visit = magazine.getVisit();
        this.createdBy = magazine.getCreatedBy();
        this.createdDate = magazine.getCreatedDate();
        this.lastModifiedBy = magazine.getLastModifiedBy();
        this.lastModifiedDate = magazine.getLastModifiedDate();
    }

    public ShowDTO getToShow() {
        return toShow;
    }

    public void setToShow(ShowDTO toShow) {
        this.toShow = toShow;
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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public TopicDTO getTopic() {
        return topic;
    }

    public void setTopic(TopicDTO topic) {
        this.topic = topic;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
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

    public String getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(String publishYear) {
        this.publishYear = publishYear;
    }

    public int getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(int stockNumber) {
        this.stockNumber = stockNumber;
    }

    public Long getVisit() {
        return visit;
    }

    public void setVisit(Long visit) {
        this.visit = visit;
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

    @Override
    public String toString() {
        return "MagazineDTO{" +
                "title='" + title + '\'' +
                ", coin='" + coin + '\'' +
                ", image='" + image + '\'' +
                ", salePrice=" + salePrice +
                ", publishYear='" + publishYear + '\'' +
                ", stockNumber=" + stockNumber +
                ", visit=" + visit +
                '}';
    }
}
