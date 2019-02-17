package soft.co.books.domain.collection;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import soft.co.books.configuration.Constants;

import javax.validation.constraints.NotEmpty;
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
public class Book extends soft.co.books.domain.collection.Document implements Serializable {

    @DBRef
    @Indexed
    @NotNull(message = Constants.ERR_NOT_NULL)
    @NotEmpty(message = Constants.ERR_NOT_NULL)
    private List<Author> authorList = new ArrayList<>();

    private AuthorBook authorBook;

    @Indexed
    @Field("sub_title")
    @Size(max = 500, message = Constants.ERR_MAX500)
    private String subTitle;

    @Field("edition_year")
    private String editionYear;

    private String pages;

    private String size;

    @DBRef
    @NotNull(message = Constants.ERR_NOT_NULL)
    private Classification classification;

    @Indexed
    private List<String> descriptorList = new ArrayList<>();

    public Book() {
    }

    public AuthorBook getAuthorBook() {
        return authorBook;
    }

    public void setAuthorBook(AuthorBook authorBook) {
        this.authorBook = authorBook;
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

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getEditionYear() {
        return editionYear;
    }

    public void setEditionYear(String editionYear) {
        this.editionYear = editionYear;
    }

    public List<Author> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }

    public List<String> getDescriptorList() {
        return descriptorList;
    }

    public void setDescriptorList(List<String> descriptorList) {
        this.descriptorList = descriptorList;
    }

    public Classification getClassification() {
        return classification;
    }

    public Optional<Classification> getClassificationOrNull() {
        return Optional.ofNullable(classification);
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
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
                ", title='" + getTitle() + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", editorList=" + getEditorList() +
                ", editionYear='" + editionYear + '\'' +
                ", pages=" + pages +
                ", classification=" + classification +
                ", stockNumber=" + getStockNumber() +
                ", size='" + size + '\'' +
                ", isbn='" + getIsbn() + '\'' +
                ", topicList=" + getTopicList() +
                ", salePrice=" + getSalePrice() +
                ", coin='" + getCoin() + '\'' +
                '}';
    }
}
