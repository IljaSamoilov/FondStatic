package ee.ilja.samoilov.containers;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Transaction {

    private BigDecimal sum;

    private String benificiary;

    private 

    public Transaction(BigDecimal sum, String benificiary, boolean isDebit) {
        this.setSum(sum, isDebit);
       this.benificiary = benificiary;
    }

    public void setSum(BigDecimal sum, boolean isDebit) {
        if (isDebit) {
            sum = sum.negate();
        }
        this.sum = sum;
    }
}
