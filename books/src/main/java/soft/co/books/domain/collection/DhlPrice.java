package soft.co.books.domain.collection;

import org.springframework.data.annotation.Id;
import soft.co.books.configuration.Constants;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A dhlPrice.
 */
public class DhlPrice implements Serializable {

    @Id
    private String id;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private double minKg;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private double maxKg;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private double price;

    public DhlPrice() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getMinKg() {
        return minKg;
    }

    public void setMinKg(double minKg) {
        this.minKg = minKg;
    }

    public double getMaxKg() {
        return maxKg;
    }

    public void setMaxKg(double maxKg) {
        this.maxKg = maxKg;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
