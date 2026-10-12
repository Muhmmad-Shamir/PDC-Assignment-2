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

                telemetry.start();
        logs.start();

        System.out.println(
            "Main continues while workers execute."
        );

        System.out.println(
            "Telemetry state snapshot: " + telemetry.getState()
        );

        System.out.println(
            "Log state snapshot: " + logs.getState()
        );

        telemetry.join();
        logs.join();

        System.out.println(
            "Final states: " + telemetry.getState()
            + ", " + logs.getState()
        );

        System.out.println("Both workers completed.");
       
    }
}