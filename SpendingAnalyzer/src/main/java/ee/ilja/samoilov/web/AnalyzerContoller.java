package ee.ilja.samoilov.web;

import ee.ilja.samoilov.containers.Status;
import org.jooq.tools.csv.CSVReader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Ilja on 9.01.2018.
 */
public class AnalyzerContoller {


    @PostMapping("/upload") // //new annotation since 4.3
    public Status singleFileUpload(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return new Status(false, "Empty file");
        }
        try {
            //here create a container for data
            CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()));
            csvReader.forEachRemaining();
            return new Status(true, "");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Status(false, "unknown error");
    }
}
