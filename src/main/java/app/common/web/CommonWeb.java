package app.common.web;

import app.common.CommonConfig;
import app.common.Context;
import app.common.Transform.TransformToWebElement;
import cucumber.api.Transform;
import cucumber.api.java.en.Given;
import org.openqa.selenium.WebElement;

public class CommonWeb {

    @Given("I start the application")
    public void startApplication() {
        if(context.getoConfig() == null) {
            context.setoConfig(new CommonConfig());
            context.loadConfig();
        }

        if(context.getoWebDriver() != null) {
            context.closeDriver();
        }
        switch (context.getoConfig().getWebDriverType()) {
            case SELENIUMLOCAL:
                context.startLocalDriver(context.getoConfig().getCapability().getBrowserName(), "");
                break;
            case ZALENIUM:
                context.startRemoteDriver(context.getoConfig().getCapability().getBrowserName(), context.getoConfig().getCapability().getDeviceName());
                break;
        }
    }

    @Given("^I click on \"(.*)\"$")
    public void clickOn(@Transform(TransformToWebElement.class) WebElement element) {
        element.click();
    }

    @Given("^I get the webpage \"(.*)\"$")
    public void getWebPage(String sUrl) {
        context.getoWebDriver().get(sUrl);
    }

    private Context context;

    public CommonWeb() {
        this.context = Context.getInstance();
    }

}
