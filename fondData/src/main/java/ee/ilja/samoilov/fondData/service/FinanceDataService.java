package ee.ilja.samoilov.fondData.service;

import com.google.gson.Gson;
import ee.ilja.samoilov.data.fondData.tables.Financedata;
import ee.ilja.samoilov.data.fondData.tables.records.FinancedataRecord;
import ee.ilja.samoilov.fondData.dto.FinanceData;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import static ee.ilja.samoilov.data.fondData.tables.Financedata.FINANCEDATA;


/**
 * Created by Ilja on 28.04.2018.
 */
@Service
public class FinanceDataService {

    @Autowired
    DSLContext dsl;

    public synchronized FinanceData[] getCrawledData() {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet request = new HttpGet("https://calm-garden-89090.herokuapp.com/crawl");
            request.addHeader("Accept", "application/json");
            HttpResponse response = httpClient.execute(request);
            response.getEntity();
            Gson gson = new Gson();
            String json = EntityUtils.toString(response.getEntity());
            return gson.fromJson(json, FinanceData[].class);
        } catch (IOException e) {
            System.err.println("error in IO");
        }
        return null;
    }

    public synchronized FinanceData getLastDataForSymbol(String symbol) {
        return dsl.selectDistinct()
                .from(FINANCEDATA)
                .where(FINANCEDATA.SYMBOL.eq(symbol))
                .orderBy(FINANCEDATA.TIMESTAMP.desc())
                .limit(1)
                .fetchInto(FinanceData.class)
                .get(0);
    }

    public synchronized void saveNewData() {
        FinanceData[] financeDataList = getCrawledData();
        for (FinanceData financeData : financeDataList) {
            if (financeData.equals(getLastDataForSymbol(financeData.getSymbol())) == false) {
//                dsl.insertInto(FINANCEDATA)
//                        .set(FINANCEDATA.SYMBOL, financeData.getSymbol())
//                        .set(FINANCEDATA.TIMESTAMP, financeData.getTimeStamp())
//                        .set(FINANCEDATA.AMOUNT, financeData.getAmount().doubleValue())
//                        .set(FINANCEDATA.EXEMPLARPURCHACEPRICE, financeData.getExemplarPurchasePrice().doubleValue())
//                        .set(FINANCEDATA.EXEMPLARMARKETPRICE, financeData.getExemplarMarketPrice().doubleValue())
//                        .set(FINANCEDATA.CHANGE, financeData.getChange().doubleValue())
//                        .set(FINANCEDATA.CHANGETODAY, financeData.getChangeToday().doubleValue())
//                        .set(FINANCEDATA.PROFIT, financeData.getProfit().doubleValue())
//                        .set(FINANCEDATA.TOTALMARKETPRICE, financeData.getTotalMarketPrice().doubleValue())
//                        .set(FINANCEDATA.PART, financeData.getPart().doubleValue())
//                        .execute();
                System.out.println(financeData.getSymbol() + " NOT EQUALS");

            } else {
                System.out.println(financeData.getSymbol() + " EQUALS");
            }
        }
    }

//    public Timestamp parseTimeStamp(FinanceData financeData) {
//        try {
//            return new Timestamp(new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(financeData.getTimeStamp()).getTime());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
