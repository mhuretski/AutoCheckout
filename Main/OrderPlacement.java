package Main;

import Export.ArchiveOrders;
import Export.OrderListing;
import Export.OrderWriter;
import Import.ImportDataForOrders;
import Logic.CreationTime;
import Logic.Driver;
import Logic.ErrorHandling;
import Logic.OrderCreation;
import org.openqa.selenium.WebDriver;

public class OrderPlacement {

    public static void main(String[] args) {
        CreationTime executeTime = new CreationTime();
        executeTime.setStartTime();

        ImportDataForOrders data = new ImportDataForOrders();
        OrderListing orderListing = new OrderListing();

        int amountOfAttemptsBeforeExit = executeTime.setAttempts(10);
        int i = 0;
        for (; i < data.getOrderSequence().length; i++) {
            if (amountOfAttemptsBeforeExit >= 0) {
                WebDriver driver = new Driver().startDriver(3);
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
                            data.getPills()[i],
                            data.getHbpRepeat()[i],
                            data.getSite()[i],
                            data.getNewLoyaltyUser()[i],
                            data.getLoyaltyCard()[i],
                            data.getCoupons()[i],
                            data.getDeliveryType()[i],
                            data.getPaymentType()[i],
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
                            driver,
                            orderListing);
                    i--;
                    amountOfAttemptsBeforeExit--;
                }
            }
        }
        OrderWriter orderWriter = new OrderWriter(orderListing);
        new ArchiveOrders(orderListing, orderWriter);

        executeTime.setEndTime();
        executeTime.show(amountOfAttemptsBeforeExit, data.getOrderSequence().length);
    }

}
