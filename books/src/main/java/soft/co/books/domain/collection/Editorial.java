package soft.co.books.domain.collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import soft.co.books.configuration.Constants;
import soft.co.books.configuration.database.AbstractAuditingEntity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A editorial.
 */
@Document(collection = "bs_editorial")
public class Editorial extends AbstractAuditingEntity implements Serializable {

    @Id
    private String id;

    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(max = 50, message = Constants.ERR_MAX50)
    private String name;

    private String city;

    @DBRef
    @NotNull(message = Constants.ERR_NOT_NULL)
    private Country country;

    @Version
    private Long version;

    public Editorial() {
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
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

        Editorial editorial = (Editorial) o;
        return !(editorial.getId() == null || getId() == null) && Objects.equals(getId(), editorial.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Editorial{" +
                "name='" + name + '\'' +
                ", country=" + country +
                ", city=" + city +
                ", version=" + version +
                '}';
    }
}
