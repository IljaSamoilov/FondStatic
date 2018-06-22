package ee.ilja.samoilov.fondData.service;

import com.google.gson.*;
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
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
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

    public synchronized void saveNewData() {
        FinanceData[] financeDataList = getCrawledData();
        for (FinanceData financeData : financeDataList) {
            FinanceData lastData = getLastDataForSymbol(financeData.getSymbol());
            if (financeData.equals(lastData) == false) {
                FinancedataRecord record = dsl.newRecord(FINANCEDATA, financeData);
                dsl.executeInsert(record);
                System.out.println(financeData.getSymbol() + " NOT EQUALS");

            } else {
                System.out.println(financeData.getSymbol() + " EQUALS");
            }
        }
    }

}
