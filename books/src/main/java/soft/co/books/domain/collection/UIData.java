package soft.co.books.domain.collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import soft.co.books.configuration.Constants;
import soft.co.books.configuration.database.AbstractAuditingEntity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A UIData.
 */
@Document(collection = "sys_ui_data")
public class UIData extends AbstractAuditingEntity implements Serializable {

    @Id
    private String id;

    @Field("name_english")
    @NotNull(message = Constants.ERR_NOT_NULL)
    private String nameEnglish;

    @Field("name_spanish")
    @NotNull(message = Constants.ERR_NOT_NULL)
    private String nameSpanish;

    @Field("main_text_english")
    private String mainTextEnglish;

    @Field("main_text_spanish")
    private String mainTextSpanish;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private String address;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private String phone;

    private String twitter;

    private String facebook;

    private String instagram;

    private String google;

    @Field("shipment_percent")
    @NotNull(message = Constants.ERR_NOT_NULL)
    private double shipmentPercent = 1;

    @Email
    @NotNull(message = Constants.ERR_NOT_NULL)
    private String email;

    public UIData() {
    }

    public UIData(UIData uiData) {
        this.id = uiData.getId();
        this.address = uiData.getAddress();
        this.email = uiData.getEmail();
        this.mainTextEnglish = uiData.getMainTextEnglish();
        this.mainTextSpanish = uiData.getMainTextSpanish();
        this.nameEnglish = uiData.getNameEnglish();
        this.nameSpanish = uiData.getNameSpanish();
        this.phone = uiData.getPhone();
        this.twitter = uiData.getTwitter();
        this.facebook = uiData.getFacebook();
        this.instagram = uiData.getInstagram();
        this.google = uiData.getGoogle();
        this.shipmentPercent = uiData.getShipmentPercent();
    }

    public double getShipmentPercent() {
        return shipmentPercent;
    }

    public void setShipmentPercent(double shipmentPercent) {
        this.shipmentPercent = shipmentPercent;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getGoogle() {
        return google;
    }

    public void setGoogle(String google) {
        this.google = google;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameEnglish() {
        return nameEnglish;
    }

    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }

    public String getNameSpanish() {
        return nameSpanish;
    }

    public void setNameSpanish(String nameSpanish) {
        this.nameSpanish = nameSpanish;
    }

    public String getMainTextEnglish() {
        return mainTextEnglish;
    }

    public void setMainTextEnglish(String mainTextEnglish) {
        this.mainTextEnglish = mainTextEnglish;
    }

    public String getMainTextSpanish() {
        return mainTextSpanish;
    }

    public void setMainTextSpanish(String mainTextSpanish) {
        this.mainTextSpanish = mainTextSpanish;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
