package soft.co.books.domain.collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import soft.co.books.configuration.Constants;
import soft.co.books.configuration.SaleState;
import soft.co.books.configuration.database.AbstractAuditingEntity;
import soft.co.books.domain.collection.data.Detail;
import soft.co.books.domain.collection.data.UserSale;
import soft.co.books.domain.service.session.ShippingSession;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * A sale.
 */
@Document(collection = "bs_sale")
public class Sale extends AbstractAuditingEntity implements Serializable {

    @Id
    private String id;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private double total;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private double shippingCost;

    @Field("detail_list")
    @NotNull(message = Constants.ERR_NOT_NULL)
    @NotEmpty(message = Constants.ERR_NOT_NULL)
    private List<Detail> detailList = new ArrayList<>();

    private UserSale user;

    private ShippingSession shipping;

    private String info;

    private SaleState saleState;

    private String description;

    @JsonIgnore
    @CreatedDate
    @Field("sale_date")
    @NotNull(message = Constants.ERR_NOT_NULL)
    private String saleDate = new SimpleDateFormat("yyyy-MM-dd").format(Date.from(Instant.now()));

    @Version
    private Long version;

    public Sale() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public List<Detail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<Detail> detailList) {
        this.detailList = detailList;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public UserSale getUser() {
        return user;
    }

    public void setUser(UserSale user) {
        this.user = user;
    }

    public ShippingSession getShipping() {
        return shipping;
    }

    public void setShipping(ShippingSession shipping) {
        this.shipping = shipping;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public SaleState getSaleState() {
        return saleState;
    }

    public void setSaleState(SaleState saleState) {
        this.saleState = saleState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sale sale = (Sale) o;
        return !(sale.getId() == null || getId() == null) && Objects.equals(getId(), sale.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Sale{" +
                "total=" + total +
                ", shippingCost=" + shippingCost +
                ", detailList=" + detailList +
                ", user=" + user +
                ", info=" + info +
                ", saleState=" + saleState +
                ", version=" + version +
                '}';
    }
}
