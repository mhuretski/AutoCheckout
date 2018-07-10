package Pages;

import Logic.Wait;
import Secured.SecureAdmin;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RecPagePlus extends Wait {

    private void oneTime(WebDriver driver) {
        driver.findElement(By.cssSelector(".subscription-option-label"))
                .click();
    }

    private void addToBasket(WebDriver driver) {
        driver.findElement(By.cssSelector("input.healthbox-button-submit"))
                .click();
    }

    private void checkPlusPage(WebDriver driver) {
        if (driver.findElements(By.cssSelector(".hbp-page")).size() == 0)
            driver.findElement(By.cssSelector(".hb-switcher-button"))
                    .click();
    }

    private void checkOutOfStock(WebDriver driver) {
        String removeOOSbtn = "//div[contains(@class,'hbp-pill-oos')]/ancestor::div[contains(@class,'hbp-pills-list')]//button[contains(@class,'hbp-pill-button-remove')]";
        String approveRemove = "button.hbp-popup-button-primary[type='submit']";
        while (driver.findElements(By.xpath(removeOOSbtn)).size() > 0) {
            try {
                driver.findElement(By.xpath(removeOOSbtn)).click();
                waitLoaderAnimation(driver);
                driver.findElement(By.cssSelector(approveRemove)).click();
                waitHBPLoaderAnimation(driver);
            } catch (org.openqa.selenium.WebDriverException exception){
                System.out.println("Probably there are no OOS pills");
            }
        }

    }

    private void turnOffErrors(SecureAdmin admin, WebDriver driver){
        driver.get(admin.getCalc());
        driver.findElement(By.cssSelector("input[value='true']")).click();
        driver.findElement(By.cssSelector("input[type='submit']")).click();
    }

    private void turnOnErrors(SecureAdmin admin, WebDriver driver){
        driver.get(admin.getCalc());
        driver.findElement(By.cssSelector("input[value='false']")).click();
        driver.findElement(By.cssSelector("input[type='submit']")).click();
    }

    public RecPagePlus(String site, String hbpRepeat, WebDriver driver) {
        SecureAdmin admin = new SecureAdmin();
        turnOffErrors(admin, driver);

        new RecPage(site, driver);
        checkPlusPage(driver);
        checkOutOfStock(driver);
        if (!hbpRepeat.equals("+"))
            oneTime(driver);
        addToBasket(driver);

        turnOnErrors(admin, driver);
    }
}
