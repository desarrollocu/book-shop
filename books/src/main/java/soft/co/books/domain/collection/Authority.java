package soft.co.books.domain.collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import soft.co.books.configuration.Constants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * An authority used by Spring Security.
 */
@Document(collection = "sys_authority")
public class Authority implements Serializable {

    @Id
    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(max = 50, message = Constants.ERR_MAX50)
    private String name;

    @Size(max = 500, message = Constants.ERR_MAX500)
    private String description;

    @Version
    private Long version;

    public Authority() {
    }

    public Authority(@NotNull(message = Constants.ERR_NOT_NULL)
                     @Size(max = 50, message = Constants.ERR_MAX50) String name,
                     @Size(max = 500, message = Constants.ERR_MAX500) String description) {
        this.name = name;
        this.description = description;
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Authority authority = (Authority) o;
        return !(authority.getName() == null || getName() == null) && Objects.equals(getName(), authority.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }

    @Override
    public String toString() {
        return "Authority{" +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", version=" + version +
                '}';
    }
}
