package Pages;

import Logic.Site;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Login {
    public Login(String site, WebDriver driver){
        login(site, driver);
    }
    private void login(String site, WebDriver driver) {

        driver.get(new Site().chosenSite(site) + "my-account/login.jsp?myaccount=true");

        WebElement login = driver.findElement(By.xpath("//input[@id='frm_login_email']"));
        login.click();
        login.sendKeys("viewreq@mailinator.com");

        WebElement password = driver.findElement(By.id("frm_login_password"));
        password.sendKeys("asdasd");

        WebElement loginButton = driver.findElement(By.cssSelector("li[class=buttons] input[name$=login]:not([type=hidden])"));
        loginButton.click();
    }

}
