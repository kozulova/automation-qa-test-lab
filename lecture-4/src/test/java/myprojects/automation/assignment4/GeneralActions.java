package myprojects.automation.assignment4;


import myprojects.automation.assignment4.model.ProductData;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import myprojects.automation.assignment4.utils.Properties;
import org.testng.Assert;

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

    /**
     * Logs in to Admin Panel.
     * @param login
     * @param password
     */
    public void login(String login, String password) {
        // TODO implement logging in to Admin Panel
        By emailInput = By.id("email");
        By passInput = By.id("passwd");
         By loginBtn = By.name("submitLogin");
        // By logoutImg = By.cssSelector("span.employee_avatar_small");
        // By logoutBtn = By.id("header_logout");
         //String email = "webinar.test@gmail.com";
         //String password = "Xcg7299bnSmMuRLp9ITw";
        driver.get(Properties.getBaseAdminUrl());
        driver.findElement(emailInput).sendKeys(login);
        driver.findElement(passInput).sendKeys(password);
        driver.findElement(loginBtn).click();
        waitForContentLoad();

        //throw new UnsupportedOperationException();
    }

    public void createProduct(ProductData newProduct) {
        // TODO implement product creation scenario
        //throw new UnsupportedOperationException();
        By addProduct = By.id("page-header-desc-configuration-add");
        driver.findElement(addProduct).click();
        waitForContentLoad();

        By productName = By.id("form_step1_name_1");
        By qty = By.id("form_step1_qty_0_shortcut");
        By price = By.id("form_step1_price_ttc_shortcut");
        By submitBtn = By.id("submit");
        By switchInput = By.className("switch-input");


        driver.findElement(productName).sendKeys(newProduct.getName());
        driver.findElement(qty).sendKeys(newProduct.getQty().toString());
        driver.findElement(price).sendKeys(newProduct.getPrice().toString());
        driver.findElement(switchInput).click();
        //sucessMessageCheck();
        driver.findElement(submitBtn).click();
        //sucessMessageCheck();
    }

    /**
     * Waits until page loader disappears from the page
     */
    public void waitForContentLoad(){
        // TODO implement generic method to wait until page content is loaded
        // wait.until(...);
        // ...
        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
                    }
                };
        //WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(pageLoadCondition);
    }

    public void openProductPage(){
        By catalog = By.id("subtab-AdminCatalog");
        By product = By.id("subtab-AdminProducts");
        WebElement catalogTabElement = driver.findElement(catalog);
        Actions actions = new Actions(driver);
        actions.moveToElement(catalogTabElement).build().perform();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions. elementToBeClickable(product));
        catalogTabElement.findElement(product).click();
        waitForContentLoad();
    }
        public void sucessMessageCheck(){
        By sucessMessage = By.className("growl-message");
        wait.until(ExpectedConditions.elementToBeClickable(sucessMessage));
        if(driver.findElement(sucessMessage).getText().equals("Настройки обновлены.")){
            driver.findElement(By.className("growl-close")).click();
        }
        else{
            System.out.println("Настройки не обновлены");
        }

    }



}
