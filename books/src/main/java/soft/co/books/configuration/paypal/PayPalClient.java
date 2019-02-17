package soft.co.books.configuration.paypal;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soft.co.books.configuration.Constants;
import soft.co.books.configuration.SaleState;
import soft.co.books.configuration.error.CustomizeException;
import soft.co.books.configuration.security.other.SecurityUtils;
import soft.co.books.domain.collection.*;
import soft.co.books.domain.collection.Sale;
import soft.co.books.domain.collection.data.*;
import soft.co.books.domain.service.*;
import soft.co.books.domain.service.dto.PaymentDTO;
import soft.co.books.domain.service.session.CartSession;
import soft.co.books.domain.service.session.ShippingSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PayPalClient {

    private final UserService userService;
    private final SaleService saleService;
    private final BookService bookService;
    private final CartServices cartServices;
    private final MagazineService magazineService;

    public PayPalClient(SaleService saleService,
                        CartServices cartServices,
                        UserService userService,
                        MagazineService magazineService,
                        BookService bookService) {
        this.userService = userService;
        this.saleService = saleService;
        this.bookService = bookService;
        this.cartServices = cartServices;
        this.magazineService = magazineService;
    }

    String clientId = "Ae3kB7nSbmR3Ty9NKg6bIrHHU64mt0hZutwVG5Wz80tpQsn2HTblJeoKA2nJQPOUXjGYUA1nxidsCUGu";
    String clientSecret = "EFF3bZ-n4Ro6rwQjowFrz3Q1mwNU2y_QpznwhUyK2lHSQp9c2yCFHKAbq_wKdQJqAtXXK9vrmk-eUfrR";

    public PaymentDTO createPayment() {
        double amountTotal = 0;
        double totalKgs = 0;
        double shippingCost = 0;
        PaymentDTO paymentDTO = new PaymentDTO();

        ItemList itemList = new ItemList();
        itemList.setItems(new ArrayList<>());

        Map<String, Object> map = creatingItems(amountTotal, totalKgs, itemList);
        amountTotal = (double) map.get("amountTotal");
        itemList = (ItemList) map.get("itemList");
        totalKgs = (double) map.get("totalKgs");

        Map<String, Object> result = cartServices.shippingData(totalKgs, shippingCost, itemList);
        shippingCost = (double) result.get("shippingCost");
        itemList = (ItemList) result.get("itemList");

        Amount amount = new Amount();
        amount.setCurrency("USD");

        double totalDouble = amountTotal + shippingCost;
        String totalDoubleString = String.format("%.2f", totalDouble).replace(",", ".");
        amount.setTotal(totalDoubleString);

        Details details = new Details();
        details.setSubtotal(String.format("%.2f", amountTotal).replace(",", "."));

        String shippingCostString = String.format("%.2f", shippingCost).replace(",", ".");
        details.setShipping(shippingCostString);

        details.setTax("0");
        details.setInsurance("0");
        details.setShippingDiscount("0");
        details.setHandlingFee("0");
        amount.setDetails(details);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setItemList(itemList);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("/");
        redirectUrls.setReturnUrl("/");
        payment.setRedirectUrls(redirectUrls);
        Payment createdPayment;
        try {
            APIContext context = new APIContext(clientId, clientSecret, "sandbox");
            createdPayment = payment.create(context);
            paymentDTO.setPaymentID(createdPayment.getId());
        } catch (PayPalRESTException e) {
            System.out.println("Error happened during payment creation!");
            if (e.getResponsecode() == 400) {
                List<String> params = new ArrayList<>();
                for (ErrorDetails errorDetails : e.getDetails().getDetails()) {
                    if (errorDetails.getField().equals("city") || errorDetails.getField().equals("state")
                            || errorDetails.getField().equals("zip") || errorDetails.getField().equals("line1")
                            || errorDetails.getField().equals("line2"))
                        params.add(errorDetails.getField());
                }
                throw new CustomizeException(Constants.ERR_PAYPAL_DATA, params, e.getStackTrace(), e.getMessage());
            } else
                throw new CustomizeException(Constants.ERR_PAYPAL_CONEX, e.getStackTrace(), e.getMessage());
        }

        return paymentDTO;
    }

    private Map<String, Object> creatingItems(double amountTotal, double totalKgs, ItemList itemList) {
        Map<String, Object> result = new HashMap<>();
        List<CartSession> sessionList = cartServices.cartSessionList();
        if (sessionList != null) {
            for (CartSession cartSession : sessionList) {
                Document document;
                if (cartSession.getBook()) {
                    document = bookService.findByIdAndVisible(cartSession.getId(), true);
                } else {
                    document = magazineService.findByIdAndVisible(cartSession.getId(), true);
                }

                if (document == null)
                    throw new CustomizeException(Constants.ERR_NOT_BOOK);

                if (document.getStockNumber() < cartSession.getCant())
                    throw new CustomizeException("error.E68");

                amountTotal += (cartSession.getCant() * document.getSalePrice());
                totalKgs += (document.getWeight() * cartSession.getCant());

                Item item = new Item();
                item.setCurrency("USD");
                item.setName(document.getTitle());
                item.setPrice(String.format("%.2f", document.getSalePrice()).replace(",", "."));
                item.setQuantity(String.valueOf(cartSession.getCant()));
                item.setTax("0");
                item.setSku("0");
                itemList.getItems().add(item);
            }
        }

        result.put("amountTotal", amountTotal);
        result.put("totalKgs", totalKgs);
        result.put("itemList", itemList);
        return result;
    }

    public void completePayment(PaymentDTO paymentDTO) {
        synchronized (this) {
            Sale sale = new Sale();
            ShippingSession shippingSession = cartServices.getShippingInfo();
            List<CartSession> cartSessionList = cartServices.cartSessionList();

            try {
                double total = 0;
                double totalKgs = 0;
                double shippingCost = 0;
                List<Detail> detailList = new ArrayList<>();
                Map<String, Object> objectMap = createDetailList(cartSessionList, total, totalKgs, detailList);
                cartSessionList = (List<CartSession>) objectMap.get("cartSessionList");
                total = (double) objectMap.get("total");
                detailList = (List<Detail>) objectMap.get("detailList");
                totalKgs = (double) objectMap.get("totalKgs");

                Map<String, Object> result = cartServices.shippingData(totalKgs, shippingCost, null);
                shippingCost = (double) result.get("shippingCost");

                String totalString = String.format("%.2f", total).replace(",", ".");
                double totalDouble = Double.valueOf(totalString);
                sale.setTotal(totalDouble);

                sale.setSaleState(SaleState.inProcess);
                sale.setDetailList(detailList);
                sale.setShipping(shippingSession);

                String shippingCostString = String.format("%.2f", shippingCost).replace(",", ".");
                double shippingCostDouble = Double.valueOf(shippingCostString);
                sale.setShippingCost(shippingCostDouble);

                String username = SecurityUtils.getCurrentUserLogin().get();
                if (username != null && !username.toLowerCase().equals("AnonymousUser".toLowerCase())) {
                    User user = userService.findOneByUserName(username).get();
                    sale.setUser(new UserSale(user));
                }

                sale = saleService.save(sale);

                Payment payment = new Payment();
                payment.setId(paymentDTO.getPaymentID());

                PaymentExecution paymentExecution = new PaymentExecution();
                paymentExecution.setPayerId(paymentDTO.getPlayerID());

                APIContext context = new APIContext(clientId, clientSecret, "sandbox");
                Payment createdPayment = payment.execute(context, paymentExecution);

                if (createdPayment != null) {
                    cartServices.clearCartSessionList();
                }
            } catch (Exception e) {
                rollBack(e, sale, cartSessionList);
            }
        }
    }

    private Map<String, Object> createDetailList(List<CartSession> cartSessionList, double total,
                                                 double totalKgs, List<Detail> detailList) {
        Map<String, Object> objectMap = new HashMap<>();

        for (CartSession info : cartSessionList) {
            Document document;
            DocumentSale documentSale;
            if (info.getBook()) {
                document = bookService.findByIdAndVisible(info.getId(), true);
                documentSale = new BookSale((Book) document);
            } else {
                document = magazineService.findByIdAndVisible(info.getId(), true);
                documentSale = new MagazineSale((Magazine) document);
            }

            if (document != null) {
                if (document.getStockNumber() < info.getCant()) {
                    throw new CustomizeException("error.E68");
                } else {
                    Detail detail = new Detail();
                    detail.setCant(info.getCant());
                    detail.setDocument(documentSale);

                    String mountString = String.format("%.2f", (info.getCant() * document.getSalePrice())).replace(",", ".");
                    double mountDouble = Double.valueOf(mountString);
                    detail.setMount(mountDouble);

                    total += detail.getMount();

                    String totalKgsString = String.format("%.2f", (info.getCant() * document.getWeight())).replace(",", ".");
                    double totalKgsDouble = Double.valueOf(totalKgsString);
                    totalKgs += totalKgsDouble;

                    detailList.add(detail);

                    int cant = document.getStockNumber() - detail.getCant();
                    document.setStockNumber(cant);

                    if (info.getBook()) {
                        bookService.save((Book) document);
                    } else {
                        magazineService.save((Magazine) document);
                    }
                    info.setPass(true);
                }
            } else
                throw new CustomizeException(Constants.ERR_NOT_BOOK);
        }

        objectMap.put("cartSessionList", cartSessionList);
        objectMap.put("total", total);
        objectMap.put("totalKgs", totalKgs);
        objectMap.put("detailList", detailList);
        return objectMap;
    }

    private void rollBack(Exception ex, Sale sale, List<CartSession> cartSessionList) {
        if (ex instanceof PayPalRESTException)
            System.err.println(((PayPalRESTException) ex).getDetails());

        if (sale.getId() != null && !sale.getId().isEmpty()) {
            saleService.delete(sale);
        }

        for (CartSession info : cartSessionList) {
            if (info.getPass()) {
                Document document;
                if (info.getBook())
                    document = bookService.findByIdAndVisible(info.getId(), true);
                else
                    document = magazineService.findByIdAndVisible(info.getId(), true);

                if (document != null) {
                    int cant = document.getStockNumber() + info.getCant();
                    document.setStockNumber(cant);

                    if (info.getBook()) {
                        bookService.save((Book) document);
                    } else {
                        magazineService.save((Magazine) document);
                    }
                } else
                    throw new CustomizeException(Constants.ERR_NOT_BOOK);
            }
        }

        if (ex instanceof CustomizeException)
            throw (CustomizeException) ex;
        else
            throw new CustomizeException(Constants.ERR_PAYPAL_DATA, ex.getStackTrace(), ex.getMessage());
    }
}
