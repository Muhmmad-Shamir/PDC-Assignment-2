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

        System.out.println("Before adjustment: " + serialSum(original));

        serialAdjust(original, cap);

        System.out.println("After adjustment: " + serialSum(original));
    }
}