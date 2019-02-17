package soft.co.books.domain.collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Document(collection = "sys_error")
public class CustomError implements Serializable {

    @Id
    private String id;

    private String error;

    private String realError;

    private StackTraceElement[] traceElement;

    public StackTraceElement[] getTraceElement() {
        return traceElement;
    }

    public String getRealError() {
        return realError;
    }

    public void setRealError(String realError) {
        this.realError = realError;
    }

    public void setTraceElement(StackTraceElement[] traceElement) {
        this.traceElement = traceElement;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
