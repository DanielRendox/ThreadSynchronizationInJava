import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        int consumersNumber = 10;
        DonutStorage donutStorage = new DonutStorage(20);
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (int i = 0; i < consumersNumber; i++) {
            executor.submit(() -> new Consumer(donutStorage).consume(1));
        }
        executor.shutdown();

        System.out.println("Number of remaining donuts: " + donutStorage.getDonutsNumber());
    }
}
