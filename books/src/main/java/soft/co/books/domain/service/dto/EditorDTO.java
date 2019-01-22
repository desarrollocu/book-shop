package soft.co.books.domain.service.dto;

import soft.co.books.configuration.Constants;
import soft.co.books.domain.collection.Country;
import soft.co.books.domain.collection.Editor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A DTO representing an editor.
 */
public class EditorDTO {

    private String id;

    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(max = 50, message = Constants.ERR_MAX50)
    private String name;

    private Country country;

    @Size(max = 50, message = Constants.ERR_MAX50)
    private String city;

    public EditorDTO() {
        // Empty constructor needed for Jackson.
    }

    public EditorDTO(Editor editor) {
        this.id = editor.getId();
        this.name = editor.getName();
        this.country = editor.getCountry();
        this.city = editor.getCity();
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
    public String toString() {
        return "EditorDTO{" +
                "name='" + name + '\'' +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
