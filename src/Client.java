/**
 * Client is meant to be a producer or consumer. Each client has access to the
 * DonutStorage does the respective action with donuts.
 */
public class Client {

    public static final ClientOperation consume = (donutStorage, numberOfItems) -> {
        int donutsNumber = donutStorage.getDonutsNumber();
        // if there aren't enough donuts in stock, consume as many as there are
        if (numberOfItems > donutsNumber) {
            donutStorage.setDonutsNumber(0);
            return donutsNumber;
        }
        donutStorage.setDonutsNumber(donutsNumber - numberOfItems);
        return numberOfItems;
    };

    public static final ClientOperation produce = (donutStorage, numberOfItems) -> {
        int donutsNumber = donutStorage.getDonutsNumber();
        int donutsCapacity = donutStorage.getDonutsCapacity();

//         Represents a number of donuts the client can put before the storage
//         capacity is reached.
        int availableSpace = donutsCapacity - donutsNumber;
        if (numberOfItems > availableSpace) {
            donutStorage.setDonutsNumber(donutsCapacity);
            return availableSpace;
        }
        donutStorage.setDonutsNumber(donutsNumber + numberOfItems);
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
