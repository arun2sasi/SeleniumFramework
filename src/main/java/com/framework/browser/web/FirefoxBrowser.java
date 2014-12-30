package com.framework.browser.web;

import java.io.File;

import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.google.common.base.Optional;

//import com.jivesoftware.selenium.pagefactory.framework.config.TimeoutsConfig;

public class FirefoxBrowser {

	/*
	
	public FirefoxBrowser(TimeoutsConfig timeoutsConfig,
			Optional<String> webDriverPath, Optional<String> browserBinaryPath,
			Optional<String> browserVersion, Optional<String> browserLocale,
			Optional<Integer> startWindowWidth,
			Optional<Integer> startWindowHeight) {

	}
	
	// Code to print Browser version/name
		WebDriver driver = new FirefoxDriver();
		Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = caps.getBrowserName();
		String browserVersion = caps.getVersion();
		System.out.println(browserName+" "+browserVersion); 
	
    public DesiredCapabilities getDesiredCapabilities() {
        DesiredCapabilities desiredCapabilities = DesiredCapabilities.firefox();
        desiredCapabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);

        FirefoxProfile profile = new FirefoxProfile();
        profile.setEnableNativeEvents(true);
        desiredCapabilities.setCapability(FirefoxDriver.PROFILE, profile);

        // If the browerBinaryPath is present, and it points to a real file, then set this as the Firefox Binary
        Optional<String> browserBinaryPath = getBrowserBinaryPath();
        if (browserBinaryPath.isPresent() && !browserBinaryPath.get().isEmpty()) {
            final String browserBinaryPathStr = browserBinaryPath.get();
            File file = new File(browserBinaryPathStr);
            if (file.exists()) {
                desiredCapabilities.setCapability(FirefoxDriver.BINARY, new FirefoxBinary(file));
            }
        }

        // If a required version is present, then set this as a desired capability.
        // Only affects remote browsers
        Optional<String> browserVersion = getBrowserVersion();
        if (browserVersion.isPresent() && !browserVersion.get().isEmpty()) {
            desiredCapabilities.setCapability(CapabilityType.VERSION, browserVersion.get());
        }

        LoggingPreferences loggingPreferences = getLoggingPreferences();
        desiredCapabilities.setCapability(CapabilityType.LOGGING_PREFS, loggingPreferences);

        return desiredCapabilities;
    }
    
    */
}
