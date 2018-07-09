package Logic;

import Export.OrderListing;
import Export.OrderLogging;
import Pages.Basket;
import org.openqa.selenium.WebDriver;

public class ErrorHandling {

    public ErrorHandling(int orderSequence, String site, String username, String password, org.openqa.selenium.WebDriverException e, WebDriver driver, OrderListing orderListing) {
        handle(orderSequence, site, username, password, e, driver, orderListing);
    }

    private void handle(int orderSequence, String site, String username, String password, org.openqa.selenium.WebDriverException e, WebDriver driver, OrderListing orderListing) {

        e.printStackTrace();
        driver.quit();
        try {
            new Basket().remove(username, password, site);
        } catch (org.openqa.selenium.WebDriverException exInBasket) {
            exInBasket.printStackTrace();
            new OrderLogging().nonEmptiedBasket(orderSequence, orderListing);
        }
    }

}
