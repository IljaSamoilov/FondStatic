package ee.ilja.samoilov.fondData.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
public class FinanceData {
    private String timeStamp = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date());
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
