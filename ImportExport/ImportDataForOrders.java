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
    private String[][] normalItems;
    private int[][] normalItemsQty;
    private String[] hbPlus;
    private String[] site;
    private String[] newLoyaltyUser;
    private String[] loyaltyCard;
    private String[] username;
    private String[] password;

    public ImportDataForOrders() {

        LinkedList<String> orderSequenceList = new LinkedList<String>();
        LinkedList<String> healthboxList = new LinkedList<String>();
        LinkedList<String> healthboxItemList = new LinkedList<String>();
        LinkedList<String> healthboxItemQtyList = new LinkedList<String>();
        LinkedList<String> normalItemList = new LinkedList<String>();
        LinkedList<String> normalItemQtyStringList = new LinkedList<String>();
        LinkedList<String> hbPlusList = new LinkedList<String>();
        LinkedList<String> siteList = new LinkedList<String>();
        LinkedList<String> newLoyaltyUserList = new LinkedList<String>();
        LinkedList<String> loyaltyCardList = new LinkedList<String>();
        LinkedList<String> usernameList = new LinkedList<String>();
        LinkedList<String> passwordList = new LinkedList<String>();

        String csvFile = "C:\\Users\\Maksim_Huretski\\Desktop\\Everest\\orders_source.csv";
        try {
            CSVReader reader;
            reader = new CSVReader(new FileReader(csvFile));
            String[] line;
            for (int i = 0; (line = reader.readNext()) != null; i++) {
                if (i != 0) {
                    orderSequenceList.add(line[0]);
                    healthboxList.add(line[1]);
                    healthboxItemList.add(line[2]);
                    healthboxItemQtyList.add(line[3]);
                    normalItemList.add(line[4]);
                    normalItemQtyStringList.add(line[5]);
                    hbPlusList.add(line[6]);
                    siteList.add(line[7]);
                    newLoyaltyUserList.add(line[8]);
                    loyaltyCardList.add(line[9]);
                    usernameList.add(line[10]);
                    passwordList.add(line[11]);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        ImportTransformations transform = new ImportTransformations();
        this.orderSequence = transform.transformToArrayOneInt(orderSequenceList);
        this.healthbox = transform.transformToArrayOneInt(healthboxList);
        this.healthboxItem = transform.transformToArrayTwoWithZeros(healthboxItemList);
        this.healthboxItemQty = transform.transformToArrayTwoInt(healthboxItemQtyList);
        this.normalItems = transform.transformToArrayTwoWithZeros(normalItemList);
        this.normalItemsQty = transform.transformToArrayTwoInt(normalItemQtyStringList);
        this.hbPlus = transform.transformToArrayOneString(hbPlusList);
        this.site = transform.transformToArrayOneString(siteList);
        this.newLoyaltyUser = transform.transformToArrayOneString(newLoyaltyUserList);
        this.loyaltyCard = transform.transformToArrayOneString(loyaltyCardList);
        this.username = transform.transformToArrayOneString(usernameList);
        this.password = transform.transformToArrayOneString(passwordList);

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
}