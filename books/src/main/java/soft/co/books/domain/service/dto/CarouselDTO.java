package soft.co.books.domain.service.dto;

import java.util.List;

public class CarouselDTO {

    List<ElementDTO> elementList;

    public CarouselDTO() {
    }

    public List<ElementDTO> getElementList() {
        return elementList;
    }

    public void setElementList(List<ElementDTO> elementList) {
        this.elementList = elementList;
    }
}
