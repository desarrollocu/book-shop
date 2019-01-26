package soft.co.books.domain.service.dto;

import java.util.ArrayList;
import java.util.List;

public class TransactionItemDTO {

    private List<ItemDTO> items = new ArrayList<>();
    private ShippingAddressDTO shipping_address;

    public TransactionItemDTO() {
    }

    public ShippingAddressDTO getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(ShippingAddressDTO shipping_address) {
        this.shipping_address = shipping_address;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }
}
