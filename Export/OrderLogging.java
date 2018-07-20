package Export;

import Logic.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class OrderLogging extends Wait {

    private PrintWriter writer(OrderListing orderListing) throws IOException {

        return new PrintWriter(
                new BufferedWriter(
                        new FileWriter(orderListing.getOutputFolder() + "orders_details.txt", true)));
    }

    private void orderStuff(int orderSequence, PrintWriter writer, WebDriver driver, OrderListing orderListing, List<String> prices) {
        WebElement orderNumberElem = driver.findElement(By.cssSelector("span.order-number"));
        driver.findElement(By.cssSelector("i.ico-s.ico-expand.ico-20"))
                .click();
        String orderNumber = orderNumberElem.getText();
        writer.append(orderNumber).append(" ");

        orderListing.addOrderSequence(String.valueOf(orderSequence));
        orderListing.addOrderNumber(orderNumber);

        getTotal(writer, driver);
        getAllPrices(prices, writer);

    }

    private void getAllPrices(List<String> prices, PrintWriter writer) {
        getItemPrice(prices, writer);
        writer.println();
    }

    private void getItemPrice(List<String> prices, PrintWriter writer) {
        for (String price : prices) {
            writer.append(price);
        }
    }

    private void getTotal(PrintWriter writer, WebDriver driver) {
        String orderTotalCssSelector = "div.checkout-order-summary-total-pricing span.pull-right p.heading-3";
        waitCssPresence(driver, orderTotalCssSelector);
        WebElement orderTotal = driver.findElement(By.cssSelector(orderTotalCssSelector));

        while (orderTotal.getText() == null || orderTotal.getText().equals(""))
            orderTotal = driver.findElement(By.cssSelector(orderTotalCssSelector));
        writer.append("Total: ").append(orderTotal.getText()).append(" | ");
    }

    public void success(int orderSequence, WebDriver driver, OrderListing orderListing, List<String> prices) {
        try (PrintWriter writer = writer(orderListing)) {
            writer.append(String.valueOf(orderSequence)).append(". ");
            orderStuff(orderSequence, writer, driver, orderListing, prices);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    public void nonEmptiedBasket(int orderSequence, OrderListing orderListing) {
        try (PrintWriter writer = writer(orderListing)) {
            writer.append(String.valueOf(orderSequence)).append(" ")
                    .append("Basket might not be empty.").println("");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
