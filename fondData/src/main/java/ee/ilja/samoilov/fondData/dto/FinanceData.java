package ee.ilja.samoilov.fondData.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
//@Entity
//@Table(name = "financedata")
public class FinanceData implements Serializable {

    private String symbol;
    private Date timestamp;
    private BigDecimal amount;
    private BigDecimal exemplarpurchaseprice;
    private BigDecimal exemplarmarketprice;
    private BigDecimal change;
    private BigDecimal changetoday;
    private BigDecimal profit;
    private BigDecimal totalmarketprice;
    private BigDecimal part;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FinanceData)) return false;
        FinanceData that = (FinanceData) o;
        return Objects.equals(getSymbol(), that.getSymbol()) &&
                Objects.equals(getAmount(), that.getAmount()) &&
                Objects.equals(getExemplarpurchaseprice(), that.getExemplarpurchaseprice()) &&
                Objects.equals(getExemplarmarketprice(), that.getExemplarmarketprice()) &&
                Objects.equals(getChange(), that.getChange()) &&
                Objects.equals(getChangetoday(), that.getChangetoday()) &&
                Objects.equals(getProfit(), that.getProfit()) &&
                Objects.equals(getTotalmarketprice(), that.getTotalmarketprice());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getSymbol(), getAmount(), getExemplarpurchaseprice(), getExemplarmarketprice(), getChange(), getChangetoday(), getProfit(), getTotalmarketprice(), getPart());
    }
}
