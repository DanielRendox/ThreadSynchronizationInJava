public class DonutStorage {
    private int donutsNumber;
    private final int donutsCapacity;
    public DonutStorage(int donutsNumber) {
        this.donutsNumber = donutsNumber;
        this.donutsCapacity = 50;
    }
    public int getDonutsNumber() {
        return donutsNumber;
    }
    public void setDonutsNumber(int donutsNumber) {
        this.donutsNumber = donutsNumber;
    }
    public int getDonutsCapacity() {
        return donutsCapacity;
    }
}
