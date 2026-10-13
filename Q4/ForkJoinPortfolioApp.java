package Q4;

import java.util.concurrent.ForkJoinPool;

public class ForkJoinPortfolioApp {

    public static double serialSum(double[] assets) {
        double sum = 0;

        for (int i = 0; i < assets.length; i++) {
            sum += assets[i];
        }

        return sum;
    }

    public static void serialAdjust(double[] assets, double cap) {
        for (int i = 0; i < assets.length; i++) {
            if (assets[i] > cap) {
                assets[i] = assets[i] * 0.95;
            }
        }
    }

        public static void main(String[] args) {
        double[] original = new double[10000];

        for (int i = 0; i < original.length; i++) {
            original[i] = (i % 1000) + 1;
        }

        double cap = 800;
        ForkJoinPool pool = new ForkJoinPool();

      
        for (int i = 0; i < 5; i++) {
            double[] serialWarmup = original.clone();

            serialSum(serialWarmup);
            serialAdjust(serialWarmup, cap);
            serialSum(serialWarmup);

            double[] parallelWarmup = original.clone();

            pool.invoke(new ParallelRiskCalculator(
                parallelWarmup, 0, parallelWarmup.length
            ));

            pool.invoke(new PortfolioThresholdTask(
                parallelWarmup, 0, parallelWarmup.length, cap
            ));

            pool.invoke(new ParallelRiskCalculator(
                parallelWarmup, 0, parallelWarmup.length
            ));
        }

        // Separate identical copies keep the comparison fair.
        double[] serial = original.clone();
        double[] parallel = original.clone();

        // Measure the complete serial workflow.
        long startTime = System.nanoTime();

        double serialBefore = serialSum(serial);
        serialAdjust(serial, cap);
        double serialAfter = serialSum(serial);

        long serialTime = System.nanoTime() - startTime;

        // Measure the same workflow using Fork/Join.
        startTime = System.nanoTime();

        double parallelBefore = pool.invoke(
            new ParallelRiskCalculator(
                parallel, 0, parallel.length
            )
        );

        pool.invoke(
            new PortfolioThresholdTask(
                parallel, 0, parallel.length, cap
            )
        );

        double parallelAfter = pool.invoke(
            new ParallelRiskCalculator(
                parallel, 0, parallel.length
            )
        );

        long parallelTime = System.nanoTime() - startTime;

        pool.shutdown();

        // Allow a small tolerance for decimal rounding.
        boolean match =
            Math.abs(serialBefore - parallelBefore) < 0.000001
            && Math.abs(serialAfter - parallelAfter) < 0.000001;

        // Also compare every adjusted asset.
        for (int i = 0; i < serial.length; i++) {
            if (Math.abs(serial[i] - parallel[i]) > 0.000001) {
                match = false;
            }
        }

        System.out.println("Assets: " + original.length);
        System.out.println("Risk cap: " + cap);
        System.out.println("Penalty: 5% reduction");

        System.out.printf(
            "Serial risk: %.2f -> %.2f%n",
            serialBefore, serialAfter
        );

        System.out.printf(
            "Parallel risk: %.2f -> %.2f%n",
            parallelBefore, parallelAfter
        );

        System.out.println("Results match: " + match);

        System.out.printf(
            "Serial total time: %.3f ms%n",
            serialTime / 1000000.0
        );

        System.out.printf(
            "Parallel total time: %.3f ms%n",
            parallelTime / 1000000.0
        );

        double speedup = (double) serialTime / parallelTime;

        System.out.printf("Speedup: %.3fx%n", speedup);

        if (speedup < 1) {
            System.out.println(
                "Parallel processing was slower in this run."
            );
        } 
        else {
            System.out.println("Parallel processing was at least as fast in this run.");
        }
    }
}