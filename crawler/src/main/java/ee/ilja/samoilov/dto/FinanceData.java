package ee.ilja.samoilov.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Data
@NoArgsConstructor
public class FinanceData {
    private Date timestamp = Calendar.getInstance().getTime();
    private String symbol;
    private BigDecimal amount;
    private BigDecimal exemplarpurchaseprice;
    private BigDecimal exemplarmarketprice;
    private BigDecimal change;
    private BigDecimal changetoday;
    private BigDecimal profit;
    private BigDecimal totalmarketprice;
    private BigDecimal part;
}
