package Pages;

import Logic.Site;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Checkout {
    private void open(String site, WebDriver driver) {
        driver.get(new Site().chosenSite(site) + "checkout/checkout.jsp");
    }

    private void chooseAddress(WebDriver driver) {
        driver.findElement(By.xpath("//input[contains(@class,'input-radio js-delivery-address-radio-selector')]"))
                .click();
    }

    private void chooseDeliveryButton(WebDriver driver) {
        driver.findElement(By.xpath("//button[@id='js-address-list-cta']"))
                .click();
    }

    private void continueToPayment(WebDriver driver) {
        driver.findElement(By.xpath("//button[contains(@class,'btn btn-primary checkout-submit-btn js-delivery-option-submit-button')]"))
                .click();
    }

    public Checkout(String site, WebDriver driver){
        open(site, driver);
/*for the first order*/
//        try {
//            chooseAddress(driver);
//            chooseDeliveryButton(driver);
//        }
//        catch (Exception e){
//            System.out.println("Looks as delivery is passed");
//        }
        continueToPayment(driver);
    }
}
