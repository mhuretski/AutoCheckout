package Export;

import java.util.ArrayList;

public class OrderListing {

    private ArrayList<String> orderNumbers = new ArrayList<>();
    private ArrayList<String> orderSequence = new ArrayList<>();
    private ArrayList<ArrayList<String>> orders = new ArrayList<>();

    void addOrderNumber(String orderNumber) {
        orderNumbers.add(orderNumber);
    }

    void addOrderSequence(String orderSequence) {
        this.orderSequence.add(orderSequence);
    }

    ArrayList<ArrayList<String>> getOrders() {
        orders.add(orderSequence);
        orders.add(orderNumbers);
        return orders;
    }

}
