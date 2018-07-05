package ImportExport;


import Logic.ImportTransformations;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class ImportDataForOrders {

    private int[] orderSequence;
    private int[] healthbox;
    private String[][] healthboxItem;
    private int[][] healthboxItemQty;
    private String[][] hbRepeatOrder;
    private String[][] normalItems;
    private int[][] normalItemsQty;
    private String[][] normalRepeatOrder;
    private String[] hbPlus;
    private String[] site;
    private String[] newLoyaltyUser;
    private String[] loyaltyCard;
    private String[] username;
    private String[] password;

    public ImportDataForOrders() {

        LinkedList<String> orderSequenceList = new LinkedList<>();
        LinkedList<String> healthboxList = new LinkedList<>();
        LinkedList<String> healthboxItemList = new LinkedList<>();
        LinkedList<String> healthboxItemQtyList = new LinkedList<>();
        LinkedList<String> hbRepeatOrderList = new LinkedList<>();
        LinkedList<String> normalItemList = new LinkedList<>();
        LinkedList<String> normalItemQtyStringList = new LinkedList<>();
        LinkedList<String> normalRepeatOrderList = new LinkedList<>();
        LinkedList<String> hbPlusList = new LinkedList<>();
        LinkedList<String> siteList = new LinkedList<>();
        LinkedList<String> newLoyaltyUserList = new LinkedList<>();
        LinkedList<String> loyaltyCardList = new LinkedList<>();
        LinkedList<String> usernameList = new LinkedList<>();
        LinkedList<String> passwordList = new LinkedList<>();

        String csvFile = "C:\\Users\\Maksim_Huretski\\Desktop\\Everest\\orders_source.csv";
        try {
            CSVReader reader = new CSVReader(new FileReader(csvFile));
            String[] line;
            for (int i = 0; (line = reader.readNext()) != null; i++) {
                if (i != 0) {
                    orderSequenceList.add(line[0]);
                    healthboxList.add(line[1]);
                    healthboxItemList.add(line[2]);
                    healthboxItemQtyList.add(line[3]);
                    hbRepeatOrderList.add(line[4]);
                    normalItemList.add(line[5]);
                    normalItemQtyStringList.add(line[6]);
                    normalRepeatOrderList.add(line[7]);
                    hbPlusList.add(line[8]);
                    siteList.add(line[9]);
                    newLoyaltyUserList.add(line[10]);
                    loyaltyCardList.add(line[11]);
                    usernameList.add(line[12]);
                    passwordList.add(line[13]);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        ImportTransformations transform = new ImportTransformations();
        orderSequence = transform.transformToArrayOneInt(orderSequenceList);
        healthbox = transform.transformToArrayOneInt(healthboxList);
        healthboxItem = transform.transformToArrayTwoWithZeros(healthboxItemList);
        healthboxItemQty = transform.transformToArrayTwoInt(healthboxItemQtyList);
        hbRepeatOrder = transform.transformToArrayTwo(hbRepeatOrderList);
        normalItems = transform.transformToArrayTwoWithZeros(normalItemList);
        normalItemsQty = transform.transformToArrayTwoInt(normalItemQtyStringList);
        normalRepeatOrder = transform.transformToArrayTwo(normalRepeatOrderList);
        hbPlus = transform.transformToArrayOneString(hbPlusList);
        site = transform.transformToArrayOneString(siteList);
        newLoyaltyUser = transform.transformToArrayOneString(newLoyaltyUserList);
        loyaltyCard = transform.transformToArrayOneString(loyaltyCardList);
        username = transform.transformToArrayOneString(usernameList);
        password = transform.transformToArrayOneString(passwordList);

    }

    public int[] getOrderSequence() {
        return orderSequence;
    }

    public int[] getHealthbox() {
        return healthbox;
    }

    public String[][] getHealthboxItem() {
        return healthboxItem;
    }

    public int[][] getHealthboxItemQty() {
        return healthboxItemQty;
    }

    public String[][] getNormalItems() {
        return normalItems;
    }

    public int[][] getNormalItemsQty() {
        return normalItemsQty;
    }

    public String[] getHbPlus() {
        return hbPlus;
    }

    public String[] getSite() {
        return site;
    }

    public String[] getNewLoyaltyUser() {
        return newLoyaltyUser;
    }

    public String[] getLoyaltyCard() {
        return loyaltyCard;
    }

    public String[] getUsername() {
        return username;
    }

    public String[] getPassword() {
        return password;
    }

    public String[][] getHbRepeatOrder() {
        return hbRepeatOrder;
    }

    public String[][] getNormalRepeatOrder() {
        return normalRepeatOrder;
    }

}
