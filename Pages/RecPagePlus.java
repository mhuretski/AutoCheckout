package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RecPagePlus {

    private void addToBasket(WebDriver driver) {
            driver.findElement(By.cssSelector("input.healthbox-button-submit"))
                    .click();
    }

    public RecPagePlus(String site, WebDriver driver){
        new RecPage(site, driver);
        addToBasket(driver);
    }
}
