package soft.co.books.domain.service.dto;

public class ElementDTO {

    private String url;
    private String position;
    private String left;
    private String documentId;
    private boolean book;

    public ElementDTO() {
    }

    public boolean getBook() {
        return book;
    }

    public void setBook(boolean book) {
        this.book = book;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}
