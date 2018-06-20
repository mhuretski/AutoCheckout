package Logic;

class ZerosBeforeItemId {

    String skuIdLength(String skuId) {
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
