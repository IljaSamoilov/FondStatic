package ee.ilja.samoilov.service;

import ee.ilja.samoilov.constants.Constants;
import ee.ilja.samoilov.containers.Transaction;
import ee.ilja.samoilov.data.tables.Transactions;
import ee.ilja.samoilov.data.tables.records.TransactionsRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ilja on 13.01.2018.
 */
@Service
public class DataProcessingHandler {

    @Autowired
    DSLContext dsl;

    public void parseTransaction(String[] line, ArrayList<Transaction> transactions) {
        if (line != null) {
            String expression = line[0].replaceAll("\"", "");
            String[] data = expression.split(";");
            if (data[1].equals(Constants.TRANSACTION)) {
                boolean isDebit = data[7].equals(Constants.DEBIT);
                transactions.add(new Transaction(new BigDecimal(data[5].replace(",", ".")),
                        data[3], isDebit, convertStringToTimestamp(data[2])));
            }
        }
    }

    public void sendTransactions(ArrayList<Transaction> transactions) {
        for (Transaction transaction: transactions) {
            TransactionsRecord transactionsRecord = dsl.newRecord(Transactions.TRANSACTIONS, transaction);
            dsl.executeInsert(transactionsRecord);
        }
    }

    public static Timestamp convertStringToTimestamp(String str_date) {
        try {
            DateFormat formatter;
            formatter = new SimpleDateFormat("dd.MM.yyyy");
            Date date = formatter.parse(str_date);
            return new Timestamp(date.getTime());
        } catch (ParseException e) {
            System.out.println("Exception :" + e);
            return null;
        }
    }
}
