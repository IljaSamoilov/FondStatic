package ee.ilja.samoilov.containers;

import lombok.Data;

/**
 * Created by Ilja on 13.01.2018.
 */
@Data
public class Status {
    private static String OK = "OK";
    private static String ERROR = "ERROR";
    private String status;
    private String message;

    public Status(boolean ok, String message) {
        this.status = ok ? OK : ERROR;
        this.message = ok? OK : message;
    }
}
