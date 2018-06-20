package Logic;

import org.openqa.selenium.WebDriver;

public class Site {

    public String chosenSite(String site) {
        return "https://" + site.toLowerCase() + "/";
    }

    public void open(String site, WebDriver driver) {
        driver.get(chosenSite(site));
    }
}
