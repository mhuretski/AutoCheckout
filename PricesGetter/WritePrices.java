package PricesGetter;

import Secured.SecureInputOutput;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

class WritePrices {

    private List<String> priceForEachItem;

    WritePrices() {
        priceForEachItem = new ArrayList<>();
    }

    void execute() {
        try {
            PrintWriter writer = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    new FileOutputStream(new SecureInputOutput().getOutputPricesCsv(), true),
                                    StandardCharsets.UTF_8)));
            for (String record : priceForEachItem) {
                writer.append(record).println();
            }
            writer.println();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void addData(GetPriceLogic priceLogic) {
        priceForEachItem.add(priceLogic.getPrice());
    }

}
