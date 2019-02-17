package soft.co.books.domain.collection;

import java.io.Serializable;

public class AuthorBook implements Serializable {

    private String id;
    private String authorName;

    public AuthorBook() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
