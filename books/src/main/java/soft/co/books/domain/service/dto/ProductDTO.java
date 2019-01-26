package soft.co.books.domain.service.dto;

/**
 * A DTO representing a product.
 */
public class ProductDTO {

    private String id;
    private boolean book;
    private int cant;

    public ProductDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getBook() {
        return book;
    }

    public void setBook(boolean book) {
        this.book = book;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }
}
