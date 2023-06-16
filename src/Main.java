import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) {
        int consumersNumber = 10;
        DonutStorage donutStorage = new DonutStorage(20);
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<?>> futures = new ArrayList<>(consumersNumber);
        for (int i = 0; i < consumersNumber; i++) {
            futures.add(executor.submit(() -> {
                Consumer consumer = new Consumer(donutStorage);
                System.out.println(Thread.currentThread().getName() + " consumed " +
                        consumer.consume(3));
            }));
        }
        executor.shutdown();

        // make the main thread wait for others to finish
        for (Future<?> future: futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("Exception while getting from future" + e.getMessage());
                e.printStackTrace();
            }
        }

        System.out.println("Number of remaining donuts: " + donutStorage.getDonutsNumber());
    }
}
