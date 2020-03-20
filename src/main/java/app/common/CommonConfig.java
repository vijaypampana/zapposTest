package app.common;

import app.common.enumType.BrowserType;
import app.common.enumType.WebDriverType;

public class CommonConfig {

    private WebDriverType webDriverType;
    private Integer webDriverTimeout;
    private WebDriverAuthentication webDriverAuthentication;
    private String applicationType;
    private String applicationEnvironment;
    private Capability capability;

    public WebDriverType getWebDriverType() {
        return webDriverType;
    }

    public void setWebDriverType(WebDriverType webDriverType) {
        this.webDriverType = webDriverType;
    }

    public Integer getWebDriverTimeout() {
        return webDriverTimeout;
    }

    public void setWebDriverTimeout(Integer webDriverTimeout) {
        this.webDriverTimeout = webDriverTimeout;
    }

    public WebDriverAuthentication getWebDriverAuthentication() {
        return webDriverAuthentication;
    }

    public void setWebDriverAuthentication(WebDriverAuthentication webDriverAuthentication) {
        this.webDriverAuthentication = webDriverAuthentication;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getApplicationEnvironment() {
        return applicationEnvironment;
    }

    public void setApplicationEnvironment(String applicationEnvironment) {
        this.applicationEnvironment = applicationEnvironment;
    }

    public Capability getCapability() {
        return capability;
    }

    public void setCapability(Capability capability) {
        this.capability = capability;
    }

    //WebDriver Authentication class with two variables
    public class WebDriverAuthentication {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public class Capability {

        private String perfectoSessionID;
        private String automationName;
        private BrowserType browserName;
        private String app;
        private String deviceName;
        private String platformName;
        private String platformVersion;

        public String getPerfectoSessionID() {
            return perfectoSessionID;
        }

        public void setPerfectoSessionID(String perfectoSessionID) {
            this.perfectoSessionID = perfectoSessionID;
        }

        public String getAutomationName() {
            return automationName;
        }

        public void setAutomationName(String automationName) {
            this.automationName = automationName;
        }

        public BrowserType getBrowserName() {
            return browserName;
        }

        public void setBrowserName(BrowserType browserName) {
            this.browserName = browserName;
        }

        public String getApp() {
            return app;
        }

        public void setApp(String app) {
            this.app = app;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public String getPlatformName() {
            return platformName;
        }

        public void setPlatformName(String platformName) {
            this.platformName = platformName;
        }

        public String getPlatformVersion() {
            return platformVersion;
        }

        public void setPlatformVersion(String platformVersion) {
            this.platformVersion = platformVersion;
        }
    }
}
