package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Loyalty {

    public void getNewLoyaltyCard(WebDriver driver) {
        driver.findElement(By.id("newRflCard")).click();
    }

    public void insertExistingLoyaltyCard(String loyaltyCard, WebDriver driver) {
        driver.findElement(By.id("rflCardNumber")).sendKeys(loyaltyCard);
        driver.findElement(By.id("addRflCard")).click();
    }

    void removeLoyaltyCard(WebDriver driver) {
        String removeCardSelector = "//a[@id='jsChangeRflCard']";
        if (driver.findElements(By.xpath(removeCardSelector)).size() > 0) {
            driver.findElement(By.xpath(removeCardSelector)).click();
        }
    }

    public Loyalty(String site, WebDriver driver) {
        new Basket().open(site, driver);
    }

    Loyalty() {
    }
}
