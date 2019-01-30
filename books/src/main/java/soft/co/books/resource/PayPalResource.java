package soft.co.books.resource;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import soft.co.books.configuration.paypal.PayPalClient;
import soft.co.books.domain.service.dto.PaymentDTO;
import soft.co.books.domain.service.dto.TransactionDTO;

@RestController
@RequestMapping("/api")
@Api(description = "PayPal operations")
public class PayPalResource {

    private final PayPalClient payPalClient;

    @Autowired
    PayPalResource(PayPalClient payPalClient) {
        this.payPalClient = payPalClient;
    }

    @PostMapping("/createPayment")
    public PaymentDTO makePayment(@RequestBody TransactionDTO transactionDTO) {
        return payPalClient.createPayment();
    }

    @PostMapping(value = "/executePayment")
    public ResponseEntity<Void> completePayment(@RequestBody PaymentDTO paymentDTO) {
        payPalClient.completePayment(paymentDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
