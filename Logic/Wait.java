package Logic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Wait {

    private int timeOut = 2;

    protected void waitLoaderAnimation(WebDriver driver) {
        driver.findElement(By.cssSelector(".ajax-loader-bg[style='display: none;']"));
    }

    protected void waitXpathPresence(WebDriver driver, String xpathExpression) {
        new WebDriverWait(driver, timeOut)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathExpression)));
    }

    protected void waitCssPresence(WebDriver driver, String cssSelector) {
        new WebDriverWait(driver, timeOut)
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector)));
    }

    protected void waitClickableElem(WebDriver driver, WebElement webElement) {
        new WebDriverWait(driver, timeOut)
                .until(ExpectedConditions.elementToBeClickable(webElement));
    }

    protected void waitClickableCSS(WebDriver driver, String cssSelector) {
        new WebDriverWait(driver, timeOut)
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelector)));
    }

    protected void waitClickableXpath(WebDriver driver, String xpathSelector) {
        new WebDriverWait(driver, timeOut)
                .until(ExpectedConditions.elementToBeClickable(By.xpath(xpathSelector)));
    }
}
