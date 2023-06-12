package day23;


import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ExplicitWaitDemo {

	public static void main(String[] args) {
	
		 
		WebDriver driver=new ChromeDriver();
		
		//declaring explicit wait
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
		
				
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
					
		WebElement useranme=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Username']")));
		useranme.sendKeys("Admin");
		
		
		WebElement password=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Password']")));
		password.sendKeys("admin123");
	
	}

}
