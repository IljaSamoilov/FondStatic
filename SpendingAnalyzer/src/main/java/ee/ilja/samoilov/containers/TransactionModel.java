package ee.ilja.samoilov.containers;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class TransactionModel {

    private List<Transaction> transactions = new ArrayList<>();
    private Map<Transaction, Category> transactionDependencies = new HashMap<>();
}
