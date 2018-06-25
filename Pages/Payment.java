package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static java.lang.Thread.sleep;

public class Payment {

    private void payByCard(WebDriver driver) throws InterruptedException {
        WebElement wtf = driver.findElement(By.xpath("//p[starts-with(., 'Card')]"));
        wtf.click();
    }

    private void agreeToTerms(WebDriver driver) {
        driver.findElement(By.xpath("//input[@name='tc']"))
                .click();
    }

    private void enterBillingSection(WebDriver driver) {
        driver.findElement(By.xpath("//button[contains(@class,'btn btn-primary checkout-submit-btn js-billing-tc-cta js-checkout-billing-card-tc-accept-button')]"))
                .click();
    }

    private void goToPayment(WebDriver driver) throws InterruptedException {
        try {
            driver.findElement(By.xpath("//button[contains(@class,'btn btn-primary checkout-submit-btn js-checkout-billing-address-submit js-adyen-payment-submit')]"))
                    .click();
        } catch (Exception e){
            System.out.println("Couldn't proceed to payment.");
        }
    }

    private void pay(WebDriver driver) throws InterruptedException {

        WebElement paymentFrame = driver.findElement(By.xpath("//iframe[@allowtransparency='true']"));
        driver.switchTo().frame(paymentFrame);

        sleep(3000);

        WebElement cardNumber = driver.findElement(By.cssSelector("input#payment-cardnumber"));
        cardNumber.click();
        cardNumber.sendKeys("4111111111111111");

        driver.findElement(By.cssSelector("input#payment-cardholdername")).sendKeys("PETER CHEATER");

        Select month = new Select(driver.findElement(By.cssSelector("select#payment-expirydate-month")));
        month.selectByIndex(4);

        Select year = new Select(driver.findElement(By.cssSelector("select#payment-expirydate-year")));
        year.selectByIndex(4);

        driver.findElement(By.cssSelector("input#payment-cvc")).sendKeys("123");

        driver.findElement(By.cssSelector("input#payment-submit")).click();
        driver.switchTo().defaultContent();
    }

    public Payment(WebDriver driver) throws InterruptedException {
        payByCard(driver);
//        agreeToTerms(driver);
//        enterBillingSection(driver);
        goToPayment(driver);
        pay(driver);
    }
}
