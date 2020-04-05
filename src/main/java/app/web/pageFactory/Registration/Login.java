package app.web.pageFactory.Registration;

import app.common.Context;
import cucumber.api.java.en.Given;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Login {

    @FindBy(id = "ap_email")
    public WebElement uName;

    @FindBy(id = "ap_password")
    public WebElement password;

    @Given("^I enter username as \"(.*)\"$")
    public void enterUserName(String userName) {
        uName.sendKeys(userName);
    }

    @Given("^I enter password as \"(.*)\"$")
    public void enterPassword(String pass) {
        password.sendKeys(pass);
    }



    private Context context;
    private WebDriver oDriver;

    public Login() {
        context = Context.getInstance();
        oDriver = context.getoWebDriver();
        PageFactory.initElements(oDriver, this);
    }

}
