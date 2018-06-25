package Pages;

import Logic.Site;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RecPage {
    private void openHLP(String site, WebDriver driver) {
        driver.get(new Site().chosenSite(site) + "healthbox/healthbox.jsp");
    }

    private void openRecsPage(WebDriver driver) {
        driver.findElement(By.xpath("//a[contains(@class,'button healthbox-button orangeSubmit')]"))
                .click();
    }

    private void openNormalHealthbox(WebDriver driver) {
        driver.findElement(By.xpath("//span[contains(@class,'hbp-switcher mobile-hidden')]//a[@data-hb-form-submit='#hbp-switcher-form']"))
                .click();
    }

    private void addToBasketNormalHealthbox(String[] arrayItemId, WebDriver driver) {
        for (String itemId : arrayItemId) {
            driver.findElement(By.xpath("//button[@type='submit'][@data-rr-sku-id='" + itemId + "']"))
                    .click();
        }

    }

    private void addToBasketFromCarousel(String[] arrayItemId, WebDriver driver) {
        for (String itemId : arrayItemId) {
            driver.findElement(By.xpath("//div[contains(@class,'carousel-prod-submit-bt-wrapper')]//button[@data-og-product='" + itemId + "']"))
                    .click();
        }

    }

    private void whatHealthbox(int healthbox, String[] itemId, WebDriver driver) {
        if (healthbox == 1) {
            addToBasketFromCarousel(itemId, driver);
        }
        if(healthbox == 3) {
            openNormalHealthbox(driver);
            addToBasketNormalHealthbox(itemId, driver);
        }
        if(healthbox == 4) {
            addToBasketNormalHealthbox(itemId, driver);
        }
    }

    public RecPage(int healthbox, String[] itemId, String site, WebDriver driver) {
        openHLP(site, driver);
        openRecsPage(driver);
        whatHealthbox(healthbox, itemId, driver);

    }

    RecPage(String site, WebDriver driver){
        openHLP(site, driver);
        openRecsPage(driver);
    }

}
