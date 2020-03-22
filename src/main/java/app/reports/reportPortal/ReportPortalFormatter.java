package app.reports.reportPortal;

import app.reports.ReportFormatter;
import cucumber.api.Result;
import gherkin.pickles.PickleTable;

import java.util.List;

public class ReportPortalFormatter implements ReportFormatter {

    //TBD

    @Override
    public void initialize() {

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
    public void endStep(Long Duration, Result.Type result, String sErrorMessage) {

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
