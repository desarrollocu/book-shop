package soft.co.books.domain.service.dto;

import soft.co.books.configuration.Constants;
import soft.co.books.configuration.security.other.SecurityUtils;
import soft.co.books.domain.collection.Topic;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

/**
 * A DTO representing an topic.
 */
public class TopicDTO {

    private String id;

    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(max = 50, message = Constants.ERR_MAX50)
    private String englishName;

    @NotNull(message = Constants.ERR_NOT_NULL)
    @Size(max = 50, message = Constants.ERR_MAX50)
    private String spanishName;

    private String name;

    public TopicDTO() {
        // Empty constructor needed for Jackson.
    }

    public TopicDTO(Topic topic) {
        this.id = topic.getId();
        this.spanishName = topic.getSpanishName();
        this.englishName = topic.getEnglishName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getSpanishName() {
        return spanishName;
    }

    public void setSpanishName(String spanishName) {
        this.spanishName = spanishName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TopicDTO{" +
                "spanishName='" + spanishName + '\'' +
                "englishName='" + englishName + '\'' +
                '}';
    }
}
