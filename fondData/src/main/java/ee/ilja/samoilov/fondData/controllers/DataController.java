package ee.ilja.samoilov.fondData.controllers;

import ee.ilja.samoilov.fondData.dto.FinanceData;
import ee.ilja.samoilov.fondData.service.FinanceDataService;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Ilja on 27.09.2017.
 */
@SuppressWarnings("unused")
@RestController
public class DataController {

    @Autowired
    private BasicDataSource basicDataSource;

    @Autowired
    private FinanceDataService financeDataService;

    @RequestMapping(value = "/getLast", method = RequestMethod.GET, produces = "application/json")
    private FinanceData getLastFinanceData() {
        System.out.println("asdsa");
        return financeDataService.performRequest();
//        try {
//            System.out.println(basicDataSource.getConnection().getClientInfo());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return new FinanceData();
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = "application/json")
    private ArrayList<FinanceData> getAllFinanceData() {
        return new ArrayList<>();
    }
}
