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
import java.util.List;

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
        ShippingSession shippingInfo = cartServices.getShippingInfo();
        List<CartSession> sessionList = cartServices.cartSessionList();

        ItemList itemList = new ItemList();
        itemList.setItems(new ArrayList<>());

        if (sessionList != null) {
            for (CartSession cartSession : sessionList) {
                Document document;
                if (cartSession.getBook()) {
                    document = bookService.findOne(cartSession.getId()).get();
                } else {
                    document = magazineService.findOne(cartSession.getId()).get();
                }

                if (document.getStockNumber() < cartSession.getCant())
                    throw new CustomizeException("error.E68");

                amountTotal += (cartSession.getCant() * document.getSalePrice());
                totalKgs += document.getWeight();

                Item item = new Item();
                item.setCurrency("USD");
                item.setName(document.getTitle());
                item.setPrice(String.format("%.2f", document.getSalePrice()));
                item.setQuantity(String.valueOf(cartSession.getCant()));
                item.setTax("0");
                item.setSku("0");
                itemList.getItems().add(item);
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

            ShippingAddress shippingAddress = new ShippingAddress();
            shippingAddress.setRecipientName(shippingInfo.getFullName());
            shippingAddress.setPhone(shippingInfo.getPhone());
            shippingAddress.setCity(shippingInfo.getCity());
            shippingAddress.setCountryCode(shippingInfo.getCountry().getCode());
            shippingAddress.setLine1(shippingInfo.getAddress());
            shippingAddress.setLine2("");
            shippingAddress.setPostalCode(shippingInfo.getPostalCode());
            shippingAddress.setState(shippingInfo.getState() != null ? shippingInfo.getState() : "");
            itemList.setShippingAddress(shippingAddress);
        }

        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(String.format("%.2f", amountTotal + shippingCost));

        Details details = new Details();
        details.setSubtotal(String.format("%.2f", amountTotal));
        details.setShipping(String.format("%.2f", shippingCost));
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
                            || errorDetails.getField().equals("zip") || errorDetails.getField().equals("line1"))
                        params.add(errorDetails.getField());
                }
                throw new CustomizeException(Constants.ERR_PAYPAL_DATA, params);
            } else
                throw new CustomizeException(Constants.ERR_PAYPAL_CONEX);
        }

        return paymentDTO;
    }

    public void completePayment(PaymentDTO paymentDTO) {
        synchronized (this) {
            Sale sale = new Sale();
            ShippingSession shippingSession = cartServices.getShippingInfo();
            List<CartSession> cartSessionList = cartServices.cartSessionList();

            try {
                double total = 0;
                List<Detail> detailList = new ArrayList<>();

                double totalKgs = 0;
                double shippingCost = 0;

                for (CartSession info : cartSessionList) {
                    Document document;
                    DocumentSale documentSale;
                    if (info.getBook()) {
                        document = bookService.findOne(info.getId()).get();
                        documentSale = new BookSale((Book) document);
                    } else {
                        document = magazineService.findOne(info.getId()).get();
                        documentSale = new MagazineSale((Magazine) document);
                    }

                    if (document != null) {
                        if (document.getStockNumber() < info.getCant()) {
                            throw new CustomizeException("error.E68");
                        } else {
                            Detail detail = new Detail();
                            detail.setCant(info.getCant());
                            detail.setDocument(documentSale);
                            detail.setMount(Double.valueOf(String.format("%.2f", info.getCant() * document.getSalePrice())));
                            total += detail.getMount();
                            totalKgs += Double.valueOf(String.format("%.2f", info.getCant() * document.getWeight()));
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
                    }
                }

                if (shippingSession != null) {
                    Country country = shippingSession.getCountry();
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

                sale.setSaleState(SaleState.inProcess);
                sale.setTotal(Double.valueOf(String.format("%.2f", total)));
                sale.setDetailList(detailList);
                sale.setShipping(shippingSession);
                sale.setShippingCost(Double.valueOf(String.format("%.2f", shippingCost)));

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
                if (e instanceof PayPalRESTException)
                    System.err.println(((PayPalRESTException) e).getDetails());

                if (sale.getId() != null && !sale.getId().isEmpty()) {
                    saleService.delete(sale);
                }

                for (CartSession info : cartSessionList) {
                    if (info.getPass()) {
                        Document document;
                        if (info.getBook())
                            document = bookService.findOne(info.getId()).get();
                        else
                            document = magazineService.findOne(info.getId()).get();

                        if (document != null) {
                            int cant = document.getStockNumber() + info.getCant();
                            document.setStockNumber(cant);

                            if (info.getBook()) {
                                bookService.save((Book) document);
                            } else {
                                magazineService.save((Magazine) document);
                            }
                        }
                    }
                }

                throw new CustomizeException(e.getMessage());
            }
        }
    }
}
