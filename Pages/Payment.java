package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static java.lang.Thread.sleep;

public class Payment {

    private void payByCard(WebDriver driver) throws InterruptedException {
        sleep(3000);
        driver.findElement(By.cssSelector("input.input-radio.js-checkout-payment-type-switch-cc"))
                .click();
        sleep(3000);
    }

    private void agreeToTerms(WebDriver driver) {
        driver.findElement(By.xpath("//input[@name='tc']"))
                .click();
    }

    private void enterBillingSection(WebDriver driver) {
        driver.findElement(By.xpath("//button[contains(@class,'btn btn-primary checkout-submit-btn js-billing-tc-cta js-checkout-billing-card-tc-accept-button')]"))
                .click();
    }

    private void goToPayment(WebDriver driver) {
        driver.findElement(By.cssSelector("button.checkout-submit-btn.js-checkout-billing-address-submit"))
                .click();

    }

    private void switchToFrame(WebDriver driver) {
        WebElement paymentFrame = driver.findElement(By.cssSelector("iframe[allowtransparency='true']"));
        driver.switchTo().frame(paymentFrame);

    }

    private void pay(WebDriver driver) {

        switchToFrame(driver);

        WebElement cardNumber = driver.findElement(By.cssSelector("input#payment-cardnumber"));
        cardNumber.click();
        cardNumber.sendKeys("4111111111111111");

        driver.findElement(By.cssSelector("input#payment-cardholdername")).sendKeys("PETER CHEATER");

        Select month = new Select(driver.findElement(By.cssSelector("select#payment-expirydate-month")));
        month.selectByIndex(4);

        Select year = new Select(driver.findElement(By.cssSelector("select#payment-expirydate-year")));
        year.selectByIndex(4);

        payFast(driver);
    }

    private void payFast(WebDriver driver) {

        driver.findElement(By.cssSelector("input#payment-cvc")).sendKeys("123");

        driver.findElement(By.cssSelector("input#payment-submit")).click();
        driver.switchTo().defaultContent();
    }

    public Payment(WebDriver driver) throws InterruptedException {
        payByCard(driver);

        /*Depends on site configuration*/
//        agreeToTerms(driver);
//        enterBillingSection(driver);
//        goToPayment(driver);

        switchToFrame(driver);
        payFast(driver);
    }

}
