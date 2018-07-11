package Pages;

import Logic.Site;
import Logic.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Checkout extends Wait {

    public Checkout(String site, WebDriver driver) {
        open(site, driver);
        /*for the first order*/
        try {
            chooseAddress(driver);
            waitLoaderAnimation(driver);
            chooseDeliveryButton(driver);
        } catch (org.openqa.selenium.NoSuchElementException ne){
            System.out.println("Address is already chosen.");
        }

        continueToPayment(driver);
    }

    private void open(String site, WebDriver driver) {
        driver.get(new Site().chosenSite(site) + "checkout/checkout.jsp");
    }

    private void continueToPayment(WebDriver driver) {
        driver.findElement(By.cssSelector("button.checkout-submit-btn"))
                .click();
    }

    private void chooseAddress(WebDriver driver) {
        driver.findElement(By.cssSelector("input.js-delivery-address-radio-selector"))
                .click();
    }

    private void chooseDeliveryButton(WebDriver driver) {
        driver.findElement(By.cssSelector("button#js-address-list-cta"))
                .click();
    }

}
