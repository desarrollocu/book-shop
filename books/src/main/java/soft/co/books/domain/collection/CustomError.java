package soft.co.books.domain.collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Document(collection = "sys_error")
public class CustomError implements Serializable {

    @Id
    private String id;

    private String error;

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
