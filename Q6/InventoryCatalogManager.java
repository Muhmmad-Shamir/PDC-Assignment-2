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

}