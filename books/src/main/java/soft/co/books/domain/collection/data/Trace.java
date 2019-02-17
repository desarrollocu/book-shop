package soft.co.books.domain.collection.data;

import org.springframework.data.mongodb.core.mapping.DBRef;
import soft.co.books.domain.collection.User;

import java.io.Serializable;

public class Trace implements Serializable {

    @DBRef
    private User user;
    private String date;
    private String action;

    public Trace() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
