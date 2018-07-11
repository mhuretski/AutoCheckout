package Import;

class InputValidation {

    void lengthEquality(String definition, String[][] item, int[][] itemQty, String[][] repeatOrder) {
        if (item.length == itemQty.length && item.length == repeatOrder.length) {
            for (int i = 0; i < item.length; i++) {
                if (!(item[i].length == itemQty[i].length && item[i].length == repeatOrder[i].length))
                    lengthMismatch(definition);
            }
        } else lengthMismatch(definition);
    }

    private void lengthMismatch(String definition) {
        System.err.println(definition + " hasn't passed validation. Check input file.");
        System.exit(1);
    }

    void healthboxType(int[] healthbox, String[][] healthboxItem) {
        for (int i = 0; i < healthbox.length; i++) {
            if ((healthbox[i] == 1 || healthbox[i] == 3 || healthbox[i] == 4)
                    && healthboxItem[i][0].equals("-")) {
                System.err.println("Healthbox field should be equal to 0 if there are no Healthbox items. Check input file.");
                System.exit(1);
            }
        }
    }

    void loyalty(String[] newLoyaltyUser, String[] loyaltyCard) {
        String plus = "+";
        String minus = "-";
        for (int i = 0; i < newLoyaltyUser.length; i++) {
            if (newLoyaltyUser[i].equals(plus) && !loyaltyCard[i].equals(minus)) {
                System.err.println("New loyalty user can't have an existing card. Check input file.");
                System.exit(1);
            }
        }
    }

    void HBPlus(String[] hbPlus, String[][] pills) {
        String plus = "+";
        String minus = "-";
        for (int i = 0; i < hbPlus.length; i++) {
            if (hbPlus[i].equals(plus)) {
                if (pills[i].length < 2 || pills[i].length > 5) {
                    System.err.println("Amount of pills should be in range of 2 to 5. Check input file.");
                    System.exit(1);
                }
                for (int j = 0; j < pills[i].length; j++) {
                    if (pills[i][j].contains(minus)) {
                        System.err.println("Pills should be present in HBPlus. Check input file.");
                        System.exit(1);
                    }
                }
            }
        }
    }

}
