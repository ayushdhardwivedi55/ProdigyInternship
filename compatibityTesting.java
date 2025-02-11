package Test02;

//Java program to detect broken 
//links on the webpage 
//Imported Packages 

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class compatibityTesting {
	public static void main(String[] args) throws IOException {

		// Providing The Location Of ChromeDriver
		System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\chromedriver-win64\\chromedriver.exe");

		WebDriver driver = new ChromeDriver();

		// Maximizing Driver Window
		driver.manage().window().maximize();

		// Checking The Google Link
		driver.get("https://www.deadlinkchecker.com/");

		// Getting All The Links In One List
		List<WebElement> links = driver.findElements(By.tagName("a"));

		// Printing The Total Links Number
		System.out.println("Total Link Size: " + links.size());
		for (int i = 0; i < links.size(); i++) {
			// Checking Each & Every Links
			WebElement element = links.get(i);

			String url = element.getAttribute("href");

			URL link = new URL(url);
			HttpURLConnection httpConn = (HttpURLConnection) link.openConnection();
			httpConn.connect();

			// Getting The Response Code
			int code = httpConn.getResponseCode();

			// If The Number Is Greater Than 400,
			// Then It Is Broken Link
			if (code >= 400) {
				System.out.println("Broken Link: " + url + "|" + "Response code: " + code);
			} else {
				System.out.println("Valid Link: " + url + "|" + "Response code: " + code);
			}
		}

		System.out.println();
		System.out.println("All Links Checked");

		// Closing The Driver
		driver.close();
		driver.quit();
	}
}
