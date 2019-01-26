package soft.co.books.domain.collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import soft.co.books.configuration.Constants;
import soft.co.books.configuration.database.AbstractAuditingEntity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A country.
 */
@Document(collection = "bs_country")
public class Country extends AbstractAuditingEntity implements Serializable {

    @Id
    private String id;

    @Field("spanish_name")
    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(max = 50, message = Constants.ERR_MAX50)
    private String spanishName;

    @Field("english_name")
    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(max = 50, message = Constants.ERR_MAX50)
    private String englishName;

    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(max = 50, message = Constants.ERR_MAX50)
    private String code;

    @NotNull(message = Constants.ERR_NOT_NULL)
    private String group;

    @NotNull(message = Constants.ERR_NOT_NULL)
    @NotEmpty(message = Constants.ERR_NOT_NULL)
    List<DhlPrice> priceList = new ArrayList<>();

    @Version
    private Long version;

    public Country() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpanishName() {
        return spanishName;
    }

    public void setSpanishName(String spanishName) {
        this.spanishName = spanishName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<DhlPrice> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<DhlPrice> priceList) {
        this.priceList = priceList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country = (Country) o;
        return !(country.getId() == null || getId() == null) && Objects.equals(getId(), country.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "country{" +
                "spanishName='" + spanishName + '\'' +
                ", englishName='" + englishName + '\'' +
                ", code='" + code + '\'' +
                ", group='" + group + '\'' +
                ", version=" + version +
                '}';
    }
}
