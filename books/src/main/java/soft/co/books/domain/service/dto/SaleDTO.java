package soft.co.books.domain.service.dto;

import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Field;
import soft.co.books.configuration.Constants;
import soft.co.books.domain.collection.Sale;
import soft.co.books.domain.collection.data.Detail;
import soft.co.books.domain.collection.data.UserDetail;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * A DTO representing a sale.
 */
public class SaleDTO implements Serializable {

    private String id;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private double total;

    private Date saleDate;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private List<Detail> detailList;

    private UserDetail userDetail;

    @Version
    private Long version;

    public SaleDTO() {
    }

    public SaleDTO(Sale sale) {
        this.id = sale.getId();
        this.total = sale.getTotal();
        this.detailList = sale.getDetailList();
        this.saleDate = Date.from(sale.getSaleDate());
        this.userDetail = sale.getUserDetail();
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

    public List<Detail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<Detail> detailList) {
        this.detailList = detailList;
    }

    public UserDetail getUser() {
        return userDetail;
    }

    public void setUser(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
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
                "total=" + total +
                ", detailList=" + detailList +
                ", userDetail=" + userDetail +
                ", version=" + version +
                '}';
    }
}
