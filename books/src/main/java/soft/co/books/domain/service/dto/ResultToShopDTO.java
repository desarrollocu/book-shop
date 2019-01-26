package soft.co.books.domain.service.dto;

import soft.co.books.domain.collection.Book;
import soft.co.books.domain.collection.Magazine;

public class ResultToShopDTO {

    private String id;
    private double salePrice;
    private String title;
    private String image;
    private int cant;
    private double weight;
    private double totalWeight;
    private int realCant;
    private double mount;
    private boolean book;

    public ResultToShopDTO() {
    }

    public ResultToShopDTO(Book book) {
        this.id = book.getId();
        this.image = book.getImageUrl();
        this.salePrice = book.getSalePrice();
        this.title = book.getTitle();
        this.realCant = book.getStockNumber();
        this.weight = book.getWeight();
        this.book = true;
    }

    public ResultToShopDTO(Magazine magazine) {
        this.id = magazine.getId();
        this.image = magazine.getImageUrl();
        this.salePrice = magazine.getSalePrice();
        this.title = magazine.getTitle();
        this.realCant = magazine.getStockNumber();
        this.weight = magazine.getWeight();
        this.book = false;
    }

    public boolean getBook() {
        return book;
    }

    public void setBook(boolean book) {
        this.book = book;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getMount() {
        return mount;
    }

    public void setMount(double mount) {
        this.mount = mount;
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

    public int getRealCant() {
        return realCant;
    }

    public void setRealCant(int realCant) {
        this.realCant = realCant;
    }
}
