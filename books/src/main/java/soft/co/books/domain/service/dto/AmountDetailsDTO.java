package soft.co.books.domain.service.dto;

public class AmountDetailsDTO {

    private double subtotal;
    private double tax;
    private double shipping;
    private double handling_fee;
    private double shipping_discount;
    private double insurance;

    public AmountDetailsDTO() {
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getShipping() {
        return shipping;
    }

    public void setShipping(double shipping) {
        this.shipping = shipping;
    }

    public double getHandling_fee() {
        return handling_fee;
    }

    public void setHandling_fee(double handling_fee) {
        this.handling_fee = handling_fee;
    }

    public double getShipping_discount() {
        return shipping_discount;
    }

    public void setShipping_discount(double shipping_discount) {
        this.shipping_discount = shipping_discount;
    }

    public double getInsurance() {
        return insurance;
    }

    public void setInsurance(double insurance) {
        this.insurance = insurance;
    }
}
