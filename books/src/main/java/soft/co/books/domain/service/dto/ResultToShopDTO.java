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
    private String city;
    private EditorDTO editor;
    private String isbn;
    private TopicDTO topic;
    private double mount;

    public ResultToShopDTO() {
    }

    public ResultToShopDTO(Book book) {
        this.id = book.getId();
        this.image = book.getImageUrl();
        this.salePrice = book.getSalePrice();
        this.title = book.getTitle();
        this.coin = book.getCoin();
        this.city = book.getCity();
        this.editor = book.getEditorOrNull().map(EditorDTO::new).orElse(null);
        this.isbn = book.getIsbn();
        this.topic = book.getTopicOrNull().map(TopicDTO::new).orElse(null);
    }

    public ResultToShopDTO(Magazine magazine) {
        this.id = magazine.getId();
        this.image = magazine.getImageUrl();
        this.salePrice = magazine.getSalePrice();
        this.title = magazine.getTitle();
        this.coin = magazine.getCoin();
        this.city = magazine.getCity();
        this.editor = magazine.getEditorOrNull().map(EditorDTO::new).orElse(null);
        this.isbn = magazine.getIsbn();
        this.topic = magazine.getTopicOrNull().map(TopicDTO::new).orElse(null);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public EditorDTO getEditor() {
        return editor;
    }

    public void setEditor(EditorDTO editor) {
        this.editor = editor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public TopicDTO getTopic() {
        return topic;
    }

    public void setTopic(TopicDTO topic) {
        this.topic = topic;
    }

    public double getMount() {
        return mount;
    }

    public void setMount(double mount) {
        this.mount = mount;
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
