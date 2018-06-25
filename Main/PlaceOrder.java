package Main;

import ImportExport.ImportDataForOrders;
import ImportExport.OrderLogging;
import Logic.OrderCreation;
import Pages.Basket;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.io.IOException;


class PlaceOrder {

    @Test
    void placeOrders() throws IOException, InterruptedException {
        ImportDataForOrders data = new ImportDataForOrders();

        int amountOfAttemptsBeforeExit = 10;
        int i = 0;
        for (; i < data.getOrderSequence().length; i++) {
            if (amountOfAttemptsBeforeExit >= 0) {
                WebDriver driver = new ChromeDriver();

                try {
                    new OrderCreation(
                            data.getOrderSequence()[i],
                            data.getHealthbox()[i],
                            data.getHealthboxItem()[i],
                            data.getHealthboxItemQty()[i],
                            data.getNormalItems()[i],
                            data.getNormalItemsQty()[i],
                            data.getHbPlus()[i],
                            data.getSite()[i],
                            data.getNewLoyaltyUser()[i],
                            data.getLoyaltyCard()[i],
                            driver);
                } catch (Exception e) {
                    e.printStackTrace();
                    driver.quit();
                    new Basket().remove(data.getSite()[i]);
                    new OrderLogging("fail", data.getOrderSequence()[i], driver);
                    i--;
                    amountOfAttemptsBeforeExit--;
                }
            }
        }
    }
}
