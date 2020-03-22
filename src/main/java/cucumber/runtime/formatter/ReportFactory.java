package cucumber.runtime.formatter;

import cucumber.runtime.formatter.reportPortal.ReportPortalFormatter;

import java.util.ArrayList;
import java.util.List;

public class ReportFactory {

    private List<ReportFormatter> reportFormatters = new ArrayList<>();

    public static ReportFactory getFactory(String type) {
        return new ReportFactory(type);
    }

    public ReportFactory(String type) {
        if(type.equalsIgnoreCase("extent")) {
            getExtentFormatter();
        } else if(type.equalsIgnoreCase("reportportal")) {
            getReportPortalFormatter();
        } else {
            getExtentFormatter();
        }
    }

    public void getExtentFormatter() {
        reportFormatters.add(new ExtentFormatter());
    }

    public void getReportPortalFormatter() {
        reportFormatters.add(new ReportPortalFormatter());
    }

    public List<ReportFormatter> getReportFormatters() {
        return reportFormatters;
    }

    public void setReportFormatters(List<ReportFormatter> reportFormatters) {
        this.reportFormatters = reportFormatters;
    }
}
