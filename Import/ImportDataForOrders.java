package Import;


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
    private String[] hbpRepeat;
    private String[] site;
    private String[] newLoyaltyUser;
    private String[] loyaltyCard;
    private String[] coupon;
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
        LinkedList<String> hbpRepeatList = new LinkedList<>();
        LinkedList<String> siteList = new LinkedList<>();
        LinkedList<String> newLoyaltyUserList = new LinkedList<>();
        LinkedList<String> loyaltyCardList = new LinkedList<>();
        LinkedList<String> couponList = new LinkedList<>();
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
                    hbpRepeatList.add(line[9]);
                    siteList.add(line[10]);
                    newLoyaltyUserList.add(line[11]);
                    loyaltyCardList.add(line[12]);
                    couponList.add(line[13]);
                    usernameList.add(line[14]);
                    passwordList.add(line[15]);
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
        hbpRepeat = transform.transformToArrayOneString(hbpRepeatList);
        site = transform.transformToArrayOneString(siteList);
        newLoyaltyUser = transform.transformToArrayOneString(newLoyaltyUserList);
        loyaltyCard = transform.transformToArrayOneString(loyaltyCardList);
        coupon = transform.transformToArrayOneString(couponList);
        username = transform.transformToArrayOneString(usernameList);
        password = transform.transformToArrayOneString(passwordList);

        InputValidation validate = new InputValidation();
        validate.lengthEquality("Healthbox", healthboxItem, healthboxItemQty, hbRepeatOrder);
        validate.lengthEquality("Normal", normalItems, normalItemsQty, normalRepeatOrder);
        validate.healthboxType(healthbox, healthboxItem);
        validate.loyalty(newLoyaltyUser, loyaltyCard);

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

    public String[] getHbpRepeat() {
        return hbpRepeat;
    }

    public String[] getCoupon() {
        return coupon;
    }
}
