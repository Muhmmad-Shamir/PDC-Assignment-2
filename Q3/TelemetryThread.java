package Q3;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;

public class TelemetryThread extends Thread {
    @Override
    public void run() {
        OperatingSystemMXBean os =
            (OperatingSystemMXBean)
            ManagementFactory.getOperatingSystemMXBean();

        for (int i = 1; i <= 5; i++) {
            double cpu = os.getCpuLoad();

            double usedMemory =
                (os.getTotalMemorySize() - os.getFreeMemorySize())
                / (1024.0 * 1024.0);

            System.out.println(
                getName() + " | iteration " + i
            );

            if (cpu < 0) {
                System.out.println("System CPU usage: unavailable");
            } else {
                System.out.printf(
                    "System CPU usage: %.1f%%%n", cpu * 100
                );
            }

            System.out.printf(
                "Used physical memory: %.1f MB%n", usedMemory
            );
        }
    }
}