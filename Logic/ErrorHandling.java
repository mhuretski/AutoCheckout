package Logic;

import ImportExport.OrderLogging;
import Pages.Basket;
import org.openqa.selenium.WebDriver;

public class ErrorHandling {

    private void handle(int orderSequence, String site, String username, String password, Exception e, WebDriver driver) {

        e.printStackTrace();
        driver.quit();
        try {
            new Basket().remove(username, password, site);
        } catch (InterruptedException exInBasket) {
            exInBasket.printStackTrace();
        }
        new OrderLogging("basket", orderSequence, driver);

    }

    public ErrorHandling(Exception e, WebDriver driver){
        System.err.println("Something went wrong.");
        e.printStackTrace();
        driver.quit();
    }

    public ErrorHandling(int orderSequence, String site, String username, String password, Exception e, WebDriver driver) {
        handle(orderSequence, site, username, password, e, driver);
    }
}