package myprojects.automation.assignment4.tests;

import myprojects.automation.assignment4.BaseTest;
import myprojects.automation.assignment4.GeneralActions;
import myprojects.automation.assignment4.model.ProductData;
import myprojects.automation.assignment4.utils.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class CreateProductTest extends BaseTest {
    ProductData product = ProductData.generate();

    @Parameters({"login", "password"})
    @Test
    public void createNewProduct(String login, String password) {
        // TODO implement test for product creation

        // actions.login(login, password);
        actions.login(login, password);
        actions.openProductPage();
        actions.waitForContentLoad();
        actions.createProduct(product);
        actions.waitForContentLoad();
    }

    // TODO implement logic to check product visibility on website
    @Test(dependsOnMethods = "createNewProduct")
    public void checkProductAppearance() {
        driver.get(Properties.getBaseUrl());
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("scroll(0, 1000)");
        By allProducts = By.className("all-product-link");
        driver.findElement(allProducts).click();
        By productTitle = By.partialLinkText(product.getName());
        Assert.assertNotNull(productTitle, "Product not found");
        jse.executeScript("scroll(0, 1000)");
        driver.findElement(productTitle).click();

        By productPageTitle = By.className("h1");
        By productPageQty = By.cssSelector(".product-quantities span");
        By productPagePrice = By.cssSelector(".current-price span");

        System.out.println(driver.findElement(productPageTitle).getText().toLowerCase() + " " + product.getName().toLowerCase());
        Assert.assertEquals(driver.findElement(productPageTitle).getText().toLowerCase(), product.getName().toLowerCase());

        String price = driver.findElement(productPagePrice).getText();
        price = price.substring(0, price.length() - 2);

        Assert.assertEquals(price, product.getPrice());

        String qty = driver.findElement(productPageQty).getText();
        qty = qty.substring(0, qty.length() - 7);

        Assert.assertEquals(qty, product.getQty().toString());
    }
}
