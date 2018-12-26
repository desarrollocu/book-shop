package soft.co.books.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soft.co.books.configuration.database.CustomBaseService;
import soft.co.books.domain.collection.Sale;
import soft.co.books.domain.repository.SaleRepository;


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
}
