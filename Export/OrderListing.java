package Export;

import Secured.SecureInputOutput;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderListing {

    public OrderListing(){
        /*path to generated folder which name contains date it's created*/
        outputFolder = new SecureInputOutput().getOutputFolder()
                + new SimpleDateFormat("dd.MM.yy HH.mm").format(new java.util.Date()) + "/";
        try {
            Files.createDirectories(Paths.get(outputFolder));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String outputFolder;
    private ArrayList<String> orderNumbers = new ArrayList<>();
    private ArrayList<String> orderSequence = new ArrayList<>();
    private List<List<String>> orders = new ArrayList<>();

    void addOrderNumber(String orderNumber) {
        orderNumbers.add(orderNumber);
    }

    void addOrderSequence(String orderSequence) {
        this.orderSequence.add(orderSequence);
    }

    List<List<String>> getOrders() {
        orders.add(orderSequence);
        orders.add(orderNumbers);
        return orders;
    }

    String getOutputFolder() {
        return outputFolder;
    }

}
