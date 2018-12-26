package soft.co.books.domain.service.dto;

import soft.co.books.domain.collection.Book;
import soft.co.books.domain.collection.Magazine;

public class ResultToShopDTO {

    private String id;
    private double salePrice;
    private String title;
    private String image;
    private String coin;
    private int cant;

    public ResultToShopDTO() {
    }

    public ResultToShopDTO(Book book) {
        this.id = book.getId();
        this.image = book.getImageUrl();
        this.salePrice = book.getSalePrice();
        this.title = book.getTitle();
        this.coin = book.getCoin();
    }

    public ResultToShopDTO(Magazine magazine) {
        this.id = magazine.getId();
        this.image = magazine.getImageUrl();
        this.salePrice = magazine.getSalePrice();
        this.title = magazine.getTitle();
        this.coin = magazine.getCoin();
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
