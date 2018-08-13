package ee.ilja.samoilov.fondData.service;

import ee.ilja.samoilov.data.fondData.tables.records.ResultsRecord;
import ee.ilja.samoilov.fondData.dto.Results;
import org.jooq.DSLContext;
import org.jooq.impl.SQLDataType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

import static ee.ilja.samoilov.data.fondData.Tables.RESULTS;

@Service
public class ResultsService {

    @Autowired
    DSLContext dsl;
    
    @PostConstruct
    void createTablesIfNonExists() {
        dsl.createTableIfNotExists("results")
                .column("profit", SQLDataType.NUMERIC)
                .column("total", SQLDataType.NUMERIC)
                .column("timestamp", SQLDataType.TIMESTAMP)
                .execute();
    }

    public List<Results> getResults() {
        return dsl.select()
                .from(RESULTS)
                .fetchInto(Results.class);
    }

    void updateResults(Results results) {
        ResultsRecord record = dsl.newRecord(RESULTS, results);
        dsl.executeInsert(record);
    }

    public Results getLastResults() {
        return dsl.select()
                .from(RESULTS)
                .orderBy(RESULTS.TIMESTAMP.desc())
                .fetchInto(Results.class)
                .get(0);
    }
}