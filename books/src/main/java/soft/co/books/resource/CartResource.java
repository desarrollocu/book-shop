package soft.co.books.resource;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soft.co.books.configuration.Constants;
import soft.co.books.configuration.error.CustomizeException;
import soft.co.books.domain.collection.Book;
import soft.co.books.domain.collection.Magazine;
import soft.co.books.domain.service.BookService;
import soft.co.books.domain.service.CartServices;
import soft.co.books.domain.service.MagazineService;
import soft.co.books.domain.service.UIDataService;
import soft.co.books.domain.service.dto.CartDTO;
import soft.co.books.domain.service.dto.ProductDTO;
import soft.co.books.domain.service.dto.ResultToShopDTO;
import soft.co.books.domain.service.session.CartSession;
import soft.co.books.domain.service.session.ShippingSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Api(description = "Shopping car operations")
public class CartResource {

    private final BookService bookService;

    private final MagazineService magazineService;

    private final CartServices cartServices;

    private final UIDataService uiDataService;

    public CartResource(BookService bookService,
                        CartServices cartServices,
                        UIDataService uiDataService,
                        MagazineService magazineService) {
        this.bookService = bookService;
        this.cartServices = cartServices;
        this.uiDataService = uiDataService;
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

        if (sessionList != null) {
            for (CartSession cartSession : sessionList) {
                ResultToShopDTO resultToShopDTO;
                if (cartSession.getBook()) {
                    Book book = bookService.findByIdAndVisible(cartSession.getId(), true);
                    if (book == null)
                        throw new CustomizeException(Constants.ERR_NOT_BOOK);
                    resultToShopDTO = new ResultToShopDTO(book);
                    resultToShopDTO.setBook(true);
                } else {
                    Magazine magazine = magazineService.findByIdAndVisible(cartSession.getId(), true);
                    if (magazine == null)
                        throw new CustomizeException(Constants.ERR_NOT_BOOK);
                    resultToShopDTO = new ResultToShopDTO(magazine);
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

        Map<String, Object> resultMap = cartServices.shippingData(totalKgs, shippingCost, null);
        totalKgs = (double) resultMap.get("totalKgs");
        shippingCost = (double) resultMap.get("shippingCost");

        String shippingCostString = String.format("%.2f", shippingCost).replace(",", ".");
        double shippingCostDouble = Double.valueOf(shippingCostString);
        cartDTO.setShippingCost(shippingCostDouble);
        cartDTO.setTotalKgs(totalKgs);

        String amountString = String.format("%.2f", (amount + cartDTO.getShippingCost())).replace(",", ".");
        double amountDouble = Double.valueOf(amountString);
        cartDTO.setAmount(amountDouble);
        cartDTO.setCant(cant);
        cartDTO.setShopDTOList(result);
        return cartDTO;
    }
}
