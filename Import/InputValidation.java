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
        System.out.println(definition + " hasn't passed validation. Check input file.");
        System.exit(1);
    }

    void healthboxType(int[] healthbox, String[][] healthboxItem) {
        for (int i = 0; i < healthbox.length; i++) {
            if ((healthbox[i] == 1 || healthbox[i] == 3 || healthbox[i] == 4)
                    && healthboxItem[i][0].equals("-")) {
                System.out.println("Healthbox field should be equal to 0 if there are no Healthbox items. Check input file.");
                System.exit(1);
            }
        }
    }

    void loyalty(String[] newLoyaltyUser, String[] loyaltyCard) {
        String plus = "+";
        String minus = "-";
        for (int i = 0; i < newLoyaltyUser.length; i++) {
            if (newLoyaltyUser[i].equals(plus) && !loyaltyCard[i].equals(minus)){
                System.out.println("New loyalty user can't have an existing card. Check input file.");
                System.exit(1);
            }
        }
    }

}
