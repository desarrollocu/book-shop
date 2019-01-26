package soft.co.books.configuration.error;

import java.util.ArrayList;
import java.util.List;

public class CustomizeException extends RuntimeException {

    private String message;
    private List<String> params = new ArrayList<>();

    public CustomizeException() {
    }

    public CustomizeException(String message) {
        super(message);
        this.message = message;
    }

    public CustomizeException(String message, List<String> params) {
        super(message);
        this.message = message;
        this.params = params;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
