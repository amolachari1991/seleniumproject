package day17;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
/*
1) Launch browser
2) open url
	https://opensource-demo.orangehrmlive.com/
3) Provide username  - Admin
4) Provide password  - admin123
5) Click on Login button 
6) Verify the title of dashboard page   
	Exp title : OrangeHRM
7) close browser

 */
import org.openqa.selenium.chrome.ChromeDriver;

public class NoCommerceLoginTest {

	public static void main(String[] args) throws InterruptedException {
		
		//System.setProperty("webdriver.chrome.driver","C:\\Drivers\\chromedriver_win32\\chromedriver.exe");
		//WebDriverManager.chromedriver().setup();
		
		//1) Launch browser
		//ChromeDriver driver=new ChromeDriver();
		WebDriver driver=new ChromeDriver();
		
		//2) open url on the browser
		driver.get("https://admin-demo.nopcommerce.com/login?ReturnUrl=%2Fadmin%2F");
		
		driver.manage().window().maximize(); // maximize the page
		
		
		Thread.sleep(500);
		
		//3) Provide username  - Admin
		//WebElement txtUsername=driver.findElement(By.name("username"));
		//txtUsername.sendKeys("Admin");
		WebElement username = driver.findElement(By.xpath("//*[@id=\"Email\"]"));
		username.clear();
		Thread.sleep(100);
		username.sendKeys("admin@yourstore.com");
 
		
		//4) Provide password  - admin123
		WebElement passward = driver.findElement(By.xpath("//*[@id=\"Password\"]"));
		passward.clear();
		Thread.sleep(100);
		passward.sendKeys("admin");
 
		//5) Click on Submit button 
		driver.findElement(By.xpath("/html/body/div[6]/div/div/div/div/div[2]/div[1]/div/form/div[3]/button")).click();
		Thread.sleep(100);
		
		//6) Verify the title of dashboard page   
		//Title validation
		String act_title=driver.getTitle();
		String exp_title="Dashboard / nopCommerce administration";
		
		if(act_title.equals(exp_title))
		{
			System.out.println("Test passed");
		}
		else
		{
			System.out.println("failed");
		}
		
		
		// Lable validation after successful login
		String act_label = "";
		try
		{
		act_label=driver.findElement(By.xpath("/html/body/div[3]/div[1]/div[1]/h1")).getText();
		}
		catch(NoSuchElementException e)	{ 
			}
		
		String exp_label="Dashboard";
		
		if(act_label.equals(exp_label))
		{
			System.out.println("test passed");
		}
		else
		{
			System.out.println("test failed");
		}
		
		//7) close browser
		//driver.close();
		driver.quit();
		
		
		
	}

}
