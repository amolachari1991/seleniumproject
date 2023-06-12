package day25;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class BingSearch {

public static void main(String[] args) {
	
	WebDriver driver=new ChromeDriver();
	
	driver.manage().deleteAllCookies(); //deletes all the cookies in your browser
	
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

	
   driver.get("https://www.bing.com/");
 
   // driver.manage().window().maximize();
   driver.findElement(By.xpath("//*[@id='sb_form_q']")).sendKeys("selenium");
   
   List<WebElement> list= driver.findElements(By.xpath("//li[contains(@class,'sa_sg')]//span")); //working - using partial xpath
   
   System.out.println("size of elements"+ list.size());

   for (int i=0;i<list.size();i++)
   {
    if(list.get(i).getText().equals("selenium tutorial"))
    {
     list.get(i).click();
     break;
    }
   }
}
}