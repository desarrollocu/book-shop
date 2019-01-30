package soft.co.books.domain.service.dto;

import soft.co.books.configuration.Constants;
import soft.co.books.configuration.SaleState;
import soft.co.books.domain.collection.Sale;
import soft.co.books.domain.collection.data.Detail;
import soft.co.books.domain.collection.data.UserSale;
import soft.co.books.domain.service.session.ShippingSession;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO representing a sale.
 */
public class SaleDTO implements Serializable {

    private String id;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private double total;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private double shippingCost;

    private String items;

    private UserSale user;

    private ShippingSession shipping;

    private String info;

    private SaleState saleState;

    private String description;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private String saleDate;

    public SaleDTO() {
    }

    public SaleDTO(Sale sale) {
        this.id = sale.getId();
        this.total = sale.getTotal();
        this.shippingCost = sale.getShippingCost();
        this.user = sale.getUser();
        this.shipping = sale.getShipping();
        this.info = sale.getInfo();
        this.saleState = sale.getSaleState();
        this.saleDate = sale.getSaleDate();
        this.description = sale.getDescription();

        this.items = "";
        for (Detail detail : sale.getDetailList()) {
            if (this.items.equals(""))
                this.items += detail.getDocument().getTitle();
            else
                this.items += "," + detail.getDocument().getTitle();
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
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

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SaleDTO sale = (SaleDTO) o;
        return !(sale.getId() == null || getId() == null) && Objects.equals(getId(), sale.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SaleDTO{" +
                ", total=" + total +
                ", shippingCost=" + shippingCost +
                ", user=" + user +
                ", shipping=" + shipping +
                ", info=" + info +
                ", saleState=" + saleState +
                ", saleDate=" + saleDate +
                '}';
    }
}
