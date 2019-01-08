package soft.co.books.domain.collection.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import soft.co.books.configuration.Constants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A detail.
 */
public class Detail implements Serializable {

    @Id
    private String id;

    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(min = 1, max = 150, message = Constants.ERR_MIN1_MAX150)
    private String title;

    @Size(max = 50, message = Constants.ERR_MAX50)
    private String city;

    private EditorDetail editor;

    @Field("sell_price")
    @NotNull(message = Constants.ERR_NOT_NULL)
    private double salePrice;

    @Size(max = 50, message = Constants.ERR_MAX50)
    private String isbn;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private TopicDetail topic;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private int cant;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private double mount;

    public Detail() {
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

    public EditorDetail getEditor() {
        return editor;
    }

    public void setEditor(EditorDetail editor) {
        this.editor = editor;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public TopicDetail getTopic() {
        return topic;
    }

    public void setTopic(TopicDetail topic) {
        this.topic = topic;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
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
                "title=" + title +
                ", cant=" + cant +
                ", salePrice=" + salePrice +
                ", city=" + city +
                ", editor=" + editor +
                ", topic=" + topic +
                ", isbn=" + isbn +
                ", mount=" + mount +
                '}';
    }
}
