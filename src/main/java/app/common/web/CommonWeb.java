package app.common.web;

import app.common.CommonConfig;
import app.common.Context;
import app.common.Transform.TransformToAssert;
import app.common.Transform.TransformToWebElement;
import cucumber.api.Transform;
import cucumber.api.java.en.Given;
import cucumber.api.java8.Tr;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Optional;

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
        //element.click();          //Seeing failures sometimes randomly
        context.getJs().executeScript("arguments[0].click();", element);
    }

    @Given("^I get the webpage \"(.*)\"$")
    public void getWebPage(String sUrl) {
        context.getoWebDriver().get(sUrl);
    }

    @Given("^I wait for \"(.*)\" page to load$")
    public void locateWaitElement(String sScreen) {
        context.setsCurrentPage(sScreen.replaceAll(" ", ""));
        context.wait_for_page_load();
        try {
            context.getoWebDriverWait().until(ExpectedConditions.visibilityOf(context.findElement("waitElement")));
        } catch (StaleElementReferenceException | NullPointerException e) {
            //This is very important
            PageFactory.initElements(context.getoWebDriver(), context.getPageInstance(context.getsCurrentPage()));
            locateWaitElement(sScreen);
        }
    }

    @Given("^I enter \"(.*)\" as \"(.*)\"$")
    public void sendKeys(@Transform(TransformToWebElement.class) WebElement element, String value) {
        element.sendKeys(value);
    }

    @Given("^I scroll (?:down|up) until the visibility of \"(.*)\"$")
    public void scroll_for_visibility(@Transform(TransformToWebElement.class) WebElement element) {
        context.wait_for_page_load();
        context.scroll_to_visible(element);
    }

    @Given("^I wait until the visibility of \"(.*)\"$")
    public void wait_visibility(@Transform(TransformToWebElement.class) WebElement element) {
        context.wait_for_page_load();
        context.getoWebDriverWait().until(ExpectedConditions.visibilityOf(element));
    }

    @Given("^I (verify|assert) \"(.*)\" is displayed as \"(.*)\"$")
    public void verify_object_label(@Transform(TransformToAssert.class) Boolean bAssert, @Transform(TransformToWebElement.class) WebElement element, String value) {
        try {
            Assert.assertEquals(element.getText(), value == null ? "" : value);
        } catch (AssertionError e) {
            if(bAssert) {
                throw e;
            } else {
                //TBD
            }
        }
    }

    private Context context;

    public CommonWeb() {
        this.context = Context.getInstance();
    }

}
