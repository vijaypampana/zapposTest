package app.web.pageFactory.Registration;

import app.common.Context;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ChangePSW {

    private Context context;
    private WebDriver oDriver;

    @FindBy(xpath = "//h1[text()='Password Change']")
    public WebElement WaitElement;

    public ChangePSW() {
        if(context == null) {
            context = Context.getInstance();
            oDriver = context.getoWebDriver();
            PageFactory.initElements(oDriver, this);
        }
    }
}
