package ee.ilja.samoilov.fondData.controllers;

import ee.ilja.samoilov.fondData.dto.FinanceData;
import ee.ilja.samoilov.fondData.repository.DataRepository;
import ee.ilja.samoilov.fondData.service.FinanceDataService;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    DataRepository dataRepository;

    @GetMapping(value = "/getLatestInfo", produces = "application/json")
    private List<FinanceData> getLastFinanceData() {
        return dataRepository.getLastFinanceData();
    }

    @RequestMapping(value = "/updateDatabase")
    private void updateDatabase() {
        financeDataService.updateDatabase();
    }

    @GetMapping(value = "/getLastForSymbol")
    private @ResponseBody FinanceData getLast(@RequestParam String symbol) {
        return financeDataService.getLastDataForSymbol(symbol);
    }
    @GetMapping(value = "/getSymbols")
    private @ResponseBody List<String> getSymbols() {
        return financeDataService.getSymbols();
    }
}
