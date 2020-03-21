package app.web.pageFactory;

import app.common.Context;
import cucumber.api.java.en.Given;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Home {

    @FindBy(xpath = "//img[@alt='Welcome! Go to Zappos.com Homepage!']")
    public WebElement waitElement;

    @FindBy(id = "searchAll")
    public WebElement searchBar;

    @FindBy(css = "input#searchAll ~ button")
    public WebElement submitButton;

    @FindBy(xpath = "//button[text()=\"Men's Size\"]")
    public WebElement mensSize;

    @FindBy(xpath = "//button[text()='Color']")
    public WebElement colorSelection;

    @Given("^I select shoe size as \"(.*)\"$")
    public void selectMenShoeSize(String size) {
        String xpathVal = "(//*[@aria-hidden='selected']//span[text()="+ size + "])[4]";
        context.getoWebDriverWait().until(ExpectedConditions.visibilityOf(oWebDriver.findElement(By.xpath(xpathVal))));
        (oWebDriver.findElement(By.xpath(xpathVal))).click();
    }

    private Context context;
    private WebDriver oWebDriver;

    public Home() {
        if(context == null) {
            context = Context.getInstance();
            oWebDriver = context.getoWebDriver();
        }
        //This is a important step to instantiate the element defined above. The step is included in context java under Wait Element exception logic
        //PageFactory.initElements(oWebDriver, this);
    }

}
