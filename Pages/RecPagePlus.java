package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RecPagePlus {

    private void addToBasket(WebDriver driver) {
            driver.findElement(By.xpath("//input[contains(@class,'healthbox-button-submit healthbox-button-orange js-hbp-submit-btn')]"))
                    .click();
    }

    public RecPagePlus(String site, WebDriver driver){
        new RecPage(site, driver);
        addToBasket(driver);
    }
}

