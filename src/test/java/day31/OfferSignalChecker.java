package day31;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OfferSignalChecker {
    public static void signal() {
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "SYSTEM";
        String password = "admin";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement()) {

            // Get the current timestamp
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTimestamp = format.format(new Date());

            // Calculate the minute group for the current timestamp
            String currentMinuteGroup = currentTimestamp.substring(0, 16);

            // Calculate the start time of the current minute
            String currentMinuteStart = currentMinuteGroup + ":00";

            // Calculate the offer value after 5 seconds from the current minute start
            Calendar cal = Calendar.getInstance();
            cal.setTime(format.parse(currentMinuteStart));
            cal.add(Calendar.SECOND, 5);
            String fiveSecondsAfterStart = format.format(cal.getTime());

            // Retrieve the min offer value for the current minute
            String minOfferQuery = "SELECT MIN(offer) FROM KITE_10_07_23 WHERE TIMESTAMPP >= TO_DATE('" + currentMinuteStart + "', 'YYYY-MM-DD HH24:MI:SS') AND TIMESTAMPP <= TO_DATE('" + currentMinuteGroup + ":59', 'YYYY-MM-DD HH24:MI:SS')";
            ResultSet minOfferResult = stmt.executeQuery(minOfferQuery);
            minOfferResult.next();
            double minOffer = minOfferResult.getDouble(1);

            // Retrieve the latest offer value after 5 seconds from the current minute start
            String latestOfferQuery = "SELECT offer FROM KITE_10_07_23 WHERE TIMESTAMPP = TO_DATE('" + fiveSecondsAfterStart + "', 'YYYY-MM-DD HH24:MI:SS')";
            ResultSet latestOfferResult = stmt.executeQuery(latestOfferQuery);
            latestOfferResult.next();
            double latestOffer = latestOfferResult.getDouble(1);

            // Check if the latest offer value after 5 seconds is greater than the min offer value
            if (latestOffer > minOffer) {
                System.out.println("Latest Offer Value: " + latestOffer);
                System.out.println("Offer Second: " + fiveSecondsAfterStart.substring(17));
            } else {
                System.out.println("No signal generated.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
