package Pages;

import Logic.Site;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

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
            if (driver.findElements(By.xpath("//button[@type='submit'][@data-rr-sku-id='" + itemId + "']")).size() > 0) {
                WebElement addToBasketIfFirstInDropdown = driver.findElement(By.xpath("//button[@type='submit'][@data-rr-sku-id='" + itemId + "']"));
                addToBasketIfFirstInDropdown.click();
            } else {
                /*find element*/
                WebElement chosenContainerForItem = driver.findElement(By.xpath("//option[@data-sku-id='" + itemId + "']"));
                /*get its "value" parameter*/
                String getValue = chosenContainerForItem.getAttribute("value");

                /*select by value parameter*/
                Select dropdown = new Select(driver.findElement(By.xpath("//option[@data-sku-id='" + itemId + "']/ancestor::select")));
                dropdown.selectByValue(getValue);

                /*wait till data is updated*/
                if (driver.findElements(By.xpath("//option[@data-sku-id='" + itemId + "']/ancestor::fieldset//input[@value='" + itemId + "']")).size() > 0) {
                    /*click "Add to Basket" using item attribute in dropdown*/
                    driver.findElement(By.xpath("//option[@data-sku-id='" + itemId + "']/ancestor::fieldset/fieldset")).click();
                }
            }
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
        if (healthbox == 3) {
            openNormalHealthbox(driver);
            addToBasketNormalHealthbox(itemId, driver);
        }
        if (healthbox == 4) {
            addToBasketNormalHealthbox(itemId, driver);
        }
    }

    public RecPage(int healthbox, String[] itemId, String site, WebDriver driver) {
        openHLP(site, driver);
        openRecsPage(driver);
        whatHealthbox(healthbox, itemId, driver);

    }

    RecPage(String site, WebDriver driver) {
        openHLP(site, driver);
        openRecsPage(driver);
    }

}
