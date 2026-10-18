package Q5;

import java.util.Random;

public class SensorGridMonitorApp {
    public static void main(String[] args)
            throws InterruptedException {

        double[] data = new double[50000];

        Random random = new Random(42);

        for (int i = 0; i < data.length; i++) {
            data[i] = random.nextDouble() * 100;
        }

        data[123] = -25;
        data[49999] = 150;

        System.out.println("Sensor values: " + data.length);
                 
                long startTime = System.nanoTime();

                Thread[] workers = new Thread[4];

        double[] localMin = new double[4];
        double[] localMax = new double[4];

        for (int i = 0; i < workers.length; i++) {
            final int index = i;

            final int start =
                i * data.length / workers.length;

            final int end =
                (i + 1) * data.length / workers.length;

            workers[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    SensorSearchEngine minSearch =
                        new ParallelMinSearch(data, start, end);

                    SensorSearchEngine maxSearch =
                        new ParallelMaxSearch(data, start, end);

                    localMin[index] = minSearch.processGrid();
                    localMax[index] = maxSearch.processGrid();
                }
            }, "Sensor-Worker-" + (i + 1));
        }

                for (int i = 0; i < workers.length; i++) {
            workers[i].start();
        }

        for (int i = 0; i < workers.length; i++) {
            workers[i].join();
        }

        double globalMin = localMin[0];
        double globalMax = localMax[0];

        for (int i = 1; i < workers.length; i++) {
            if (localMin[i] < globalMin) {
                globalMin = localMin[i];
            }

            if (localMax[i] > globalMax) {
                globalMax = localMax[i];
            }
        }

        long elapsed = System.nanoTime() - startTime;

        double checkMin = data[0];
        double checkMax = data[0];

        for (int i = 1; i < data.length; i++) {
            if (data[i] < checkMin) {
                checkMin = data[i];
            }

            if (data[i] > checkMax) {
                checkMax = data[i];
            }
        }

        boolean correct = globalMin == checkMin && globalMax == checkMax;

        for (int i = 0; i < workers.length; i++) {
            System.out.println(
                workers[i].getName()
                + " | local minimum: " + localMin[i]
                + " | local maximum: " + localMax[i]
            );
        }

        System.out.println("Global minimum: " + globalMin);
        System.out.println("Global maximum: " + globalMax);

        System.out.println("Matches serial check: " + correct);

        System.out.printf( "Parallel time including thread startup: %.3f ms%n",
            elapsed / 1000000.0
        );
    }
}