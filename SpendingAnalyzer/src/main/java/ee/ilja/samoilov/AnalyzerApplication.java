package ee.ilja.samoilov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAutoConfiguration
@SpringBootApplication
@EnableTransactionManagement
public class AnalyzerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnalyzerApplication.class, args);
    }
}
