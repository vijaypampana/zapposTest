package app.web.pageFactory;

import app.common.Context;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

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

    @FindBy(css = "div .ch")
    public List<WebElement> resultsSet;


    @Given("^I select shoe size as \"(.*)\"$")
    public void selectMenShoeSize(String size) {
        String xpathVal = "(//*[@aria-hidden='selected']//span[text()="+ size + "])[4]";
        context.getoWebDriverWait().until(ExpectedConditions.visibilityOf(oWebDriver.findElement(By.xpath(xpathVal))));
        (oWebDriver.findElement(By.xpath(xpathVal))).click();
    }

    @Given("^I select the color using below table$")
    public void colorSelection(DataTable table) {
        List<List<String>> values = table.raw();
        values.forEach( val -> {
            WebElement element = oWebDriver.findElement(By.xpath("//span[text()='"+ val.get(0)+"']"));
            context.scroll_to_visible(element);
            element.click();
            context.wait_for_page_load();
        });
    }

    @Given("^I click (first|last) result element$")
    public void select_result(String str) {
        context.wait_for_page_load();
        List<WebElement> listEle = oWebDriver.findElements(By.cssSelector("div .ch"));
        if(listEle.size() == 0) {
            select_result(str);
        } else {
            if (str.equalsIgnoreCase("first")) {
                listEle.get(0).click();
            } else {
                listEle.get(listEle.size()).click();
            }
        }
        context.wait_for_page_load();
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
