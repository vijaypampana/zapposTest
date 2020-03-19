package app.common;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

public class Context {

    private static final Logger logger = LoggerFactory.getLogger(Context.class);
    public static final Context context = new Context();
    private Map<String, Object> oPageInstance = new HashMap<>();

    private CommonConfig oConfig = new CommonConfig();

    private String BASE_PACKAGE = "app";

    private String sCurrentPage = "";

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
    public List<WebElement> findElements(String value) {
        return findElements(sCurrentPage, value);
    }

    //This method will take the current Page and String webElement and return the webElement if found
    public WebElement findElement(String sCPage, String sWebElement) {
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
                oPageInstance.put(sPage, Class.forName(BASE_PACKAGE + sSeperator + oConfig.applicationType + sSeperator + processPage(sPage)).getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return oPageInstance.get(sPage);
    }

    //This method will actually removes any spaces and converts the string to lower case so that it matches the method names
    private String processPage(String sPage) {
        sReturn = ".pageFactory";
        List<String> aPage = Arrays.asList(sPage.split("\\."));
        aPage.forEach(val -> sReturn += "." + val.toLowerCase().replaceAll(" ", ""));
        return sReturn;
    }

    public WebElement searchElement(Object oPage, String sObject) {
        element = null;

        Class<?> aClass = oPage.getClass();
        Class<?> aSuperClass = aClass.getSuperclass();

        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(aClass.getDeclaredFields()));
        fields.addAll(Arrays.asList(aSuperClass.getDeclaredFields()));

        fields.forEach(field -> {
            if(field.getName().equalsIgnoreCase(sObject)) {
                try {
                    element = (WebElement) field.get(oPage);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        });

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

}
