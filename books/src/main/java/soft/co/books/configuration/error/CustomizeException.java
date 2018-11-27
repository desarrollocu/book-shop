package soft.co.books.configuration.error;

public class CustomizeException extends RuntimeException {

    private String message;

    public CustomizeException() {
    }

    public CustomizeException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
