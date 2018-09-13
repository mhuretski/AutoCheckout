package Pages;

import Logic.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Loyalty extends Wait {

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

    public void insertCoupons(String[] coupons, WebDriver driver) {
        for (String coupon : coupons) {
            driver.findElement(By.id("discountCode")).sendKeys(coupon);
            driver.findElement(By.id("addDiscount")).click();
            waitLoaderAnimation(driver);
        }

    }

    void removeLoyaltyCard(WebDriver driver) {
        String removeCardSelector = "jsChangeRflCard";
        if (driver.findElements(By.id(removeCardSelector)).size() > 0) {
            driver.findElement(By.id(removeCardSelector)).click();
        }
    }

}
