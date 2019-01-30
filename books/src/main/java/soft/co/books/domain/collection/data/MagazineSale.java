package soft.co.books.domain.collection.data;

import org.springframework.data.mongodb.core.mapping.Field;
import soft.co.books.domain.collection.Magazine;

import java.io.Serializable;
import java.util.Objects;

/**
 * A MagazineSale.
 */
public class MagazineSale extends DocumentSale implements Serializable {

    private String frequency;

    @Field("publish_year")
    private String publishYear;

    public MagazineSale() {
    }

    public MagazineSale(Magazine magazine) {
        super(magazine.getId(), magazine.getTitle(), magazine.getEditorList(), magazine.getSalePrice(), magazine.getIsbn(),
                magazine.getWeight(), magazine.getTopicList(), magazine.getCoin(), magazine.getStockNumber(), magazine.getComments());
        this.frequency = magazine.getFrequency();
        this.publishYear = magazine.getPublishYear();
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(String publishYear) {
        this.publishYear = publishYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MagazineSale magazine = (MagazineSale) o;
        return !(magazine.getDocumentId() == null || getDocumentId() == null) && Objects.equals(getDocumentId(), magazine.getDocumentId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getDocumentId());
    }

    @Override
    public String toString() {
        return "Magazine{" +
                ", title='" + getTitle() + '\'' +
                ", editorList=" + getEditorList() +
                ", publishYear='" + publishYear + '\'' +
                ", frequency='" + frequency + '\'' +
                ", stockNumber='" + getStockNumber() + '\'' +
                ", isbn='" + getIsbn() + '\'' +
                ", topicList=" + getTopicList() +
                ", salePrice=" + getSalePrice() +
                ", coin='" + getCoin() + '\'' +
                '}';
    }
}
