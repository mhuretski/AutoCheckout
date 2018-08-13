package Logic;

import Export.OrderListing;
import Export.OrderLogging;
import Export.PriceTracking;
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
                         String[] pills,
                         String hbpRepeat,
                         String site,
                         String newLoyaltyUser,
                         String loyaltyCard,
                         String[] coupons,
                         String deliveryType,
                         String paymentType,
                         String username,
                         String password,
                         WebDriver driver,
                         OrderListing orderListing) {

        new DriverTiming(3, driver);
        new Login(username, password, site, driver);

        /*add healthbox items*/
        if (hbPlus.equals("+"))
            new RecPagePlus(pills, site, hbpRepeat, driver);
        boolean isHealthbox = healthbox == 1 || healthbox == 3 || healthbox == 4;
        if (isHealthbox || healthboxItem.length == 1 && !healthboxItem[0].equals("-"))
            new RecPage(healthbox, healthboxItem, site, driver);

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

        /*add coupons*/
        if (!coupons[0].equals("-")) {
            new Loyalty(site, driver).insertCoupons(coupons, driver);
        }

        /*choose repeat order*/
        new RepeatOrder(healthboxItem, hbRepeatOrder, normalItem, normalRepeatOrder, site, driver);

        PriceTracking trackedPrices = new PriceTracking(healthboxItem, normalItem, hbPlus, site, driver);

        new Checkout(username, deliveryType, site, driver);
        new Payment(deliveryType, username, paymentType, driver);
        new OrderLogging().success(orderSequence, driver, orderListing, trackedPrices.getPriceForEachItem());

        driver.quit();
    }

}
