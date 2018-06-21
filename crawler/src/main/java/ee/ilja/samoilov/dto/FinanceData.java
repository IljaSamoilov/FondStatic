package ee.ilja.samoilov.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
public class FinanceData {
    private Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
    private String symbol;
    private BigDecimal amount;
    private BigDecimal exemplarPurchasePrice;
    private BigDecimal exemplarMarketPrice;
    private BigDecimal change;
    private BigDecimal changeToday;
    private BigDecimal profit;
    private BigDecimal totalMarketPrice;
    private BigDecimal part;
}
