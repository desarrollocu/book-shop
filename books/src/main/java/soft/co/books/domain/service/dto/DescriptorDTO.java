package soft.co.books.domain.service.dto;

import soft.co.books.configuration.Constants;
import soft.co.books.domain.collection.Descriptor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A DTO representing a descriptor.
 */
public class DescriptorDTO {

    private String id;

    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(max = 50, message = Constants.ERR_MAX50)
    private String name;

    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(max = 1000, message = Constants.ERR_MAX1000)
    private String description;

    public DescriptorDTO() {
        // Empty constructor needed for Jackson.
    }

    public DescriptorDTO(Descriptor descriptor) {
        this.id = descriptor.getId();
        this.name = descriptor.getName();
        this.description = descriptor.getDescription();
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
        return "DescriptorDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
