package Pages;

import Logic.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class Payment extends Wait {

    public Payment(String deliveryType, String username, String paymentType, WebDriver driver) {
        if (paymentType.equals("f")) payFast(driver);
        else choosePaymentType(deliveryType, username, paymentType, driver);
    }

    private void choosePaymentType(String deliveryType, String username, String paymentType, WebDriver driver) {
        if (paymentType.equals("c")) {
            if (driver.findElements(By.cssSelector(".pmBcard[name='brandName']")).size() > 0)
                adyenPayment(driver);
            else {
                payByCard(deliveryType, username, driver);
            }
        }
        else payByPayPal(driver);
    }

    private void payByPayPal(WebDriver driver) {
        waitLoaderAnimation(driver);
        String payPalSelector = "//input[contains(@class,'js-checkout-payment-type-switch-paypal')]/ancestor::label";
        WebElement choosePayPal = driver.findElement(By.xpath(payPalSelector));
        waitClickableElem(driver, choosePayPal);
        waitClickableXpath(driver, payPalSelector);
        choosePayPal.click();
        additionalButtons(driver);
        try {
            WebElement payNow = driver.findElement(By.cssSelector(".js-paypal-create-account-submit"));
            payNow.click();
        } catch (org.openqa.selenium.WebDriverException ne) {
            /*just skip*/
        }

        WebElement phoneNumber = driver.findElement(By.cssSelector("input[name='ContactPhone']:not([type='hidden'])"));
        phoneNumber.sendKeys("1");

        WebElement identificationOK = driver.findElement(By.cssSelector("input[type='submit'][value='IDENTIFICATION OK']:not([type='hidden'])"));
        identificationOK.click();
    }

    private void additionalButtons(WebDriver driver) {
        /*Depends on site configuration*/
        String termsXpath = "//input[@name='tc']";
        if (driver.findElements(By.xpath(termsXpath)).size() > 0) {
            try {
                agreeToTerms(termsXpath, driver);
                enterBillingSection(driver);
            } catch (org.openqa.selenium.WebDriverException ne) {
                /*just skip*/
            }
        }
        try {
            goToPayment(driver);
        } catch (org.openqa.selenium.WebDriverException ne) {
            /*just skip*/
        }


    }

    private void payByCard(String deliveryType, String username, WebDriver driver) {
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
        guestCollectDetails(deliveryType, driver);
        waitLoaderAnimation(driver);
        additionalButtons(driver);
        pay(username, driver);
    }

    private void guestCollectDetails(String deliveryType, WebDriver driver) {
        if (deliveryType.equals("c")) {
            try {
                driver.findElement(By.id("checkout_form_postcode_lookup")).sendKeys("1");
                driver.findElement(By.cssSelector(".btn-secondary.checkout-fieldset-main-doubled.js-lookup-trigger")).click();
                waitLoaderAnimation(driver);
                driver.findElement(By.cssSelector(".js-select-qas-address")).click();
                waitLoaderAnimation(driver);
            } catch (org.openqa.selenium.NoSuchElementException ne) {
                /*just skip*/
            }
        }
    }

    private void switchToFrame(WebDriver driver) {
        WebElement paymentFrame = driver.findElement(By.cssSelector("iframe[allowtransparency='true']"));
        driver.switchTo().frame(paymentFrame);

    }

    private void enterCard(boolean isNewUser, WebDriver driver) {

        if (isNewUser) {
            WebElement cardNumber = driver.findElement(By.cssSelector("input#payment-cardnumber"));
            cardNumber.click();
            cardNumber.sendKeys("4111111111111111");
            driver.findElement(By.cssSelector("input#payment-cardholdername")).sendKeys("PETER CHEATER");
            Select month = new Select(driver.findElement(By.cssSelector("select#payment-expirydate-month")));
            month.selectByIndex(4);
            Select year = new Select(driver.findElement(By.cssSelector("select#payment-expirydate-year")));
            year.selectByIndex(4);
        }

        driver.findElement(By.cssSelector("input#payment-cvc")).sendKeys("123");

        String submitButton = "input#payment-submit";
        waitCssPresence(driver, submitButton);
        waitClickableCSS(driver, submitButton);
        driver.findElement(By.cssSelector(submitButton)).click();

        driver.switchTo().defaultContent();
    }

    private void agreeToTerms(String termsXpath, WebDriver driver) {
        driver.findElement(By.xpath(termsXpath))
                .click();
    }

    private void enterBillingSection(WebDriver driver) {
        driver.findElement(By.xpath("//button[contains(@class,'btn btn-primary checkout-submit-btn js-billing-tc-cta js-checkout-billing-card-tc-accept-button')]"))
                .click();
    }

    private void goToPayment(WebDriver driver) {
        String goToPaymentSelector = "button.checkout-submit-btn.js-checkout-billing-address-submit";
        try {
            while (driver.findElements(By.cssSelector(goToPaymentSelector)).size() != 0)
                driver.findElement(By.cssSelector(goToPaymentSelector))
                        .click();
        } catch (org.openqa.selenium.NoSuchElementException ne) {
            /*just skip*/
        }
    }

    private void payFast(WebDriver driver) {
        switchToFrame(driver);
        driver.findElement(By.cssSelector("input#payment-cvc")).sendKeys("737");

        String submitButton = "input#payment-submit";
        waitCssPresence(driver, submitButton);
        waitClickableCSS(driver, submitButton);
        driver.findElement(By.cssSelector(submitButton)).click();

        driver.switchTo().defaultContent();
    }

    private void pay(String username, WebDriver driver) {
        boolean isNewUser = enterCardCredentials(username, driver);
            switchToFrame(driver);
            enterCard(isNewUser, driver);
    }

    private void adyenPayment(WebDriver driver){
        driver.findElement(By.cssSelector(".pmBcard[name='brandName']")).click();
        driver.findElement(By.name("card.cvcCode")).sendKeys("737");
        driver.findElement(By.cssSelector(".paySubmitcard")).click();
    }

    private boolean enterCardCredentials(String username, WebDriver driver) {
        /*for unsaved card or guest*/
        return driver.findElements(By.cssSelector(".checkout-fieldset-item .checkbox-label")).size() != 0 || username.equals("g");
    }

}
