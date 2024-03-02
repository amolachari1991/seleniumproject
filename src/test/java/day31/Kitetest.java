package day31;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

public class Kitetest {
//*********************Global Variables***************************************************************************

	static WebDriver driver;
	static Actions action;
	static JavascriptExecutor js;
	
//*********************Login Method***************************************************************************
	public void login() {
    	ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
//        options.addArguments("--headless");
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

     }
	 
//*********************databaseDataCollection Method***************************************************************************

	 public void databaseDataCollection(String TableName) throws SQLException, InterruptedException{
	    	// Database connection details
	        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:xe";
	        String username = "SYSTEM";
	        String password = "admin";

	        // Establish a database connection
	        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
	        System.out.println("Connection established...");

	       

	        while (true) {
	        	Thread.sleep(1000);
	            List<WebElement> datas = driver.findElements(By.xpath("//table[@class='six columns sell']//tbody//tr[1]//td"));
	           // Input data
	            double offerr = Double.parseDouble(datas.get(0).getText());
	            int orderr = Integer.parseInt(datas.get(1).getText());
	            int quantityy = Integer.parseInt(datas.get(2).getText());

	            // Create a SQL INSERT statement
	            String sql = "INSERT INTO "+TableName+"(timestampp, offer, orders, quantity) VALUES (?, ?, ?, ?)";

	            // Create a prepared statement with the SQL query
	            PreparedStatement statement = connection.prepareStatement(sql);

	            // Set the values for the parameters in the INSERT statement
	            
	            // Get the current system time
	            LocalTime currentTime = LocalTime.now();

	            // Get the current date
	            LocalDate currentDate = LocalDate.now();

	            // Define the target time
	            LocalTime targetTime = LocalTime.of(15, 30); // 3:30 PM
	            // Define the target time
	            LocalTime targetTime1 = LocalTime.of(14, 35); // 2:35 PM
	            // Define the target time
	            LocalTime targetTime2 = LocalTime.of(14, 55); // 2:55 PM
	            // Combine the current date and time to create a LocalDateTime object
	            LocalDateTime currentDateTime = LocalDateTime.of(currentDate, currentTime);

	            // Convert LocalDateTime to java.sql.Timestamp
	            Timestamp timestamp = Timestamp.valueOf(currentDateTime);

	            // Set the value for the timestampp parameter in the SQL statement
	            statement.setTimestamp(1, timestamp);

	            // Set the value for the ddate parameter in the SQL statement
//	            statement.setDate(2, java.sql.Date.valueOf(currentDate));
	            
	            statement.setDouble(2, offerr);
	            statement.setInt(3, orderr);
	            statement.setInt(4, quantityy);

	            // Execute the INSERT statement
	            int rowsAffected = statement.executeUpdate();

	            // Check the number of affected rows
	            if (rowsAffected > 0) {
	                System.out.println("Data inserted successfully.");
	            } else {
	                System.out.println("Failed to insert data.");
	            }

	            // Close the statement to release resources
	            statement.close();
	            
	            //************************
	            
	            //************************

	            // Compare the current time with the target time
	            if (currentTime.compareTo(targetTime) >= 0) {
	                System.out.println("Time reached 3:30 PM. Exiting the loop.");
	                break; // Break the while loop
	            }
	     
	        }

	        // Close the connection and release resources
	        connection.close();
	        // Quit the WebDriver and close the ChromeDriver process
	        driver.quit();
	        LaptopShutdown Shutdown =new LaptopShutdown(); 
	    }
	 
//*********************TakePosition Method***************************************************************************
	 
		public void takePosition() {
			action = new Actions(driver);
			WebElement move = driver.findElement(By.xpath("//span[contains(text(),'BANKNIFTY')][1]"));
			action.moveToElement(move).doubleClick().build().perform();//get option for buy button
			WebElement buy = driver.findElement(By.xpath("//button[normalize-space()='B']"));
			action.moveToElement(buy).click().build().perform();// click Buy button to Open Buy window
			js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//*[@label='Regular']")));	//click on Regulet option of Buy window
			js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//label[contains(text(),'Intraday')] ")));//click on intraday option
			WebElement quantity = driver.findElement(By.xpath("//*[@label='Qty.']"));
			quantity.clear();
			quantity.sendKeys("500");//set quantity
			js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//*[@label='Market']")));// select market order
			js.executeScript("arguments[0].click();", driver.findElement(By.xpath("(//span[contains(text(),'Buy')])[2]")));//click on Buy button to execute market order

		}
	 
//*********************exitPosition Method***************************************************************************

	  public void exitPosition() {//working code for exiting the position cost 1600 lagli script lihayala
      	action = new Actions(driver);
      	driver.findElement(By.xpath("//span[normalize-space()='Positions']")).click();//switch to position tab
      	WebElement pos = driver.findElement(By.xpath("//section[1]//table[1]/tbody[1]/tr[1]/td[4]"));
      	action.moveToElement(pos).click().build().perform();//click on Qty to get (...)Btn maintain option
      	driver.findElement(By.xpath("//span[@class='icon icon-ellipsis']")).click();//click on (...)Btn to get exit option
      	action.moveToElement(driver.findElement(By.xpath("//a[normalize-space()='Exit']"))).click().build().perform();//click on exit option to get sell window
      	driver.findElement(By.xpath("//button[@type='submit']//span[contains(text(),'Sell')]")).click();//click on sell btn to exit position	
      }
	  
    public static void main(String[] args) throws InterruptedException, IOException, SQLException {

    	Kitetest kt = new Kitetest();
    	kt.login();
    	kt.marketDepth();
    	kt.databaseDataCollection("KITE_10_07_23");//sql query => CREATE TABLE KITE_10_07_23 (timestampp TIMESTAMP,Ddate DATE,offer NUMBER(10, 2),orders NUMBER,quantity NUMBER);
//    	kt.takePosition();	
//    	Thread.sleep(100);
//    	kt.takePosition();																				

    
       
      
        
    
        
        
       
    }
}
