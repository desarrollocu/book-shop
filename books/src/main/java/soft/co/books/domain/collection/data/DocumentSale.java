package soft.co.books.domain.collection.data;

import org.springframework.data.mongodb.core.mapping.Field;
import soft.co.books.configuration.Constants;
import soft.co.books.domain.collection.Editor;
import soft.co.books.domain.collection.Topic;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class DocumentSale implements Serializable {

    private String documentId;

    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(min = 1, max = 150, message = Constants.ERR_MIN1_MAX150)
    private String title;

    private List<Editor> editorList = new ArrayList<>();

    @Field("sell_price")
    @NotNull(message = Constants.ERR_NOT_NULL)
    private double salePrice;

    @Size(max = 50, message = Constants.ERR_MAX50)
    private String isbn;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private double weight;

    @NotNull(message = Constants.ERR_NOT_NULL)
    @NotEmpty(message = Constants.ERR_NOT_NULL)
    private List<Topic> topicList = new ArrayList<>();

    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(max = 50, message = Constants.ERR_MAX50)
    private String coin = "(USD)";

    @Field("stock_number")
    @NotNull(message = Constants.ERR_NOT_NULL)
    private int stockNumber = 0;

    private String comments;

    public DocumentSale() {
    }

    public DocumentSale(String documentId, String title, List<Editor> editorList,
                        double salePrice, String isbn, double weight,
                        List<Topic> topicList, String coin, int stockNumber,
                        String comments) {

        this.documentId = documentId;
        this.title = title;
        this.editorList = editorList;
        this.salePrice = salePrice;
        this.isbn = isbn;
        this.weight = weight;
        this.topicList = topicList;
        this.coin = coin;
        this.stockNumber = stockNumber;
        this.comments = comments;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Editor> getEditorList() {
        return editorList;
    }

    public void setEditorList(List<Editor> editorList) {
        this.editorList = editorList;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public List<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public int getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(int stockNumber) {
        this.stockNumber = stockNumber;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
