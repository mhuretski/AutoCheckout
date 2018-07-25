package PricesGetter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class GetPrices {

    public static void main(String[] args) {

        GetPriceData data = new GetPriceData();
        WritePrices writePrices = new WritePrices();
        WebDriver driver = new ChromeDriver();

        for (int i = 0; i < data.getItemSequence().size(); i++) {
            try {
                writePrices.addData(new GetPriceLogic(
                        data.getItemSequence().get(i),
                        data.getItems().get(i),
                        data.getSites().get(i),
                        driver));
            } catch (org.openqa.selenium.WebDriverException exception) {
                exception.printStackTrace();
                i--;
            }
        }
        driver.close();
        writePrices.execute();
    }
}
