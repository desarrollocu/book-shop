package soft.co.books.resource;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soft.co.books.domain.service.BookService;
import soft.co.books.domain.service.MagazineService;
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

    public CartResource(BookService bookService, MagazineService magazineService) {
        this.bookService = bookService;
        this.magazineService = magazineService;
    }

    @PostMapping("/searchToShop")
    public List<ResultToShopDTO> searchToShop(@RequestBody List<ProductDTO> productDTOList) {
        List<ResultToShopDTO> result = new ArrayList<>();
        for (ProductDTO productDTO : productDTOList) {
            if (productDTO.isBook()) {
                ResultToShopDTO bookDto = bookService.findOne(productDTO.getId()).map(ResultToShopDTO::new).get();
                bookDto.setCant(productDTO.getCant());
                result.add(bookDto);
            } else {
                ResultToShopDTO magazineDto = magazineService.findOne(productDTO.getId()).map(ResultToShopDTO::new).get();
                magazineDto.setCant(productDTO.getCant());
                result.add(magazineDto);
            }
        }
        return result;
    }
}
