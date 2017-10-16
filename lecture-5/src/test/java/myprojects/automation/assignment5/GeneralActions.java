package myprojects.automation.assignment5;


import myprojects.automation.assignment5.model.ProductData;
import myprojects.automation.assignment5.utils.logging.CustomReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;

/**
 * Contains main script actions that may be used in scripts.
 */
public class GeneralActions {
    private WebDriver driver;
    private WebDriverWait wait;

    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    public void openRandomProduct() {
        // TODO implement logic to open random product before purchase
        //throw new UnsupportedOperationException();
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        List<WebElement> productTitles = driver.findElements(By.cssSelector("#js-product-list .product-title a"));
        Random randomGenerator = new Random();
        int indexOfProductTitle = randomGenerator.nextInt(productTitles.size());
        WebElement randomProductTitle = productTitles.get(indexOfProductTitle);
        jse.executeScript("arguments[0].scrollIntoView(true);", randomProductTitle);
        wait.until(ExpectedConditions.elementToBeClickable(randomProductTitle));
        randomProductTitle.click();
    }

    /**
     * Extracts product information from opened product details page.
     *
     * @return
     */
    public ProductData getOpenedProductInfo() {
        CustomReporter.logAction("Get information about currently opened product");
        // TODO extract data from opened page
        //throw new UnsupportedOperationException();
        By productQtyEl = By.cssSelector(".product-quantities span");
        By moreEl = By.partialLinkText("Подробнее о товаре");
        driver.findElement(moreEl).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(productQtyEl, "Товары"));
        String productTitle = driver.findElement(By.className("h1")).getText().toLowerCase();
        String productPrice = driver.findElement(By.cssSelector(".current-price span")).getText().toLowerCase();
        productPrice = productPrice.replaceAll("\\D+","");
        float productPriceFloat = Float.parseFloat(productPrice);

        String productQty = driver.findElement(productQtyEl).getText();
        productQty = productQty.replaceAll("\\D+","");
        int productQtyInt = Integer.parseInt(productQty);

        ProductData product = new ProductData(productTitle, productQtyInt, productPriceFloat);
        return product;
    }
}
