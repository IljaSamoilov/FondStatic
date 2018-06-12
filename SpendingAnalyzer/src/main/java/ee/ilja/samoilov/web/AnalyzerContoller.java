package ee.ilja.samoilov.web;

import ee.ilja.samoilov.containers.Category;
import ee.ilja.samoilov.containers.Status;
import ee.ilja.samoilov.containers.Transaction;
import ee.ilja.samoilov.containers.TransactionModel;
import ee.ilja.samoilov.service.DataProcessingHandler;
import org.jooq.DSLContext;
import org.jooq.tools.csv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static ee.ilja.samoilov.data.tables.Transactions.TRANSACTIONS;
import static ee.ilja.samoilov.data.tables.Categories.CATEGORIES;

/**
 * Created by Ilja on 9.01.2018.
 */
@RestController
public class AnalyzerContoller {

    @Autowired
    DataProcessingHandler dataProcessingHandler;

    @Autowired
    DSLContext dsl;

    @PostMapping("/upload") // //new annotation since 4.3
    public @ResponseBody
    Status singleFileUpload(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return new Status(false, "Empty file");
        }
        try {
            //here create a container for data
            ArrayList<Transaction> transactions = new ArrayList<>();
            CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()), ';', '"');
            while (csvReader.hasNext()) {
                dataProcessingHandler.parseTransaction(csvReader.next(), transactions);
            }
            dataProcessingHandler.sendTransactions(transactions);
            return new Status(true, "");
        } catch (IOException e) {
            e.printStackTrace();
            return new Status(false, e.getMessage());
        }
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = "application/json")
    private List<Transaction> getAllTransacrions() {
        return dsl.select().from(TRANSACTIONS).fetch().into(Transaction.class);
    }

    /**
     * @param month
     * @return
     */
    @GetMapping("/getTransactionsForMonth")
    private @ResponseBody List<Transaction> getMonthTransaction(@RequestParam int month) {
        List<Transaction> transactions = dsl.select().from(TRANSACTIONS).fetch().into(Transaction.class);
        return transactions.stream().filter(transaction -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date((transaction.getDate()).getTime()));
            return calendar.get(Calendar.MONTH) == month - 1;
        }).collect(Collectors.toList());
    }

    /**
     * TODO
     * @param category
     * @return
     */
    @PostMapping("/updateCategory")
    private @ResponseBody Status updateCategoryForBeneficiary(@RequestBody Category category) {
        dsl.update(CATEGORIES)
                .set(CATEGORIES.CATEGORY, category.getCategory())
                .where(CATEGORIES.BENEFICIARY.eq(category.getBeneficiary()))
                .executeAsync();
        return new Status(true, "OK");
    }

    /**
     * TODO
     * @return
     */
    @PostMapping("/model")
    private @ResponseBody TransactionModel getTransactionModel() {
        return new TransactionModel();
    }

    /**
     * @return
     */
    @GetMapping("/getEmptyCategories")
    private @ResponseBody List<Category> getAllBeneficiariesWithOutCategory() {
        return dsl.select().from(CATEGORIES).where(CATEGORIES.CATEGORY.eq("").or(CATEGORIES.CATEGORY.isNull())).fetch().into(Category.class);
    }
}
