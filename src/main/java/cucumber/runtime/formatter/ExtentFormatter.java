package cucumber.runtime.formatter;

import app.common.Context;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.GherkinKeyword;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import cucumber.api.Result;
import gherkin.pickles.PickleTable;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.util.Base64;
import java.util.List;

public class ExtentFormatter implements ReportFormatter {

    private static ExtentReports extentReports;
    private ExtentHtmlReporter htmlReporter;
    private ExtentTest extentFeature;
    private ExtentTest extentScenario;
    private ExtentTest extentStep;
    public Context context;

    @Override
    public void initialize() {
        context = Context.getInstance();
        extentReports = new ExtentReports();

        //System.info
        setSystemInfo("User", System.getProperty("user.name"));

        File oFile = new File(context.getReports().getReportMeta().getReportName());

        htmlReporter = new ExtentHtmlReporter(oFile);
        htmlReporter.config().setDocumentTitle(context.getReports().getReportMeta().getReportTitle());
        htmlReporter.config().setReportName(context.getReports().getReportMeta().getReportTitle());
        htmlReporter.config().setTheme(Theme.DARK);

        extentReports.attachReporter(htmlReporter);
    }

    public void setSystemInfo(String sKey, String value) {
        if(StringUtils.isNotEmpty(value)) {
            extentReports.setSystemInfo(sKey, value);
        }
    }

    @Override
    public void endExecution() {
        extentReports.flush();
    }

    @Override
    public void addFeature(String sKeyword, String sName, String sDescription, List<String> args) {
        try {
            extentFeature = extentReports.createTest(new GherkinKeyword(sKeyword), StringUtils.isEmpty(sName) ? sKeyword : sName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startTest(String sKeyword, String sName, String sDescription, List<String> oCategory) {
        sKeyword = sKeyword.equalsIgnoreCase("Scenario Outline") ? "Scenario" : sKeyword;
        try {
            extentScenario = extentFeature.createNode(new GherkinKeyword(sKeyword), StringUtils.isEmpty(sName) ? sKeyword : sName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        oCategory.forEach( cat -> extentScenario.assignCategory(cat));      //extentScenario.test.Categories -> Tags will be added to this list
    }

    @Override
    public void endTest(Long duration, Result.Type result, String sErrorMessage) {
        //Not applicable for here
    }

    @Override
    public void testPass(String sReportText) {
        //Not applicable for here
    }

    @Override
    public void testFail(String sReportText) {
        //Not applicable for here
    }

    @Override
    public void testFatal(String sReportText) {
        //Not applicable for here
    }

    @Override
    public void testError(String sReportText) {
        //Not applicable for here
    }

    @Override
    public void testWarning(String sReportText) {
        //Not applicable for here
    }

    @Override
    public void testInfo(String sReportText) {
        //Not applicable for here
    }

    @Override
    public void testDebug(String sReportText) {
        //Not applicable for here
    }

    @Override
    public void testSkip(String sReportText) {
        //Not applicable for here
    }

    @Override
    public void testUnknown(String sReportText) {
        //Not applicable for here
    }

    @Override
    public void testException(String sReportText) {
        //Not applicable for here
    }

    @Override
    public void testException(Exception e) {
        //Not applicable for here
    }

    @Override
    public void startStep(int stepNumber, String sKeyword, String sStepDefinition, String argument) {
        startStep(stepNumber, sKeyword, sStepDefinition);
        stepInfo(argument);
    }

    @Override
    public void startStep(int stepNumber, String sKeyword, String sStepDefinition, PickleTable oTable) {
        startStep(stepNumber, sKeyword, sStepDefinition);
        stepTable(oTable);
    }

    @Override
    public void startStep(int stepNumber, String sKeyword, String sStepDefinition) {
        try {
            extentStep = extentScenario.createNode(new GherkinKeyword(sKeyword), StringUtils.isEmpty(sStepDefinition) ? sKeyword : sStepDefinition);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void endStep(Long Duration, Result.Type result, String sErrorMessage) {
        switch (result) {
            case PASSED:
                if(!context.getReports().getReportMeta().isbStepFailure()) {
                    stepPass(result.name());
                }
                break;
            case FAILED:
                stepFail(sErrorMessage);
                break;
            case SKIPPED:
                stepSkip(result.name());
                break;
            case PENDING:
            case AMBIGUOUS:
            case UNDEFINED:
                stepError(StringUtils.isEmpty(sErrorMessage) ? result.name() : sErrorMessage);
                break;
        }
    }

    @Override
    public void stepPass(String sReportText) {
        extentStep.pass(sReportText);
    }

    @Override
    public void stepFail(String sReportText) {
        extentStep.fail(sReportText);
        stepScreenshot();
    }

    @Override
    public void stepFatal(String sReportText) {
        extentStep.fatal(sReportText);
        stepScreenshot();
    }

    @Override
    public void stepError(String sReportText) {
        extentStep.fatal(sReportText);
        stepScreenshot();
    }

    @Override
    public void stepWarning(String sReportText) {
        extentStep.warning(sReportText);
    }

    @Override
    public void stepInfo(String sReportText) {
        extentStep.info(sReportText);
    }

    @Override
    public void stepDebug(String sReportText) {
        //Not Applicable for here
    }

    @Override
    public void stepSkip(String sReportText) {
        extentStep.skip(sReportText);
    }

    @Override
    public void stepUnknown(String sReportText) {
        //Not Applicable for here
    }

    @Override
    public void stepException(String sReportText) {
        extentStep.fatal(sReportText);
        stepScreenshot();
    }

    @Override
    public void stepException(Exception e) {
        extentStep.fatal(e);
        stepScreenshot();
    }

    @Override
    public void stepCode(String sReportText) {
        extentStep.info(MarkupHelper.createCodeBlock(sReportText));
    }

    @Override
    public void stepLabel(String sReportText) {
        extentStep.info(MarkupHelper.createLabel(sReportText, ExtentColor.PURPLE));
    }

    //This step will capture the screen when a UI Step fails
    @Override
    public void stepScreenshot() {
        if(!context.isAPI()) {
            try {
                byte[] content = ((TakesScreenshot) context.getoWebDriver()).getScreenshotAs(OutputType.BYTES);
                context.getReports().getReportMeta().setScreenshot(content);
                extentStep.addScreenCaptureFromPath("data:image/gif;base64," + Base64.getEncoder().encodeToString(content));
            } catch (Exception e) {
                extentStep.fatal(e.getStackTrace().toString());
            }
        }
    }

    @Override
    public void stepTable(PickleTable oTable) {
        extentStep.info(context.getReports().getReportMeta().getMarkUp(oTable));
    }

}

//public ExtentTest createTest(Class<? extends IGherkinFormatterModel> type, String testName, String description)
