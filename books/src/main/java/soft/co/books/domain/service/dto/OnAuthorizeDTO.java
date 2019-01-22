package soft.co.books.domain.service.dto;

public class OnAuthorizeDTO {

    private String paymentID;
    private String payerID;

    public OnAuthorizeDTO() {
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getPayerID() {
        return payerID;
    }

    public void setPayerID(String payerID) {
        this.payerID = payerID;
    }
}
