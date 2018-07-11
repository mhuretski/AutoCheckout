package Pages;

import Logic.DriverTiming;
import Logic.Site;
import Logic.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Basket extends Wait{

    public Basket(String[] healthboxItem, int[] healthboxItemQty, String site, WebDriver driver) {
        open(site, driver);
        addHealthboxItem(healthboxItem, healthboxItemQty, driver);
    }

    public Basket() {
    }

    void open(String site, WebDriver driver) {
        driver.get(new Site().chosenSite(site) + "basket/basket.jsp");
    }

    private void addHealthboxItem(String[] healthboxItem, int[] healthboxItemQty, WebDriver driver) {

        for (int i = 0; i < healthboxItem.length; i++) {

            for (int j = 0; j < healthboxItemQty[i] - 1; j++) {
                driver.findElement(By.xpath("//span[starts-with(., '" + healthboxItem[i] + "')]/ancestor::article//dl[contains(@class, 'l-col quantity mobile-hidden')]//button[@type='button'][contains(@class, 'plus')]"))
                        .click();
                waitLoaderAnimation(driver);
            }
        }

    }

    public void remove(String username, String password, String site) {
        WebDriver driver = new ChromeDriver();
        new DriverTiming(4, driver);
        removeItems(username, password, site, driver);
        driver.quit();
    }

    private void removeItems(String username, String password, String site, WebDriver driver) {

        do {
            open(site, driver);
        }
        while (driver.findElements(By.xpath("//pre[text()[contains(.,'Your session expired due to inactivity')]]")).size() != 0);

        if (driver.findElements(By.cssSelector("a.lnk-not-you")).size() == 0)
            new Login(username, password, site, driver);

        open(site, driver);
        new Loyalty().removeLoyaltyCard(driver);

        while (isBasketEmpty(driver)) {
            driver.findElement(By.cssSelector("dl.l-col.quantity.mobile-hidden a.act-remove.removeCartItem"))
                    .click();
            waitLoaderAnimation(driver);

        }
    }

    private boolean isBasketEmpty(WebDriver driver) {
        return driver.findElements(By.cssSelector("section.s-basket-empty")).size() == 0;
    }

}
