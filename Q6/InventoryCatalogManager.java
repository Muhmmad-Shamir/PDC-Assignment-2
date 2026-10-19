package Q6;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class InventoryCatalogManager {

    private static final int PRODUCTS = 1000;
    private static final int WORKERS = 10;
    private static final int OPERATIONS_PER_WORKER = 5000;
    private static final int INITIAL_STOCK = 100;

    private Map<String, Integer> catalog;
    private String[] productIds;

    public InventoryCatalogManager() {
        productIds = new String[PRODUCTS];

        for (int i = 0; i < PRODUCTS; i++) {
            productIds[i] = "Product-" + i;
        }
    }

    private void initializeCatalog() {
        for (int i = 0; i < PRODUCTS; i++) {
            catalog.put(productIds[i], INITIAL_STOCK);
        }
    }

    public double benchmarkSynchronizedMap()
        throws InterruptedException {

    catalog = Collections.synchronizedMap(
            new HashMap<String, Integer>());

    initializeCatalog();

    return runBenchmark("Synchronized Map");
    }


    public double benchmarkConcurrentMap()
        throws InterruptedException {

    catalog = new ConcurrentHashMap<String, Integer>();

    initializeCatalog();

    return runBenchmark("ConcurrentHashMap");
}

private double runBenchmark(String label)
        throws InterruptedException {

    ExecutorService pool = Executors.newFixedThreadPool(WORKERS);

    long[] readTotals = new long[WORKERS];

    long startTime = System.nanoTime();

    for (int worker = 0; worker < WORKERS; worker++) {

        final int workerId = worker;

        pool.execute(new Runnable() {

            @Override
            public void run() {

                long totalRead = 0;

                for (int operation = 0;
                     operation < OPERATIONS_PER_WORKER;
                     operation++) {

                    int productIndex =
                            (operation + workerId) % PRODUCTS;

                    String product = productIds[productIndex];

                    if (operation % 2 == 0) {
                        totalRead += catalog.get(product);
                    } else {
                        catalog.merge(product, 1, Integer::sum);
                    }
                }

                readTotals[workerId] = totalRead;
            }
        });
    }

    pool.shutdown();

    boolean finished = pool.awaitTermination(
            1, TimeUnit.MINUTES);

    if (!finished) {
        pool.shutdownNow();
        throw new IllegalStateException(
                "Workers did not finish within one minute.");
    }

    long elapsed = System.nanoTime() - startTime;

    long actualStock = 0;

for (int stock : catalog.values()) {
    actualStock += stock;
}

int totalOperations = WORKERS * OPERATIONS_PER_WORKER;
int totalUpdates = WORKERS * (OPERATIONS_PER_WORKER / 2);
int totalReads = totalOperations - totalUpdates;

long expectedStock =
        (long) PRODUCTS * INITIAL_STOCK + totalUpdates;

long combinedReadTotal = 0;

for (long value : readTotals) {
    combinedReadTotal += value;
}

boolean correct = actualStock == expectedStock;

double seconds = elapsed / 1_000_000_000.0;
double throughput = totalOperations / seconds;


double milliseconds = elapsed / 1_000_000.0;

    System.out.println("\n--- " + label + " ---");
    System.out.printf("Time: %.3f ms%n", milliseconds);
    System.out.println("Workers: " + WORKERS);
    System.out.println("Total operations: " + totalOperations);
    System.out.println("Read operations: " + totalReads);
    System.out.println("Update operations: " + totalUpdates);
    
    System.out.println("Expected total stock: " + expectedStock);
    System.out.println("Actual total stock: " + actualStock);
    System.out.println("Stock check passed: " + correct);
    
    System.out.println("Combined read total: " + combinedReadTotal);
    
    System.out.printf(
        "Throughput: %.2f operations/second%n", throughput);
    return milliseconds;
}

public static void main(String[] args)
        throws InterruptedException {

    InventoryCatalogManager manager =
            new InventoryCatalogManager();

    System.out.println("Inventory Catalog Benchmark");
    System.out.println("Each worker performs 5000 operations.");

    double synchronizedTime =
            manager.benchmarkSynchronizedMap();

    double concurrentTime =
            manager.benchmarkConcurrentMap();

    double speedup = synchronizedTime / concurrentTime;

    System.out.println("\n--- Comparison ---");

    System.out.printf(
            "Synchronized Map time: %.3f ms%n",
            synchronizedTime);

    System.out.printf(
            "ConcurrentHashMap time: %.3f ms%n",
            concurrentTime);

    System.out.printf(
            "Speedup ratio: %.2f%n", speedup);

    if (concurrentTime < synchronizedTime) {
        System.out.println(
                "ConcurrentHashMap was faster in this run.");
    } else if (concurrentTime > synchronizedTime) {
        System.out.println(
                "Synchronized Map was faster in this run.");
    } else {
        System.out.println(
                "Both maps had the same measured time.");
    }
}


}