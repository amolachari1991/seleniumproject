package day39;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage2 {

	//public WebDriver driver;
	WebDriverWait wait ;
	 
	//Constructor
	LoginPage2(WebDriver driver)
	{
		//this.driver=driver;
		PageFactory.initElements(driver,this);
		wait = new WebDriverWait(driver,Duration.ofSeconds(10));

	}
 
		
	// WebElements Locators+identification
	//@FindBy(xpath="//img[@alt='company-branding']") 
	@FindBy(how=How.XPATH, using="//img[@alt='company-branding']")
	WebElement img_logo;
	 
	@FindBy(name="username")
	WebElement txt_username;
	
	@FindBy(name="password")
	WebElement txt_password;
	
	@FindBy(xpath="//button[normalize-space()='Login']")
	WebElement btn_submit;
		
	//@FindBy(tagName="a")
	//List<WebElement> links;
	
	//Action methods
	
	public void setUserName(String username)
	{
		txt_username.sendKeys(username);
		//wait.until(ExpectedConditions.visibilityOf(txt_username)).sendKeys(username);
	}
	
	public void setPassword(String password)
	{
		txt_password.sendKeys(password);
	}
	
	public void clickSubmit()
	{
		btn_submit.click();
	}
	
	public boolean checkLogoPresence()
	{
		boolean status=wait.until(ExpectedConditions.visibilityOf(img_logo)).isDisplayed();
		
		return status;
	}
	
	
}
