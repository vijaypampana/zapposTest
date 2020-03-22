package app.reports;

import java.util.List;

public class ReportMeta {

    private String reportName;
    private String reportTitle;

    private boolean bTestFailure;
    private boolean bTestSkipped;

    private boolean bStepFailure;

    private String currentFeatureFile;
    private boolean isNewFeatureFile;

    private List<String> featureFlags;

    private byte[] screenshot;

    private int currentStepNumber;

    void initializeStep() {
        setCurrentStepNumber(0);
    }

    void incrementStepNumber() {
        setCurrentStepNumber(getCurrentStepNumber() + 1);
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public boolean isbTestFailure() {
        return bTestFailure;
    }

    public void setbTestFailure(boolean bTestFailure) {
        this.bTestFailure = bTestFailure;
    }

    public boolean isbTestSkipped() {
        return bTestSkipped;
    }

    public void setbTestSkipped(boolean bTestSkipped) {
        this.bTestSkipped = bTestSkipped;
    }

    public boolean isbStepFailure() {
        return bStepFailure;
    }

    public void setbStepFailure(boolean bStepFailure) {
        this.bStepFailure = bStepFailure;
    }

    public String getCurrentFeatureFile() {
        return currentFeatureFile;
    }

    public void setCurrentFeatureFile(String currentFeatureFile) {
        this.currentFeatureFile = currentFeatureFile;
    }

    public boolean isNewFeatureFile() {
        return isNewFeatureFile;
    }

    public void setNewFeatureFile(boolean newFeatureFile) {
        isNewFeatureFile = newFeatureFile;
    }

    public List<String> getFeatureFlags() {
        return featureFlags;
    }

    public void setFeatureFlags(List<String> featureFlags) {
        this.featureFlags = featureFlags;
    }

    public byte[] getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(byte[] screenshot) {
        this.screenshot = screenshot;
    }

    public int getCurrentStepNumber() {
        return currentStepNumber;
    }

    public void setCurrentStepNumber(int currentStepNumber) {
        this.currentStepNumber = currentStepNumber;
    }
}
