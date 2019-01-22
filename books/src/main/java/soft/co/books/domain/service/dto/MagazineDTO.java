package soft.co.books.domain.service.dto;

import soft.co.books.configuration.Constants;
import soft.co.books.domain.collection.Magazine;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A DTO representing an magazine.
 */
public class MagazineDTO {

    private String id;

    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(min = 1, max = 150, message = Constants.ERR_MIN1_MAX150)
    private String title;

    private List<EditorDTO> editorList = new ArrayList<>();

    @Size(max = 50, message = Constants.ERR_MAX50)
    private String isbn;

    private String frequency;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private List<TopicDTO> topicList = new ArrayList<>();

    @NotNull(message = Constants.ERR_NOT_NULL)
    private double salePrice;

    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(max = 50, message = Constants.ERR_MAX50)
    private String coin;

    private String image;

    private String publishYear;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private int stockNumber;

    private Long visit = 0L;

    private ShowDTO toShow;

    private String topicsEnglish;

    private String topicsSpanish;

    private String editors;

    private String cities;

    public MagazineDTO() {
        // Empty constructor needed for Jackson.
    }

    public MagazineDTO(Magazine magazine) {
        this.id = magazine.getId();
        this.title = magazine.getTitle();

        this.editorList = magazine.getEditorList().stream().map(EditorDTO::new).collect(Collectors.toList());
        this.editors = "";
        this.cities = "";
        for (int i = 0; i < this.editorList.size(); i++) {
            if (this.editors.equals(""))
                this.editors = this.editorList.get(i).getName();
            else
                this.editors += ", " + this.editorList.get(i).getName();

            if (this.cities.equals(""))
                this.cities = this.editorList.get(i).getCity();
            else
                this.cities += ", " + this.editorList.get(i).getCity();
        }

        this.topicList = magazine.getTopicList().stream().map(TopicDTO::new).collect(Collectors.toList());
        this.topicsEnglish = "";
        this.topicsSpanish = "";
        for (int i = 0; i < this.topicList.size(); i++) {
            if (this.topicsEnglish.equals(""))
                this.topicsEnglish = this.topicList.get(i).getEnglishName();
            else
                this.topicsEnglish += ", " + this.topicList.get(i).getEnglishName();

            if (this.topicsSpanish.equals(""))
                this.topicsSpanish = this.topicList.get(i).getSpanishName();
            else
                this.topicsSpanish += ", " + this.topicList.get(i).getSpanishName();
        }

        this.publishYear = magazine.getPublishYear();
        this.frequency = magazine.getFrequency();
        this.isbn = magazine.getIsbn();
        this.salePrice = magazine.getSalePrice();
        this.stockNumber = magazine.getStockNumber();
        ShowDTO showDTO = new ShowDTO();
        showDTO.setElem(magazine.isToShow() == true ? "user.yes" : "user.no");
        showDTO.setVal(magazine.isToShow());
        this.toShow = showDTO;
        this.coin = magazine.getCoin();
        this.image = magazine.getImageUrl();
        this.visit = magazine.getVisit();
    }

    public ShowDTO getToShow() {
        return toShow;
    }

    public void setToShow(ShowDTO toShow) {
        this.toShow = toShow;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(String publishYear) {
        this.publishYear = publishYear;
    }

    public int getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(int stockNumber) {
        this.stockNumber = stockNumber;
    }

    public Long getVisit() {
        return visit;
    }

    public void setVisit(Long visit) {
        this.visit = visit;
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

    public String getTopicsEnglish() {
        return topicsEnglish;
    }

    public void setTopicsEnglish(String topicsEnglish) {
        this.topicsEnglish = topicsEnglish;
    }

    public String getTopicsSpanish() {
        return topicsSpanish;
    }

    public void setTopicsSpanish(String topicsSpanish) {
        this.topicsSpanish = topicsSpanish;
    }

    public String getEditors() {
        return editors;
    }

    public void setEditors(String editors) {
        this.editors = editors;
    }

    public String getCities() {
        return cities;
    }

    public void setCities(String cities) {
        this.cities = cities;
    }

    @Override
    public String toString() {
        return "MagazineDTO{" +
                "title='" + title + '\'' +
                ", coin='" + coin + '\'' +
                ", image='" + image + '\'' +
                ", salePrice=" + salePrice +
                ", publishYear='" + publishYear + '\'' +
                ", stockNumber=" + stockNumber +
                ", visit=" + visit +
                '}';
    }
}
