import java.util.concurrent.atomic.AtomicInteger;

public class DonutStorage {
    private final AtomicInteger donutsNumber;

    public DonutStorage(int donutsNumber) {
        this.donutsNumber = new AtomicInteger(donutsNumber);
    }

    public AtomicInteger getDonutsNumber() {
        return donutsNumber;
    }
}
