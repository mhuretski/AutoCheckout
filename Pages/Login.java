package Pages;

import Logic.Site;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Login {
    public Login(String username, String password, String site, WebDriver driver) {
        login(username, password, site, driver);
    }

    private void login(String username, String password, String site, WebDriver driver) {

        driver.get(new Site().chosenSite(site) + "my-account/login.jsp?myaccount=true");

        WebElement login = driver.findElement(By.xpath("//input[@id='frm_login_email']"));
        login.click();

        login.sendKeys(username);

        driver.findElement(By.id("frm_login_password"))
                .sendKeys(password);

        WebElement loginButton = driver.findElement(By.cssSelector("li[class=buttons] input[name$=login]:not([type=hidden])"));
        loginButton.click();
    }

}
