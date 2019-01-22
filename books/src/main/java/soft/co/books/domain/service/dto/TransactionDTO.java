package soft.co.books.domain.service.dto;

public class TransactionDTO {

    private AmountDTO amount;
    private TransactionItemDTO item_list;

    public TransactionDTO() {
    }

    public AmountDTO getAmount() {
        return amount;
    }

    public void setAmount(AmountDTO amount) {
        this.amount = amount;
    }

    public TransactionItemDTO getItem_list() {
        return item_list;
    }

    public void setItem_list(TransactionItemDTO item_list) {
        this.item_list = item_list;
    }
}
