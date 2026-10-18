package Q5;

public class ParallelMinSearch extends SensorSearchEngine {

    public ParallelMinSearch(double[] sensorData,
                             int startIdx, int endIdx) {
        super(sensorData, startIdx, endIdx);
    }

    @Override
    public double processGrid() {
        double minimum = sensorData[startIdx];

        for (int i = startIdx + 1; i < endIdx; i++) {
            if (sensorData[i] < minimum) {
                minimum = sensorData[i];
            }
        }

        return minimum;
    }
}