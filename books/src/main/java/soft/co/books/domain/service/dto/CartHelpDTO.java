package soft.co.books.domain.service.dto;

import java.util.ArrayList;
import java.util.List;

public class CartHelpDTO {

    private List<ProductDTO> productDTOList = new ArrayList<>();

    public CartHelpDTO() {
    }

    public List<ProductDTO> getProductDTOList() {
        return productDTOList;
    }

    public void setProductDTOList(List<ProductDTO> productDTOList) {
        this.productDTOList = productDTOList;
    }
}
