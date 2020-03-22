package app.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import cucumber.api.Result;
import gherkin.pickles.PickleTable;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.List;

public class ExtentFormatter implements ReportFormatter {

    private static ExtentReports extentReports;
    private ExtentHtmlReporter htmlReporter;
    private ExtentTest extentFeature;
    private ExtentTest extentScenario;
    private ExtentTest extentStep;

    @Override
    public void initialize() {
        extentReports = new ExtentReports();

        //System.info
        setSystemInfo("User", System.getProperty("user.name"));

        File oFile = new File("TBD");

        htmlReporter = new ExtentHtmlReporter(oFile);


    }

    public void setSystemInfo(String sKey, String value) {
        if(StringUtils.isNotEmpty(value)) {
            extentReports.setSystemInfo(sKey, value);
        }
    }

    @Override
    public void endExecution() {

    }

    @Override
    public void addFeature(String sKeyword, String sName, String sDescription, List<String> args) {

    }

    @Override
    public void startTest(String sKeyword, String sName, String sDescription, List<String> oCategory) {

    }

    @Override
    public void endTest(Long duration, Result.Type result, String sErrorMessage) {

    }

    @Override
    public void testPass(String sReportText) {

    }

    @Override
    public void testFail(String sReportText) {

    }

    @Override
    public void testFatal(String sReportText) {

    }

    @Override
    public void testError(String sReportText) {

    }

    @Override
    public void testWarning(String sReportText) {

    }

    @Override
    public void testInfo(String sReportText) {

    }

    @Override
    public void testDebug(String sReportText) {

    }

    @Override
    public void testSkip(String sReportText) {

    }

    @Override
    public void testUnknown(String sReportText) {

    }

    @Override
    public void testException(String sReportText) {

    }

    @Override
    public void testException(Exception e) {

    }

    @Override
    public void startStep(int stepNumber, String sKeyword, String sStepDefinition, String argument) {

    }

    @Override
    public void startStep(int stepNumber, String sKeyword, String sStepDefinition, PickleTable oTable) {

    }

    @Override
    public void startStep(int stepNumber, String sKeyword, String sStepDefinition) {

    }

    @Override
    public void stopStep(Long Duration, Result.Type result, String sErrorMessage) {

    }

    @Override
    public void stepPass(String sReportText) {

    }

    @Override
    public void stepFail(String sReportText) {

    }

    @Override
    public void stepFatal(String sReportText) {

    }

    @Override
    public void stepError(String sReportText) {

    }

    @Override
    public void stepWarning(String sReportText) {

    }

    @Override
    public void stepInfo(String sReportText) {

    }

    @Override
    public void stepDebug(String sReportText) {

    }

    @Override
    public void stepSkip(String sReportText) {

    }

    @Override
    public void stepUnknown(String sReportText) {

    }

    @Override
    public void stepException(String sReportText) {

    }

    @Override
    public void stepException(Exception e) {

    }

    @Override
    public void stepCode(String sReportText) {

    }

    @Override
    public void stepLabel(String sReportText) {

    }

    @Override
    public void stepScreenshot() {

    }

    @Override
    public void stepTable(PickleTable oTable) {

    }

}

//public ExtentTest createTest(Class<? extends IGherkinFormatterModel> type, String testName, String description)
