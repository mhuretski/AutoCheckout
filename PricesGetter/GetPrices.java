package PricesGetter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class GetPrices {

    public static void main(String[] args) {

        GetPriceData data = new GetPriceData();
        WritePrices writePrices = new WritePrices();
        WebDriver driver = new ChromeDriver();

        int amountOfAttemptsBeforeExit = 10;
        for (int i = 0; i < data.getItemSequence().size(); i++) {
            if (amountOfAttemptsBeforeExit == 0) break;
            try {
                writePrices.addData(new GetPriceLogic(
                        data.getItemSequence().get(i),
                        data.getItems().get(i),
                        data.getSites().get(i),
                        driver));
            } catch (org.openqa.selenium.WebDriverException exception) {
                exception.printStackTrace();
                amountOfAttemptsBeforeExit--;
                i--;
            }
        }
        driver.close();
        writePrices.execute();
    }
}
