package day31;
import java.util.ArrayDeque;
import java.util.Deque;

public class p {
    private static Deque<Double> priceQueue = new ArrayDeque<>();

    public static void main(String[] args) {
        // Simulated real-time data updates
        simulateRealTimeData();

        // Continuously analyze real-time data
        while (true) {
            double latestPrice = getLatestPrice();

            // Collect latest 60 prices
            collectLatestPrices(latestPrice);

            // Find the minimum and maximum prices from the collected data
            double minPrice = findMinPrice();
            double maxPrice = findMaxPrice();

            // Generate a signal if the latest collected price is greater than the minimum price
            if (latestPrice > minPrice) {
                System.out.println("Signal: Latest price is greater than the minimum price.");
            }

            // Simulate real-time data updates
            updateRealTimeData();

            // Wait for a specific interval before analyzing the next set of data
            try {
                Thread.sleep(5000); // Adjust the interval as per your requirement
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void simulateRealTimeData() {
        // Simulate real-time data updates
        for (int i = 0; i < 100; i++) {
            double price = Math.random() * 100; // Simulated price between 0 and 100
            priceQueue.add(price);
        }
    }

    private static void updateRealTimeData() {
        // Simulate real-time data updates
        double latestPrice = Math.random() * 100; // Simulated price between 0 and 100
        priceQueue.add(latestPrice);

        // Remove oldest price if the size exceeds 60
        if (priceQueue.size() > 60) {
            priceQueue.removeFirst();
        }
    }

    private static double getLatestPrice() {
        // Get the latest price from the offer variable or any other data source
        // Replace this with your actual data retrieval logic
        return Math.random() * 100; // Simulated price between 0 and 100
    }

    private static void collectLatestPrices(double latestPrice) {
        // Collect the latest prices
        priceQueue.add(latestPrice);

        // Remove oldest price if the size exceeds 60
        if (priceQueue.size() > 60) {
            priceQueue.removeFirst();
        }
    }

    private static double findMinPrice() {
        // Find the minimum price from the collected prices
        double minPrice = Double.MAX_VALUE;
        for (double price : priceQueue) {
            if (price < minPrice) {
                minPrice = price;
            }
        }
        return minPrice;
    }

    private static double findMaxPrice() {
        // Find the maximum price from the collected prices
        double maxPrice = Double.MIN_VALUE;
        for (double price : priceQueue) {
            if (price > maxPrice) {
                maxPrice = price;
            }
        }
        return maxPrice;
    }
}
