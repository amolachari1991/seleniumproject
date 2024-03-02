package day31;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class RealTimeDataAnalyzer {
	public static List<Double> realTimePrices = new ArrayList<>();
	public static List<Double> bottomPrices = new ArrayList<>();
	public static List<Double> topPrices = new ArrayList<>();
	public static int cycleCount = 0;

    public static void main(String[] args) throws InterruptedException {
    	
    	//
    	  ChromeOptions options = new ChromeOptions();
          options.addArguments("--disable-notifications");
          WebDriver driver = new ChromeDriver(options);
          driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
          driver.manage().window().maximize();

          // Login to the app
          driver.get("https://kite.zerodha.com/dashboard");
          driver.findElement(By.cssSelector("#userid")).sendKeys("XK9516");
          driver.findElement(By.cssSelector("#password")).sendKeys("AAaa@#1991");
          driver.findElement(By.cssSelector("button[type='submit']")).click();
          WebElement move = driver.findElement(By.cssSelector(".nice-name"));
          Actions action = new Actions(driver);
          action.moveToElement(move).doubleClick().build().perform();
          driver.findElement(By.cssSelector("span.icon.icon-align-center")).click();
          
          while (true) {
          	Thread.sleep(1000);
              List<WebElement> datas = driver.findElements(By.xpath("//table[@class='six columns sell']//tbody//tr[1]//td"));

              // Input data
              double offerr = Double.parseDouble(datas.get(0).getText());
              int orderr = Integer.parseInt(datas.get(1).getText());
              int quantityy = Integer.parseInt(datas.get(2).getText());
              
              // Simulate real-time data updates
              simulateRealTimeData(offerr);

              
              // Get the current system time
              LocalTime currentTime = LocalTime.now();

              // Get the current date
              LocalDate currentDate = LocalDate.now();

              // Define the target time
              LocalTime targetTime = LocalTime.of(15, 30); // 3:30 PM
              // Combine the current date and time to create a LocalDateTime object
              LocalDateTime currentDateTime = LocalDateTime.of(currentDate, currentTime);

             
             


              // Compare the current time with the target time
              if (currentTime.compareTo(targetTime) >= 0) {
                  System.out.println("Time reached 3:30 PM. Exiting the loop.");
                  break; // Break the while loop
              }
          }
          
        
          // Quit the WebDriver and close the ChromeDriver process
          driver.quit();
          LaptopShutdown Shutdown =new LaptopShutdown(); 
    	
    	//
          
          
          
          
        // Simulate real-time data updates
//        simulateRealTimeData();

        // Continuously analyze real-time data
        while (true) {
            // Check for cyclic pattern
            if (isCyclicPattern(realTimePrices)) {
                // Check if the current price has already bounced back from any of the previously stored bottom prices
                double currentPrice = realTimePrices.get(realTimePrices.size() - 1);
                if (hasBouncedBack(currentPrice, bottomPrices)) {
                    // Generate a signal or perform any desired action
                    System.out.println("Signal: Price has bounced back from a previous cycle's bottom price.");
                }

                // Identify and store the bottom and top prices in the current cycle
                double cycleBottomPrice = findCycleBottomPrice(realTimePrices);
                double cycleTopPrice = findCycleTopPrice(realTimePrices);
                bottomPrices.add(cycleBottomPrice);
                topPrices.add(cycleTopPrice);
                cycleCount++;
            }

            // Check if the current price is heading towards any of the stored top prices
            double currentPrice = realTimePrices.get(realTimePrices.size() - 1);
            if (isHeadingTowardsTopPrice(currentPrice, topPrices)) {
                // Generate a signal or perform any desired action
                System.out.println("Signal: Price is heading towards a stored top price.");
            }

            // Simulate real-time data updates
//            updateRealTimeData();

            // Wait for a specific interval before analyzing the next set of data
            try {
                Thread.sleep(5000); // Adjust the interval as per your requirement
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void simulateRealTimeData(double offerr) {
        // Simulate real-time data updates
    	while(true) {
            realTimePrices.add(offerr);
    	}
        
    }

//    public static void updateRealTimeData() {
//        // Simulate real-time data updates
//        double previousPrice = realTimePrices.get(realTimePrices.size() - 1);
//        double currentPrice = previousPrice + Math.random() * 20; // Simulate price fluctuations
//        realTimePrices.add(currentPrice);
//    }

    public static boolean isCyclicPattern(List<Double> prices) {
        // Check if the list of prices has enough data points to form a cyclic pattern
        if (prices.size() < 300) {
            return false;
        }

        // Identify the cycle by comparing the differences between consecutive prices
        List<Double> priceDifferences = new ArrayList<>();
        for (int i = 1; i < prices.size(); i++) {
            double difference = prices.get(i) - prices.get(i - 1);
            priceDifferences.add(difference);
        }

        // Determine if the pattern is cyclic by comparing the differences with the first difference
        double firstDifference = priceDifferences.get(0);
        for (int i = 1; i < priceDifferences.size(); i++) {
            if (priceDifferences.get(i) != firstDifference) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasBouncedBack(double currentPrice, List<Double> bottomPrices) {
        // Check if the current price has already bounced back from any of the previously stored bottom prices
        for (double bottomPrice : bottomPrices) {
            if (currentPrice > bottomPrice) {
                return true;
            }
        }
        return false;
    }

    public static boolean isHeadingTowardsTopPrice(double currentPrice, List<Double> topPrices) {
        // Check if the current price is heading towards any of the stored top prices
        for (double topPrice : topPrices) {
            if (currentPrice < topPrice) {
                return true;
            }
        }
        return false;
    }

    public static double findCycleBottomPrice(List<Double> prices) {
        // Find the minimum price in the given list of prices
        double minPrice = Double.MAX_VALUE;
        for (double price : prices) {
            if (price < minPrice) {
                minPrice = price;
            }
        }
        return minPrice;
    }

    public static double findCycleTopPrice(List<Double> prices) {
        // Find the maximum price in the given list of prices
        double maxPrice = Double.MIN_VALUE;
        for (double price : prices) {
            if (price > maxPrice) {
                maxPrice = price;
            }
        }
        return maxPrice;
    }
}
