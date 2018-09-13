package Pages;

import Logic.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static java.lang.Thread.sleep;

public class RecPagePlus extends Wait {

    public RecPagePlus(String[] pills, String site, String hbpRepeat, WebDriver driver) {
        new RecPage(site, driver);
        checkPlusPage(driver);
//        deleteBadPill(site, driver);
        if (!pills[0].equals("-")) {
            clearAllPills(driver);
            addPills(pills, driver);
        }
        checkOutOfStock(driver);
        oneTimeHBPlusIfNeeded(hbpRepeat, driver);
        addToBasket(driver);
        validateHBPlus(driver);
    }

    private String addPillSelector = ".hpb-pill-click-to-add";

    private void oneTimeHBPlusIfNeeded(String hbpRepeat, WebDriver driver) {
        if (!hbpRepeat.equals("+"))
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
        while (driver.findElements(By.xpath(removeOOSbtn)).size() > 0) {
            try {
                driver.findElement(By.xpath(removeOOSbtn)).click();
                approveRemoval(driver);
            } catch (org.openqa.selenium.WebDriverException exception) {
                System.out.println("Probably there are no OOS pills");
            }
        }
    }

    private void approveRemoval(WebDriver driver) {
        waitLoaderAnimation(driver);
        driver.findElement(By.cssSelector("button.hbp-popup-button-primary[type='submit']")).click();
        waitHBPLoaderAnimation(driver);
    }

    private void addPills(String[] pills, WebDriver driver) {
        String searchWindow = ".hbp-search-title";
        String pillSelectorStart = "[data-sku-id='";
        String pillSelectorEnd = "']";

        for (String pillId : pills) {
            waitLoaderAnimation(driver);
            driver.findElement(By.cssSelector(addPillSelector)).click();
            waitLoaderAnimation(driver);
            if (driver.findElements(By.cssSelector(pillSelectorStart + pillId + pillSelectorEnd)).size() != 0)
                driver.findElement(By.cssSelector(pillSelectorStart + pillId + pillSelectorEnd)).click();
            else try {
                WebElement search = driver.findElement(By.cssSelector("input[type='text']"));
                search.sendKeys(pillId);
                search.sendKeys(Keys.ENTER);
                WebElement addPillBtn = driver.findElement(By.cssSelector(pillSelectorStart + pillId + pillSelectorEnd));
                addPillBtn.click();
                if (driver.findElements(By.cssSelector(pillSelectorStart + pillId + pillSelectorEnd)).size() > 0)
                    addPillBtn.click();
            } catch (org.openqa.selenium.WebDriverException we) {
                System.out.println("no scroll or no item " + pillId);
                we.printStackTrace();
            }
            if (driver.findElements(By.cssSelector(".hbp-popup-button-link[type='submit']")).size() > 0){
                try {
                    waitLoaderAnimation(driver);
                    sleep(3001);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                driver.findElement(By.cssSelector(".hbp-popup-button-link[type='submit']")).click();
            }
            /*waitAbsence works faster than explicit wait*/
            waitAbsence(driver, searchWindow);
        }

    }

    private void deleteBadPill(String site, WebDriver driver) {
        String badPillSelector = " //a[@data-hb-sku-id='019377']/ancestor::div[contains(@class,'hbp-pills-list-item hbp-pill')]//button[contains(@class,'hbp-pill-button hbp-pill-button-remove')]";
        if (driver.findElements(By.xpath(badPillSelector)).size() > 0) {
            driver.findElement(By.xpath(badPillSelector)).click();
            waitLoaderAnimation(driver);
            driver.findElement(By.cssSelector("button.hbp-popup-button-primary[type='submit']")).click();
            new RecPage(site, driver);
        }
    }

    private void clearAllPills(WebDriver driver) {
        String removePill = ".hbp-pill-button-remove";
        while (driver.findElements(By.cssSelector(removePill)).size() > 0) {
            driver.findElement(By.cssSelector(removePill)).click();
            approveRemoval(driver);
        }
    }

    private void validateHBPlus(WebDriver driver) {
        if (driver.findElements(By.cssSelector(addPillSelector)).size() > 3) {
            System.out.println("Not enough HBPlus pills.");
        } else try {
            driver.findElement(By.cssSelector(".act-remove.removeCartItem"));
        } catch (org.openqa.selenium.NoSuchElementException ne) {
            System.out.println("HBPlus isn't added to basket.");
        }
    }

}
