package ee.ilja.samoilov.fondData.controllers;

import ee.ilja.samoilov.fondData.dto.FinanceData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * Created by Ilja on 27.09.2017.
 */
@SuppressWarnings("unused")
@RestController
public class DataController {

    @RequestMapping(value = "/getLast", method = RequestMethod.GET, produces = "application/json")
    private FinanceData getLastFinanceData() {
        return new FinanceData();
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = "application/json")
    private ArrayList<FinanceData> getAllFinanceData() {
        return new ArrayList<>();
    }
}
