package app.common.web;

import app.common.Context;
import cucumber.api.java.en.Given;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DeepLinks {

    private Context context;
    private WebDriver oDriver;

    @Given("^I launch \"(.*)\" page with User ID \"(.*)\"$")
    public void getDeepLinks(String sPage, String sSSOID) {
        HashMap<String, String> hm = new HashMap<>();
        hm.put("username", sSSOID);
        hm.put("password", "test123");
        deepLinksLogin(sPage, hm);
    }

    @Given("^I navigate to \"(.*)\" screen$")
    public void deepLinksLogin(String sPage) {
        deepLinksLogin(sPage, new HashMap<>());
    }

    @Given("^I navigate to \"(.*)\" screen using below parameters$")
    public void deepLinksLogin(String deepLinkName, Map<String, String> actualParams) {

        if(StringUtils.isNotEmpty(deepLinkName)) {
            DeepLink deepLink = DeepLink.valueOf(DeepLink.getEnum(deepLinkName));
            String deepLinkUrl = deepLink.getUrl(new LinkedHashMap<>(actualParams));
            oDriver.get(deepLinkUrl);

            context.setsCurrentPage(deepLink.getLandingPage());
            context.getoWebDriverWait().until(ExpectedConditions.visibilityOf(context.findElement("WaitElement")));
        }

    }

    public DeepLinks() {
        if(context == null) {
            context = Context.getInstance();
            oDriver = context.getoWebDriver();
        }
    }

}
