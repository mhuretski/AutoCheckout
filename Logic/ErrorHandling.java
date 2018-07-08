package Logic;

import Export.OrderLogging;
import Pages.Basket;
import org.openqa.selenium.WebDriver;

public class ErrorHandling {

    private void handle(int orderSequence, String site, String username, String password, Exception e, WebDriver driver) {

        e.printStackTrace();
        driver.quit();
        try {
            new Basket().remove(username, password, site);
        } catch (Exception exInBasket) {
            exInBasket.printStackTrace();
            new OrderLogging().nonEmptiedBasket(orderSequence);
        }
    }

    public ErrorHandling(int orderSequence, String site, String username, String password, Exception e, WebDriver driver) {
        handle(orderSequence, site, username, password, e, driver);
    }

}
