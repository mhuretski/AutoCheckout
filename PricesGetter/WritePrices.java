package PricesGetter;

import Secured.SecureInputOutput;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

class WritePrices {

    private List<String> priceForEachItem;

    WritePrices(){
        priceForEachItem = new ArrayList<>();
    }

    void execute() {
        try {
            PrintWriter writer = new PrintWriter(
                    new BufferedWriter(
                            new FileWriter(new SecureInputOutput().getOutputPrices(), true)));
            for (String record : priceForEachItem) {
                writer.append(record).println();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void addData(GetPriceLogic priceLogic) {
        priceForEachItem.add(priceLogic.getPrice());
    }
}
