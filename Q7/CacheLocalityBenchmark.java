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

    public double sumRowMajor() {

    double sum = 0;

    for (int row = 0; row < SIZE; row++) {
        for (int column = 0; column < SIZE; column++) {
            sum += matrix[row][column];
        }
    }

    return sum;
}

public double sumColumnMajor() {

    double sum = 0;

    for (int column = 0; column < SIZE; column++) {
        for (int row = 0; row < SIZE; row++) {
            sum += matrix[row][column];
        }
    }

    return sum;
}
  
 public static void main(String[] args) {

    CacheLocalityBenchmark benchmark =
            new CacheLocalityBenchmark();

    System.out.println("Cache Locality Benchmark");
    System.out.println("Matrix size: " + SIZE + " x " + SIZE);
    System.out.println("Measured iterations: " + ITERATIONS);

    // Warm up both methods before taking measurements.
    for (int i = 0; i < 5; i++) {
        resultSink = benchmark.sumRowMajor();
        resultSink = benchmark.sumColumnMajor();
    }

    long totalRowTime = 0;
    long totalColumnTime = 0;

    double rowSum = 0;
    double columnSum = 0;

    boolean allSumsMatch = true;

    for (int iteration = 1;
         iteration <= ITERATIONS;
         iteration++) {

        long start;
        long rowTime;
        long columnTime;

        // Alternate which method runs first.
        if (iteration % 2 == 1) {

            start = System.nanoTime();
            rowSum = benchmark.sumRowMajor();
            rowTime = System.nanoTime() - start;

            start = System.nanoTime();
            columnSum = benchmark.sumColumnMajor();
            columnTime = System.nanoTime() - start;

        } else {

            start = System.nanoTime();
            columnSum = benchmark.sumColumnMajor();
            columnTime = System.nanoTime() - start;

            start = System.nanoTime();
            rowSum = benchmark.sumRowMajor();
            rowTime = System.nanoTime() - start;
        }

        resultSink = rowSum + columnSum;

        totalRowTime += rowTime;
        totalColumnTime += columnTime;

        if (rowSum != columnSum) {
            allSumsMatch = false;
        }

        System.out.printf(
                "Iteration %d | Row: %.3f ms | Column: %.3f ms%n",
                iteration,
                rowTime / 1_000_000.0,
                columnTime / 1_000_000.0);
    }

    // Add the final comparison here in Step 5.
}

}