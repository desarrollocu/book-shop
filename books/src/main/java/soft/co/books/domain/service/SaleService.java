package soft.co.books.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soft.co.books.configuration.database.CustomBaseService;
import soft.co.books.domain.collection.Book;
import soft.co.books.domain.collection.Sale;
import soft.co.books.domain.collection.data.Detail;
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

    private final BookService bookService;

    private final BookTraceService bookTraceService;

    public SaleService(SaleRepository saleRepository, BookService bookService, BookTraceService bookTraceService) {
        super(saleRepository);
        this.saleRepository = saleRepository;
        this.bookService = bookService;
        this.bookTraceService = bookTraceService;
    }

    public Optional<SaleDTO> createSale(SaleDTO saleDTO) {
        Sale sale = new Sale();
        sale.setId(saleDTO.getId());
        sale.setDetailList(saleDTO.getDetailList());
        sale.setTotal(saleDTO.getTotal());
        sale.setUser(saleDTO.getUser());

        /**
         * Remove cant from book stock*/
        for (Detail detail : saleDTO.getDetailList()) {
            Book book = bookService.findOne(detail.getId()).get();
            if (book != null) {
                int cant = book.getStockNumber() - detail.getCant();
                book.setStockNumber(cant);
                bookService.save(book);
                log.debug("Changed Information for Book: {}", book);
            }
        }

        log.debug("Created Information for Sale: {}", sale);
        return Optional.of(saleRepository.save(sale))
                .map(SaleDTO::new);
    }
}
