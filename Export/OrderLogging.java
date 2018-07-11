package Export;

import Logic.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class OrderLogging extends Wait {

    private PrintWriter writer(OrderListing orderListing) throws IOException {

        return new PrintWriter(
                new BufferedWriter(
                        new FileWriter(orderListing.getOutputFolder() + "orders_details.txt", true)));
    }

    private void orderStuff(String[] healthboxItem, String[] normalItem, int orderSequence, PrintWriter writer, WebDriver driver, OrderListing orderListing) {
        WebElement orderNumberElem = driver.findElement(By.cssSelector("span.order-number"));
        driver.findElement(By.cssSelector("i.ico-s.ico-expand.ico-20"))
                .click();
        String orderNumber = orderNumberElem.getText();
        writer.append(orderNumber).append(" ");

        orderListing.addOrderSequence(String.valueOf(orderSequence));
        orderListing.addOrderNumber(orderNumber);

        getAllPrices(healthboxItem, normalItem, writer, driver);
        getTotal(writer, driver);
    }

    private void getAllPrices(String[] healthboxItem, String[] normalItem, PrintWriter writer, WebDriver driver) {
        getItemPrice(healthboxItem, writer, driver);
        getItemPrice(normalItem, writer, driver);

    }

    private void getItemPrice(String[] allItems, PrintWriter writer, WebDriver driver) {
        if (!allItems[0].equals("-")) {
            for (String itemId : allItems) {
                try {
                    String itemSelector = "//div[@data-og-product='" + itemId + "']/ancestor::tr//td[contains(@class,'td price')]//p[contains(@class,'label')]";
                    WebElement itemPrice = driver.findElement(By.xpath(itemSelector));
                    while (itemPrice.getText() == null || itemPrice.getText().equals(""))
                        itemPrice = driver.findElement(By.xpath(itemSelector));
                    writer.append(itemId).append(": ").append(itemPrice.getText()).append(" ");
                } catch (org.openqa.selenium.WebDriverException e) {
                    writer.append(itemId).append(": - ");
                    System.err.println("Couldn't get price for " + itemId + ". Item isn't eligible for OG (selector).");
                }
            }
        }
    }

    private void getTotal(PrintWriter writer, WebDriver driver) {
        String orderTotalCssSelector = "div.checkout-order-summary-total-pricing span.pull-right p.heading-3";
        waitCssPresence(driver, orderTotalCssSelector);
        WebElement orderTotal = driver.findElement(By.cssSelector(orderTotalCssSelector));

        while (orderTotal.getText() == null || orderTotal.getText().equals(""))
            orderTotal = driver.findElement(By.cssSelector(orderTotalCssSelector));
        writer.append("Total: ").append(orderTotal.getText());
        writer.println("");
    }

    public void success(int orderSequence, String[] healthboxItem, String[] normalItem, WebDriver driver, OrderListing orderListing) {
        try {
            PrintWriter writer = writer(orderListing);
            writer.append(String.valueOf(orderSequence)).append(". ");

            orderStuff(healthboxItem, normalItem, orderSequence, writer, driver, orderListing);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    public void nonEmptiedBasket(int orderSequence, OrderListing orderListing) {
        try {
            PrintWriter writer = writer(orderListing);
            writer.append(String.valueOf(orderSequence)).append(" ")
                    .append("Basket might not be empty.").println("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
