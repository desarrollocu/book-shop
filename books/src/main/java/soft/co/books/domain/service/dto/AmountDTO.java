package soft.co.books.domain.service.dto;

public class AmountDTO {

    private double total;
    private String currency;
    private AmountDetailsDTO details;

    public AmountDTO() {
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public AmountDetailsDTO getDetails() {
        return details;
    }

    public void setDetails(AmountDetailsDTO details) {
        this.details = details;
    }
}
