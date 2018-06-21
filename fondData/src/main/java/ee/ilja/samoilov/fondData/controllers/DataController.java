package ee.ilja.samoilov.fondData.controllers;

import ee.ilja.samoilov.fondData.dto.FinanceData;
import ee.ilja.samoilov.fondData.service.FinanceDataService;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Ilja on 27.09.2017.
 */
@SuppressWarnings("unused")
@RestController
public class DataController {

//    @Autowired
//    private BasicDataSource basicDataSource;

    @Autowired
    DSLContext dsl;

    @Autowired
    private FinanceDataService financeDataService;

    @RequestMapping(value = "/getLast", method = RequestMethod.GET, produces = "application/json")
    private FinanceData[] getLastFinanceData() {
        System.out.println("asdsa");
        FinanceData[] financeDatas = financeDataService.getCrawledData();
        return financeDatas;
//        try {
//            System.out.println(basicDataSource.getConnection().getClientInfo());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return new FinanceData();
    }

//    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = "application/json")
//    private List<String> getAllFinanceData() {
//        return dsl.select().from(Transactions.TRANSACTIONS).fetch().getValues(Transactions.TRANSACTIONS.BENEFICIARY);
//    }

    @RequestMapping(value = "/updateDatabase")
    private void updateDatabase() {
        financeDataService.saveNewData();
    }
}
