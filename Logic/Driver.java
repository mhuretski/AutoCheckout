package Logic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Driver {

    public WebDriver startDriver(int timeout) {
        Logger logger = Logger.getLogger("org.openqa.selenium.remote.ProtocolHandshake");
        logger.setLevel(Level.OFF);
        ChromeDriverService service = new ChromeDriverService.Builder()
                .withSilent(true)
                .build();
        WebDriver driver = new ChromeDriver(service);
        driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }

}
