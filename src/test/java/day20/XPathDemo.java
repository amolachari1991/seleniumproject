package day20;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class XPathDemo {

	public static void main(String[] args) {
		
		
		WebDriver driver=new ChromeDriver();
		
		driver.get("https://demo.opencart.com/");
		driver.manage().window().maximize();
		
		String productname= driver.findElement(By.xpath("//a[normalize-space()='MacBook']")).getText(); 
		//normalize-space()='Mac Book'=> ignores the spaces present between text
		
		//String productname=driver.findElement(By.xpath("/html[1]/body[1]/main[1]/div[2]/div[1]/div[1]/div[2]/div[1]/form[1]/div[1]/div[2]/div[1]/h4[1]/a[1]")).getText();
				
		System.out.println(productname);
		
		driver.quit();

	}

}
