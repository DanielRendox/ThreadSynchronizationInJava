public class Consumer {
    private final DonutStorage donutStorage;

    public Consumer(DonutStorage donutStorage) {
        this.donutStorage = donutStorage;
    }

    /**
     * Subtracts the given number from the DonutStorage's donutsNumber. If the given number is bigger
     * than the number of donuts in stock, sets the donutsNumber to 0.
     * @param numberOfItemsToConsume Number that will be subtracted from the donutsNumber
     * @return the number of consumed items
     */
    public int consume(int numberOfItemsToConsume) {
        synchronized (donutStorage) {
            int donutsNumber = donutStorage.getDonutsNumber();
            // if there aren't enough donuts in stock, consume as many as there are
            if (numberOfItemsToConsume > donutsNumber) {
                donutStorage.setDonutsNumber(0);
                return donutsNumber;
            }
            donutStorage.setDonutsNumber(donutsNumber - numberOfItemsToConsume);
            return numberOfItemsToConsume;
        }
    }
}
