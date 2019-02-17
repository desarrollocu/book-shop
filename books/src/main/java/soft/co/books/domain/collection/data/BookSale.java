package soft.co.books.domain.collection.data;

import org.springframework.data.mongodb.core.mapping.Field;
import soft.co.books.configuration.Constants;
import soft.co.books.domain.collection.Author;
import soft.co.books.domain.collection.Book;
import soft.co.books.domain.collection.Classification;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * A BookSale.
 */
public class BookSale extends DocumentSale implements Serializable {

    @NotNull(message = Constants.ERR_NOT_NULL)
    @NotEmpty(message = Constants.ERR_NOT_NULL)
    private List<Author> authorList = new ArrayList<>();

    @Field("sub_title")
    @Size(max = 500, message = Constants.ERR_MAX500)
    private String subTitle;

    @Field("edition_year")
    private String editionYear;

    private String pages;

    private String size;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private Classification classification;

    private List<String> descriptorList = new ArrayList<>();

    public BookSale() {
    }

    public BookSale(Book book) {
        super(book.getId(), book.getTitle(), book.getEditorList(), book.getSalePrice(), book.getIsbn(),
                book.getWeight(), book.getTopicList(), book.getCoin(), book.getStockNumber(), book.getComments());
        this.authorList = book.getAuthorList();
        this.subTitle = book.getSubTitle();
        this.editionYear = book.getEditionYear();
        this.pages = book.getPages();
        this.size = book.getSize();
        this.classification = book.getClassification();
        this.descriptorList = book.getDescriptorList();
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

        BookSale book = (BookSale) o;
        return !(book.getDocumentId() == null || getDocumentId() == null) && Objects.equals(getDocumentId(), book.getDocumentId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getDocumentId());
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
