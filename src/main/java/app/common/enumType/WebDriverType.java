package app.common.enumType;

public enum WebDriverType {

    API(""),
    ZALENIUM("http://localhost:4444/wd/hub"),
    APPIUM("http://localhost:4723/wd/hub"),
    SELENIUMLOCAL("");

    private String sUrl;
    WebDriverType(String sUrl) {
        this.sUrl = sUrl;
    }

    public String getsUrl() {
        return sUrl;
    }
}
