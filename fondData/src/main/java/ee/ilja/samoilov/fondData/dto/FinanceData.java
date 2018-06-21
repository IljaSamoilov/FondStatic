package ee.ilja.samoilov.fondData.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Table(name = "financedata")
public class FinanceData implements Serializable {
    @Column(unique = true)
    private String symbol;
    @Column(name = "timestamp")
    private Timestamp timeStamp;
    @Column
    private BigDecimal amount;
    @Column(name = "exemplarpurchaseprice")
    private BigDecimal exemplarPurchasePrice;
    @Column(name = "exemplarmarketprice")
    private BigDecimal exemplarMarketPrice;
    @Column
    private BigDecimal change;
    @Column
    private BigDecimal changeToday;
    @Column
    private BigDecimal profit;
    @Column
    private BigDecimal totalMarketPrice;
    @Column
    private BigDecimal part;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FinanceData)) return false;
        FinanceData that = (FinanceData) o;
        return Objects.equals(getSymbol(), that.getSymbol()) &&
                Objects.equals(getAmount(), that.getAmount()) &&
                Objects.equals(getExemplarPurchasePrice(), that.getExemplarPurchasePrice()) &&
                Objects.equals(getExemplarMarketPrice(), that.getExemplarMarketPrice()) &&
                Objects.equals(getChange(), that.getChange()) &&
                Objects.equals(getChangeToday(), that.getChangeToday()) &&
                Objects.equals(getProfit(), that.getProfit()) &&
                Objects.equals(getTotalMarketPrice(), that.getTotalMarketPrice()) &&
                Objects.equals(getPart(), that.getPart());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getSymbol(), getAmount(), getExemplarPurchasePrice(), getExemplarMarketPrice(), getChange(), getChangeToday(), getProfit(), getTotalMarketPrice(), getPart());
    }
}
