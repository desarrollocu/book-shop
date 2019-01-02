package soft.co.books.domain.service.dto;

import soft.co.books.configuration.Constants;
import soft.co.books.domain.collection.Book;

import javax.validation.constraints.NotEmpty;
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

    @NotNull(message = Constants.ERR_NOT_NULL)
    @NotEmpty(message = Constants.ERR_NOT_NULL)
    private List<AuthorDTO> authorList = new ArrayList<>();

    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(min = 1, max = 150, message = Constants.ERR_MIN1_MAX150)
    private String title;

    @Size(min = 1, max = 150, message = Constants.ERR_MIN1_MAX150)
    private String subTitle;

    private String city;

    private EditorDTO editor;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private ClassificationDTO classification;

    private String editionYear;

    private int pages = 0;

    private String size;

    private String isbn;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private TopicDTO topic;

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
        this.classification = book.getClassificationOrNull().map(ClassificationDTO::new).orElse(null);
        this.editionYear = book.getEditionYear();
        this.pages = book.getPages();
        this.size = book.getSize();
        this.isbn = book.getIsbn();
        this.topic = book.getTopicOrNull().map(TopicDTO::new).orElse(null);
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

    @Override
    public String toString() {
        return "BookDTO{" +
                "title='" + title + '\'' +
                ", pages=" + pages +
                ", coin='" + coin + '\'' +
                ", image='" + image + '\'' +
                ", salePrice=" + salePrice +
                ", editionYear='" + editionYear + '\'' +
                ", authorList=" + authorList +
                ", descriptors=" + descriptors +
                ", visit=" + visit +
                '}';
    }
}
