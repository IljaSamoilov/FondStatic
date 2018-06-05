package ee.ilja.samoilov.fondData.service;

import com.google.gson.Gson;
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


/**
 * Created by Ilja on 28.04.2018.
 */
@Service
public class FinanceDataService {

    @Autowired
    DSLContext dsl;

    public synchronized FinanceData[] performRequest() {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet request = new HttpGet("https://calm-garden-89090.herokuapp.com/crawl");
            request.addHeader("Accept", "application/json   ");
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
}
