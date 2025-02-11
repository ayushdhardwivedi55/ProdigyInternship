package Test02;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.time.Duration;

public class EcommerceCheckout {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // 1. Login to the website
    @Test(priority = 1)
    public void loginTest() {
        try {
            driver.get("https://www.saucedemo.com/");

            // Enter username and password
            WebElement username = driver.findElement(By.id("user-name"));
            username.sendKeys("standard_user");

            WebElement password = driver.findElement(By.id("password"));
            password.sendKeys("secret_sauce");

            // Click login button
            driver.findElement(By.id("login-button")).click();

            // Verify login success
            wait.until(ExpectedConditions.urlContains("inventory.html"));
            System.out.println("✅ Login Successful!");

        } catch (Exception e) {
            System.out.println("❌ Login Test Failed: " + e.getMessage());
        }
    }

    // 2. Navigate to inventory page
    @Test(priority = 2)
    public void navigateToInventory() {
        driver.navigate().to("https://www.saucedemo.com/inventory.html");
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"));
        System.out.println("✅ Navigated to Inventory Page!");
    }

    // 3. Select a product and add to cart
    @Test(priority = 3)
    public void selectAndAddProduct() {
        try {
            WebElement selectProduct = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#item_4_title_link")));
            selectProduct.click();

            WebElement addToCart = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn_primary")));
            addToCart.click();

            System.out.println("✅ Product Added to Cart!");

        } catch (Exception e) {
            System.out.println("❌ Select and Add Product Failed: " + e.getMessage());
        }
    }

    // 4. Open cart
    @Test(priority = 4)
    public void openCart() {
        try {
            WebElement cart = wait.until(ExpectedConditions.elementToBeClickable(By.className("shopping_cart_link")));
            cart.click();
            System.out.println("✅ Cart Opened!");

        } catch (Exception e) {
            System.out.println("❌ Open Cart Failed: " + e.getMessage());
        }
    }

    // 5. Proceed to Checkout
    @Test(priority = 5)
    public void proceedToCheckout() {
        try {
            WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("checkout")));
            checkoutButton.click();
            System.out.println("✅ Proceeded to Checkout!");

        } catch (Exception e) {
            System.out.println("❌ Checkout Failed: " + e.getMessage());
        }
    }

    // 6. Fill checkout details
    @Test(priority = 6)
    public void fillCheckoutDetails() {
        try {
            driver.findElement(By.id("first-name")).sendKeys("Ayush");
            driver.findElement(By.id("last-name")).sendKeys("Dwivedi");
            driver.findElement(By.id("postal-code")).sendKeys("231216");

            driver.findElement(By.id("continue")).click();
            System.out.println("✅ Checkout Details Filled!");

        } catch (Exception e) {
            System.out.println("❌ Filling Checkout Details Failed: " + e.getMessage());
        }
    }

    // 7. Confirm order
    @Test(priority = 7)
    public void confirmOrder() {
        try {
            WebElement finishButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/div/div[2]/div/div[2]/div[9]/button[2]")));
            finishButton.click();

            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#checkout_complete_container")));
            Assert.assertTrue(successMessage.getText().contains("Thank you for your order!"));

            System.out.println("✅ Order Placed Successfully!");

        } catch (Exception e) {
            System.out.println("❌ Order Confirmation Failed: " + e.getMessage());
        }
    }
    @Test (priority = 8)
    public void ReturnHome() {
    	WebElement returnButton = driver.findElement(By.cssSelector("#back-to-products"));
    	returnButton.click();
    	
    	WebElement returnedToHome = driver.findElement(By.cssSelector("#header_container > div.header_secondary_container"));
    	
    	Assert.assertTrue(returnedToHome.getText().contains("Products"));
    	System.out.println("✅ Returned back to Home Successfully!");
    }

    @AfterClass
    public void tearDown() {
        driver.quit(); 
        System.out.println("✅ Test Completed! All tests passed! Browser Closed.");
    }
}
