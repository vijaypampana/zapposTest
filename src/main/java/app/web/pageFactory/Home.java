package app.web.pageFactory;

import app.common.Context;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Home {

    @FindBy(xpath = "//img[@alt='Welcome! Go to Zappos.com Homepage!']")
    public WebElement waitElement;

    @FindBy(id = "searchAll")
    public WebElement searchBar;

    @FindBy(css = "input#searchAll ~ button")
    public WebElement submitButton;

    private Context context;

    public Home() {
        if(context != null) {
            context = Context.getInstance();
        }
    }

}
