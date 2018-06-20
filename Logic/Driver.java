package Logic;

import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

class Driver {
    Driver(WebDriver driver) {
        open(driver);
    }

    private void open(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }
}
