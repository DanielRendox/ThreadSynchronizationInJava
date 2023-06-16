public class Consumer {
    private final DonutStorage donutStorage;

    public Consumer(DonutStorage donutStorage) {
        this.donutStorage = donutStorage;
    }

    /**
     * Subtracts the given number from the DonutStorage's donutsNumber.
     * @param numberOfItemsToConsume Number that will be subtracted from the donutsNumber
     */
    public void consume(int numberOfItemsToConsume) {
        donutStorage.getDonutsNumber().addAndGet(-numberOfItemsToConsume);
    }
}
