package myprojects.automation.assignment3.tests;

import myprojects.automation.assignment3.BaseScript;
import myprojects.automation.assignment3.utils.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.events.EventFiringWebDriver;


public class LoginTest extends BaseScript {
    private By emailInput = By.id("email");
    private By passInput = By.id("passwd");
    private By loginBtn = By.name("submitLogin");
    private By logoutImg = By.cssSelector("span.employee_avatar_small");
    private By logoutBtn = By.id("header_logout");
    private String email = "webinar.test@gmail.com";
    private String password = "Xcg7299bnSmMuRLp9ITw";
    private EventFiringWebDriver driver;

    //WebDriver driver = getConfiguredDriver();
    public LoginTest(EventFiringWebDriver driver) {
        this.driver = driver;
    }

    public void Login() {
        driver.get(Properties.getBaseAdminUrl());
        driver.findElement(emailInput).sendKeys(email);
        driver.findElement(passInput).sendKeys(password);
        driver.findElement(loginBtn).click();
    }

    public void LogOut(){
        driver.findElement(logoutImg).click();
        driver.findElement(logoutBtn).click();
        //quitDriver(driver);
    }
}
