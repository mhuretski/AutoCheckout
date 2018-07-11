package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static java.lang.Thread.sleep;

public class Loyalty {

    public Loyalty(String site, WebDriver driver) {
        new Basket().open(site, driver);
    }

    Loyalty() {
    }

    public void getNewLoyaltyCard(WebDriver driver) {
        driver.findElement(By.id("newRflCard")).click();
    }

    public void insertExistingLoyaltyCard(String loyaltyCard, WebDriver driver) {
        driver.findElement(By.id("rflCardNumber")).sendKeys(loyaltyCard);
        driver.findElement(By.id("addRflCard")).click();
    }

    public void insertCoupon(String[] coupons, WebDriver driver) {
        for (String coupon : coupons) {
            driver.findElement(By.id("discountCode")).sendKeys(coupon);
            driver.findElement(By.id("addDiscount")).click();
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    void removeLoyaltyCard(WebDriver driver) {
        String removeCardSelector = "jsChangeRflCard";
        if (driver.findElements(By.id(removeCardSelector)).size() > 0) {
            driver.findElement(By.id(removeCardSelector)).click();
        }
    }

}
