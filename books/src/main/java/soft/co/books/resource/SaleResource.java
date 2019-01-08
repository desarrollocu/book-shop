package soft.co.books.resource;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soft.co.books.domain.service.SaleService;
import soft.co.books.domain.service.dto.SaleDTO;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@Api(description = "Sale operations")
public class SaleResource {

    private final Logger log = LoggerFactory.getLogger(SaleResource.class);

    private final SaleService saleService;

    public SaleResource(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping("/saveSale")
    public SaleDTO saveAuthor(@Valid @RequestBody SaleDTO saleDTO) {
        return saleService.createSale(saleDTO).get();
    }
}
