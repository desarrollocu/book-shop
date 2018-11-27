package soft.co.books.domain.collection.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import soft.co.books.configuration.Constants;
import soft.co.books.domain.collection.Book;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A detail.
 */
public class Detail implements Serializable {

    @Id
    private String id;

    @DBRef
    @NotNull(message = Constants.ERR_NOT_NULL)
    private Book book;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private int cant;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private double price;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private double mount;

    public Detail() {
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getMount() {
        return mount;
    }

    public void setMount(double mount) {
        this.mount = mount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Detail detail = (Detail) o;
        return !(detail.getId() == null || getId() == null) && Objects.equals(getId(), detail.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Detail{" +
                "book=" + book +
                ", cant=" + cant +
                ", price=" + price +
                ", mount=" + mount +
                '}';
    }
}
