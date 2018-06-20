package Main;

import ImportExport.ImportDataForOrders;
import ImportExport.OrderLogging;
import Logic.OrderCreation;
import Pages.Basket;
import Pages.Loyalty;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.io.IOException;


class PlaceOrder {

    @Test
    void placeOrders() throws IOException, InterruptedException {
        ImportDataForOrders data = new ImportDataForOrders();
        int i = 0;
        for (; i < data.getOrderSequence().length; i++) {
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
                driver.quit();
                for (int j = 0; j < 10; j++) {
                    new Basket().remove(data.getSite()[i], data.getOrderSequence()[i]);
                }
                new Loyalty(data.getSite()[i], driver);
                new OrderLogging("fail", data.getOrderSequence()[i], driver);
                i--;
            }
        }
    }
}
