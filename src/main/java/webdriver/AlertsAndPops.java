package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AlertsAndPops {
	WebDriver driver;

	@BeforeMethod
	public void init() {
		System.setProperty("webdriver.chrome.driver", "Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://mail.rediff.com/cgi-bin/login.cgi");
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
	}

	@Test
	public void cnn() throws Exception {

		WebElement LOGIN_ELEMENT = driver.findElement(By.xpath("//input[@name='proceed']"));
		LOGIN_ELEMENT.click();
		/*
		 * WebDriverWait wait = new WebDriverWait(driver, 60);
		 * wait.until(ExpectedConditions.visibilityOf(LOGIN_ELEMENT));
		 */

		String a = driver.switchTo().alert().getText();
		System.out.println(a);
		// Class b=driver.switchTo().getClass();
		// System.out.println(b);

		Thread.sleep(2000);
		driver.switchTo().alert().accept();

	}

	@AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
	}

}


