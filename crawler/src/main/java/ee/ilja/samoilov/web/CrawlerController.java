package ee.ilja.samoilov.web;

import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLCanvasElement;
import ee.ilja.samoilov.crawler.HTMLCrawler;
import ee.ilja.samoilov.dto.FinanceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * Created by ilsam on 6/16/2017.
 */
@RestController
public class CrawlerController {

    final
    HTMLCrawler htmlCrawler;

    @Autowired
    public CrawlerController(HTMLCrawler htmlCrawler) {
        this.htmlCrawler = htmlCrawler;
    }

    @RequestMapping(value = "/crawl", method = RequestMethod.GET, produces = "application/json")
    private @ResponseBody ArrayList<FinanceData> crawlData() {
        return htmlCrawler.crawlData();
    }
}
