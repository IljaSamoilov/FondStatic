package ee.ilja.samoilov.fondData.service;

import com.google.gson.*;
import ee.ilja.samoilov.data.fondData.tables.records.FinancedataRecord;
import ee.ilja.samoilov.fondData.dto.FinanceData;
import ee.ilja.samoilov.fondData.repository.DataRepository;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static ee.ilja.samoilov.data.fondData.tables.Financedata.FINANCEDATA;


/**
 * Created by Ilja on 28.04.2018.
 */
@Service
public class FinanceDataService {

    @Autowired
    DSLContext dsl;

    @Autowired
    DataRepository dataRepository;

    private Logger logger = Logger.getGlobal();

    public synchronized FinanceData[] getCrawledData() {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet request = new HttpGet("https://calm-garden-89090.herokuapp.com/crawl");
            request.addHeader("Accept", "application/json");
            HttpResponse response = httpClient.execute(request);
            response.getEntity();
//            Gson gson = new Gson();
            final Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                        public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
                            return new Date(jsonElement.getAsJsonPrimitive().getAsLong());
                        }
                    })
                    .create();
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
                .fetchInto(FinanceData.class)
                .get(0);
    }


    public synchronized List<String> getSymbols() {
        return dsl.selectDistinct(FINANCEDATA.SYMBOL)
                .from(FINANCEDATA)
                .fetch().into(String.class);
    }

    public synchronized void updateDatabase() {
        List<String> symbols = getSymbols();
        List<FinanceData> financeDataList = dataRepository.getLastFinanceData();
        for (FinanceData financeData : financeDataList) {
            if (symbols.contains(financeData.getSymbol())) {
                FinanceData lastData = getLastDataForSymbol(financeData.getSymbol());
                if (financeData.equals(lastData) == false) {
                    FinancedataRecord record = dsl.newRecord(FINANCEDATA, financeData);
                    dsl.executeInsert(record);
                    logger.info("Database updated with new finance data for " + financeData.getSymbol());
                } else {
                    logger.info("Database already has latest info for " + financeData.getSymbol());
                }
            } else {
                FinancedataRecord record = dsl.newRecord(FINANCEDATA, financeData);
                dsl.executeInsert(record);
                logger.info("Database updated with new finance data for " + financeData.getSymbol());
            }

        }
    }

    @Scheduled(cron = "0 0 * * * MON-FRI")
    private void scheduledDatabaseUpdate() {
        logger.info("SCHEDULED DATABASE UPDATING");
        updateDatabase();
    }

    public List<FinanceData> getAllData() {
        return dsl.select()
                .from(FINANCEDATA)
                .fetch().into(FinanceData.class);
    }

    public List<FinanceData> getAllDataForSymbol(String symbol) {
        return dsl.selectDistinct()
                .from(FINANCEDATA)
                .where(FINANCEDATA.SYMBOL.eq(symbol))
                .orderBy(FINANCEDATA.TIMESTAMP.desc())
                .fetchInto(FinanceData.class);
    }
}
