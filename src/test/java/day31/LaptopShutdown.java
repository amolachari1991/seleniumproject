package day31;
import java.io.IOException;
import java.time.LocalTime;

public class LaptopShutdown {

		public LaptopShutdown() {
        // Set the desired shutdown time (3:30 PM)
        LocalTime shutdownTime = LocalTime.of(15, 32);

        // Get the current time
        LocalTime currentTime = LocalTime.now();

        // Calculate the time difference until the desired shutdown time
        long delayMillis = shutdownTime.toNanoOfDay() - currentTime.toNanoOfDay();

        if (delayMillis <= 0) {
            System.err.println("Specified time has already passed.");
            return;
        }

        // Calculate the delay in seconds
        long delaySeconds = delayMillis / 1_000_000_000;

        // Schedule the shutdown
        try {
            // Close all tasks
            ProcessBuilder closeTasksProcessBuilder = new ProcessBuilder("taskkill", "/f", "/im", "*");
            closeTasksProcessBuilder.start();

            // Delay for a few seconds before shutting down
            Thread.sleep(5000);

            // Shutdown the laptop
            ProcessBuilder shutdownProcessBuilder = new ProcessBuilder("shutdown", "/s", "/t", String.valueOf(delaySeconds));
            shutdownProcessBuilder.start();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
