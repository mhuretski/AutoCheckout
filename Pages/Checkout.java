package Pages;

import Logic.Site;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Checkout {
    private void open(String site, WebDriver driver) {
        driver.get(new Site().chosenSite(site) + "checkout/checkout.jsp");
    }

    private void chooseAddress(WebDriver driver) {
        driver.findElement(By.cssSelector("input.js-delivery-address-radio-selector"))
                .click();
    }

    private void chooseDeliveryButton(WebDriver driver) {
        driver.findElement(By.cssSelector("button#js-address-list-cta"))
                .click();
    }

    private void continueToPayment(WebDriver driver) {
        driver.findElement(By.cssSelector("button.checkout-submit-btn"))
                .click();
    }

    public Checkout(String site, WebDriver driver) {
        open(site, driver);
        
        /*for first time placing order*/
//        chooseAddress(driver);
//        chooseDeliveryButton(driver);

        continueToPayment(driver);
    }

}
