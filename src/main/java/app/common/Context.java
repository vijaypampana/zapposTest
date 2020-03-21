package app.common;

import app.common.enumType.BrowserType;
import app.common.enumType.WebDriverType;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import cucumber.api.java.en.Given;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Context {

    private static final Logger logger = LoggerFactory.getLogger(Context.class);
    public static final Context context = new Context();
    private static final String BASE_PACKAGE = "app";

    private Map<String, Object> oPageInstance = new HashMap<>();

    private CommonConfig oConfig;

    private WebDriver oWebDriver;
    private WebDriverWait oWebDriverWait;
    private DesiredCapabilities oCapabilities = new DesiredCapabilities();

    private String sCurrentPage = "";
    private JavascriptExecutor js;

    //These two fields are defined here as we cannot use local variables in Lambda functions
    private String sReturn;
    private WebElement element;
    private List<WebElement> elements;

    public Context() {
    }

    public static Context getInstance() {
        return context;
    }

    //This method will take the string value and find the web element
    public WebElement findElement(String value) {
        return findElement(sCurrentPage, value);
    }
    public List<WebElement>
    findElements(String value) {
        return findElements(sCurrentPage, value);
    }

    //This method will take the current Page and String webElement and return the webElement if found
    public WebElement findElement(String sCPage, String sWebElement) {
        sWebElement = sWebElement.replaceAll(" ", "");
        WebElement element = searchElement(getPageInstance(sCPage), sWebElement);
        if(element == null) {
            logger.error(sWebElement + " is not found in the pageFactory");
        }
        return element;
    }

    public List<WebElement> findElements(String sCPage, String sWebElement) {
        return searchElements(getPageInstance(sCPage), sWebElement);
    }

    //This method will add a element to PageInstance map with key as page Name and Object as PageClass Object
    public Object getPageInstance(String sPage) {
        String sSeperator = ".";
        if(!oPageInstance.containsKey(sPage)) {
            try {
                oPageInstance.put(sPage, Class.forName(BASE_PACKAGE + sSeperator + oConfig.getApplicationType() + sSeperator + processPage(sPage)).getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return oPageInstance.get(sPage);


    }

    //This method will actually removes any spaces and converts the string to lower case so that it matches the method names
    private String processPage(String sPage) {
        sReturn = "pageFactory";
        List<String> aPage = Arrays.asList(sPage.split("\\."));
        aPage.forEach(val -> sReturn += "." + val.replaceAll(" ", ""));
        return sReturn;
    }

    //This webElement will search for sObject in the list of fields defined in the current or parent class
    public WebElement searchElement(Object oPage, String sObject) {
        element = null;
        if(!isAPI()) {
            Class<?> aClass = oPage.getClass();
            Class<?> aSuperClass = aClass.getSuperclass();

            List<Field> fields = new ArrayList<>();
            fields.addAll(Arrays.asList(aClass.getDeclaredFields()));
            fields.addAll(Arrays.asList(aSuperClass.getDeclaredFields()));

            fields.forEach(field -> {
                if (field.getName().equalsIgnoreCase(sObject)) {
                    try {
                        element = (WebElement) field.get(oPage);
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                        System.out.println(e.getMessage());
                    }
                }
            });
        }
        return element;
    }

    public List<WebElement> searchElements(Object oPage, String sObject) {
        elements = null;

        Class<?> aClass = oPage.getClass();
        Class<?> aSuperClass = aClass.getSuperclass();

        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(aClass.getDeclaredFields()));
        fields.addAll(Arrays.asList(aSuperClass.getDeclaredFields()));

        fields.forEach(field -> {
            if(field.getName().equalsIgnoreCase(sObject)) {
                try {
                    elements = (List<WebElement>) field.get(oPage);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        });

        return elements;
    }

    public CommonConfig getoConfig() {
        return oConfig;
    }
    public void setoConfig(CommonConfig oConfig) {
        this.oConfig = oConfig;
    }

    public WebDriver getoWebDriver() {
        return oWebDriver;
    }

    public WebDriverWait getoWebDriverWait() {
        if(oWebDriverWait == null) {
            oWebDriverWait = new WebDriverWait(context.getoWebDriver(), context.getoConfig().getWebDriverTimeOut());
        }
        return oWebDriverWait;
    }

    public DesiredCapabilities getoCapabilities() {
        return oCapabilities;
    }

    public void setoCapabilities(DesiredCapabilities oCapabilities) {
        this.oCapabilities = oCapabilities;
    }

    public String getsCurrentPage() {
        return sCurrentPage;
    }

    public void setsCurrentPage(String sCurrentPage) {
        this.sCurrentPage = sCurrentPage;
    }

    public JavascriptExecutor getJs() {
        return js;
    }

    public void setJs(JavascriptExecutor js) {
        this.js = js;
    }

    public void setDriver(WebDriverType oWebDriverType, BrowserType oBrowserType, String sDeviceName) {
        switch (oWebDriverType) {
            case ZALENIUM:
                startRemoteDriver(oBrowserType, sDeviceName);
                break;
            case SELENIUMLOCAL:
                startLocalDriver(oBrowserType, sDeviceName);
                break;
        }
    }

    //This method will create a local browser Driver
    public void startLocalDriver(BrowserType oBrowserType, String sDeviceName) {
        switch (oBrowserType) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                oWebDriver = new ChromeDriver(getChromeOptions(sDeviceName));
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                oWebDriver = new FirefoxDriver(getFirefoxOptions(sDeviceName));
                break;
            case IE:
                WebDriverManager.iedriver().setup();
                oWebDriver = new InternetExplorerDriver(getInternetExplorerOptions());
                break;
            case SAFARI:
                oWebDriver = new SafariDriver();
                break;
            case OPERA:
                WebDriverManager.operadriver().setup();
                oWebDriver = new OperaDriver();
                break;
            case EDGE:
                WebDriverManager.edgedriver().setup();
                oWebDriver = new EdgeDriver();
                break;
        }
        js = (JavascriptExecutor) oWebDriver;
        oWebDriver.manage().window().maximize();
    }

    public void startRemoteDriver(BrowserType oBrowserType, String sDeviceName) {

    }

    //This method will close the webDriver
    public void closeDriver() {
        if(oWebDriver != null) {
            oWebDriver.quit();
        }
    }

    //This method will turn on Implicit Wait
    public void turnOnImplicitWait() {
        oWebDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    //This method will turn off Implicit Wait
    public void turnOffImplicitWait() {
        oWebDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

    //This method will give the chromeOptions
    public ChromeOptions getChromeOptions(String sDeviceName) {
        ChromeOptions options = new ChromeOptions();
        if(StringUtils.isEmpty(sDeviceName)) {
            options.addArguments("disable-extensions");
            options.addArguments("--start-maximized");
            options.addArguments("disable-infobars");
            options.setExperimentalOption("useAutomationExtension", false);
            options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
            options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        } else {
            Map<String, String> mobileEmulation = new HashMap<>();
            mobileEmulation.put("deviceName", sDeviceName);
            options.setExperimentalOption("mobileEmulation", mobileEmulation);
        }
        return options;
    }

    //This method will give you FireFoxOptions
    public FirefoxOptions getFirefoxOptions(String sDeviceName) {
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("network.automatic-ntlm-auth.trusted-uris", ".cigna.com,.cignaglobal.com");
        oCapabilities.setCapability(FirefoxDriver.PROFILE, profile);
        oCapabilities.setCapability("acceptInsecureCerts", true);
        oCapabilities.setCapability("moz:webdriverClick", false);
        return new FirefoxOptions(oCapabilities);
    }

    //This method will give you IE Options
    public InternetExplorerOptions getInternetExplorerOptions() {
        InternetExplorerOptions options = new InternetExplorerOptions();
        options.ignoreZoomSettings();
        options.destructivelyEnsureCleanSession();
        return options;
    }

    public void loadConfig() {
        logger.info("Started Reading CommonConfig.yaml");

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        try {
            oConfig = mapper.readValue(new File(getClass().getClassLoader().getResource("CommonConfig.yaml").getFile()),CommonConfig.class);
        } catch (Exception e) {
            logger.error("Encounter Error while reading the common Config file");
        }
    }

    public Boolean isAPI() {
        return context.getoConfig().getApplicationType().equalsIgnoreCase("API") ? true : false;
    }

    @Given("^I wait for (\\d+)$")
    public void wait_Thread(Integer sec) {
        try {
            Thread.sleep(sec);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
}
