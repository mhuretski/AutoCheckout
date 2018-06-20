package Logic;

import ImportExport.OrderLogging;
import Pages.*;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class OrderCreation {

    public OrderCreation(int orderSequence,
                         int healthbox,
                         String[] healthboxItem,
                         int[] healthboxItemQty,
                         String[] normalItem,
                         int[] normalItemQty,
                         String hbPlus,
                         String site,
                         String newLoyaltyUser,
                         String loyaltyCard,
                         WebDriver driver) throws IOException, InterruptedException {

        new Driver(driver);
        new Login(site, driver);

        /*add healthbox items*/
        if (healthbox == 1 || healthbox == 2 || healthbox == 3 || healthboxItem.length == 1 && !healthboxItem[0].equals("-"))
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
        if (multipleHealthboxItems)
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

        new Checkout(site, driver);
        new Payment(driver);
        new OrderLogging("success", orderSequence, driver);

        driver.quit();
    }
}
