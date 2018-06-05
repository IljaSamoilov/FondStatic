package ee.ilja.samoilov.fondData.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class DBConfig implements CommandLineRunner {

//    private final DSLContext create;

//    @Autowired
//    public DBConfig(DSLContext dslContext) {
//        this.create = dslContext;
//    }

//    @Bean(name="basicDataSource")
    public BasicDataSource basicDataSource() throws URISyntaxException {
        URI dbUri = new URI(System.getenv("DATABASE_URL"));
        System.out.println(dbUri.toString());
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(dbUrl);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);
        return basicDataSource;
    }

    @Override
    public void run(String... strings) throws Exception {

    }
}
