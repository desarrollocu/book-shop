package soft.co.books.configuration.paypal;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soft.co.books.configuration.Constants;
import soft.co.books.configuration.error.CustomizeException;
import soft.co.books.domain.collection.Book;
import soft.co.books.domain.collection.data.UserDetail;
import soft.co.books.domain.service.BookService;
import soft.co.books.domain.service.SaleService;
import soft.co.books.domain.service.dto.*;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PayPalClient {

    private final SaleService saleService;
    private final BookService bookService;

    public PayPalClient(SaleService saleService, BookService bookService) {
        this.saleService = saleService;
        this.bookService = bookService;
    }

    String clientId = "Ae3kB7nSbmR3Ty9NKg6bIrHHU64mt0hZutwVG5Wz80tpQsn2HTblJeoKA2nJQPOUXjGYUA1nxidsCUGu";
    String clientSecret = "EFF3bZ-n4Ro6rwQjowFrz3Q1mwNU2y_QpznwhUyK2lHSQp9c2yCFHKAbq_wKdQJqAtXXK9vrmk-eUfrR";

    public PaymentDTO createPayment(TransactionDTO transactionDTO) {
        PaymentDTO paymentDTO = new PaymentDTO();

        Amount amount = new Amount();
        amount.setCurrency(transactionDTO.getAmount().getCurrency());
        amount.setTotal(String.format("%.2f", transactionDTO.getAmount().getTotal()));

        AmountDetailsDTO detailsDTO = transactionDTO.getAmount().getDetails();

        Details details = new Details();
        details.setSubtotal(String.format("%.2f", detailsDTO.getSubtotal()));
        details.setShipping(String.format("%.2f", detailsDTO.getShipping()));
        details.setTax(String.format("%.2f", detailsDTO.getTax()));
        details.setInsurance(String.format("%.2f", detailsDTO.getInsurance()));
        details.setShippingDiscount(String.format("%.2f", detailsDTO.getShipping_discount()));
        details.setHandlingFee(String.format("%.2f", detailsDTO.getHandling_fee()));
        amount.setDetails(details);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);

        ItemList itemList = new ItemList();
        itemList.setItems(new ArrayList<>());
        for (ItemDTO itemDTO : transactionDTO.getItem_list().getItems()) {
            Item item = new Item();
            item.setCurrency(itemDTO.getCurrency());
            item.setName(itemDTO.getName());
            item.setPrice(String.format("%.2f", itemDTO.getPrice()));
            item.setQuantity(String.valueOf(itemDTO.getQuantity()));
            item.setTax(String.format("%.2f", itemDTO.getTax()));
            item.setSku(String.valueOf(itemDTO.getSku()));
            itemList.getItems().add(item);
        }

        ShippingAddressDTO addressDTO = transactionDTO.getItem_list().getShipping_address();
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setRecipientName(addressDTO.getRecipient_name());
        shippingAddress.setPhone(addressDTO.getPhone());
        shippingAddress.setCity(addressDTO.getCity());
        shippingAddress.setCountryCode(addressDTO.getCountry_code());
        shippingAddress.setLine1(addressDTO.getLine1());
        shippingAddress.setLine2(addressDTO.getLine2());
        shippingAddress.setPostalCode(addressDTO.getPostal_code());
        shippingAddress.setState(addressDTO.getState());

        itemList.setShippingAddress(shippingAddress);

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
            paymentDTO.getSaleDTO().getDetailList().forEach(detail -> {
                Book book = bookService.findOne(detail.getId()).get();
                if (book != null) {
                    if (book.getStockNumber() < detail.getCant()) {
                        throw new CustomizeException("error.E68");
                    }
                }
            });
            Payment payment = new Payment();
            payment.setId(paymentDTO.getPaymentID());

            PaymentExecution paymentExecution = new PaymentExecution();
            paymentExecution.setPayerId(paymentDTO.getPlayerID());
            try {
                APIContext context = new APIContext(clientId, clientSecret, "sandbox");
                Payment createdPayment = payment.execute(context, paymentExecution);

                if (createdPayment != null) {
                    SaleDTO saleDTO = paymentDTO.getSaleDTO();
                    UserDetail userDetail = new UserDetail();
                    userDetail.setFirstName(createdPayment.getPayer().getPayerInfo().getFirstName());
                    userDetail.setLastName(createdPayment.getPayer().getPayerInfo().getLastName());

                    PayerInfo payerInfo = createdPayment.getPayer().getPayerInfo();
                    if (payerInfo != null) {
                        ShippingAddress address = payerInfo.getShippingAddress();
                        if (address != null) {
                            String line1 = address.getLine1() != null ? address.getLine1() : "";
                            String line2 = address.getLine2() != null ? address.getLine2() : "";
                            String city = address.getCity() != null ? address.getCity() : "";
                            String postalCode = address.getPostalCode() != null ? address.getPostalCode() : "";
                            String countryCode = address.getCountryCode() != null ? address.getCountryCode() : "";
                            String addressString = line1 + " " + line2 + " " + city + " " + postalCode + " " + countryCode;
                            userDetail.setAddress(addressString);
                            userDetail.setRecipientName(address.getRecipientName());
                            userDetail.setPhone(address.getPhone() != null ? address.getPhone() : "");
                        }

                        userDetail.setEmail(payerInfo.getEmail() != null ? payerInfo.getEmail() : "");
                    }

                    saleDTO.setUser(userDetail);
                    saleService.createSale(saleDTO);
                }
            } catch (PayPalRESTException e) {
                System.err.println(e.getDetails());
            }
        }
    }
}
