package soft.co.books.domain.service.dto;

import soft.co.books.domain.collection.data.Trace;

/**
 * A DTO representing a BookTrace.
 */
public class BookTraceDTO {

    private UserDTO user;
    private String date;
    private String action;

    public BookTraceDTO() {
        // Empty constructor needed for Jackson.
    }

    public BookTraceDTO(Trace trace) {
        this.date = trace.getDate();
        this.action = trace.getAction();
        this.user = new UserDTO(trace.getUser());
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
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
