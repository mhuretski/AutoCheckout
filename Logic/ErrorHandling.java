package Logic;

import Export.OrderLogging;
import Pages.Basket;
import org.openqa.selenium.WebDriver;

public class ErrorHandling {

    public ErrorHandling(int orderSequence, String site, String username, String password, org.openqa.selenium.WebDriverException e, WebDriver driver) {
        handle(orderSequence, site, username, password, e, driver);
    }

    private void handle(int orderSequence, String site, String username, String password, org.openqa.selenium.WebDriverException e, WebDriver driver) {

        e.printStackTrace();
        driver.quit();
        try {
            new Basket().remove(username, password, site);
        } catch (org.openqa.selenium.WebDriverException exInBasket) {
            exInBasket.printStackTrace();
            new OrderLogging().nonEmptiedBasket(orderSequence);
        }
    }

}
