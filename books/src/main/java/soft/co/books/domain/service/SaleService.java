package soft.co.books.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soft.co.books.configuration.database.CustomBaseService;
import soft.co.books.domain.collection.Sale;
import soft.co.books.domain.repository.SaleRepository;
import soft.co.books.domain.service.dto.SaleDTO;

import java.util.Optional;


/**
 * Service class for managing sales.
 */
@Service
@Transactional
public class SaleService extends CustomBaseService<Sale, String> {

    private final Logger log = LoggerFactory.getLogger(SaleService.class);

    private final SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        super(saleRepository);
        this.saleRepository = saleRepository;
    }

    public Optional<SaleDTO> createSale(SaleDTO saleDTO) {
        Sale sale = new Sale();
        sale.setId(saleDTO.getId());
        sale.setDetailList(saleDTO.getDetailList());
        sale.setTotal(saleDTO.getTotal());
        sale.setUser(saleDTO.getUser());

        log.debug("Created Information for Sale: {}", sale);
        return Optional.of(saleRepository.save(sale))
                .map(SaleDTO::new);
    }
}
