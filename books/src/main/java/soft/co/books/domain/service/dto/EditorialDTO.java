package soft.co.books.domain.service.dto;

import soft.co.books.configuration.Constants;
import soft.co.books.domain.collection.Editorial;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

/**
 * A DTO representing an editorial.
 */
public class EditorialDTO {

    private String id;

    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(max = 50, message = Constants.ERR_MAX50)
    private String name;

    private String city;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private String country;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    public EditorialDTO() {
        // Empty constructor needed for Jackson.
    }

    public EditorialDTO(Editorial editorial) {
        this.id = editorial.getId();
        this.name = editorial.getName();
        this.city = editorial.getCity();
        this.country = editorial.getCountry().getName();
        this.createdBy = editorial.getCreatedBy();
        this.createdDate = editorial.getCreatedDate();
        this.lastModifiedBy = editorial.getLastModifiedBy();
        this.lastModifiedDate = editorial.getLastModifiedDate();
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "EditorialDTO{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
