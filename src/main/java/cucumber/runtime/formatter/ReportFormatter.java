package cucumber.runtime.formatter;

import cucumber.api.Result;
import gherkin.pickles.PickleTable;

import java.util.List;

public interface ReportFormatter {

    void initialize();
    void endExecution();
    
    void addFeature(String sKeyword, String sName, String sDescription, List<String> args);

    void startTest(String sKeyword, String sName, String sDescription, List<String> oCategory);
    void endTest(Long duration, Result.Type result, String sErrorMessage);
    
    void testPass(String sReportText);
    void testFail(String sReportText);
    void testFatal(String sReportText);
    void testError(String sReportText);
    void testWarning(String sReportText);
    void testInfo(String sReportText);
    void testDebug(String sReportText);
    void testSkip(String sReportText);
    void testUnknown(String sReportText);
    void testException(String sReportText);
    void testException(Exception e);
    
    void startStep(int stepNumber, String sKeyword, String sStepDefinition, String argument);
    void startStep(int stepNumber, String sKeyword, String sStepDefinition, PickleTable oTable);
    void startStep(int stepNumber, String sKeyword, String sStepDefinition);
    void endStep(Long Duration, Result.Type result, String sErrorMessage);

    void stepPass(String sReportText);
    void stepFail(String sReportText);
    void stepFatal(String sReportText);
    void stepError(String sReportText);
    void stepWarning(String sReportText);
    void stepInfo(String sReportText);
    void stepDebug(String sReportText);
    void stepSkip(String sReportText);
    void stepUnknown(String sReportText);
    void stepException(String sReportText);
    void stepException(Exception e);

    void stepCode(String sReportText);
    void stepLabel(String sReportText);
    void stepScreenshot();

    void stepTable(PickleTable oTable);




}
