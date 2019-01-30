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
import soft.co.books.domain.service.CartServices;
import soft.co.books.domain.service.CountryService;
import soft.co.books.domain.service.MagazineService;
import soft.co.books.domain.service.dto.CartDTO;
import soft.co.books.domain.service.dto.ProductDTO;
import soft.co.books.domain.service.dto.ResultToShopDTO;
import soft.co.books.domain.service.session.CartSession;
import soft.co.books.domain.service.session.ShippingSession;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api(description = "Shopping car operations")
public class CartResource {

    private final BookService bookService;

    private final MagazineService magazineService;

    private final CountryService countryService;

    private final CartServices cartServices;

    public CartResource(BookService bookService,
                        CountryService countryService,
                        CartServices cartServices,
                        MagazineService magazineService) {
        this.bookService = bookService;
        this.countryService = countryService;
        this.cartServices = cartServices;
        this.magazineService = magazineService;
    }

    @PostMapping("/removeSession")
    public CartDTO removeSession(@RequestBody ProductDTO productDTO) {
        CartDTO cartDTO = new CartDTO();
        cartServices.clearSession();
        return cartDTO;
    }

    @PostMapping("/addToCart")
    public CartDTO addToCart(@RequestBody ProductDTO productDTO) {
        CartSession cartSession = new CartSession();
        cartSession.setId(productDTO.getId());
        cartSession.setBook(productDTO.getBook());
        cartSession.setCant(productDTO.getCant());
        return cartServices.addToCart(cartSession);
    }

    @PostMapping("/removeFormCart")
    public CartDTO removeFormCart(@RequestBody ProductDTO productDTO) {
        CartSession cartSession = new CartSession();
        cartSession.setId(productDTO.getId());
        cartSession.setBook(productDTO.getBook());
        cartSession.setCant(productDTO.getCant());
        return cartServices.removeFormCart(cartSession);
    }

    @PostMapping("/elementsInCart")
    public CartDTO elementsInCart() {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCant(cartServices.elementsInCart());
        return cartDTO;
    }

    @PostMapping("/addShippingInfo")
    public ShippingSession addShippingInfo(@RequestBody ShippingSession infoDTO) {
        return cartServices.addShippingInfo(infoDTO);
    }

    @PostMapping("/getShippingInfo")
    public ShippingSession getShippingInfo() {
        return cartServices.getShippingInfo();
    }

    @PostMapping("/searchToShop")
    public CartDTO searchToShop() {
        int cant = 0;
        double amount = 0;
        double totalKgs = 0;
        double shippingCost = 0;
        CartDTO cartDTO = new CartDTO();
        List<ResultToShopDTO> result = new ArrayList<>();
        List<CartSession> sessionList = cartServices.cartSessionList();
        ShippingSession shippingInfo = cartServices.getShippingInfo();

        if (sessionList != null) {
            for (CartSession cartSession : sessionList) {
                ResultToShopDTO resultToShopDTO;
                if (cartSession.getBook()) {
                    resultToShopDTO = bookService.findOne(cartSession.getId()).map(ResultToShopDTO::new).get();
                    resultToShopDTO.setBook(true);
                } else {
                    resultToShopDTO = magazineService.findOne(cartSession.getId()).map(ResultToShopDTO::new).get();
                    resultToShopDTO.setBook(false);
                }

                if (resultToShopDTO.getRealCant() < cartSession.getCant())
                    throw new CustomizeException("error.E68");

                cant += cartSession.getCant();
                resultToShopDTO.setCant(cartSession.getCant());
                resultToShopDTO.setMount(cartSession.getCant() * resultToShopDTO.getSalePrice());
                resultToShopDTO.setTotalWeight(cartSession.getCant() * resultToShopDTO.getWeight());
                amount += resultToShopDTO.getMount();
                totalKgs += resultToShopDTO.getTotalWeight();
                result.add(resultToShopDTO);
            }
        }

        if (shippingInfo != null) {
            Country country = shippingInfo.getCountry();
            if (country != null) {
                for (DhlPrice dhlPrice : country.getPriceList()) {
                    if (totalKgs > dhlPrice.getMinKg() && totalKgs <= dhlPrice.getMaxKg()) {
                        shippingCost = dhlPrice.getPrice();
                        break;
                    }
                    if (totalKgs > 20) {
                        shippingCost = country.getPriceList().get(country.getPriceList().size() - 1).getPrice();
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
