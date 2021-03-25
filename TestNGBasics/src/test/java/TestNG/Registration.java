package TestNG;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Registration {

	public WebDriver driver;

	public void testinitialize(String Browser) throws Exception {
		if (Browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "C:\\Browser drivers\\chromedriver_win32\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (Browser.equalsIgnoreCase("Edge")) {
			System.setProperty("webdriver.edge.driver", "C:\\Browser drivers\\edgedriver_win64\\msedgedriver.exe");
			driver = new EdgeDriver();
		} else if (Browser.equalsIgnoreCase("FireFox")) {
			System.setProperty("webdriver.gecko.driver",
					"C:\\Browser drivers\\geckodriver-v0.29.0-win64\\geckodriver.exe");
			driver = new FirefoxDriver();
		} else {
			throw new Exception("Invalid browser");
		}

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();

	}

	@BeforeMethod
	public void setup() throws Exception {

		testinitialize("Chrome");
		driver.get("http://demo.guru99.com/test/newtours/");
	}

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {

		if (ITestResult.FAILURE == result.getStatus()) {

			TakesScreenshot takescreenshot = (TakesScreenshot) driver;
			File Screenshot = takescreenshot.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(Screenshot, new File("./Screenshots/" + result.getName() + ".png"));
		}
		driver.close();
	}
	
	@Test(enabled = true)
	void verifyRegistration() {

		WebElement register = driver.findElement(By.xpath("//a[text()='REGISTER']"));
		register.click();
		WebElement firstname = driver.findElement(By.xpath("//input[@name='firstName']"));
		firstname.sendKeys("deena");
		WebElement lastname = driver.findElement(By.xpath("//input[@name='lastName']"));
		lastname.sendKeys("thomas");
		WebElement phoneno = driver.findElement(By.xpath("//input[@name='phone']"));
		phoneno.sendKeys("9157686686");
		WebElement email = driver.findElement(By.xpath("//input[@id='userName']"));
		email.sendKeys("deenathomas@gmail.com");
		WebElement address = driver.findElement(By.xpath("//input[@name='address1']"));
		address.sendKeys("kalikal house");
		WebElement city = driver.findElement(By.xpath("//input[@name='city']"));
		city.sendKeys("kottayam");
		WebElement state = driver.findElement(By.xpath("//input[@name='state']"));
		state.sendKeys("kerala");
		WebElement postalcode = driver.findElement(By.xpath("//input[@name='postalCode']"));
		postalcode.sendKeys("755558");
		WebElement countrydropdown = driver.findElement(By.name("country"));
		Select select = new Select(countrydropdown);
		select.selectByValue("INDIA");
		WebElement username = driver.findElement(By.xpath("//input[@id='email']"));
		username.sendKeys("deenathomas@gmail.com");
		WebElement password = driver.findElement(By.xpath("//input[@name='password']"));
		password.sendKeys("deenathomas");
		WebElement confirmpassword = driver.findElement(By.xpath("//input[@name='confirmPassword']"));
		confirmpassword.sendKeys("deenathomas");
		WebElement submit = driver.findElement(By.xpath("//input[@name='submit']"));
		submit.click();
	}
}
