package app.web.pageFactory;

import app.common.Context;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Home {

    @FindBy(xpath = "//img[@alt='Welcome! Go to Zappos.com Homepage!']")
    public WebElement waitElement;

    @FindBy(id = "searchAll")
    public WebElement searchBar;

    @FindBy(css = "input#searchAll ~ button")
    public WebElement submitButton;

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
