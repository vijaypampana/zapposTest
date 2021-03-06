
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class DevRunner {

    private static final Logger logger = LoggerFactory.getLogger(DevRunner.class);

    private WebDriver oDriver;
    private WebDriverWait oWebDriverWait;
    private WebDriverWait oWebDriverImplicitWait;

    @Test(enabled = true)
    public void startTest() throws InterruptedException {
        try {
            WebDriverManager.chromedriver().setup();
            //System.setProperty("webdriver.chrome.driver", "src/test/resources/driver/chromedriver.exe");
            oDriver = new ChromeDriver(getChromeOption());
            oDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            oDriver.get("https://www.zappos.com");
            oWebDriverImplicitWait = new WebDriverWait(oDriver, 10);
            oWebDriverWait = new WebDriverWait(oDriver, 15);
            JavascriptExecutor js = (JavascriptExecutor) oDriver;
            //oWebDriverWait.until(ExpectedConditions.visibilityOf(oDriver.findElement(By.xpath("//*[@role='dialog']"))));
            Thread.sleep(3000);
            oDriver.findElement(By.xpath("//aside[@role='dialog']//button")).click();
            oDriver.findElement(By.id("searchAll")).sendKeys("Nike Air Zoom Pegasus 36");
            //oDriver.findElement(By.xpath("//button[text()='Search']")).click();
            Thread.sleep(3000);
            oDriver.findElement(By.id("searchAll")).sendKeys(Keys.ENTER);
            oWebDriverWait.until(ExpectedConditions.visibilityOf(oDriver.findElement(By.id("feedback"))));
            oDriver.findElement(By.xpath("//*[text()='Men's Size']")).click();
            Thread.sleep(3000);
            oDriver.findElement(By.xpath("(//*[@aria-hidden='selected']//span[text()=10])[4]")).click();
            //js.executeScript("arguments[0].scrollIntoView()", oDriver.findElement(By.xpath("//section[@class='SR TR kS']")));
            Thread.sleep(3000);
            List<WebElement> oElements = oDriver.findElements(By.xpath("(//div[@role='group'])[6]//span"));
            js.executeScript("arguments[0].scrollIntoView()", oElements.get(0));

            oElements.forEach(ele -> {
                if (ele.getText().equalsIgnoreCase("Black") || ele.getText().equalsIgnoreCase("White") || ele.getText().equalsIgnoreCase("Gray")) {
                    ele.click();
                }
            });
            js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
            Thread.sleep(3000);
            (oDriver.findElements(By.xpath("//div[@class='ch Of']"))).get(0).click();
            oWebDriverImplicitWait.until(d -> ((JavascriptExecutor) d).executeScript("return (document.readyState === 'complete' || document.readyState === 'interactive')"));
            Thread.sleep(5000);
            oWebDriverWait.until(ExpectedConditions.visibilityOf(oDriver.findElement(By.xpath("//span[text()='Air Zoom Pegasus 36']"))));
            (new Select(oDriver.findElement(By.id("pdp-color-select")))).selectByVisibleText("Black/White/Thunder Grey");
            (new Select(oDriver.findElement(By.id("pdp-size-select")))).selectByVisibleText("10");
            (new Select(oDriver.findElement(By.id("pdp-width-select")))).selectByVisibleText("EE - Wide");
            oDriver.findElement(By.xpath("//button[text()='Add to Cart']")).click();
            Thread.sleep(5000);
            try {
                oDriver.findElement(By.xpath("//button[text()='Proceed to Checkout']")).click();
            } catch (Exception ignored) {

            }
            Thread.sleep(3000);
            oDriver.findElement(By.id("ap_email")).sendKeys("vijaypampana@gmail.com");
            oDriver.findElement(By.id("ap_password")).sendKeys("test12");
            oDriver.findElement(By.id("signInSubmit")).click();
            Thread.sleep(3000);
            Assert.assertEquals(oDriver.findElement(By.xpath("//*[text()='Air Zoom Pegasus 36']/following-sibling::div")).getText(), "Color: Blck/White/Thunder Grey");
            Assert.assertEquals(oDriver.findElement(By.xpath("//*[text()='Air Zoom Pegasus 36']/following-sibling::div/following-sibling::div")).getText(), "Size: 10");
            Assert.assertEquals(oDriver.findElement(By.xpath("//*[text()='Air Zoom Pegasus 36']/following-sibling::div/following-sibling::div/following-sibling::div")).getText(), "Width: EE - Wide");
            System.out.println("test");
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            System.out.println(e.getMessage());
        }
        finally {
            tearDown();
        }

    }

    public void tearDown() {
        if(oDriver!=null) {
            oDriver.close();
        }
    }

    private ChromeOptions getChromeOption() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-extensions");
        options.addArguments("--start-maximized");
        options.addArguments("disable-infobars");
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        return options;
    }

}
