package ee.ilja.samoilov.fondData.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
public class FinanceData implements Serializable {
    @Column
    private String symbol;
    @Column
    private String timeStamp;
    @Column
    private BigDecimal amount;
    @Column
    private BigDecimal exemplarPurchasePrice;
    @Column
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
}
