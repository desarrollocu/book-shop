package soft.co.books.domain.collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import soft.co.books.configuration.Constants;
import soft.co.books.configuration.database.AbstractAuditingEntity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * A book.
 */
@Document(collection = "bs_book")
public class Book extends AbstractAuditingEntity implements Serializable {

    @Id
    private String id;

    @Indexed
    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(min = 1, max = 150, message = Constants.ERR_MIN1_MAX150)
    private String title;

    private int pages = 0;

    private String coin;

    private String image;

    private String edition;

    @Field("sell_price")
    @NotNull(message = Constants.ERR_NOT_NULL)
    private double salePrice;

    @Field("edition_date")
    private String editionDate;

    @DBRef
    private Editorial editorial;

    @DBRef
    @Indexed
    private List<Author> authorList;

    @DBRef
    @Indexed
    private List<Descriptor> descriptorList;

    private Long visit = 0L;

    @Version
    private Long version;

    public Book() {
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

    public void setSalePrice(double sellPrice) {
        this.salePrice = sellPrice;
    }

    public String getEditionDate() {
        return editionDate;
    }

    public void setEditionDate(String editionDate) {
        this.editionDate = editionDate;
    }

    public List<Author> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }

    public List<Descriptor> getDescriptorList() {
        return descriptorList;
    }

    public void setDescriptorList(List<Descriptor> descriptorList) {
        this.descriptorList = descriptorList;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public Optional<Editorial> getEditorialOrNull() {
        return Optional.ofNullable(editorial);
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    public Long getVisit() {
        return visit;
    }

    public void setVisit(Long visit) {
        this.visit = visit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;
        return !(book.getId() == null || getId() == null) && Objects.equals(getId(), book.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", pages=" + pages +
                ", authorList=" + authorList +
                ", coin='" + coin + '\'' +
                ", image='" + image + '\'' +
                ", edition='" + edition + '\'' +
                ", salePrice=" + salePrice +
                ", editorial=" + editorial +
                ", editionDate=" + editionDate +
                ", descriptorList=" + descriptorList +
                ", version=" + version +
                '}';
    }
}
