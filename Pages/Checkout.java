package Pages;

import Logic.Site;
import Logic.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class Checkout extends Wait {

    public Checkout(String username, String deliveryType, String site, WebDriver driver) {
        open(site, driver);
        guestUser(username, driver);
        deliveryOrCollection(username, deliveryType, driver);
        continueToPayment(username, deliveryType, driver);
    }

    private void guestUser(String username, WebDriver driver) {
        if (username.equals("g")) {
            WebElement enterEmail = driver.findElement(By.cssSelector("input#js-welcome-email"));
            enterEmail.sendKeys("userDoesNotExist@noUser.NotCom");

            String continueAsGuest = "button#js-welcome-guest-submit";
//            while (driver.findElements(By.cssSelector(".checkout-create-account.hidden")).size() > 0) {
//                try {
//                    driver.findElement(By.cssSelector(continueAsGuest));
//                } catch (org.openqa.selenium.NoSuchElementException ne) {
//                    /*just skip*/
//                }
//            }
            driver.findElement(By.cssSelector(continueAsGuest)).click();
            waitLoaderAnimation(driver);
        }
    }

    private void deliveryOrCollection(String username, String deliveryType, WebDriver driver) {
        if (deliveryType.equals("d"))
            delivery(username, driver);
        else collection(driver);
    }

    private void collection(WebDriver driver) {
        waitLoaderAnimation(driver);
        chooseCollection(driver);

        String storeSelector = ".sections-grid.sections-full-grid .one-column-section";
        firstTimeCollection(storeSelector, driver);
        waitLoaderAnimation(driver);

        WebElement chooseStore = driver.findElement(By.cssSelector(storeSelector + " .stores-list-table-td"));
        chooseStore.click();

        waitLoaderAnimation(driver);
        try {
            do {
                WebElement chooseStandardDeliveryTime = driver.findElement(By.cssSelector(".checkout-delivery-choose-option .section"));
                chooseStandardDeliveryTime.click();
                waitLoaderAnimation(driver);
            } while (driver.findElements(By.cssSelector(".checkout-submit-btn.js-checkout-collection-details-submit")).size() == 0);
        } catch (org.openqa.selenium.NoSuchElementException ne) {
            System.err.println("Can't click \"Continue to payment\"" + ne);
        }
    }

    private void randomPopUp(WebDriver driver) {
        if (driver.findElements(By.id("popup-content")).size() != 0) {
            try {
                WebElement closeButton = driver.findElement(By.cssSelector("div#popup-content .modal-close-btn"));
                closeButton.click();
            } catch (org.openqa.selenium.NoSuchElementException ne) {
                /*just skip*/
            }
        }
    }

    private void chooseCollection(WebDriver driver) {
        String collectionNotChosen = ".js-checkout-delivery-method-selector[value='collection'].input-radio.js-checkout-delivery-method-selector:not(valid)";
        while (driver.findElements(By.cssSelector("input#postcode")).size() == 0) {
            try {
                waitLoaderAnimation(driver);
                driver.findElement(By.cssSelector(collectionNotChosen)).click();
            } catch (org.openqa.selenium.WebDriverException ne) {
                /*just skip*/
            }
        }

    }

    private void firstTimeCollection(String storeSelector, WebDriver driver) {
        if (driver.findElements(By.cssSelector(storeSelector)).size() == 0) {
            try {
                WebElement searchFiled = driver.findElement(By.cssSelector("input#postcode"));
                searchFiled.sendKeys("London");
                searchFiled.sendKeys(Keys.ENTER);
                waitLoaderAnimation(driver);
                randomPopUp(driver);
            } catch (org.openqa.selenium.NoSuchElementException ne) {
                /*just skip*/
            }
        }
    }

    private void delivery(String username, WebDriver driver) {
        String guest = "g";
        if (username.equals(guest)) {
            guestOrder(driver);
        }
        if ((driver.findElements(By.cssSelector("button.checkout-submit-btn")).size() == 0
                || driver.findElements(By.id("checkout_form_postcode_lookup")).size() != 0)
                && !username.equals(guest)) {
            waitLoaderAnimation(driver);
            firstOrder(username, driver);
            additionalButtons(driver);
        }
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

    private void guestOrder(WebDriver driver) {
        String deliveryNotChosen = ".js-checkout-delivery-method-selector[value='delivery'].input-radio.js-checkout-delivery-method-selector:not(valid)";

        if (driver.findElements(By.id("checkout_form_postcode_lookup")).size() == 0)
            driver.findElement(By.cssSelector(deliveryNotChosen)).click();
        driver.findElement(By.id("checkout_form_postcode_lookup")).sendKeys("1");
        driver.findElement(By.cssSelector(".btn-secondary.checkout-fieldset-main-doubled")).click();
        waitLoaderAnimation(driver);

        do {
            driver.findElement(By.cssSelector(".js-select-qas-address")).click();
            waitLoaderAnimation(driver);
        }
        while (driver.findElements(By.cssSelector(".deliver_to_view_label.label")).size() == 0);

        userDetailsGuest(driver);
        waitAbsence(driver, ".js-delivery-address-submit-button[disabled]");
        waitLoaderAnimation(driver);

        String chooseDeliverySelector = ".js-delivery-method-holder .js-delivery-address-submit-button";

        waitClickableCSS(driver, chooseDeliverySelector);
        driver.findElement(By.cssSelector(chooseDeliverySelector)).click();

        waitLoaderAnimation(driver);

    }

    private void firstOrder(String username, WebDriver driver) {
        String deliveryNotChosen = ".js-checkout-delivery-method-selector[value='delivery'].input-radio.js-checkout-delivery-method-selector:not(valid)";

        driver.findElement(By.cssSelector(deliveryNotChosen)).click();
        driver.findElement(By.id("checkout_form_postcode_lookup")).sendKeys("1");
        driver.findElement(By.cssSelector(".btn-secondary.checkout-fieldset-main-doubled")).click();
        waitLoaderAnimation(driver);

        while (driver.findElements(By.cssSelector(".deliver_to_view_label.label")).size() == 0) {
            driver.findElement(By.cssSelector(".js-select-qas-address")).click();
            waitLoaderAnimation(driver);
        }
        if (username.equals("g")) {
            userDetailsGuest(driver);
        }
        waitAbsence(driver, ".js-delivery-address-submit-button[disabled]");

        String chooseDeliverySelector = ".js-delivery-address-submit-button";
        driver.findElement(By.cssSelector(chooseDeliverySelector)).click();

    }

    private void open(String site, WebDriver driver) {
        driver.get(new Site().chosenSite(site) + "checkout/checkout.jsp");
    }

    private void continueToPayment(String username, String deliveryType, WebDriver driver) {
        if (username.equals("g") && deliveryType.equals("c"))
            userDetailsGuest(driver);

        String continuePaymentCSS;
        if (deliveryType.equals("d"))
            continuePaymentCSS = "button.checkout-submit-btn.js-delivery-option-submit-button";
        else
            continuePaymentCSS = "button.checkout-submit-btn.js-checkout-collection-details-submit";

        waitClickableCSS(driver, continuePaymentCSS);
        waitLoaderAnimation(driver);
        String deliveryPassed = ".delivery-block.completed";
        while (driver.findElements(By.cssSelector(deliveryPassed)).size() == 0) {
            try {
                WebElement continuePayment = driver.findElement(By.cssSelector(continuePaymentCSS));
                continuePayment.click();
            } catch (org.openqa.selenium.WebDriverException ne) {
                /*just skip*/
            }
        }
        waitLoaderAnimation(driver);

    }

    private void userDetailsGuest(WebDriver driver) {
        String firstName = "fieldset:not(.hidden) input#checkout_form_first_name";
        while (driver.findElements(By.cssSelector(firstName)).size() == 0) {
            try {
                driver.findElement(By.cssSelector(firstName));
            } catch (org.openqa.selenium.WebDriverException ne) {
                /*just skip*/
            }
        }
        driver.findElement(By.cssSelector(firstName)).sendKeys("Perchick");
        WebElement lastName = driver.findElement(By.cssSelector("fieldset:not(.hidden) input#checkout_form_last_name"));
        lastName.sendKeys("Sladkyi");

        enterPhoneNumber(driver);
    }

    private void enterPhoneNumber(WebDriver driver) {
        driver.findElement(By.cssSelector("fieldset:not(.hidden) input#checkout_form_phone_number"))
                .sendKeys("012345678");
    }

    private void chooseAddress(String addressSelector, WebDriver driver) {
        driver.findElement(By.cssSelector(addressSelector))
                .click();
        waitLoaderAnimation(driver);
    }

    private void chooseDeliveryButton(WebDriver driver) {
        driver.findElement(By.cssSelector("button#js-address-list-cta"))
                .click();
    }

}
