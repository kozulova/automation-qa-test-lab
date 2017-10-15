package myprojects.automation.assignment5;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.openqa.selenium.ie.InternetExplorerDriver;
//import org.openqa.selenium.ie.InternetExplorerOptions;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

/**
 * Base script functionality, can be used for all Selenium scripts.
 */
public abstract class BaseTest {
    protected WebDriver driver;
    protected GeneralActions actions;
    protected boolean isMobileTesting;

    private WebDriver getDriver(String browser) {
        switch (browser) {
            case "firefox":
                System.setProperty(
                        "webdriver.gecko.driver",
                        new File(BaseTest.class.getResource("/geckodriver.exe").getFile()).getPath());
                return new FirefoxDriver();
            case "ie":
            case "internet explorer":
                System.setProperty(
                        "webdriver.ie.driver",
                        new File(BaseTest.class.getResource("/IEDriverServer.exe").getFile()).getPath());
                DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
                capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);            // disable native events to speed up typing
                capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);   // force clean session
                return new InternetExplorerDriver();
            case "phantomjs":
                System.setProperty(
                        "phantomjs.binary.path",
                        new File(BaseTest.class.getResource("/phantomjs.exe").getFile()).getPath());
                return new PhantomJSDriver();
            case "chrome":
            default:
                System.setProperty(
                        "webdriver.chrome.driver",
                        new File(BaseTest.class.getResource("/chromedriver.exe").getFile()).getPath());
                return new ChromeDriver();
        }
    }

    private RemoteWebDriver getRemoteDriver(String gridUrl, String browser) throws MalformedURLException {
        DesiredCapabilities capabilities;
        switch (browser) {
            case "firefox":
                capabilities = DesiredCapabilities.firefox();
                break;
            case "ie":
            case "internet explorer":
                capabilities = DesiredCapabilities.internetExplorer();
                break;
            case "phantomjs":
                capabilities = DesiredCapabilities.phantomjs();
                break;
            case "chrome":
            default:
                capabilities = DesiredCapabilities.chrome();
                break;
        }
        return new RemoteWebDriver(new URL(gridUrl), capabilities);
    }

    /**
     * Prepares {@link WebDriver} instance with timeout and browser window configurations.
     *
     * Driver type is based on passed parameters to the automation project,
     * creates {@link ChromeDriver} instance by default.
     *
     */

    @BeforeClass
    @Parameters({"selenium.browser", "selenium.grid"})
    public void setUp(@Optional("chrome") String browser, @Optional("") String gridUrl) throws MalformedURLException {
        // TODO create WebDriver instance according to passed parameters
        // driver = new EventFiringWebDriver(....);
        // driver.register(new EventHandler());
        // ...
        //driver = new EventFiringWebDriver(getDriver(browser));
       driver = gridUrl.isEmpty() ? getRemoteDriver(gridUrl, browser) : getDriver(browser);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        // unable to maximize window in mobile mode
        if (!isMobileTesting(browser))
            driver.manage().window().maximize();

        isMobileTesting = isMobileTesting(browser);

        actions = new GeneralActions(driver);
    }

    /**
     * Closes driver instance after test class execution.
     */
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     *
     * @return Whether required browser displays content in mobile mode.
     */
    public boolean isMobileTesting(String browser) {
        switch (browser) {
            case "android":
                return true;
            case "firefox":
            case "ie":
            case "internet explorer":
            case "chrome":
            case "phantomjs":
            default:
                return false;
        }
    }
}
