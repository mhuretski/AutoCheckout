package ImportExport;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class OrderLogging {

    private PrintWriter writer() throws IOException {
        return new PrintWriter(
                new BufferedWriter(
                        new FileWriter("C:\\Users\\Maksim_Huretski\\Desktop\\Everest\\OrderNumber.txt", true)));
    }

    public OrderLogging(String success, int orderSequence, WebDriver driver) {

        try {
            PrintWriter writer = writer();
            PrintWriter log = writer.append(String.valueOf(orderSequence)).append(" ");

            if (success.equals("success")) {
                WebElement orderNumber = driver.findElement(By.xpath("//span[contains(@class,'bold order-number')]"));
                log.append(orderNumber.getText()).println("");
            } else if (success.equals("basket")) {
                log.append("Basket might not be empty.").println("");
            } else
                log.append("Order isn't placed.").println("");
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
