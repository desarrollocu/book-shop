package soft.co.books.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soft.co.books.configuration.Constants;
import soft.co.books.configuration.database.CustomBaseService;
import soft.co.books.configuration.error.CustomizeException;
import soft.co.books.configuration.security.other.SecurityUtils;
import soft.co.books.domain.collection.Sale;
import soft.co.books.domain.collection.User;
import soft.co.books.domain.repository.SaleRepository;
import soft.co.books.domain.service.dto.PageResultDTO;
import soft.co.books.domain.service.dto.SaleDTO;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;


/**
 * Service class for managing sales.
 */
@Service
@Transactional
public class SaleService extends CustomBaseService<Sale, String> {

    private final Logger log = LoggerFactory.getLogger(SaleService.class);

    private final SaleRepository saleRepository;

    private final UserService userService;

    private MongoTemplate mongoTemplate;

    public SaleService(SaleRepository saleRepository,
                       UserService userService,
                       MongoTemplate mongoTemplate) {
        super(saleRepository);
        this.saleRepository = saleRepository;
        this.userService = userService;
        this.mongoTemplate = mongoTemplate;
    }

    public PageResultDTO findAll(SaleDTO saleDTO, Pageable pageable) {
        PageResultDTO<SaleDTO> resultDTO = new PageResultDTO<>();

        Query query = new Query();
        query.limit(pageable.getPageSize());
        query.skip(pageable.getPageNumber() * pageable.getPageSize());
        query.with(pageable.getSort());

        if (saleDTO.getUser() != null)
            query.addCriteria(where("user.userId").is(saleDTO.getUser().getUserId()));
        if (saleDTO.getSaleState() != null) {
            query.addCriteria(where("saleState").is(saleDTO.getSaleState()));
        }

        Page<Sale> sales = new PageImpl<>(mongoTemplate.find(query, Sale.class));
        resultDTO.setElements(sales.stream().map(SaleDTO::new).collect(Collectors.toList()));
        resultDTO.setTotal(mongoTemplate.count(query, Sale.class));
        return resultDTO;
    }

    public PageResultDTO findAllWithUser(SaleDTO saleDTO, Pageable pageable) {
        PageResultDTO<SaleDTO> resultDTO = new PageResultDTO<>();

        User user = null;
        String username = SecurityUtils.getCurrentUserLogin().get();
        if (username != null && !username.toLowerCase().equals("AnonymousUser".toLowerCase())) {
            user = userService.findOneByUserName(username).get();
        }

        if (user == null)
            throw new CustomizeException(Constants.ERR_SERVER);

        Query query = new Query();
        query.limit(pageable.getPageSize());
        query.skip(pageable.getPageNumber() * pageable.getPageSize());
        query.with(pageable.getSort());

        query.addCriteria(where("user.userId").is(user.getId()));

        if (saleDTO.getSaleDate() != null) {
            query.addCriteria(where("saleDate").is(saleDTO.getSaleDate()));
        }

        Page<Sale> sales = new PageImpl<>(mongoTemplate.find(query, Sale.class));
        resultDTO.setElements(sales.stream().map(SaleDTO::new).collect(Collectors.toList()));
        resultDTO.setTotal(mongoTemplate.count(query, Sale.class));
        return resultDTO;
    }

    public Optional<SaleDTO> updateSale(SaleDTO saleDTO) {
        return Optional.of(saleRepository
                .findById(saleDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(sale -> {
                    sale.setSaleState(saleDTO.getSaleState());
                    sale.setDescription(saleDTO.getDescription());
                    saleRepository.save(sale);
                    log.debug("Changed Information for Sale: {}", sale);
                    return sale;
                })
                .map(SaleDTO::new);
    }

    public void delete(String id) {
        saleRepository.findById(id).ifPresent(sale -> {
            saleRepository.delete(sale);
            log.debug("Deleted Sale: {}", sale);
        });
    }
}
