package Pages;

import ImportExport.OrderLogging;
import Logic.Site;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class Basket {

    private int attemptsToClearBasket = 0;

    void open(String site, WebDriver driver) {
        driver.get(new Site().chosenSite(site) + "basket/basket.jsp");
    }

    private void addItem(String[] healthboxItem, int[] healthboxItemQty, WebDriver driver) throws InterruptedException {

        for (int i = 0; i < healthboxItem.length; i++) {
            WebElement itemQty = driver.findElement(By.xpath("//span[starts-with(., '" + healthboxItem[i] + "')]/ancestor::article//dl[contains(@class, 'l-col quantity mobile-hidden')]//button[@type='button'][contains(@class, 'plus')]"));
            for (int j = 0; j < healthboxItemQty[i] - 1; j++) {
                itemQty.click();
                sleep(3000);
            }
        }

    }

    public void remove(String site, int orderSequence) throws IOException, InterruptedException {
        WebDriver driver = new ChromeDriver();
        removeItems(site, orderSequence, driver);
        driver.quit();
    }

    private void removeItems(String site, int orderSequence, WebDriver driver) throws InterruptedException, IOException {
        open(site, driver);

        while (driver.findElements(By.xpath("//pre[text()[contains(.,'Your session expired due to inactivity')]]")).size() != 0) {
            open(site, driver);
        }

        new Loyalty().removeLoyaltyCard(driver);

        if (!isBasketEmpty(driver)) {
            WebElement itemsToRemove = driver.findElement(By.xpath("//dl[contains(@class, 'l-col quantity mobile-hidden')]//a[contains(@class,'act-remove removeCartItem')]"));
            itemsToRemove.click();
            sleep(3000);
            this.attemptsToClearBasket++;
            try {
                if (this.attemptsToClearBasket < 10) {
                    removeItems(site, orderSequence, driver);
                }
            } catch (Exception e) {
                System.err.println("Basket isn't cleaned");
                new OrderLogging("fail", orderSequence, driver);
                System.exit(1);
            }

        }

    }

    private boolean isBasketEmpty(WebDriver driver) {
        return driver.findElements(By.cssSelector(".s-basket.s-basket-empty")).size() == 0;
    }

    public Basket(String[] healthboxItem, int[] healthboxItemQty, String site, WebDriver driver) throws InterruptedException {
        open(site, driver);
        addItem(healthboxItem, healthboxItemQty, driver);
    }

    public Basket() {
    }
}
