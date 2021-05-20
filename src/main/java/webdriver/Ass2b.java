package webdriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Ass2b {
	WebDriver driver;
	String techfios;
	String user_name;
	String passcode;
	String userbrowser;

	@BeforeTest
	public void property() {

		try {
			String path = "src\\main\\java\\config.properties";
			Properties prop = new Properties();
			FileInputStream fi = new FileInputStream(path);
			prop.load(fi);
			techfios = prop.getProperty("url");
			user_name = prop.getProperty("username");
			passcode = prop.getProperty("passwd");

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@BeforeMethod
	public void init() {
		System.out.println("select the browser to perform in...");
		System.out.println("option1. chrome");
		System.out.println("option2. Edge");
		Scanner scanner = new Scanner(System.in);
		String userbrowser = scanner.next();

		if (userbrowser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "Drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (userbrowser.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver", "Drivers\\msedgedriver.exe");
			driver = new EdgeDriver();
		}else {
			System.out.println("Browser cannot open");
		}
		driver.get(techfios);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);

	}

	@Test
	public void customerTest() {
		// Webelement lib by using BY class.
		By USERNAME2 = By.xpath("//input[@id='username']");
		By PASSWORD2 = By.xpath("//input[@name='password']");
		By SUBMIT2 = By.xpath("//button[@type='submit']");
		By DASHBOARD = By.xpath("//h2[contains(text(),'Dashboard')]");
		By CUSTOMER = By.xpath("//span[contains(text(),'Customers')]");
		By ADD_CUSTOMER = By.xpath("//a[contains(text(),'Add Customer')]");
		By NAME_FIELD = By.xpath("//input[@name='account']");
		By EMAIL_FIELD = By.xpath("//input[@id='email']");
		By PHONE_FIELD = By.xpath("//input[@id='phone']");
		By ADDRESS_FIELD = By.xpath("//input[@id='address']");
		By CITY_FIELD = By.xpath("//input[@id='city']");
		By STATE_FIELD = By.xpath("//input[@name='state']");
		By ZIP_CODE_FIELD = By.xpath("//input[@id='zip']");
		By COUNTRY_FIELD = By.xpath("//select[@id='country']");
		By SAVE_FIELD = By.xpath("//button[@id='submit']");

		// input datas.
		String name = "Rocket";
		String email = "lucky@gmail.com";
		String phone = "3452678";
		String address = "hen dr";
		String city = "ellen";
		String state = "AZ";
		String zip = "73834";

		driver.findElement(USERNAME2).sendKeys(user_name);
		driver.findElement(PASSWORD2).sendKeys(passcode);
		driver.findElement(SUBMIT2).click();

		String check = "Dashboard";
		String dashboardText = driver.findElement(DASHBOARD).getText();
		Assert.assertEquals(dashboardText, check, "Wrong page");
		waitMethod(driver, 60, CUSTOMER);
		driver.findElement(CUSTOMER).click();

		driver.findElement(ADD_CUSTOMER).click();
		waitMethod(driver, 60, NAME_FIELD);
		driver.findElement(NAME_FIELD).sendKeys(name);
		WebElement COMPANY_FIELD = driver.findElement(By.xpath("//select[@id='cid']"));

		dropdown(COMPANY_FIELD, "Techfios");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", COMPANY_FIELD);
		waitMethod(driver, 80, EMAIL_FIELD);

		int b = randomnum();
		driver.findElement(EMAIL_FIELD).sendKeys(b + email);
		driver.findElement(PHONE_FIELD).sendKeys(b + phone);
		waitMethod(driver, 80, ADDRESS_FIELD);

		driver.findElement(ADDRESS_FIELD).sendKeys(b + address);
		driver.findElement(CITY_FIELD).sendKeys(city);
		driver.findElement(STATE_FIELD).sendKeys(state);
		waitMethod(driver, 80, STATE_FIELD);

		driver.findElement(ZIP_CODE_FIELD).sendKeys(b + zip);
		waitMethod(driver, 80, ZIP_CODE_FIELD);
		
		WebElement COUNTRY_ELEMENT = driver.findElement(COUNTRY_FIELD);
		dropdown(COUNTRY_ELEMENT, "Canada");

		waitMethod(driver, 80, SAVE_FIELD);
		driver.findElement(SAVE_FIELD).click();

		By acc_created = By.xpath("//a[@id='summary']");
		waitMethod(driver, 100, acc_created);

		String acc_created_text = driver.findElement(By.xpath("//a[@id='summary']")).getText();

		Assert.assertEquals(acc_created_text, "Summary", "wrong page");
		waitMethod(driver, 100, acc_created);
		
		//verifying the data entered in list customer or not.
		driver.findElement(CUSTOMER).click();
		driver.findElement(By.xpath("//a[contains(text(),'List Customers')]")).click();
		driver.findElement(By.xpath("//input[@id='foo_filter']")).sendKeys(name);
		
		By name_verify = By.xpath("//tbody/tr/td[3]/a"); 
		WebElement a = driver.findElement(name_verify);
		String data = a.getText();
		waitMethod(driver,60,name_verify);
		Assert.assertEquals(data,name,"not found");
		waitMethod(driver,60,name_verify);
		
		}

	public void waitMethod(WebDriver driver, int waiting, By element) {
		WebDriverWait wait = new WebDriverWait(driver, waiting);
		wait.until(ExpectedConditions.visibilityOfElementLocated(element));
	}

	public void dropdown(WebElement element, String value) {
		Select sel = new Select(element);
		sel.selectByVisibleText(value);
	}

	public int randomnum() {
		Random num = new Random();
		int a = num.nextInt(500);
		return a;
	}

	@AfterMethod
	public void tearDown() {

		driver.close();
		driver.quit();
	}
}
