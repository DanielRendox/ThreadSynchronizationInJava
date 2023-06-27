import java.util.concurrent.TimeUnit;

/**
 * Client is meant to be a producer or consumer. Each client has access to the
 * DonutStorage does the respective action with donuts.
 */
public class Client {
    public static final ClientOperation consume = (donutStorage, numberOfItems) -> {
        int numberOfConsumedItems = 0;
        for (int i = 0; i < numberOfItems; i++) {
            try {
                if (donutStorage.blockingQueue.poll(1, TimeUnit.SECONDS) != null) {
                    numberOfConsumedItems++;
                }
            } catch (InterruptedException ignored) {
            }
        }
        return numberOfConsumedItems;
    };

    public static final ClientOperation produce = (donutStorage, numberOfItems) -> {
        int numberOfProducedItems = 0;
        for (int i = 0; i < numberOfItems; i++) {
            try {
                if (donutStorage.blockingQueue.offer(new Object(), 1, TimeUnit.SECONDS)) {
                    numberOfProducedItems++;
                }
            } catch (InterruptedException ignored) {
            }
        }
        return numberOfProducedItems;
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
        return clientOperation.operate(donutStorage, numberOfItems);
    }

    @FunctionalInterface
    private interface ClientOperation {
        int operate(DonutStorage donutStorage, int numberOfItems);
    }
}
