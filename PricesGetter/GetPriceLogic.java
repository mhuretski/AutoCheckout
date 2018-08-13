package PricesGetter;

import Logic.Site;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

class GetPriceLogic {

    private String price;

    GetPriceLogic(String sequence, String itemId, String site, WebDriver driver) {
        searchForItem(itemId, site, driver);
        trackPrice(sequence, itemId, site, driver);
    }

    private void searchForItem(String itemId, String site, WebDriver driver) {
        new Site().open(site, driver);

        WebElement searchField = driver.findElement(By.cssSelector("input[type='search']"));
        searchField.sendKeys(itemId);
        searchField.sendKeys(Keys.ENTER);
    }

    private void trackPrice(String sequence, String item, String site, WebDriver driver) {
        try {
            String priceText = driver.findElement(By.cssSelector("[data-sku-id=\"" + item + "\"] .clearfix .mobile-hidden .prod-size-price")).getText();
            priceText = priceText.replace('\n', ' ');
            this.price = sequence + "," + item + ":,\"" + priceText + "\"" + "," + site;
        } catch (org.openqa.selenium.NoSuchElementException ne){
            this.price = sequence + "," + item + ":,\"Price not found\"" + "," + site;
        }
    }

    String getPrice() {
        return this.price;
    }
}

