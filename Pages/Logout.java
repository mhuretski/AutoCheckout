package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Logout {

    private void logout(WebDriver driver){
        driver.findElement(By.cssSelector(".lnk-not-you")).click();
    }

    public Logout(WebDriver driver){
        logout(driver);
    }
}
