package Q5;

public abstract class SensorSearchEngine {
    protected double[] sensorData;
    protected int startIdx;
    protected int endIdx;

    public SensorSearchEngine(double[] sensorData,
                              int startIdx, int endIdx) {
        if (startIdx < 0 || endIdx > sensorData.length
                || startIdx >= endIdx) {
            throw new IllegalArgumentException("Invalid range");
        }

        this.sensorData = sensorData;
        this.startIdx = startIdx;
        this.endIdx = endIdx;
    }

    public abstract double processGrid();
}