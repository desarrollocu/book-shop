package soft.co.books.domain.service.dto;

import java.io.Serializable;
import java.util.List;

public class PageResultDTO<T> implements Serializable {
    private List<T> elements;
    private long total;

    public PageResultDTO() {
    }

    public PageResultDTO(List<T> elements, long total) {
        this.elements = elements;
        this.total = total;
    }

    public List<T> getElements() {
        return elements;
    }

    public void setElements(List<T> elements) {
        this.elements = elements;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
