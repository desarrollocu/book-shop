package soft.co.books.domain.service.dto;

import soft.co.books.configuration.Constants;
import soft.co.books.domain.collection.Classification;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

/**
 * A DTO representing an classification.
 */
public class ClassificationDTO {

    private String id;

    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(max = 500, message = Constants.ERR_MAX500)
    private String name;

    public ClassificationDTO() {
        // Empty constructor needed for Jackson.
    }

    public ClassificationDTO(Classification classification) {
        this.id = classification.getId();
        this.name = classification.getName();
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

    @Override
    public String toString() {
        return "ClassificationDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
