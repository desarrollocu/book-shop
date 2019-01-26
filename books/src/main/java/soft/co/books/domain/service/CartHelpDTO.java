package soft.co.books.domain.service;

import soft.co.books.domain.collection.Country;
import soft.co.books.domain.service.dto.ProductDTO;

import java.util.ArrayList;
import java.util.List;

public class CartHelpDTO {

    private List<ProductDTO> productDTOList = new ArrayList<>();
    private Country countryDTO;

    public CartHelpDTO() {
    }

    public List<ProductDTO> getProductDTOList() {
        return productDTOList;
    }

    public void setProductDTOList(List<ProductDTO> productDTOList) {
        this.productDTOList = productDTOList;
    }

    public Country getCountryDTO() {
        return countryDTO;
    }

    public void setCountryDTO(Country countryDTO) {
        this.countryDTO = countryDTO;
    }
}
