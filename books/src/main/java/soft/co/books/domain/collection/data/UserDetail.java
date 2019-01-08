package soft.co.books.domain.collection.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import soft.co.books.configuration.Constants;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * A userDetail.
 */
public class UserDetail implements Serializable {

    @Id
    private String id;

    private String userId;

    @Field("user_name")
    @Size(max = 50, message = Constants.ERR_MAX50)
    @Pattern(regexp = Constants.USER_NAME_REGEX, message = Constants.ERR_USER_NAME_REGEX)
    private String userName;

    @Field("first_name")
    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(max = 50, message = Constants.ERR_MAX50)
    private String firstName;

    @Field("last_name")
    @Size(max = 50, message = Constants.ERR_MAX50)
    private String lastName;

    private String recipientName;

    @Email
    @Size(min = 5, max = 254, message = Constants.ERR_MIN5_MAX254)
    private String email;

    private String address;

    public UserDetail() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDetail userDetail = (UserDetail) o;
        return !(userDetail.getId() == null || getId() == null) && Objects.equals(getId(), userDetail.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserDetail{" +
                "userId='" + userId + '\'' +
                "userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
