package soft.co.books.domain.collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import soft.co.books.configuration.Constants;
import soft.co.books.configuration.database.AbstractAuditingEntity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A descriptor.
 */
@Document(collection = "bs_descriptor")
public class Descriptor extends AbstractAuditingEntity implements Serializable {

    @Id
    private String id;

    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(max = 50, message = Constants.ERR_MAX50)
    private String name;

    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(max = 1000, message = Constants.ERR_MAX1000)
    private String description;

    @Version
    private Long version;

    public Descriptor() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Descriptor{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", version=" + version +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Descriptor that = (Descriptor) o;

        return !(that.getId() == null || getId() == null) && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
