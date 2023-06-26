import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class DonutStorage {
    public BlockingQueue<Object> blockingQueue;
    public DonutStorage(int donutsNumber) {
        blockingQueue = new ArrayBlockingQueue<>(50);
        for (int i = 0; i < donutsNumber; i++) {
            blockingQueue.add(new Object());
        }
    }
}
