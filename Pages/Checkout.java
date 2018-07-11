package Pages;

import Logic.Site;
import Logic.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Checkout extends Wait {

    public Checkout(String site, WebDriver driver) {
        open(site, driver);
        /*for the first order*/
        additionalBtns(driver);

        continueToPayment(driver);
    }

    private void additionalBtns(WebDriver driver) {
        String addressSelector = "input.js-delivery-address-radio-selector";
        if (driver.findElements(By.cssSelector(addressSelector)).size() > 0) {
            try {
                chooseAddress(addressSelector, driver);
                waitLoaderAnimation(driver);
                chooseDeliveryButton(driver);
            } catch (org.openqa.selenium.NoSuchElementException ne) {
                /*just skip*/
            }
        }
    }

    private void open(String site, WebDriver driver) {
        driver.get(new Site().chosenSite(site) + "checkout/checkout.jsp");
    }

    private void continueToPayment(WebDriver driver) {
        driver.findElement(By.cssSelector("button.checkout-submit-btn"))
                .click();
    }

    private void chooseAddress(String addressSelector, WebDriver driver) {
        driver.findElement(By.cssSelector(addressSelector))
                .click();
    }

    private void chooseDeliveryButton(WebDriver driver) {
        driver.findElement(By.cssSelector("button#js-address-list-cta"))
                .click();
    }

}
