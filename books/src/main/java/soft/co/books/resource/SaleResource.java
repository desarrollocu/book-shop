package soft.co.books.resource;

import io.swagger.annotations.Api;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soft.co.books.configuration.security.other.AuthoritiesConstants;
import soft.co.books.domain.service.SaleService;
import soft.co.books.domain.service.dto.PageResultDTO;
import soft.co.books.domain.service.dto.SaleDTO;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@Api(description = "Sale operations")
public class SaleResource {

    private final SaleService saleService;

    public SaleResource(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping("/sales")
    public PageResultDTO<SaleDTO> sales(@RequestBody SaleDTO saleDTO, Pageable pageable) {
        return saleService.findAll(saleDTO, pageable);
    }

    @PostMapping("/salesByUser")
    public PageResultDTO<SaleDTO> salesByUser(@RequestBody SaleDTO saleDTO, Pageable pageable) {
        return saleService.findAllWithUser(saleDTO, pageable);
    }

    @PostMapping("/sale")
    public SaleDTO sale(@RequestBody SaleDTO saleDTO) {
        return new SaleDTO(saleService.findOne(saleDTO.getId()).get());
    }

    @PostMapping("/updateSale")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.SALE_MANAGEMENT + "\")")
    public SaleDTO updateSale(@Valid @RequestBody SaleDTO saleDTO) {
        return saleService.updateSale(saleDTO).get();
    }
}
