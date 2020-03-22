package cucumber.runtime.formatter;

import app.common.Context;
import cucumber.api.Result;
import gherkin.pickles.PickleTable;
import org.testng.Assert;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReportDriver implements ReportFormatter {

    private List<ReportFormatter> reportFormatters = new ArrayList<>();
    private ReportMeta reportMeta;
    private Context context;

    public ReportDriver(String type) {
        super();
        context = Context.getInstance();
        reportMeta = new ReportMeta();
        reportMeta.setReportName(setLocalReportPath());
        setReportFormatters(ReportFactory.getFactory(type).getReportFormatters());
    }

    public String setLocalReportPath() {
        String sTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH_mm_ss"));
        return getReporDirectoryName().concat(File.separator).concat(sTime+"__").concat(context.isAPI() ? "API.html" : "UI.html");
    }

    public String getReporDirectoryName() {
        String sDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MMM_yyyy"));
        return "reports".concat(File.separator).concat("Run_" + sDate);
    }

    public List<ReportFormatter> getReportFormatters() {
        return reportFormatters;
    }

    public void setReportFormatters(List<ReportFormatter> reportFormatters) {
        this.reportFormatters = reportFormatters;
    }

    public ReportMeta getReportMeta() {
        return reportMeta;
    }

    public void setReportMeta(ReportMeta reportMeta) {
        this.reportMeta = reportMeta;
    }

    @Override
    public void initialize() {
        this.reportFormatters.forEach( formatter -> formatter.initialize());
    }

    @Override
    public void endExecution() {
        this.reportFormatters.forEach( formatter -> formatter.endExecution());
    }

    @Override
    public void addFeature(String sKeyword, String sName, String sDescription, List<String> tags) {
        this.reportFormatters.forEach( formatter -> formatter.addFeature(sKeyword, sName, sDescription, tags));
    }

    @Override
    public void startTest(String sKeyword, String sName, String sDescription, List<String> oCategory) {
        this.reportFormatters.forEach( formatter -> formatter.startTest(sKeyword, sName, sDescription, oCategory));
    }

    @Override
    public void endTest(Long duration, Result.Type result, String sErrorMessage) {
        switch (result) {
            case PASSED:
                if(reportMeta.isbTestFailure()) {
                    testFail(sErrorMessage);
                } else {
                    testPass(result.name());
                }
                break;
            case FAILED:
                testFail(sErrorMessage);
                break;
            case SKIPPED:
                testSkip(result.name());
                reportMeta.setbTestSkipped(true);
                break;
            case PENDING:
            case AMBIGUOUS:
                testError(sErrorMessage);
                break;
            case UNDEFINED:
                testError(result.name());
                break;
        }

        reportFormatters.forEach( formatter -> formatter.endTest(duration, result, sErrorMessage));

        if(reportMeta.isbTestFailure()) {
            Assert.fail("Test Case Failed");        //This line is just to fail the test case
        }

    }

    @Override
    public void testPass(String sReportText) {
        this.reportFormatters.forEach( formatter -> formatter.testPass(sReportText));
    }

    @Override
    public void testFail(String sReportText) {
        this.reportFormatters.forEach( formatter -> formatter.testFail(sReportText));
    }

    @Override
    public void testFatal(String sReportText) {
        this.reportFormatters.forEach( formatter -> formatter.testFatal(sReportText));
    }

    @Override
    public void testError(String sReportText) {
        this.reportFormatters.forEach( formatter -> formatter.testError(sReportText));
    }

    @Override
    public void testWarning(String sReportText) {
        this.reportFormatters.forEach( formatter -> formatter.testWarning(sReportText));
    }

    @Override
    public void testInfo(String sReportText) {
        this.reportFormatters.forEach( formatter -> formatter.testInfo(sReportText));
    }

    @Override
    public void testDebug(String sReportText) {
        this.reportFormatters.forEach( formatter -> formatter.testDebug(sReportText));
    }

    @Override
    public void testSkip(String sReportText) {
        this.reportFormatters.forEach( formatter -> formatter.testSkip(sReportText));
    }

    @Override
    public void testUnknown(String sReportText) {
        this.reportFormatters.forEach( formatter -> formatter.testUnknown(sReportText));
    }

    @Override
    public void testException(String sReportText) {
        this.reportFormatters.forEach( formatter -> formatter.testException(sReportText));
    }

    @Override
    public void testException(Exception e) {
        this.reportFormatters.forEach( formatter -> formatter.testException(e));
    }

    @Override
    public void startStep(int stepNumber, String sKeyword, String sStepDefinition, String argument) {
        reportMeta.setbStepFailure(false);
        this.reportFormatters.forEach( formatter -> formatter.startStep(stepNumber, sKeyword, sStepDefinition, argument));
    }

    @Override
    public void startStep(int stepNumber, String sKeyword, String sStepDefinition, PickleTable oTable) {
        reportMeta.setbStepFailure(false);
        this.reportFormatters.forEach( formatter -> formatter.startStep(stepNumber, sKeyword, sStepDefinition, oTable));
    }

    @Override
    public void startStep(int stepNumber, String sKeyword, String sStepDefinition) {
        reportMeta.setbStepFailure(false);
        this.reportFormatters.forEach( formatter -> formatter.startStep(stepNumber, sKeyword, sStepDefinition));
    }

    @Override
    public void endStep(Long duration, Result.Type result, String sErrorMessage) {
        this.reportFormatters.forEach( formatter -> formatter.endStep(duration, result, sErrorMessage));
    }

    @Override
    public void stepPass(String sReportText) {
        this.reportFormatters.forEach( formatter -> formatter.stepPass(sReportText));
    }

    @Override
    public void stepFail(String sReportText) {
        reportMeta.setbStepFailure(true);
        reportMeta.setbTestFailure(true);
        this.reportFormatters.forEach( formatter -> formatter.stepFail(sReportText));
    }

    @Override
    public void stepFatal(String sReportText) {
        reportMeta.setbStepFailure(true);
        reportMeta.setbTestFailure(true);
        this.reportFormatters.forEach( formatter -> formatter.stepFatal(sReportText));
    }

    @Override
    public void stepError(String sReportText) {
        reportMeta.setbStepFailure(true);
        reportMeta.setbTestFailure(true);
        this.reportFormatters.forEach( formatter -> formatter.stepError(sReportText));
    }

    @Override
    public void stepWarning(String sReportText) {
        this.reportFormatters.forEach( formatter -> formatter.stepWarning(sReportText));
    }

    @Override
    public void stepInfo(String sReportText) {
        this.reportFormatters.forEach( formatter -> formatter.stepInfo(sReportText));
    }

    @Override
    public void stepDebug(String sReportText) {
        this.reportFormatters.forEach( formatter -> formatter.stepDebug(sReportText));
    }

    @Override
    public void stepSkip(String sReportText) {
        reportMeta.setbStepFailure(true);
        reportMeta.setbTestFailure(true);
        this.reportFormatters.forEach( formatter -> formatter.stepSkip(sReportText));
    }

    @Override
    public void stepUnknown(String sReportText) {
        reportMeta.setbStepFailure(true);
        reportMeta.setbTestFailure(true);
        this.reportFormatters.forEach( formatter -> formatter.stepUnknown(sReportText));
    }

    @Override
    public void stepException(String sReportText) {
        reportMeta.setbStepFailure(true);
        reportMeta.setbTestFailure(true);
        this.reportFormatters.forEach( formatter -> formatter.stepException(sReportText));
    }

    @Override
    public void stepException(Exception e) {
        stepException(e.getMessage());
    }

    @Override
    public void stepCode(String sReportText) {
        this.reportFormatters.forEach( formatter -> formatter.stepCode(sReportText));
    }

    @Override
    public void stepLabel(String sReportText) {
        this.reportFormatters.forEach( formatter -> formatter.stepLabel(sReportText));
    }

    @Override
    public void stepScreenshot() {
        this.reportFormatters.forEach( formatter -> formatter.stepScreenshot());
    }

    @Override
    public void stepTable(PickleTable oTable) {
        this.reportFormatters.forEach( formatter -> formatter.stepTable(oTable));
    }
}
