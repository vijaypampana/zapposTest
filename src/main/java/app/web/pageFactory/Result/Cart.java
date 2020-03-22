package app.web.pageFactory.Result;

import app.common.Context;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Cart {

    @FindBy(className = "Cy")
    public WebElement waitElement;

    @FindBy(xpath = "//div[@class='Dz']//dd")
    public WebElement color;

    @FindBy(xpath = "//div[@class='Dz']//dd/following-sibling::dd")
    public WebElement size;

    @FindBy(xpath = "//div[@class='Dz']//dd/following-sibling::dd/following-sibling::dd")
    public WebElement width;

    private Context context;
    private WebDriverWait oWebDriverWait;
    private WebDriver oWebDriver;

    public Cart() {
        if(context == null) {
            this.context = Context.getInstance();
            this.oWebDriver = context.getoWebDriver();
            this.oWebDriverWait = context.getoWebDriverWait();
            //PageFactory.initElements(oWebDriver, this);
        }
    }
}
