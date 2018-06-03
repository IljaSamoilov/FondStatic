package ee.ilja.samoilov.service;

import ee.ilja.samoilov.constants.Constants;
import ee.ilja.samoilov.containers.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Ilja on 13.01.2018.
 */
@Service
public class DataProcessingHandler {

    public void parseTransaction(String[] line, ArrayList<Transaction> transactions) {
        if (line[2].equals(Constants.TRANSACTION)) {
            boolean isDebit = line[7].equals(Constants.DEBIT);
            transactions.add(new Transaction(new BigDecimal(line[5]), line[3], isDebit));
        }
    }

    public void sendTransactions(ArrayList<Transaction> transactions) {
        //TODO send transaction list to database
    }
}
