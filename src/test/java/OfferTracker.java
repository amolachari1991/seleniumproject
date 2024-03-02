import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class OfferTracker {
	
	//*********************Global Variables***************************************************************************

		static WebDriver driver;
		static Actions action;
		static JavascriptExecutor js;
		static double offerr;
		  // Get the current system time
        static LocalTime currentTime = LocalTime.now();
        // Define the target time
        static LocalTime targetTime = LocalTime.of(15, 30); // 3:30 PM
	
	//*********************Login Method***************************************************************************
		public void login() {
	    	ChromeOptions options = new ChromeOptions();
	        options.addArguments("--disable-notifications");
//	        options.addArguments("--headless");
	        driver = new ChromeDriver(options);
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
	        driver.manage().window().maximize();

	        // Login to the app
	        driver.get("https://kite.zerodha.com/dashboard");
	        driver.findElement(By.cssSelector("#userid")).sendKeys("XK9516");
	        driver.findElement(By.cssSelector("#password")).sendKeys("AAaa@#1991");
	        driver.findElement(By.cssSelector("button[type='submit']")).click();
	        driver.findElement(By.xpath("//button[normalize-space()='I understand']")).click() ;// Accept I understand pop-up
	    }
	
//*********************marketDepth Method***************************************************************************

		public void marketDepth() {
			action = new Actions(driver);
			WebElement move = driver.findElement(By.xpath("//span[contains(text(),'BANKNIFTY')][1]"));
			action.moveToElement(move).doubleClick().build().perform();
			driver.findElement(By.cssSelector("span.icon.icon-align-center")).click();
			List<WebElement> datas = driver.findElements(By.xpath("//table[@class='six columns sell']//tbody//tr[1]//td"));
            offerr = Double.parseDouble(datas.get(0).getText());

		}
		 
    public static void main(String[] args) throws InterruptedException {
    	
    	OfferTracker OfferTrackerOBJ=new OfferTracker();
    	OfferTrackerOBJ.login();
    	OfferTrackerOBJ.marketDepth();
		while (true) {
			Map<LocalDateTime, Double> offerMap = new HashMap<>();

			addOffer(offerMap, offerr);
			System.out.println(offerMap);
			printMinimumPrices(offerMap);
		}
    }

    public static void addOffer(Map<LocalDateTime, Double> offerMap, double offerValue) throws InterruptedException {
        LocalDateTime currentTime = LocalDateTime.now();
        int count=0;
        while(true) {
            // Simulating adding offer values at different times
        	
            offerMap.put(currentTime, offerValue);
            
            if(count>58)
            	break;
            count++;
            Thread.sleep(1000);
            }
    }

    public static void printMinimumPrices(Map<LocalDateTime, Double> offerMap) {
        Map<Integer, Double> minutePrices = new HashMap<>();

        for (Map.Entry<LocalDateTime, Double> entry : offerMap.entrySet()) {
            LocalDateTime currentTime = entry.getKey();
            int minute = currentTime.getMinute();
            double offerValue = entry.getValue();

            if (!minutePrices.containsKey(minute)) {
                minutePrices.put(minute, offerValue);
            } else {
                double currentMinPrice = minutePrices.get(minute);
                if (currentTime.getSecond() == 0 && offerValue < currentMinPrice) {
                    minutePrices.put(minute, offerValue);
                }
            }
        }

        for (Map.Entry<Integer, Double> entry : minutePrices.entrySet()) {
            int minute = entry.getKey();
            double minPrice = entry.getValue();
            System.out.println("Minute " + minute + ": Minimum Price = " + minPrice);
        }
    }
}
