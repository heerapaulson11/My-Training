package TestNG;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


public class HomePage {

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
	@Parameters({"browser","url"})
	public void setup(String browsername,String applicationurl) throws Exception {

		testinitialize(browsername);
		driver.get(applicationurl);
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

	
	//@BeforeSuite
	public void BeforeSuit() {
		
		System.out.println("This is before suite");
	}
	
	//@AfterSuite
	public void AfterSuit() {
		
		System.out.println("This is after suite");
	}
	
    //@org.testng.annotations.BeforeTest
    public void BeforeTest() {
    	
    	System.out.println("This is before test");
    }
	
    //@org.testng.annotations.AfterTest
	public void AfterTest() {
		
		System.out.println("This is after test");
	}
	
    //@org.testng.annotations.BeforeClass
	public void BeforeClass() {
		
		System.out.println("This is before class");
	}
	
	//@org.testng.annotations.AfterClass
	public void AfterClass() {
		
		System.out.println("This is after class");
	} 
	/*@Test(enabled = true)
	void verifyTitle() {

		String expectedtitle = "Welcome: Mercury Tours";
		String actualtitle = driver.getTitle();
		Assert.assertEquals(actualtitle, expectedtitle, "Title mismatch");
	}*/

	

	@Test(enabled = true, dataProvider="usercredentials")
	void verifyLogin(String username,String password) {

		WebElement username1 = driver.findElement(By.xpath("//input[@name='userName']"));
		username1.sendKeys("deenathomas@gmail.com");
		WebElement password1 = driver.findElement(By.xpath("//input[@name='password']"));
		password1.sendKeys("deenathomas");
		WebElement submit = driver.findElement(By.xpath("//input[@name='submit']"));
		submit.click();
		WebElement actuallogin = driver.findElement(By.xpath("//h3[text()='Login Successfully']"));
		String actual = actuallogin.getText();
		String expectedlogin = "Login Successfully";
		Assert.assertEquals(actual, expectedlogin, "Not login");
	}

	@DataProvider(name="usercredentials")
	public Object[][] usercredentials(){
		
			Object[][] data=new Object[2][2];
			data[0][0]="deenathomas@gmail.com";
			data[0][1]="deenathomas";
			data[1][0]="user";
			data[1][1]="user1";
			
			return data;
	}
	@Test(enabled = false)
	void verifySidePanel() {

		List<String> expectedsidepanellinks = new ArrayList<String>();
		expectedsidepanellinks.add("Home");
		expectedsidepanellinks.add("Flights");
		expectedsidepanellinks.add("Hotels");
		expectedsidepanellinks.add("Car Rentals");
		expectedsidepanellinks.add("Cruises");
		expectedsidepanellinks.add("Destinations");
		expectedsidepanellinks.add("Vacations");

		List<WebElement> actualsidepanellinks = driver.findElements(By.xpath("//tr[@class='mouseOut']"));
		for (int i = 0; i < actualsidepanellinks.size(); i++) {
			System.out.println(actualsidepanellinks.get(i).getText());
		}
		for (int i = 0; i < expectedsidepanellinks.size(); i++) {

			Assert.assertEquals(actualsidepanellinks.get(i).getText(), expectedsidepanellinks.get(i),
					"Side panel doesnot match");
		}
	}
	
	@Test(enabled = true)
	void softAssertVerifyTitle() {
        SoftAssert softassertion=new SoftAssert();
		String expectedtitle = "Welcome: Mercury Tours123";
		String actualtitle = driver.getTitle();
		softassertion.assertEquals(actualtitle, expectedtitle, "Title mismatch");
		System.out.println("Soft assertion");
		//softassertion.assertAll();
	
	}
	
	@Test(enabled = true)
	void hardAssertVerifyTitle() {
 
		String expectedtitle = "Welcome: Mercury Tours123";
		String actualtitle = driver.getTitle();
		Assert.assertEquals(actualtitle, expectedtitle, "Title mismatch");
	    System.out.println("Hard assertion");
	}
}
