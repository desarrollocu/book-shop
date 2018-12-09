package soft.co.books.configuration.error;

import java.io.Serializable;
import java.util.List;

public class ErrorDTO implements Serializable {

    private String error;
    private List<String> fields;

    public ErrorDTO() {
    }

    public ErrorDTO(String error) {
        this.error = error;
    }

    public ErrorDTO(String error, List<String> fields) {
        this.error = error;
        this.fields = fields;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
