package ee.ilja.samoilov.containers;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class Transaction {

    private BigDecimal sum;

    private String beneficiary;

    private String explanation;

    private Timestamp date;

    public Transaction() {

    }

    public Transaction(BigDecimal sum, String beneficiary, boolean isDebit) {
        this.setSum(sum, isDebit);
       this.beneficiary = beneficiary;
    }

    public void setSum(BigDecimal sum, boolean isDebit) {
        if (isDebit) {
            sum = sum.negate();
        }
        this.sum = sum;
    }
}
