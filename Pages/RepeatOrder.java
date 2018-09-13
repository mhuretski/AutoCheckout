package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RepeatOrder {

    public RepeatOrder(String[] healthboxItem, String[] hbRepeatOrder, String[] normalItem, String[] normalRepeatOrder, String site, WebDriver driver) {
        new Basket().open(site, driver);
        chooseRepeatOrder(healthboxItem, hbRepeatOrder, normalItem, normalRepeatOrder, driver);
    }

    private void chooseRepeatOrder(String[] healthboxItem, String[] hbRepeatOrder, String[] normalItem, String[] normalRepeatOrder, WebDriver driver) {
        for (int i = 0; i < healthboxItem.length; i++) {
            if (hbRepeatOrder[i].equals("+"))
                driver.findElement(By.id("og-on-sc_recs-" + healthboxItem[i])).click();
        }
        for (int i = 0; i < normalItem.length; i++) {
            if (normalRepeatOrder[i].equals("+"))
                driver.findElement(By.id("og-on-sc-" + normalItem[i])).click();
        }
    }

}
