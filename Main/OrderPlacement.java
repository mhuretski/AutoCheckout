package Main;

import Export.OrderListing;
import Export.OrderWriter;
import Import.ImportDataForOrders;
import Logic.ErrorHandling;
import Logic.OrderCreation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;


public class OrderPlacement {

    @Test
    void placeOrders() {
        ImportDataForOrders data = new ImportDataForOrders();
        OrderListing orderListing = new OrderListing();

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
                            data.getHbRepeatOrder()[i],
                            data.getNormalItems()[i],
                            data.getNormalItemsQty()[i],
                            data.getNormalRepeatOrder()[i],
                            data.getHbPlus()[i],
                            data.getHbpRepeat()[i],
                            data.getSite()[i],
                            data.getNewLoyaltyUser()[i],
                            data.getLoyaltyCard()[i],
                            data.getCoupon()[i],
                            data.getUsername()[i],
                            data.getPassword()[i],
                            driver,
                            orderListing);
                } catch (org.openqa.selenium.WebDriverException exception) {
                    new ErrorHandling(
                            data.getOrderSequence()[i],
                            data.getSite()[i],
                            data.getUsername()[i],
                            data.getPassword()[i],
                            exception,
                            driver);
                    i--;
                    amountOfAttemptsBeforeExit--;
                }
            }
        }
        new OrderWriter(orderListing);

    }

}


