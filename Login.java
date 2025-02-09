//Second task: To automate login details of the given website by using selenium and testng.

package Test02;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class Login {
    
    public WebDriver driver;
    public WebElement username, password, submit;
    String baseUrl = "https://www.saucedemo.com/";

    // Setting up the chromedriver.
    @BeforeMethod
    public void setup() {
        System.out.println("Before Method: Setting up WebDriver...");
        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\chromedriver-win64\\chromedriver.exe");  // Corrected path
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl);
    }    

    @Test(priority = 1)
    // Opening the website and print title.
    public void TitleTest() {
        String title = driver.getTitle();
        if(title.equals("Swag Labs")) {  // Corrected title comparison
            System.out.println("Title fetched: " + title);
        } else {
            System.out.println("Fetching failed");
        }
        Assert.assertEquals(title, "Swag Labs");
    }

    // Login with valid username and valid password.
    @Test(priority = 2)
    public void loginTest() {
        username = driver.findElement(By.id("user-name"));
        username.sendKeys("performance_glitch_user");
        password = driver.findElement(By.id("password"));
        password.sendKeys("secret_sauce");
        submit = driver.findElement(By.cssSelector("#login-button"));
        submit.click();
        
        String nurl = driver.getCurrentUrl();
        if(nurl.equals("https://www.saucedemo.com/inventory.html")) {
            System.out.println("First Login Test Passed Successfully!");
        } else {
            System.out.println("First Login Failed");
        }
    }
    @Test(priority = 3)
    // Login with invalid username and valid password.
    public void loginTestInvalidUsername() {
        username = driver.findElement(By.id("user-name"));
        username.sendKeys("performan_glitch_user");
        password = driver.findElement(By.id("password"));
        password.sendKeys("secret_sauce");
        submit = driver.findElement(By.cssSelector("#login-button"));
        submit.click();
        
        String expected_msg = "Epic sadface: Username and password do not match any user in this service";
        String invalid_Msg = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[1]/div/div/form/div[3]/h3")).getText();
        Assert.assertTrue(invalid_Msg.contains(expected_msg));
        System.out.println("Second Login Failed as: " + invalid_Msg);
    }

    @Test(priority = 4)
    // Login using empty username and empty password.
    public void loginTestBlankCredentials() {
        username = driver.findElement(By.id("user-name"));
        username.sendKeys("");
        password = driver.findElement(By.id("password"));
        password.sendKeys("");
        submit = driver.findElement(By.cssSelector("#login-button"));
        submit.click();

        String expected_msg1 = "Epic sadface: Username is required";
        String invalid_Msg1 = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[1]/div/div/form/div[3]/h3")).getText();
        Assert.assertTrue(invalid_Msg1.contains(expected_msg1));
        System.out.println("Third Login Failed as: " + invalid_Msg1);
    }

    @AfterMethod
    public void tearDown() {
        System.out.println("After Method: Closing browser...");
        driver.close();
        driver.quit();
    }
}
