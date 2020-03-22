package app.web.pageFactory.Result;

import app.common.Context;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Home {

    @FindBy(id = "main")
    public WebElement waitElement;

    @FindBy(id = "pdp-color-select")
    public WebElement colorSelect;

    @FindBy(id = "pdp-size-select")
    public WebElement sizeSelect;

    @FindBy(id = "pdp-width-select")
    public WebElement widthSelect;

    @FindBy(xpath = "//button[text()='Add to Cart']")
    public WebElement addToCart;

    @FindBy(xpath = "//button[text()='Proceed to Checkout']")
    public WebElement checkOut;

    @FindBy(xpath = "//div[@class='HR']//a[@href='/cart']")
    public WebElement viewCartLink;

    private Context context;
    private WebDriverWait oWebDriverWait;
    private WebDriver oWebDriver;

    public Home() {
        if(context == null) {
            this.context = Context.getInstance();
            this.oWebDriver = context.getoWebDriver();
            this.oWebDriverWait = context.getoWebDriverWait();
            PageFactory.initElements(oWebDriver, this);
        }
    }
}
