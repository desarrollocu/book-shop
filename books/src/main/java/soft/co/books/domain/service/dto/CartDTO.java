package soft.co.books.domain.service.dto;

import java.util.ArrayList;
import java.util.List;

public class CartDTO {

    private int cant;
    private double amount;
    private double totalKgs;
    private double shippingCost;
    private List<ResultToShopDTO> shopDTOList = new ArrayList<>();
    private boolean exist;

    public CartDTO() {
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getTotalKgs() {
        return totalKgs;
    }

    public void setTotalKgs(double totalKgs) {
        this.totalKgs = totalKgs;
    }

    public List<ResultToShopDTO> getShopDTOList() {
        return shopDTOList;
    }

    public void setShopDTOList(List<ResultToShopDTO> shopDTOList) {
        this.shopDTOList = shopDTOList;
    }
}
