package soft.co.books.domain.collection.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import soft.co.books.configuration.Constants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A editor detail.
 */
public class EditorDetail implements Serializable {

    @Id
    private String id;

    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(max = 50, message = Constants.ERR_MAX50)
    private String name;

    @Size(max = 50, message = Constants.ERR_MAX50)
    private String city;

    @Version
    private Long version;

    public EditorDetail() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EditorDetail editor = (EditorDetail) o;
        return !(editor.getId() == null || getId() == null) && Objects.equals(getId(), editor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EditorDetail{" +
                "name='" + name + '\'' +
                ", city=" + city +
                ", version=" + version +
                '}';
    }
}
