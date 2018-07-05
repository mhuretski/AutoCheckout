package ImportExport;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import static java.lang.Thread.sleep;

public class OrderLogging {

    private PrintWriter writer() throws IOException {
        return new PrintWriter(
                new BufferedWriter(
                        new FileWriter("C:\\Users\\Maksim_Huretski\\Desktop\\Everest\\OrderNumber.txt", true)));
    }

    private void orderStuff(PrintWriter writer, WebDriver driver) throws InterruptedException {
        WebElement orderNumber = driver.findElement(By.cssSelector("span.order-number"));
        driver.findElement(By.cssSelector("i.ico-s.ico-expand.ico-20"))
                .click();
        sleep(1000);
        WebElement orderTotal = driver.findElement(By.cssSelector("div.checkout-order-summary-total-pricing span.pull-right p.heading-3"));
        writer.append(orderNumber.getText()).append(" ").append(orderTotal.getText()).println("");

    }

    public OrderLogging(String result, int orderSequence, WebDriver driver) throws InterruptedException {

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
