package ee.ilja.samoilov.fondData.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
public class Results {
    private BigDecimal total = BigDecimal.ZERO;
    private BigDecimal profit = BigDecimal.ZERO;
    private Date timestamp = new Date();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Results)) return false;

        Results results = (Results) o;

        if (!getTotal().equals(results.getTotal())) return false;
        return getProfit().equals(results.getProfit());
    }

    @Override
    public int hashCode() {
        int result = getTotal().hashCode();
        result = 31 * result + getProfit().hashCode();
        return result;
    }

    public void update(BigDecimal price, BigDecimal fondsProfit) {
        this.total = total.add(price);
        this.profit = profit.add(fondsProfit);
    }
}
