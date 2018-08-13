package ee.ilja.samoilov.fondData.utility;

import ee.ilja.samoilov.fondData.dto.FinanceData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataUtility {

    public static List<String> extractSymbols(List<FinanceData> financeDataList) {
        return financeDataList.stream()
                .map(FinanceData::getSymbol)
                .collect(Collectors.toList());
    }
}
