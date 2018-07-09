package Logic;

public class CreationTime {
    private long startTime;
    private long endTime;

    public void show(int amountOfOrders) {
        int executeTime = (int) ((endTime - startTime) / 1000000000.0 / 60.0);
        System.out.println();
        if (executeTime == 1) {
            if (amountOfOrders == 1)
                System.out.println(executeTime + " minute to create " + amountOfOrders + " order");
            else
                System.out.println(executeTime + " minute to create " + amountOfOrders + " orders");

        } else if (amountOfOrders == 1)
            System.out.println(executeTime + " minutes to create " + amountOfOrders + " order");
        else
            System.out.println(executeTime + " minutes to create " + amountOfOrders + " orders");
    }

    public void setStartTime() {
        startTime = time();
    }

    public void setEndTime() {
        endTime = time();
    }

    private long time() {
        return System.nanoTime();
    }
}
