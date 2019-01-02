package soft.co.books.domain.service.dto;

import soft.co.books.configuration.Constants;
import soft.co.books.domain.collection.Author;
import soft.co.books.domain.collection.Country;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A DTO representing an author.
 */
public class AuthorDTO {

    private String id;

    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(max = 50, message = Constants.ERR_MAX50)
    private String firstName;

    @Size(max = 50, message = Constants.ERR_MAX50)
    private String lastName;

    private String fullName;

    private String bornDate;

    private String deathDate;

    private Country country;

    @Size(max = 100, message = Constants.ERR_MAX100)
    private String state;

    @Size(max = 100, message = Constants.ERR_MAX100)
    private String city;

    public AuthorDTO() {
        // Empty constructor needed for Jackson.
    }

    public AuthorDTO(Author author) {
        this.id = author.getId();
        this.firstName = author.getFirstName();
        this.lastName = author.getLastName();
        this.fullName = author.getFirstName() + " " + author.getLastName();
        this.country = author.getCountry();
        this.state = author.getState();
        this.bornDate = author.getBornDate();
        this.deathDate = author.getDeathDate();
        this.city = author.getCity();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBornDate() {
        return bornDate;
    }

    public void setBornDate(String bornDate) {
        this.bornDate = bornDate;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "AuthorDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
