package soft.co.books.resource;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soft.co.books.configuration.Constants;
import soft.co.books.configuration.error.CustomizeException;
import soft.co.books.domain.collection.Country;
import soft.co.books.domain.collection.DhlPrice;
import soft.co.books.domain.service.BookService;
import soft.co.books.domain.service.CartHelpDTO;
import soft.co.books.domain.service.CountryService;
import soft.co.books.domain.service.MagazineService;
import soft.co.books.domain.service.dto.CartDTO;
import soft.co.books.domain.service.dto.ProductDTO;
import soft.co.books.domain.service.dto.ResultToShopDTO;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api(description = "Shopping car operations")
public class CartResource {

    private final BookService bookService;

    private final MagazineService magazineService;

    private final CountryService countryService;

    public CartResource(BookService bookService,
                        CountryService countryService,
                        MagazineService magazineService) {
        this.bookService = bookService;
        this.countryService = countryService;
        this.magazineService = magazineService;
    }

    @PostMapping("/searchToShop")
    public CartDTO searchToShop(@RequestBody CartHelpDTO cartHelpDTO) {
        CartDTO cartDTO = new CartDTO();
        List<ResultToShopDTO> result = new ArrayList<>();
        double totalKgs = 0;
        double amount = 0;
        int cant = 0;
        double shippingCost = 0;

        for (ProductDTO productDTO : cartHelpDTO.getProductDTOList()) {
            ResultToShopDTO resultToShopDTO;
            if (productDTO.isBook()) {
                resultToShopDTO = bookService.findOne(productDTO.getId()).map(ResultToShopDTO::new).get();
            } else {
                resultToShopDTO = magazineService.findOne(productDTO.getId()).map(ResultToShopDTO::new).get();
            }

            if (resultToShopDTO.getRealCant() < productDTO.getCant())
                throw new CustomizeException("error.E68");

            cant += productDTO.getCant();
            resultToShopDTO.setCant(productDTO.getCant());
            resultToShopDTO.setMount(productDTO.getCant() * resultToShopDTO.getSalePrice());
            resultToShopDTO.setTotalWeight(productDTO.getCant() * resultToShopDTO.getWeight());
            amount += resultToShopDTO.getMount();
            totalKgs += resultToShopDTO.getTotalWeight();

            result.add(resultToShopDTO);
        }

        if (cartHelpDTO.getCountryDTO() != null) {
            Country country = countryService.findOne(cartHelpDTO.getCountryDTO().getId()).get();
            if (country != null) {
                for (DhlPrice dhlPrice : country.getPriceList()) {
                    if (totalKgs > dhlPrice.getMinKg() && totalKgs <= dhlPrice.getMaxKg()) {
                        shippingCost = dhlPrice.getPrice();
                        break;
                    }
                }
            } else
                throw new CustomizeException(Constants.ERR_SERVER);
        }

        cartDTO.setShippingCost(Double.valueOf(String.format("%.2f", shippingCost)));
        cartDTO.setTotalKgs(totalKgs);
        cartDTO.setAmount(Double.valueOf(String.format("%.2f", amount + cartDTO.getShippingCost())));
        cartDTO.setCant(cant);
        cartDTO.setShopDTOList(result);
        return cartDTO;
    }
}
