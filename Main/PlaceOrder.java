package Main;

import ImportExport.ImportDataForOrders;
import Logic.ErrorHandling;
import Logic.OrderCreation;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;


class PlaceOrder {

    @Test
    void placeOrders() {
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
                            data.getUsername()[i],
                            data.getPassword()[i],
                            driver);
                } catch (Exception exception) {
                    if (exception instanceof org.openqa.selenium.NoSuchElementException || exception instanceof InterruptedException) {
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
                    else new ErrorHandling(exception, driver);
                }
            }
        }
    }

}
