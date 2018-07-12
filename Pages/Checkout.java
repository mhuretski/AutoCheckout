package Pages;

import Logic.Site;
import Logic.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Checkout extends Wait {

    public Checkout(String site, WebDriver driver) {
        open(site, driver);
        /*for the first order*/
        firstOrderInNewAccount(driver);
        additionalButtons(driver);

        continueToPayment(driver);
    }

    private void additionalButtons(WebDriver driver) {
        String addressSelector = "input.js-delivery-address-radio-selector";
        if (driver.findElements(By.cssSelector(addressSelector)).size() > 0) {
            try {
                chooseAddress(addressSelector, driver);
                waitLoaderAnimation(driver);
                try {
                    chooseDeliveryButton(driver);
                } catch (org.openqa.selenium.NoSuchElementException ne) {
                    /*just skip*/
                }
            } catch (org.openqa.selenium.NoSuchElementException ne) {
                /*just skip*/
            }
        }
    }

    private void firstOrderInNewAccount(WebDriver driver) {
        String deliveryNotChosen = ".js-checkout-delivery-method-selector[value='delivery'].input-radio.js-checkout-delivery-method-selector:not(valid)";
        try {
            if (driver.findElements(By.cssSelector(deliveryNotChosen)).size() != 0) {
                driver.findElement(By.cssSelector(deliveryNotChosen)).click();
                driver.findElement(By.id("checkout_form_postcode_lookup")).sendKeys("1");
                driver.findElement(By.cssSelector(".btn-secondary.checkout-fieldset-main-doubled")).click();
                waitLoaderAnimation(driver);
                driver.findElement(By.cssSelector(".js-select-qas-address")).click();
                waitLoaderAnimation(driver);
                waitAbsence(driver, ".js-delivery-address-submit-button[disabled]");
                driver.findElement(By.cssSelector(".js-delivery-address-submit-button")).click();
            }
        } catch (org.openqa.selenium.NoSuchElementException ne) {
            /*just skip*/
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
