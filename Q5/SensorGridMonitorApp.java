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

        // Add thread execution here.
    }
}