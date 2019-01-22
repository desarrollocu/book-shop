package soft.co.books.domain.service.dto;

import java.util.ArrayList;
import java.util.List;

public class TransactionItemDTO {

    private List<ItemDTO> items = new ArrayList<>();

    public TransactionItemDTO() {
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }
}
