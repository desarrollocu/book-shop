package soft.co.books.domain.collection.data;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;
import soft.co.books.configuration.Constants;
import soft.co.books.domain.collection.Country;
import soft.co.books.domain.collection.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A UserSale.
 */
public class UserSale implements Serializable {

    private String userId;

    @Field("user_name")
    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(min = 1, max = 50, message = Constants.ERR_MIN1_MAX50)
    @Pattern(regexp = Constants.USER_NAME_REGEX, message = Constants.ERR_USER_NAME_REGEX)
    private String userName;

    @Field("full_name")
    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(min = 1, max = 100, message = Constants.ERR_MAX100)
    private String fullName;

    @Email
    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(min = 5, max = 100, message = Constants.ERR_MAX100)
    private String email;

    @Size(max = 140, message = Constants.ERR_MAX120)
    @NotNull(message = Constants.ERR_NOT_NULL)
    private String line1;

    @Size(max = 100, message = Constants.ERR_MAX100)
    private String line2;

    @DBRef
    @NotNull(message = Constants.ERR_NOT_NULL)
    private Country country;

    @Size(max = 100, message = Constants.ERR_MAX100)
    private String state;

    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(max = 100, message = Constants.ERR_MAX100)
    private String city;

    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(max = 100, message = Constants.ERR_MAX100)
    private String cp;

    @Size(max = 50, message = Constants.ERR_MAX50)
    @NotNull(message = Constants.ERR_NOT_NULL)
    private String phone;

    public UserSale() {
    }

    public UserSale(User user) {
        this.userId = user.getId();
        this.userName = user.getUserName();
        this.fullName = user.getFirstName() + " " + user.getLastName();
        this.email = user.getEmail();
        this.line1 = user.getLine1();
        this.line2 = user.getLine2();
        this.country = user.getCountry();
        this.state = user.getState();
        this.city = user.getCity();
        this.cp = user.getCp();
        this.phone = user.getPhone();
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUserId());
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", phone='" + phone + '\'' +
                ", line1=" + line1 +
                ", line2=" + line2 +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        UserSale user = (UserSale) o;
        return !(user.getUserId() == null || getUserId() == null) && Objects.equals(getUserId(), user.getUserId());
    }
}
