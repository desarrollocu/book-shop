package soft.co.books.domain.collection;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Magazine.
 */
@Document(collection = "bs_magazine")
public class Magazine extends soft.co.books.domain.collection.Document implements Serializable {

    private String frequency;

    @Field("publish_year")
    private String publishYear;

    public Magazine() {
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

        Magazine magazine = (Magazine) o;
        return !(magazine.getId() == null || getId() == null) && Objects.equals(getId(), magazine.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Magazine{" +
                ", title='" + getTitle() + '\'' +
                ", city='" + getCity() + '\'' +
                ", editor=" + getEditor() +
                ", publishYear='" + publishYear + '\'' +
                ", frequency='" + frequency + '\'' +
                ", stockNumber='" + getStockNumber() + '\'' +
                ", isbn='" + getIsbn() + '\'' +
                ", topic=" + getTopic() +
                ", salePrice=" + getSalePrice() +
                ", coin='" + getCoin() + '\'' +
                '}';
    }
}
