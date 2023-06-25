import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) {
        int consumersNumber = 10;
        int producersNumber = 5;
        DonutStorage donutStorage = new DonutStorage(20);
        ExecutorService executor = Executors.newFixedThreadPool(15);
        List<Future<?>> futures = new ArrayList<>(consumersNumber);
        for (int i = 0; i < consumersNumber; i++) {
            futures.add(executor.submit(() -> {
                Client consumer = new Client(Client.consume, donutStorage);
                System.out.println(Thread.currentThread().getName() + " consumed " +
                        consumer.operate(7));
            }));
        }
        for (int i = 0; i < producersNumber; i++) {
            futures.add(executor.submit(() -> {
                Client producer = new Client(Client.produce, donutStorage);
                System.out.println(Thread.currentThread().getName() + " produced " +
                        producer.operate(10));
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
