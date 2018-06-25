package Pages;

import Logic.Site;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static java.lang.Thread.sleep;

public class Basket {

    void open(String site, WebDriver driver) {
        driver.get(new Site().chosenSite(site) + "basket/basket.jsp");
    }

    private void addItem(String[] healthboxItem, int[] healthboxItemQty, WebDriver driver) {

        for (int i = 0; i < healthboxItem.length; i++) {

            for (int j = 0; j < healthboxItemQty[i] - 1; j++) {
                driver.findElement(By.xpath("//span[starts-with(., '" + healthboxItem[i] + "')]/ancestor::article//dl[contains(@class, 'l-col quantity mobile-hidden')]//button[@type='button'][contains(@class, 'plus')]"))
                        .click();
                driver.findElement(By.xpath("//div[contains(@class,'ajax-loader-bg')][@style='display: none;']"));
            }
        }

    }

    public void remove(String site) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        removeItems(site, driver);
        driver.quit();
    }

    private void removeItems(String site, WebDriver driver) throws InterruptedException {

        do {
            open(site, driver);
        }
        while (driver.findElements(By.xpath("//pre[text()[contains(.,'Your session expired due to inactivity')]]")).size() != 0);

        if (driver.findElements(By.xpath("//a[contains(@class,'lnk-not-you')]")).size() == 0)
            new Login(site, driver);

        open(site, driver);
        new Loyalty().removeLoyaltyCard(driver);

        while (isBasketEmpty(driver)) {
            driver.findElement(By.xpath("//dl[contains(@class, 'l-col quantity mobile-hidden')]//a[contains(@class,'act-remove removeCartItem')]"))
                    .click();
            sleep(500);
            driver.findElement(By.xpath("//div[contains(@class,'ajax-loader')][@style='display: none;']"));

        }
    }

    private boolean isBasketEmpty(WebDriver driver) {
        return driver.findElements(By.xpath("//section[contains(@class,'s-basket s-basket-empty')]")).size() == 0;
    }

    public Basket(String[] healthboxItem, int[] healthboxItemQty, String site, WebDriver driver) throws InterruptedException {
        open(site, driver);
        addItem(healthboxItem, healthboxItemQty, driver);
    }

    public Basket() {
    }
}
