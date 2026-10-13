package Q4;

import java.util.concurrent.RecursiveAction;

public class PortfolioThresholdTask extends RecursiveAction {
    private static final int THRESHOLD = 1000;

    private double[] assets;
    private int start;
    private int end;
    private double riskCap;

    public PortfolioThresholdTask(double[] assets, int start,
                                  int end, double riskCap) {
        this.assets = assets;
        this.start = start;
        this.end = end;
        this.riskCap = riskCap;
    }

       @Override
    protected void compute() {
        for (int i = start; i < end; i++) {
            if (assets[i] > riskCap) {
                assets[i] = assets[i] * 0.95;
            }
        }
    }
}