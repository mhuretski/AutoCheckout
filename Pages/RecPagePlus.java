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

    public RecPagePlus(String site, String hbpRepeat, WebDriver driver) {
        new RecPage(site, driver);
        if (!hbpRepeat.equals("+"))
            oneTime(driver);
        addToBasket(driver);
    }
}
