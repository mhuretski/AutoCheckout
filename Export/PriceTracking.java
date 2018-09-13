package Export;

import Pages.Basket;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

public class PriceTracking {

    private String spaceWithDots = ": ";
    private String space = " ";
    private List<String> priceForEachItem = new ArrayList<>();

    public PriceTracking(String[] healthboxItem, String[] normalItem, String hbPlus, String site, WebDriver driver) {
        new Basket().open(site, driver);

        trackPrice(healthboxItem, driver);
        trackPrice(normalItem, driver);
        trackPrice(hbPlus, driver);

    }

    public List<String> getPriceForEachItem() {
        return priceForEachItem;
    }

    private void trackPrice(String[] items, WebDriver driver) {
        String firstPartSelector = "//span[starts-with(., '";
        String lastPartSelector = "')]/ancestor::article//div[contains(@class,'current-price')]";
        String price;
        for (String item : items) {
            if (item.equals("-")) break;
            price = driver.findElement(By.xpath(firstPartSelector + item + lastPartSelector)).getText();
            priceForEachItem.add(item + spaceWithDots + price + space);
        }
    }

    private void trackPrice(String hbPlus, WebDriver driver) {
        if (hbPlus.equals("+")) {
            String hbPlusXpath = "//span[contains(@class,'healthbox-break-word')]/ancestor::article//div[contains(@class,'current-price')]";
            String price = driver.findElement(By.xpath(hbPlusXpath)).getText();
            priceForEachItem.add("HB+" + spaceWithDots + price + space);
        }
    }

}
