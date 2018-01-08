package ee.ilja.samoilov.crawler;

import ee.ilja.samoilov.configuration.Configuration;
import ee.ilja.samoilov.dto.FinanceData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class HTMLCrawler {

    private ArrayList<FinanceData> financeDatas;

    private final Configuration configuration;

    @Autowired
    public HTMLCrawler(Configuration configuration) {
        this.configuration = configuration;
    }

    public ArrayList<FinanceData> crawlData() {
        financeDatas = new ArrayList<>();

        HtmlUnitDriver driver = new HtmlUnitDriver(true); // true meaning javascript support (Using rhino i be leave)

        String baseUrl = "https://www.lhv.ee/auth/login.cfm?nocache=1";

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        driver.get(baseUrl);

        driver.findElement(By.xpath("//a[@data-id='PWD']")).click();
        driver.findElement(By.xpath("//input[@id='nickname']")).sendKeys(configuration.getLogin());
        driver.findElement(By.xpath("//input[@id='pwd']")).sendKeys(configuration.getPassword());
        driver.findElement(By.id("login_submit")).click();
        String htmlContent = driver.getPageSource();
        Document document = Jsoup.parse(htmlContent);
         Element table = document.body().getElementById("view_12102").getElementsByTag("tbody").get(0);
        ArrayList<Element> financeDataRows = parseChildNodes(table.children());
        //we need to remove 0 element because it's head and does not contains useful info.
        financeDataRows.remove(0);
//        ArrayList<Elements> elements = financeDataRows.stream().map(Element::children).collect(Collectors.toList());
        financeDataRows.stream().map(Element::children).forEach(this::parseFinanceData);
        return financeDatas;
    }


    private ArrayList<Element> parseChildNodes(ArrayList list) {
        return (ArrayList<Element>) list.stream()
                .filter(node -> node.getClass().equals(Element.class))
                .collect(Collectors.toList());
    }

    private FinanceData parseFinanceData(Elements data) {
        FinanceData financeData = new FinanceData();
        financeData.setSymbol(data.get(0).text());
        financeData.setAmount(new BigDecimal(data.get(1).text()));
        financeData.setExemplarPurchasePrice(new BigDecimal(data.get(2).text()));
        financeData.setExemplarMarketPrice(new BigDecimal(data.get(3).text()));
        financeData.setChange(new BigDecimal(data.get(4).text().replace("%", "")));
        financeData.setChangeToday(new BigDecimal(data.get(5).text().replace("%", "")));
        financeData.setProfit(new BigDecimal(data.get(6).text()));
        financeData.setTotalMarketPrice(new BigDecimal(data.get(7).text()));
        financeData.setPart(new BigDecimal(data.get(8).text().replace("%", "")));
        financeDatas.add(financeData);
        return financeData;
    }

}
