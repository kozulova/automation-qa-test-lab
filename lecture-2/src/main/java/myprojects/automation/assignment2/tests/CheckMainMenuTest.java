package myprojects.automation.assignment2.tests;

import myprojects.automation.assignment2.BaseScript;
import myprojects.automation.assignment2.utils.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import myprojects.automation.assignment2.tests.LoginTest;
import org.openqa.selenium.WebElement;


import javax.xml.bind.Element;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;


public class CheckMainMenuTest extends BaseScript {

    public static void main(String[] args) throws InterruptedException {
        // TODO Script to check Main Menu items

        // WebDriver driver = getDriver();
        // ...
        WebDriver driver = getDriver();

        By emailInput = By.id("email");
        By passInput = By.id("passwd");
        By loginBtn = By.name("submitLogin");
        String email = "webinar.test@gmail.com";
        String password = "Xcg7299bnSmMuRLp9ITw";

        driver.get(Properties.getBaseAdminUrl());
        driver.findElement(emailInput).sendKeys(email);
        driver.findElement(passInput).sendKeys(password);
        driver.findElement(loginBtn).click();
        Thread.sleep(2000);

        By menuLocator = By.className("maintab");
        By menuLocator2 = By.className("link-levelone");
        List<WebElement> menuLinks = driver.findElements(menuLocator);
        System.out.println(menuLinks.size());


        for (int i = 0; i < menuLinks.size(); i++) {
            menuLinks = driver.findElements(menuLocator);
            if (menuLinks.isEmpty()) {
                menuLinks = driver.findElements(menuLocator2);
            }
            WebElement item = menuLinks.get(i);
            item.click();

            Thread.sleep(2000);

            String title1 = driver.getTitle();
            System.out.println(title1);
            driver.navigate().refresh();

            String title2 = driver.getTitle();

            if (title1.equals(title2)) {
                System.out.println("Same page");
            } else {
                System.out.println("Different page");
            }
        }

    }
}
