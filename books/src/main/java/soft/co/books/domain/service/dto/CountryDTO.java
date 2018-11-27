package soft.co.books.domain.service.dto;

import org.springframework.data.mongodb.core.index.Indexed;
import soft.co.books.configuration.Constants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A DTO representing a country.
 */
public class CountryDTO {

    private String id;

    @Indexed
    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(max = 50, message = Constants.ERR_MAX50)
    private String name;

    public CountryDTO() {
        // Empty constructor needed for Jackson.
    }

    public CountryDTO(String id, String name) {
        this.id = id;
        this.name = name;
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
        return "CountryDTO{" +
                ", name='" + name + '\'' +
                '}';
    }
}
