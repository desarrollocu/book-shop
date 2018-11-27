package soft.co.books.domain.service.dto;

import soft.co.books.configuration.Constants;
import soft.co.books.domain.collection.Book;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A DTO representing a book.
 */
public class BookDTO {

    private String id;

    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(min = 1, max = 150, message = Constants.ERR_MIN1_MAX150)
    private String title;

    private int pages;

    private String coin;

    private String image;

    private String edition;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private double salePrice;

    private String editionDate;

    private EditorialDTO editorial;

    private List<AuthorDTO> authorList;

    private List<DescriptorDTO> descriptorList;

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
        this.title = book.getTitle();
        this.pages = book.getPages();
        this.coin = book.getCoin();
        this.image = book.getImage();
        this.edition = book.getEdition();
        this.salePrice = book.getSalePrice();
        this.editionDate = book.getEditionDate();
        this.visit = book.getVisit();
        this.createdBy = book.getCreatedBy();
        this.createdDate = book.getCreatedDate();
        this.lastModifiedBy = book.getLastModifiedBy();
        this.lastModifiedDate = book.getLastModifiedDate();
        this.authorList = book.getAuthorList().stream().map(AuthorDTO::new).collect(Collectors.toList());
        this.descriptorList = book.getDescriptorList().stream().map(DescriptorDTO::new).collect(Collectors.toList());
        this.editorial = book.getEditorialOrNull().map(EditorialDTO::new).orElse(null);
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
