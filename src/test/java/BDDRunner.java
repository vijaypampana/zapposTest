import app.common.Context;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

@CucumberOptions(
        glue = {"app"},
        features = "src/test/Resources/features",
        plugin = {},
        tags = {"@zappos"}
)
public class BDDRunner extends AbstractTestNGCucumberTests {

    @BeforeClass(alwaysRun = true)
    public void setup() {

    }

    @AfterClass(alwaysRun = true)
    public void tearDown(){
        Context.getInstance().closeDriver();
    }
}
