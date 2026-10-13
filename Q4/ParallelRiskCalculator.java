package Q4;

import java.util.concurrent.RecursiveTask;

public class ParallelRiskCalculator extends RecursiveTask<Double> {
    private static final int THRESHOLD = 1000;

    private double[] assets;
    private int start;
    private int end;

    public ParallelRiskCalculator(double[] assets, int start, int end) {
        this.assets = assets;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Double compute() {
        double sum = 0;

        for (int i = start; i < end; i++) {
            sum += assets[i];
        }

        return sum;
    }
}