package app.reports;

import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import gherkin.pickles.PickleTable;

import java.util.List;

public class ReportMeta {

    private String reportName;
    private String reportTitle;

    private boolean bTestFailure;
    private boolean bTestSkipped;

    private boolean bStepFailure;

    private String currentFeatureFile;
    private boolean isNewFeatureFile;

    private List<String> featureTags;

    private byte[] screenshot;

    private int currentStepNumber;

    private String[][] markUpTable;
    Integer iRow = 0, iColumn = 0;

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

    public List<String> getFeatureTgs() {
        return featureTags;
    }

    public void setFeatureTags(List<String> featureTags) {
        this.featureTags = featureTags;
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

    //get MarkUp Table from a Pickle Table
    public Markup getMarkUp(PickleTable oTable) {
        iRow = 0;
        iColumn = 0;
        //We are not initializing markup Table here as we dont know the column size. Also a instance variable is created so that we can modify its value in forEach loop
        markUpTable = null;
        oTable.getRows().forEach( row -> {
            row.getCells().forEach( cell -> {
                if (markUpTable == null) {
                    markUpTable = new String[oTable.getRows().size()][row.getCells().size()];
                }
                markUpTable[iRow][iColumn] = cell.getValue();
                iColumn++;
            });
            iRow++;
            iColumn = 0;
        });
        return MarkupHelper.createTable(markUpTable);
    }

    //Very specific to ReportPortal
    public String buildMultiLineArgument(PickleTable oTable) {
        //TBD
        return null;
    }
}
