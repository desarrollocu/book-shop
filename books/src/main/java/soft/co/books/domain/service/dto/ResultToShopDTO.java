package soft.co.books.domain.service.dto;

import soft.co.books.domain.collection.Book;
import soft.co.books.domain.collection.Magazine;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ResultToShopDTO {

    private String id;
    private double salePrice;
    private String title;
    private String image;
    private String coin;
    private int cant;
    private int realCant;
    private List<EditorDTO> editorList = new ArrayList<>();
    private String isbn;
    private List<TopicDTO> topicList = new ArrayList<>();
    private double mount;

    public ResultToShopDTO() {
    }

    public ResultToShopDTO(Book book) {
        this.id = book.getId();
        this.image = book.getImageUrl();
        this.salePrice = book.getSalePrice();
        this.title = book.getTitle();
        this.coin = book.getCoin();
        this.editorList = book.getEditorList().stream().map(EditorDTO::new).collect(Collectors.toList());
        this.isbn = book.getIsbn();
        this.topicList = book.getTopicList().stream().map(TopicDTO::new).collect(Collectors.toList());
        this.realCant = book.getStockNumber();
    }

    public ResultToShopDTO(Magazine magazine) {
        this.id = magazine.getId();
        this.image = magazine.getImageUrl();
        this.salePrice = magazine.getSalePrice();
        this.title = magazine.getTitle();
        this.coin = magazine.getCoin();
        this.editorList = magazine.getEditorList().stream().map(EditorDTO::new).collect(Collectors.toList());
        this.isbn = magazine.getIsbn();
        this.topicList = magazine.getTopicList().stream().map(TopicDTO::new).collect(Collectors.toList());
        this.realCant = magazine.getStockNumber();
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

    public List<EditorDTO> getEditorList() {
        return editorList;
    }

    public void setEditorList(List<EditorDTO> editorList) {
        this.editorList = editorList;
    }

    public List<TopicDTO> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<TopicDTO> topicList) {
        this.topicList = topicList;
    }

    public int getRealCant() {
        return realCant;
    }

    public void setRealCant(int realCant) {
        this.realCant = realCant;
    }
}
