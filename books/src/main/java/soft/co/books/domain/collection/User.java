package soft.co.books.domain.collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import soft.co.books.configuration.Constants;
import soft.co.books.configuration.database.AbstractAuditingEntity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A user.
 */
@Document(collection = "sys_user")
@CompoundIndexes({
        @CompoundIndex(name = "user_emailx", def = "{'email': 1, 'deleted': 1}",
                unique = true),
        @CompoundIndex(name = "user_namex", def = "{'user_name': 1, 'deleted': 1}", unique = true)
})
public class User extends AbstractAuditingEntity implements Serializable {

    @Id
    private String id;

    @Indexed
    @Field("user_name")
    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(min = 1, max = 50, message = Constants.ERR_MIN1_MAX50)
    @Pattern(regexp = Constants.USER_NAME_REGEX, message = Constants.ERR_USER_NAME_REGEX)
    private String userName;

    @Field("first_name")
    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(min = 1, max = 50, message = Constants.ERR_MIN1_MAX50)
    private String firstName;

    @Field("last_name")
    @Size(max = 50, message = Constants.ERR_MAX50)
    private String lastName;

    @Email
    @Indexed
    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(min = 5, max = 100, message = Constants.ERR_MAX100)
    private String email;

    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(max = 100, message = Constants.ERR_MAX100)
    private String line1;

    @Size(max = 100, message = Constants.ERR_MAX100)
    private String line2;

    @Field("lang_key")
    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(min = 2, max = 6, message = Constants.ERR_MIN2_MAX6)
    private String langKey = Constants.DEFAULT_LANGUAGE;

    @JsonIgnore
    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(min = 60, max = 60)
    private String password;

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

    @NotNull(message = Constants.ERR_NOT_NULL)
    private boolean visible;

    private String deleted = "empty";

    @DBRef
    private Set<Authority> authorities = new HashSet<>();

    private boolean activated = false;

    @Version
    private Long version;

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
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

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", langKey='" + langKey + '\'' +
                ", password='" + password + '\'' +
                ", activated=" + activated +
                ", line1=" + line1 +
                ", line2=" + line2 +
                ", version=" + version +
                '}';
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
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        User user = (User) o;
        return !(user.getId() == null || getId() == null) && Objects.equals(getId(), user.getId());
    }
}
