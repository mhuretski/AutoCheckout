package Export;

import Logic.DriverTiming;
import Secured.SecureAdmin;
import Secured.SecureInputOutput;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class OrderWriter {

    public OrderWriter(OrderListing orderListing) {
        WebDriver driver = new ChromeDriver();
        new DriverTiming(2, driver);

        /*open admin site*/
        SecureAdmin admin = new SecureAdmin();
        driver.get(admin.getAdminSite());

        /*get sequence, number and order XML*/
        ArrayList<ArrayList<String>> orders = orderListing.getOrders();
        getOrderXMLs(orders, admin, driver);

        /*write each orderXML in a separate file*/
        String outputFolder = new SecureInputOutput().getOutputFolder();
        writeOrderXMLs(orders, outputFolder);
        driver.quit();

    }

    private void getOrderXMLs(ArrayList<ArrayList<String>> orders, SecureAdmin admin, WebDriver driver) {
        String textAreaSelector = "textarea";
        String searchSelector = "input[type='submit']";
        String resultSelector = "pre code";
        ArrayList<String> results = new ArrayList<>();
        for (int i = 0; i < orders.get(0).size(); i++) {

            /*RQL request*/
            driver.findElement(By.cssSelector(textAreaSelector))
                    .sendKeys(admin.setRequestRQL(orders.get(1).get(i)));
            driver.findElement(By.cssSelector(searchSelector)).click();
            results.add(driver.findElement(By.cssSelector(resultSelector)).getText());
            driver.findElement(By.cssSelector(textAreaSelector)).clear();

        }
        orders.add(results);
    }

    private void writeOrderXMLs(ArrayList<ArrayList<String>> orders, String outputFolder) {
        
        String space = ". ";
        String fileExtension = ".xml";
        String scn = "UTF-8";
        for (int i = 0; i < orders.get(0).size(); i++) {

            String fileName = orders.get(0).get(i) + space + orders.get(1).get(0);
            try {
                PrintWriter writer = new PrintWriter(outputFolder + fileName + fileExtension, scn);
                writer.print(orders.get(2).get(i));
                writer.close();
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

}
