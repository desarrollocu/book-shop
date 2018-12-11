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
import java.util.ArrayList;
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

    @DBRef
    @Indexed
    private List<Author> authorList = new ArrayList<>();

    @Indexed
    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(min = 1, max = 150, message = Constants.ERR_MIN1_MAX150)
    private String title;

    @Indexed
    @Field("sub_title")
    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(min = 1, max = 150, message = Constants.ERR_MIN1_MAX150)
    private String subTitle;

    @Size(max = 50, message = Constants.ERR_MAX50)
    private String city;

    @DBRef
    private Editor editor;

    @Field("edition_date")
    private String editionDate;

    private int pages = 0;

    @Size(max = 50, message = Constants.ERR_MAX50)
    private String size;

    @Size(max = 50, message = Constants.ERR_MAX50)
    private String isbn;

    @DBRef
    private Topic topic;

    @Field("sell_price")
    @NotNull(message = Constants.ERR_NOT_NULL)
    private double salePrice;

    @Size(max = 50, message = Constants.ERR_MAX50)
    private String coin;

    private String image;

    @Size(max = 50, message = Constants.ERR_MAX50)
    private String edition;

    @DBRef
    private Editorial editorial;

    @DBRef
    @Indexed
    private List<Descriptor> descriptorList = new ArrayList<>();

    private Long visit = 0L;

    @Version
    private Long version;

    public Book() {
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

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
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

    public Topic getTopic() {
        return topic;
    }

    public Optional<Topic> getTopicOrNull() {
        return Optional.ofNullable(topic);
    }

    public void setTopic(Topic topic) {
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

    public Optional<Editor> getEditorOrNull() {
        return Optional.ofNullable(editor);
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
                "authorList=" + authorList +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", city='" + city + '\'' +
                ", editor=" + editor +
                ", editionDate='" + editionDate + '\'' +
                ", pages=" + pages +
                ", size='" + size + '\'' +
                ", isbn='" + isbn + '\'' +
                ", topic=" + topic +
                ", salePrice=" + salePrice +
                ", coin='" + coin + '\'' +
                '}';
    }
}
