package ImportExport;

import Logic.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class OrderLogging extends Wait {

    private PrintWriter writer() throws IOException {
        return new PrintWriter(
                new BufferedWriter(
                        new FileWriter("C:\\Users\\Maksim_Huretski\\Desktop\\Everest\\OrderNumber.txt", true)));
    }

    private void orderStuff(PrintWriter writer, WebDriver driver) {
        WebElement orderNumber = driver.findElement(By.cssSelector("span.order-number"));
        driver.findElement(By.cssSelector("i.ico-s.ico-expand.ico-20"))
                .click();
        String orderTotalCssSelector = "div.checkout-order-summary-total-pricing span.pull-right p.heading-3";
        waitCssPresence(driver, orderTotalCssSelector);
        WebElement orderTotal = driver.findElement(By.cssSelector(orderTotalCssSelector));
        writer.append(orderNumber.getText()).append(" ");
        while (orderTotal.getText() == null || orderTotal.getText().equals(""))
            orderTotal = driver.findElement(By.cssSelector(orderTotalCssSelector));
        writer.append(orderTotal.getText());
        writer.println("");

    }

    public OrderLogging(String result, int orderSequence, WebDriver driver) {

        try {
            PrintWriter writer = writer();
            writer.append(String.valueOf(orderSequence)).append(" ");

            switch (result) {
                case "success":
                    orderStuff(writer, driver);
                    break;
                case "basket":
                    writer.append("Basket might not be empty.").println("");
                    break;
                default:
                    writer.append("Order isn't placed.").println("");
                    break;
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
