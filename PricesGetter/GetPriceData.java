package PricesGetter;

import Secured.SecureInputOutput;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class GetPriceData {

    private List<String> itemSequence = new ArrayList<>();
    private List<String> items = new ArrayList<>();
    private List<String> sites = new ArrayList<>();

    GetPriceData() {
        try (CSVReader reader = new CSVReader(
                new FileReader(
                        new SecureInputOutput().getInputPrices()))) { /*path to input file*/
            String[] line;
            for (int i = 0; (line = reader.readNext()) != null; i++) {
                if (i != 0) {
                    itemSequence.add(line[0]);
                    items.add(skuIdLength(line[1]));
                    sites.add(line[2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    List<String> getItemSequence() {
        return itemSequence;
    }

    List<String> getItems() {
        return items;
    }

    List<String> getSites() {
        return sites;
    }

    private String skuIdLength(String skuId) {
        int length = skuId.length();

        if (skuId.equals("-")) {
            return skuId;
        } else if (length < 2) {
            return "00000" + skuId;
        } else if (length < 3) {
            return "0000" + skuId;
        } else if (length < 4) {
            return "000" + skuId;
        } else if (length < 5) {
            return "00" + skuId;
        } else if (length < 6) {
            return "0" + skuId;
        } else
            return skuId;
    }
}
