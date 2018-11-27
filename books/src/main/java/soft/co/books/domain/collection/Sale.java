package soft.co.books.domain.collection;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import soft.co.books.configuration.Constants;
import soft.co.books.configuration.database.AbstractAuditingEntity;
import soft.co.books.domain.collection.data.Detail;
import soft.co.books.domain.collection.data.UserDetail;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
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

    @CreatedDate
    @Field("sale_date")
    @NotNull(message = Constants.ERR_NOT_NULL)
    private Instant saleDate = Instant.now();

    @Field("detail_list")
    @NotNull(message = Constants.ERR_NOT_NULL)
    private List<Detail> detailList;

    @Field("user_detail")
    @NotNull(message = Constants.ERR_NOT_NULL)
    private UserDetail userDetail;

    @Version
    private Long version;

    public Sale() {
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
                ", detailList=" + detailList +
                ", userDetail=" + userDetail +
                ", version=" + version +
                '}';
    }
}
