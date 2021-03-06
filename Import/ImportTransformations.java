package Import;

import java.util.List;

class ImportTransformations {

    int[][] transformToArrayTwoInt(List<String> arrayList) {

        String[][] arrayString = transformToArrayTwo(arrayList);
        int[][] arrayInt = new int[arrayString.length][];
        for (int i = 0; i < arrayString.length; i++) {
            arrayInt[i] = new int[arrayString[i].length];
            for (int j = 0; j < arrayInt[i].length; j++) {
                arrayInt[i][j] = Integer.parseInt(arrayString[i][j]);
            }
        }
        return arrayInt;
    }

    int[] transformToArrayOneInt(List<String> arrayList) {
        String[] arrayString = transformToArrayOneString(arrayList);
        int[] arrayInt = new int[arrayString.length];
        for (int i = 0; i < arrayString.length; i++) {
            arrayInt[i] = Integer.parseInt(arrayString[i]);
        }
        return arrayInt;
    }

    String[][] transformToArrayTwo(List<String> arrayList) {
        String[][] array = new String[arrayList.size()][1];
        fromListToArray(array, arrayList);
        return splitArrayCells(array);
    }

    private void fromListToArray(String[][] array, List<String> arrayList) {
        for (int i = 0; i < array.length; i++) {
            array[i][0] = arrayList.get(i);
        }
    }

    private String[][] splitArrayCells(String[][] array) {

        String[][] tempArray = new String[array.length][];
        for (int i = 0; i < array.length; i++) {
            String[] split = array[i][0].split(", ");
            tempArray[i] = new String[split.length];

            System.arraycopy(split, 0, tempArray[i], 0, split.length);
        }
        return tempArray;
    }

    String[][] transformToArrayTwoWithZeros(List<String> arrayList){
        String[][] arrayString = transformToArrayTwo(arrayList);
        ZerosBeforeItemId addZeros = new ZerosBeforeItemId();
        for (int i = 0; i < arrayString.length; i++) {
            for (int j = 0; j < arrayString[i].length; j++) {
                arrayString[i][j] = addZeros.skuIdLength(arrayString[i][j]);
            }
        }
        return arrayString;
    }

    String[] transformToArrayOneString(List<String> arrayList) {
        String[] array = new String[arrayList.size()];
        fromListToArray(array, arrayList);
        return array;
    }

    private void fromListToArray(String[] array, List<String> arrayList) {
        for (int i = 0; i < array.length; i++) {
            array[i] = arrayList.get(i);
        }
    }

}
