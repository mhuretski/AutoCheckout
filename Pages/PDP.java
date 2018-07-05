package Pages;

import Logic.Site;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PDP {

    private void searchForItem(String itemId, String site, WebDriver driver) {
        new Site().open(site, driver);

        WebElement searchField = driver.findElement(By.cssSelector("input[type='search']"));
        searchField.sendKeys(itemId);
        searchField.sendKeys(Keys.ENTER);
    }

    private void amountOfItemsToAdd(int qty, WebDriver driver) {
        WebElement itemQty = driver.findElement(By.cssSelector("button.plus"));
        for (int i = 0; i < qty - 1; i++)
            itemQty.click();
    }

    private void addToBasket(WebDriver driver) {
        driver.findElement(By.cssSelector("input[type='submit']"))
                .click();
    }

    public PDP(String[] itemId, int[] itemQty, String site, WebDriver driver) {
        for (int i = 0; i < itemId.length; i++) {
            searchForItem(itemId[i], site, driver);
            amountOfItemsToAdd(itemQty[i], driver);
            addToBasket(driver);
        }
    }

}
