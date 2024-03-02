package day31;
import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        while (true) {
            LocalDate currentDate = LocalDate.now();
            LocalTime currentTime = LocalTime.now();
            
            System.out.println("Current Date: " + currentDate);
            System.out.println("Current Time: " + currentTime);

            // Check if current time is greater than or equal to 3:30 PM
            if (currentTime.compareTo(LocalTime.of(15, 30)) >= 0) {
                break; // Exit the loop
            }

            // Wait for a short period before checking the time again
            try {
                Thread.sleep(1000); // Sleep for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Current time is 3:30 PM or later. Exiting the loop.");
    }
}
