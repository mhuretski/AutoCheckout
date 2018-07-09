package Pages;

import Logic.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class Payment extends Wait {

    private void payByCard(WebDriver driver) {
        waitLoaderAnimation(driver);
        String cardSelector = "//input[contains(@class,'js-checkout-payment-type-switch-cc')]/ancestor::label";
        WebElement chooseCard = driver.findElement(By.xpath(cardSelector));
        waitClickableElem(driver, chooseCard);
        waitClickableXpath(driver, cardSelector);
        chooseCard.click();
        if (driver.findElements(By.cssSelector(".js-billing-card-type-holder")).size() == 0) {
            chooseCard.click();
        }
        waitLoaderAnimation(driver);
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

        String submitButton = "input#payment-submit";
        waitCssPresence(driver, submitButton);
        waitClickableCSS(driver, submitButton);
        driver.findElement(By.cssSelector(submitButton)).click();

        driver.switchTo().defaultContent();
    }

    public Payment(WebDriver driver) {
        payByCard(driver);

        /*Depends on site configuration*/
//        agreeToTerms(driver);
//        enterBillingSection(driver);
//        goToPayment(driver);

        switchToFrame(driver);
        payFast(driver);
    }

}
