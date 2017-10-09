package myprojects.automation.assignment3.tests;

import myprojects.automation.assignment3.BaseScript;
import myprojects.automation.assignment3.tests.LoginTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateCategoryTest extends BaseScript {
    public static void main(String[] args) throws InterruptedException {
        // TODO prepare driver object
        EventFiringWebDriver driver = getConfiguredDriver();

        // ...
        // login
        LoginTest login = new LoginTest(driver);
        login.Login();
        //login.LogOut();
        // create category
        CategotyTest category = new CategotyTest(driver);
        category.catalogClick();
        // check that new category appears in Categories table
        category.categoryCheck();
        category.categoryAddCheck();
        // finish script
        login.LogOut();
        driver.quit();
    }
}
