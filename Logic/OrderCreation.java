package Logic;

import ImportExport.OrderLogging;
import Pages.*;
import org.openqa.selenium.WebDriver;

public class OrderCreation {

    public OrderCreation(int orderSequence,
                         int healthbox,
                         String[] healthboxItem,
                         int[] healthboxItemQty,
                         String[] hbRepeatOrder,
                         String[] normalItem,
                         int[] normalItemQty,
                         String[] normalRepeatOrder,
                         String hbPlus,
                         String site,
                         String newLoyaltyUser,
                         String loyaltyCard,
                         String username,
                         String password,
                         WebDriver driver) throws InterruptedException {

        new DriverTiming(3, driver);
        new Login(username, password, site, driver);

        /*add healthbox items*/
        boolean isHealthbox = healthbox == 1 || healthbox == 2 || healthbox == 3 || healthbox == 4;
        if (isHealthbox || healthboxItem.length == 1 && !healthboxItem[0].equals("-"))
            new RecPage(healthbox, healthboxItem, site, driver);
        if (hbPlus.equals("+")) {
            new RecPagePlus(site, driver);
        }

        /*change qTy of healthbox items*/
        boolean multipleHealthboxItems = false;
        for (int qty : healthboxItemQty) {
            if (qty > 1) {
                multipleHealthboxItems = true;
                break;
            }
        }
        if (multipleHealthboxItems && isHealthbox)
            new Basket(healthboxItem, healthboxItemQty, site, driver);

        /*add normal items*/
        if (normalItem.length >= 1 && !normalItem[0].equals("-"))
            new PDP(normalItem, normalItemQty, site, driver);

        /*request for a new card*/
        if (newLoyaltyUser.equals("+")) {
            new Loyalty(site, driver).getNewLoyaltyCard(driver);
        }

        /*add loyalty card*/
        if (!loyaltyCard.equals("-")) {
            new Loyalty(site, driver).insertExistingLoyaltyCard(loyaltyCard, driver);
        }

        /*choose repeat order*/
        new RepeatOrder(healthboxItem, hbRepeatOrder, normalItem, normalRepeatOrder, site, driver);

        new DriverTiming(20, driver);
        new Checkout(site, driver);
        new Payment(driver);
        new OrderLogging("success", orderSequence, driver);

        driver.quit();
    }

}
