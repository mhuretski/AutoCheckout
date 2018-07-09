package Logic;

import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class DriverTiming {

    public DriverTiming(int timeout, WebDriver driver) {
        open(timeout, driver);
    }

    private void open(int timeout, WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

}
