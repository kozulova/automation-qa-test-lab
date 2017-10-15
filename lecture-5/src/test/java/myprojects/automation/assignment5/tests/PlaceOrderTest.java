package myprojects.automation.assignment5.tests;

import myprojects.automation.assignment5.BaseTest;
import myprojects.automation.assignment5.GeneralActions;
import myprojects.automation.assignment5.model.ProductData;
import myprojects.automation.assignment5.utils.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;

public class PlaceOrderTest extends BaseTest {
    @Parameters({"selenium.browser"})
    @Test
    public void checkSiteVersion(String browser) {
        // TODO open main page and validate website version
        driver.get(Properties.getBaseUrl());
        By mobileMenu = By.id("menu-icon");
        Assert.assertEquals(isMobileTesting(browser), driver.findElement(mobileMenu).isDisplayed());
        isMobileTesting(browser);
    }

    @Test
    public void createNewOrder() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        //GeneralActions action = new GeneralActions(driver);
        // TODO implement order creation test
        driver.get(Properties.getBaseUrl());
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("scroll(0, 1000)");
        By allProducts = By.className("all-product-link");
        driver.findElement(allProducts).click();
        // open random product
        // TODO implement random search
        List<WebElement> productTitles = driver.findElements(By.cssSelector("#js-product-list .product-title a"));
        Random randomGenerator = new Random();
        int indexOfProductTitle = randomGenerator.nextInt(productTitles.size());
        //System.out.println(productTitles.size());
        //System.out.println(indexOfProductTitle);
        WebElement randomProductTitle = productTitles.get(0);
        //System.out.println(randomProductTitle.getText());
        randomProductTitle.click();

        // save product parameters

        String productTitle = driver.findElement(By.className("h1")).getText().toLowerCase();
        String productPrice = driver.findElement(By.cssSelector(".current-price span")).getText().toLowerCase();
        String productUrl = driver.getCurrentUrl();

        // Loking for count of elemets
        By moreEl = By.xpath("//*[@id=\"main\"]/div[1]/div[2]/div[2]/div[3]/ul/li[2]/a");
        By productQtyEl = By.cssSelector(".product-quantities span");

        driver.findElement(moreEl).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(productQtyEl, "Товары"));
        String productQty = driver.findElement(productQtyEl).getText();
        productQty = productQty.replaceAll("\\D+","");
        int productQtyInt = Integer.parseInt(productQty);
        //System.out.println(productQtyInt);
        // add product to Cart and validate product information in the Cart

        driver.findElement(By.className("add-to-cart")).click();


        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"blockcart-modal\"]/div/div/div[2]/div/div[2]/div/a")));
        driver.findElement(By.xpath("//*[@id=\"blockcart-modal\"]/div/div/div[2]/div/div[2]/div/a")).click();

        //wait.until(ExpectedConditions.elementToBeClickable());
        String basketCount = driver.findElement(By.className("js-subtotal")).getText();
        basketCount = basketCount.substring(0, basketCount.length() - 4);
        String basketPrice = driver.findElement(By.className("product-price")).getText().toLowerCase();
        basketPrice = basketPrice.substring(0, basketPrice.length() - 2);
        String basketTitle = driver.findElement(By.cssSelector(".product-line-info a")).getText().toLowerCase();
        Assert.assertEquals(basketTitle, productTitle);
        Assert.assertEquals(basketPrice, basketPrice);
        Assert.assertEquals(basketCount, "1");
        driver.findElement(By.cssSelector(".checkout a")).click();
        // proceed to order creation, fill required information
        driver.findElement(By.name("firstname")).sendKeys("TestName");
        driver.findElement(By.name("lastname")).sendKeys("TestSurname");
        driver.findElement(By.name("email")).sendKeys("test@test.com");
        driver.findElement(By.xpath("//*[@id=\"customer-form\"]/footer/button")).click();
        driver.findElement(By.name("address1")).sendKeys("TestAddress");
        driver.findElement(By.name("postcode")).sendKeys("00000");
        driver.findElement(By.name("city")).sendKeys("TestTown");
        jse.executeScript("scroll(0, 300)");
        driver.findElement(By.name("confirm-addresses")).click();
        driver.findElement(By.name("confirmDeliveryOption")).click();
        driver.findElement(By.id("conditions_to_approve[terms-and-conditions]")).click();
        driver.findElement(By.id("payment-option-1")).click();
        driver.findElement(By.xpath("//*[@id=\"payment-confirmation\"]/div[1]/button")).click();

        driver.findElement(By.cssSelector(".card-title"));
        // place new order and validate order summary
        Assert.assertTrue(driver.findElement(By.className("done")).isDisplayed());

        String orderCount = driver.findElement(By.xpath("//*[@id=\"order-items\"]/div/div/div[3]/div/div[2]")).getText();
        Assert.assertEquals(orderCount, "1");

        String orderProductTitles = driver.findElement(By.cssSelector(".details span")).getText().toLowerCase();
        Assert.assertTrue(orderProductTitles.contains(productTitle));

        String orderPrice = driver.findElement(By.xpath("//*[@id=\"order-items\"]/div/div/div[3]/div/div[3]")).getText().toLowerCase();
        orderPrice = orderPrice.substring(0, orderPrice.length() - 2);
        Assert.assertEquals(basketPrice, orderPrice);

        // check updated In Stock value
        driver.get(productUrl);
        driver.findElement(moreEl).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(productQtyEl, "Товары"));
        String newProductQty = driver.findElement(productQtyEl).getText();
        newProductQty = newProductQty.replaceAll("\\D+","");
        int newProductQtyInt = Integer.parseInt(newProductQty);
        Assert.assertEquals(newProductQtyInt, productQtyInt-1);
    }

}
