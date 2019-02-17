package soft.co.books.configuration.error;

import java.util.ArrayList;
import java.util.List;

public class CustomizeException extends RuntimeException {

    private String message;
    private String realError;
    private List<String> params = new ArrayList<>();
    private StackTraceElement[] traceElement;

    public CustomizeException() {
    }

    public CustomizeException(String message) {
        super(message);
        this.message = message;
    }

    public CustomizeException(String message, StackTraceElement[] traceElement, String realError) {
        super(message);
        this.message = message;
        this.realError = realError;
        this.traceElement = traceElement;
    }

    public CustomizeException(String message, List<String> params) {
        super(message);
        this.message = message;
        this.params = params;
    }

    public CustomizeException(String message, List<String> params, StackTraceElement[] traceElement) {
        super(message);
        this.message = message;
        this.params = params;
        this.traceElement = traceElement;
    }

    public CustomizeException(String message, List<String> params, StackTraceElement[] traceElement, String realError) {
        super(message);
        this.message = message;
        this.params = params;
        this.realError = realError;
        this.traceElement = traceElement;
    }

    public String getRealError() {
        return realError;
    }

    public void setRealError(String realError) {
        this.realError = realError;
    }

    public StackTraceElement[] getTraceElement() {
        return traceElement;
    }

    public void setTraceElement(StackTraceElement[] traceElement) {
        this.traceElement = traceElement;
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
