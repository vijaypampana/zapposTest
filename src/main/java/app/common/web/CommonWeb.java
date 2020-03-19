package app.common.web;

import app.common.Context;
import app.common.Transform.TransformToWebElement;
import cucumber.api.Transform;
import cucumber.api.java.en.Given;
import org.openqa.selenium.WebElement;

public class CommonWeb {

    @Given("^I click on \"(.*)\"$")
    public void clickOn(@Transform(TransformToWebElement.class) WebElement element) {
        element.click();
    }

    private Context context;

    public CommonWeb() {
        this.context = Context.getInstance();
    }

}
