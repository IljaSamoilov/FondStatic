package ee.ilja.samoilov.fondData.repository;

import ee.ilja.samoilov.fondData.dto.FinanceData;
import ee.ilja.samoilov.fondData.service.FinanceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Service
public class DataRepository {

    private List<FinanceData> lastFinanceData;
    private Calendar registeredTime;

    @Autowired
    FinanceDataService financeDataService;

    public List<FinanceData> getLastFinanceData() {
        Calendar now = Calendar.getInstance();
        if (lastFinanceData != null && registeredTime != null) {
            if (now.getTime().getTime() - registeredTime.getTime().getTime() < 3600000) {
                System.out.println("RETURNING OLD FINANCE DATA FROM REPOSITORY");
                return lastFinanceData;
            }

        }
        System.out.println("REGISTERING NEW FINANCE DATA TO REPOSITORY");
        registeredTime = now;
        lastFinanceData = Arrays.asList(financeDataService.getCrawledData());
        return lastFinanceData;
    }
}
