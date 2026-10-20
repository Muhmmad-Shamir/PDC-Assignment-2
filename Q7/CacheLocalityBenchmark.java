package Q7;

public class CacheLocalityBenchmark {

    private static final int SIZE = 2000;
    private static final int ITERATIONS = 10;

    private double[][] matrix;

    private static volatile double resultSink;

    public CacheLocalityBenchmark() {

        matrix = new double[SIZE][SIZE];

        for (int row = 0; row < SIZE; row++) {
            for (int column = 0; column < SIZE; column++) {
                matrix[row][column] = row + column;
            }
        }
    }
}