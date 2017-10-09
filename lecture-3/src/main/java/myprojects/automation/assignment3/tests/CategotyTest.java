package myprojects.automation.assignment3.tests;
        import myprojects.automation.assignment3.BaseScript;
        import myprojects.automation.assignment3.utils.Properties;
        import org.openqa.selenium.*;
        import org.openqa.selenium.interactions.Actions;
        import org.openqa.selenium.support.events.EventFiringWebDriver;
        import org.openqa.selenium.support.ui.ExpectedConditions;
        import org.openqa.selenium.support.ui.WebDriverWait;

        import java.util.List;


public class CategotyTest extends BaseScript{
    private EventFiringWebDriver driver;
    By catalog = By.id("subtab-AdminCatalog");
    By category = By.id("subtab-AdminCategories");
    By addCategory = By.id("page-header-desc-category-new_category");
    By categoryTitle = By.id("name_1");
    By categorySubmit = By.id("category_form_submit_btn");
    By downIcon = By.xpath("//*[@id=\"table-category\"]/thead/tr[1]/th[3]/span/a[2]");
    By alert = By.className("alert-success");
    By categories = By.xpath("//table[@class=\"table category\"]//td[3]");
    String categoryTitleText = "New shop category";

    public CategotyTest(EventFiringWebDriver driver) {
        this.driver = driver;
    }

    public boolean scrollPageDown(){
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        //executor.executeScript("window.scrollBy(0,250)");
        boolean scrollResult = (boolean) executor.executeScript(
                "var scrollBefore = $(window).scrollTop();" +
                        "window.scrollTo(scrollBefore, document.body.scrollHeight);" +
                        "return $(window).scrollTop() > scrollBefore;");
        return scrollResult;
    }

    public void catalogClick(){
        WebElement catalogTabElement = driver.findElement(catalog);
        Actions actions = new Actions(driver);
        actions.moveToElement(catalogTabElement).build().perform();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions. elementToBeClickable(category));
        catalogTabElement.findElement(category).click();
        wait.until(ExpectedConditions. elementToBeClickable(addCategory));
        driver.findElement(addCategory).click();
        driver.findElement(categoryTitle).sendKeys(categoryTitleText);
        scrollPageDown();
        wait.until(ExpectedConditions. elementToBeClickable(categorySubmit));
        driver.findElement(categorySubmit).click();
    }

    public void categoryCheck(){
        boolean alertSucess= driver.findElement(alert).isDisplayed();
        if(alertSucess){
            System.out.println("CATEGORY IS CREATED ");
        }
        else{
            System.out.println("Category is not created ");
        }
        driver.findElement(downIcon).click();

    }

    public void categoryAddCheck(){
        List<WebElement> categoriesValues =  driver.findElements(categories);

        for(int i=0; i<categoriesValues.size(); i++){
            if(categoriesValues.get(i).getText().equals(categoryTitleText)){
                System.out.println(categoriesValues.get(i).getText()+" IS IN CATEGORY TABLE");

            }
        }
    }
}
