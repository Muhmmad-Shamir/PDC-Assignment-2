package Q5;

public class ParallelMaxSearch extends SensorSearchEngine {

    public ParallelMaxSearch(double[] sensorData,
                             int startIdx, int endIdx) {
        super(sensorData, startIdx, endIdx);
    }

    @Override
    public double processGrid() {
        double maximum = sensorData[startIdx];

        for (int i = startIdx + 1; i < endIdx; i++) {
            if (sensorData[i] > maximum) {
                maximum = sensorData[i];
            }
        }

        return maximum;
    }
}