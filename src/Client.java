/**
 * Client is meant to be a producer or consumer. Each client has access to the
 * DonutStorage does the respective action with donuts.
 */
public class Client {

    public static final ClientOperation consume = (donutStorage, numberOfItems) -> {
        if (numberOfItems > donutStorage.getDonutsNumber()) {

            // if there aren't enough donuts in stock, consume as many as there are
            int numberOfTakenItems = donutStorage.getDonutsNumber();
            int numberOfItemsToTake = numberOfItems - numberOfTakenItems;
            donutStorage.setDonutsNumber(0);


            // but wait in case producers put some more items
            while (numberOfItemsToTake > donutStorage.getDonutsNumber()) {
                try {
                    donutStorage.wait();
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() +
                            " was interrupted and didn't consume the desired amount of items.");
                    return numberOfTakenItems;
                }
            }
            donutStorage.setDonutsNumber(donutStorage.getDonutsNumber() - numberOfItemsToTake);
        } else donutStorage.setDonutsNumber(donutStorage.getDonutsNumber() - numberOfItems);

        // notify the producers that it's not full anymore as we've just took some items
        donutStorage.notifyAll();
        return numberOfItems;
    };

    public static final ClientOperation produce = (donutStorage, numberOfItems) -> {
        final int donutsCapacity = donutStorage.getDonutsCapacity();

        // Represents a number of donuts the client can put before the storage
        // capacity is reached. We will take exactly this amount of donuts if
        // there is not enough space.
        int availableSpace = donutsCapacity - donutStorage.getDonutsNumber();

        // Number of items the producer hasn't put yet
        int numberOfItemsToPut = numberOfItems - availableSpace;

        if (numberOfItems > (availableSpace)) {
            donutStorage.setDonutsNumber(donutsCapacity);

            while (numberOfItems > (donutsCapacity - donutStorage.getDonutsNumber())) {
                try {
                    donutStorage.wait();
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() +
                            " was interrupted and didn't produce the desired amount of items.");
                    return availableSpace;
                }
            }
            donutStorage.setDonutsNumber(donutStorage.getDonutsNumber() + numberOfItemsToPut);
        } else donutStorage.setDonutsNumber(donutStorage.getDonutsNumber() + numberOfItems);
        // notify the consumers that it's not empty anymore as we've just took some items
        donutStorage.notifyAll();
        return numberOfItems;
    };

    private final ClientOperation clientOperation;
    private final DonutStorage donutStorage;

    public Client(ClientOperation clientOperation, DonutStorage donutStorage) {
        this.clientOperation = clientOperation;
        this.donutStorage = donutStorage;
    }

    /**
     *
     * @param numberOfItems number that represents how to change the donutsNumber
     * @return  number that represents how the donutsNumber was changed
     */
    public int operate(int numberOfItems) {
        synchronized (donutStorage) {
            return clientOperation.operate(donutStorage, numberOfItems);
        }
    }

    @FunctionalInterface
    private interface ClientOperation {
        int operate(DonutStorage donutStorage, int numberOfItems);
    }
}
