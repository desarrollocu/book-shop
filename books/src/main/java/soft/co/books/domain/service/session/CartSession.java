package soft.co.books.domain.service.session;

import java.io.Serializable;
import java.util.Objects;

public class CartSession implements Serializable {

    private String id;
    private boolean book;
    private int cant;
    private double price;

    public CartSession() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartSession cartSession = (CartSession) o;
        return !(cartSession.getId() == null || getId() == null) && Objects.equals(getId(), cartSession.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
