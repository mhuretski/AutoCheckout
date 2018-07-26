package Export;

import Logic.DriverTiming;
import Secured.SecureAdmin;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class OrderWriter {

    public OrderWriter(OrderListing orderListing) {
        WebDriver driver = new ChromeDriver();
        new DriverTiming(2, driver);

        try {
            /*open admin site*/
            SecureAdmin admin = new SecureAdmin();
            driver.get(admin.getAdminSite());

            /*get sequence, number and order XML*/
            List<List<String>> orders = orderListing.getOrders();
            getOrderXMLs(orders, admin, driver);

            writeOrderXMLs(orders, orderListing.getOutputFolder());
        } catch (org.openqa.selenium.NoSuchElementException ne) {
            System.out.println("Couldn't open admin site.");
        }

        driver.quit();
    }

    private List<String> listOfFiles = new ArrayList<>();

    List<String> getListOfFiles() {
        return listOfFiles;
    }

    private void getOrderXMLs(List<List<String>> orders, SecureAdmin admin, WebDriver driver) {
        String textAreaSelector = "textarea";
        String searchSelector = "input[type='submit']";
        String resultSelector = "pre code";
        List<String> results = new ArrayList<>();
        for (int i = 0; i < orders.get(0).size(); i++) {
            /*RQL request*/
            searchForResult(i, orders, results, admin, textAreaSelector, searchSelector, resultSelector, driver);
        }
        orders.add(results);
    }

    private void searchForResult(int i, List<List<String>> orders,
                                 List<String> results,
                                 SecureAdmin admin,
                                 String textAreaSelector,
                                 String searchSelector,
                                 String resultSelector,
                                 WebDriver driver) {
        /*sending RQL request to get specific data containing orderXML*/
        driver.findElement(By.cssSelector(textAreaSelector))
                .sendKeys(admin.setRequestRQL(orders.get(1).get(i)));
        driver.findElement(By.cssSelector(searchSelector)).click();
        driver.findElement(By.cssSelector(textAreaSelector)).clear();
        String result = driver.findElement(By.cssSelector(resultSelector)).getText();
        if (result.contains(orders.get(1).get(i))) {
            results.add(driver.findElement(By.cssSelector(resultSelector)).getText());
        } else
            searchForResult(i, orders, results, admin, textAreaSelector, searchSelector, resultSelector, driver);
    }

    private void writeOrderXMLs(List<List<String>> orders, String outputFolder) {

        String space = ". ";
        String slash = "/";
        String fileExtension = ".xml";
        String scn = "UTF-8";
        for (int i = 0; i < orders.get(0).size(); i++) {

            String fileName = orders.get(0).get(i) + space + orders.get(1).get(i) + fileExtension;
            listOfFiles.add(fileName);
            try (PrintWriter writer = new PrintWriter(outputFolder + slash + fileName, scn)) {
                writer.print(getOrderXML(orders, i));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getOrderXML(List<List<String>> orders, int i) {
        /*split data with unnecessary info*/
        String[] dataList = orders.get(2).get(i).split("\\[");
        String order = "<order";
        char closedSquareBracket = ']';
        for (String dataRecord : dataList) {
            if (dataRecord.startsWith(order)) {
                /*get only orderXML*/
                return dataRecord.substring(0, dataRecord.indexOf(closedSquareBracket));
            }
        }
        return orders.get(1).get(0) + " orderXML isn't found";
    }

}
