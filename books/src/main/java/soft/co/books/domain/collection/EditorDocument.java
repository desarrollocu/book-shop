package soft.co.books.domain.collection;

import java.io.Serializable;

public class EditorDocument implements Serializable {

    private String id;
    private String name;

    public EditorDocument() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
