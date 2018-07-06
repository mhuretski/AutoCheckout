package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RecPagePlus {

    private void oneTime(WebDriver driver) {
        driver.findElement(By.cssSelector(".subscription-option-label"))
                .click();
    }

    private void addToBasket(WebDriver driver) {
        driver.findElement(By.cssSelector("input.healthbox-button-submit"))
                .click();
    }

    private void checkPlusPage(WebDriver driver) {
        if (driver.findElements(By.cssSelector(".hbp-page")).size() == 0)
            driver.findElement(By.cssSelector(".hb-switcher-button"))
                    .click();
    }

    public RecPagePlus(String site, String hbpRepeat, WebDriver driver) {
        new RecPage(site, driver);
        checkPlusPage(driver);
        if (!hbpRepeat.equals("+"))
            oneTime(driver);
        addToBasket(driver);
    }
}
