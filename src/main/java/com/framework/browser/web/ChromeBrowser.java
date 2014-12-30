package com.framework.browser.web;

public class ChromeBrowser {

	  /**
	   * System property that defines the location of the chromedriver executable that will be used by
	   * the {@link #createDefaultService() default service}.
	   */
	  public static final String CHROME_DRIVER_EXE_PROPERTY = "webdriver.chrome.driver";

	  /**
	   * System property that defines the location of the log that will be written by
	   * the {@link #createDefaultService() default service}.
	   */
	  public final static String CHROME_DRIVER_LOG_PROPERTY = "webdriver.chrome.logfile";

	  /**
	   * Boolean system property that defines whether the ChromeDriver executable should be started
	   * with verbose logging.
	   */
	  public static final String CHROME_DRIVER_VERBOSE_LOG_PROPERTY =
	      "webdriver.chrome.verboseLogging";

	  ChromeBrowser(){
		  /*
		  this.webDriverPath = webDriverPath;
	      this.browserBinaryPath = browserBinaryPath;
	      this.browserVersion = browserVersion;
	      this.browserLocale = browserLocale;
	      this.startWindowWidth = startWindowWidth;
	      this.startWindowHeight = startWindowHeight;
	      this.browserLogLevel = browserLogLevel;
	      this.browserLogFile = browserLogFile;
	      */
	  }
}
