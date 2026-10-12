package Q3;

public class AsyncWebEngineApp {
    public static void main(String[] args)
            throws InterruptedException {

        System.out.println(
            "Default thread: " + Thread.currentThread().getName()
        );

        TelemetryThread telemetry = new TelemetryThread();
        telemetry.setName("Telemetry-Worker");

        LogProcessorTask logTask = new LogProcessorTask();
        Thread logs = new Thread(logTask, "Log-Worker");

       
    }
}