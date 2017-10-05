package myprojects.automation.assignment2.tests;

import myprojects.automation.assignment2.BaseScript;
import myprojects.automation.assignment2.utils.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

public class LoginTest extends BaseScript {

    public static void main(String[] args) throws InterruptedException {
        // TODO Script to execute login and logout steps

        // WebDriver driver = getDriver();
        // ...

        WebDriver driver = getDriver();
        By emailInput = By.id("email");
        By passInput = By.id("passwd");
        By loginBtn = By.name("submitLogin");
        By logoutImg = By.cssSelector("span.employee_avatar_small");
        By logoutBtn = By.id("header_logout");
        String email = "webinar.test@gmail.com";
        String password = "Xcg7299bnSmMuRLp9ITw";

        driver.get(Properties.getBaseAdminUrl());
        driver.findElement(emailInput).sendKeys(email);
        driver.findElement(passInput).sendKeys(password);
        driver.findElement(loginBtn).click();
        Thread.sleep(2000);
        driver.findElement(logoutImg).click();
        driver.findElement(logoutBtn).click();

        //quitDriver(driver);

    }
}
