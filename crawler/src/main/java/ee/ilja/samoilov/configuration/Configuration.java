package ee.ilja.samoilov.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class Configuration {

    @Value("${LHV.login}")
    private String login;

    @Value("${LHV.password}")
    private String password;
}
