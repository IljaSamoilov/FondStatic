package ee.ilja.samoilov.fondData.controllers;

import ee.ilja.samoilov.fondData.dto.FinanceData;
import ee.ilja.samoilov.fondData.dto.Results;
import ee.ilja.samoilov.fondData.repository.DataRepository;
import ee.ilja.samoilov.fondData.service.FinanceDataService;
import ee.ilja.samoilov.fondData.service.ResultsService;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Ilja on 27.09.2017.
 */
@SuppressWarnings("unused")
@CrossOrigin(origins ={"http://localhost:8080","http://localhost:8082", "https://fondstatic.herokuapp.com"})
@RestController
public class DataController {

//    @Autowired
//    private BasicDataSource basicDataSource;

    @Autowired
    DSLContext dsl;

    @Autowired
    private FinanceDataService financeDataService;

    @Autowired
    private ResultsService resultsService;

    @Autowired
    DataRepository dataRepository;

    @GetMapping(value = "/getAllData")
    private List<FinanceData> getAllFinanceData() {
        return financeDataService.getAllData();
    }

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

    @GetMapping(value = "/getAllDataForSymbol")
    private @ResponseBody List<FinanceData> getAllDataForSymbol(@RequestParam String symbol) {
        return financeDataService.getAllDataForSymbol(symbol);
    }

    @GetMapping(value = "getResults")
    private @ResponseBody List<Results> getResults() {
        return resultsService.getResults();
    }

    @GetMapping(value = "getLastResults")
    private @ResponseBody Results getLastResults() {
        return resultsService.getLastResults();
    }
}
