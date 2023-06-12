package day31;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import day33.ExcelUtils;

public class kitetest {

	public static void main(String[] args) throws InterruptedException, IOException  {
		
	
 
		ChromeOptions options=new ChromeOptions();
		options.addArguments("--disable-notifications");
		WebDriver driver = new ChromeDriver(options); 
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.manage().window().maximize();
		
		driver.get("https://kite.zerodha.com/dashboard");
			
		driver.findElement(By.xpath("//input[@id='userid']")).sendKeys("XK9516");
			 
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("AAaa@#1991");
		
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		WebElement move =driver.findElement(By.cssSelector(".nice-name"));
		
		Actions action = new Actions(driver);
		action.moveToElement(move).doubleClick().build().perform();
		driver.findElement(By.xpath("//span[@class='icon icon-align-center']")).click();
		String path ="C:\\Users\\softw\\eclipse-workspace\\Pavan Kumar\\seleniumproject\\testdata\\kite.xlsx";
		int r=0;
		while(true)
		{		
		List <WebElement> datas=driver.findElements(By.xpath("//table[@class='six columns sell']//tbody//tr[1]//td"));
			if((Integer.parseInt(datas.get(2).getText())!=500))
			{   
				ExcelUtils.createRow(path, "Sheet1", r);
				int c=0;
				for(int i=0;i<datas.size();i++)
				{
					String dta = datas.get(i).getText();
					System.out.print(datas.get(i).getText()+"\t");
					ExcelUtils.setCellData(path, "Sheet1", r, c, dta);
					c++;
				}
				r++;
			System.out.println();
			/*do
			{
			action.moveToElement(move).click().build().perform();
			WebElement buy=driver.findElement(By.cssSelector(".button-blue.buy"));
			action.moveToElement(buy).click().build().perform();
			WebElement OrdrTypeIntraday = driver.findElement(By.xpath("(//label[@class='su-radio-label'])[7]"));
			OrdrTypeIntraday.click();
			WebElement mrktOrdr =driver.findElement(By.xpath("(//label[@class='su-radio-label'])[9]"));
			mrktOrdr.click();
			WebElement qntity=driver.findElement(By.xpath("//div[@class='no su-input-group su-static-label']//input[@type='number']"));
			qntity.clear();
			qntity.sendKeys("100");
			driver.findElement(By.xpath("//button[@type='submit']//span[contains(text(),'Buy')]")).click();

			}while(false); */
			//Thread.sleep(1500);
			 
			}
			
		} 
			  

						
		//driver.quit(); 
		
	}

}


