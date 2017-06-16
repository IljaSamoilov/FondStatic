package ee.ilja.samoilov.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class FinanceData {
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
