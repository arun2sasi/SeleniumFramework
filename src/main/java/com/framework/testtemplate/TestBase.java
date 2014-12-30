package com.framework.testtemplate;

import static com.framework.util.BrowserType.FIREFOX;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.FileAppender;
import org.apache.log4j.PatternLayout;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;

import com.framework.exception.SeleniumException;
import com.framework.report.CaptureBrowserScreenShot;
import com.framework.report.DetailedLogs;
import com.framework.util.BrowserType;
import com.framework.util.CommonFunctionLib;
import com.framework.util.Reader;

/**
 * TestBase.java is inherited by all test classes & contains method to setup,
 * teardown, load test data, set capabilities etc.
 * 
 * @author Abhay Bharti
 * 
 */
public class TestBase {

	private static ResourceBundle _prop = ResourceBundle.getBundle("Selenium");
	private static String browserType;
	protected WebDriver driver;
	Properties TestExecution = new Properties();
	Properties properties;
	
	
	
	public DetailedLogs AppLogs = new DetailedLogs();
	
	protected TestBase() throws SeleniumException{
		FileInputStream fs;
		try {
			fs = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\TestExecution.properties");
		} catch (FileNotFoundException e) {
			throw new SeleniumException("Error in connecting with Remote WebDriver"+e);
		}
		
		try {
			TestExecution.load(fs);
		} catch (IOException e) {
			throw new SeleniumException("Error in connecting with Remote WebDriver"+e);
		}
	}
	
	//public Logger AppLogs;
	//public static Logger AppLogs = Logger.getLogger("devpinoyLogger");
	Reader xls = new Reader();

	CaptureBrowserScreenShot captureBrowserScreenShot = new CaptureBrowserScreenShot();

	/**
	 * Purpose : This method starts browser on available node & connect wih HUB
	 * 
	 * @param hubAddress
	 * @throws MalformedURLException
	 * @throws SeleniumException
	 */
	public void startWebDriver(@Optional("localhost") String hubAddress)
			throws MalformedURLException, SeleniumException {
		initPropertiesFile();
		
		String BrowserList [] = TestExecution.getProperty("Browser").split(":");
	    for(String browser : BrowserList)
	    {		
	    	if (browser == null) {
	    			AppLogs.error("Unknown browser specified, defaulting to 'Firefox'...");
	    			browserType = "Firefox";
	    	}else {
	    		browserType = browser.toUpperCase();
	    	}
	    	try{
				if (driver == null) {
					AppLogs.info("startDriver starts..");
					driver = new RemoteWebDriver(new URL("http://" + hubAddress + ":" + "4444/wd/hub"), generateDesiredCapabilities(browserType));
					AppLogs.debug("hubAddress : " + hubAddress + "browserType : "+ browserType);
					driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					driver.manage().window().maximize();
					driver.get(properties.getProperty("BaseURL"));
					AppLogs.info("startDriver ends..");
				}
			}catch(MalformedURLException e){
				System.out.println("Error in Connecting with HUB URL  "+"http://" + hubAddress + ":" + "4444/wd/hub");
				throw new SeleniumException("Error in connecting with Remote WebDriver"+e);
			}catch(Exception e){
				throw new SeleniumException("Error in connecting with Remote WebDriver"+e);
			}
	    }
	}


	/**
	 * Purpose : This method close/quit driver once test class execution is
	 * complete
	 */
	@AfterClass(alwaysRun = true)
	public void stopDriver() {
		AppLogs.info("stopDriver starts..");
		if (driver != null) {
			driver.quit();
			driver = null;
		}

		AppLogs.info("stopDriver ends..");
	}

	/**
	 * Purpose : This method reads data from TestDate file for respective test
	 * case
	 * 
	 * @param testName
	 * @return
	 */
	@DataProvider
	public Object[][] readData(Method testName) {
		AppLogs.info("readData starts..");
		return CommonFunctionLib.readData(testName.getName(), xls);
	}

	/**
	 * Purpose : This method loads browser enum from .properties file &
	 * BrowserType class before suite execution starts
	 */
	@BeforeSuite
	public void setUpTest() {
		AppLogs.info("setUpTest starts..");
		String BrowserList [] = TestExecution.getProperty("Browser").split(":");
		for (int browserLoop=0; browserLoop<BrowserList.length; browserLoop++){
			System.out.println(BrowserList[browserLoop]);
			browserType = BrowserList[browserLoop].toUpperCase();
		}
		
		/*
		if (browserType == null) {
			AppLogs.error("Unknown browser specified, defaulting to 'Firefox'...");
			browserType = "Firefox";
		}*/
		AppLogs.info("setUpTest ends..");
	}

	/**
	 * Purpose : This method performs tearDown after a test case execution &
	 * takes browser screen shot in case of test fail
	 * 
	 * @param result
	 * @throws SeleniumException
	 * @throws IOException
	 */
	@AfterMethod(alwaysRun = true)
	public void TearDown(ITestResult result, Method testName) throws SeleniumException, IOException {
		AppLogs.info("-------------" + testName.getName()+ " ---------- TearDown starts..");
		if (!result.isSuccess()) {
			captureBrowserScreenShot.embedScreenShotIntoReport(driver);
		}
		if (!(driver.getCurrentUrl() == properties.getProperty("BaseURL"))) {
			driver.get(properties.getProperty("BaseURL"));
		}
		AppLogs.info("-------------" + testName.getName()	+ " ---------- TearDown ends..");
	}

	/**
	 * Purpose : This method set capability in browser
	 * 
	 * @param capabilityType
	 * @return
	 */
	private DesiredCapabilities generateDesiredCapabilities(String capabilityType) {
		AppLogs.info("generateDesiredCapabilities starts.. "+capabilityType);
		DesiredCapabilities capabilities=null;

		switch (capabilityType) {
		case "IE":
			capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(
					CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION,
					true);
			capabilities.setCapability(
					InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
			capabilities.setCapability("requireWindowFocus", true);
			capabilities.setBrowserName("iexplore");
			capabilities.setPlatform(org.openqa.selenium.Platform.WINDOWS);
			capabilities.setCapability("takesScreenShot", true);
			System.setProperty("webdriver.ie.driver",System.getProperty("user.dir")+"\\Binary\\IEDriverServer.exe");
			// capability.setVersion("");
			break;
		case "SAFARI":
			capabilities = DesiredCapabilities.safari();
			capabilities.setCapability("safari.cleanSession", true);
			capabilities.setCapability("takesScreenShot", true);
			// capability.setVersion("");
			break;
		case "OPERA":
			capabilities = DesiredCapabilities.opera();
			capabilities.setCapability("opera.arguments", "-nowin -nomail");
			capabilities.setCapability("takesScreenShot", true);
			// capability.setVersion("");
			break;
		case "CHROME":
			capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability("chrome.switches",
					Arrays.asList("--no-default-browser-check"));
			HashMap<String, String> chromePreferences = new HashMap<String, String>();
			chromePreferences.put("profile.password_manager_enabled", "false");
			capabilities.setCapability("chrome.prefs", chromePreferences);
			capabilities.setCapability("takesScreenShot", true);
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\Binary\\chromedriver.exe"); 
			// capability.setVersion("");
			break;
		case "FIREFOX":
			capabilities = DesiredCapabilities.firefox();
			capabilities.setBrowserName("firefox");
			capabilities.setCapability("takesScreenShot", true);
			capabilities.setCapability("acceptSSLCerts", true);
			capabilities.setPlatform(org.openqa.selenium.Platform.ANY);
			// capability.setVersion("");
			break;
		case "HTMLUNIT":
			break;
		default:
			capabilities = DesiredCapabilities.htmlUnit();
			capabilities.setCapability("javascriptEnabled", "true");
		}
		AppLogs.info("generateDesiredCapabilities ends.. "+capabilityType);
		return capabilities;
	}

	private DesiredCapabilities generateAndroidDesiredCapabilities(String Device) {
		AppLogs.info("generateAndroidDesiredCapabilities starts..");
		DesiredCapabilities capabilities = null;
		if (Device.equalsIgnoreCase("ANDROID")) {
			capabilities = new DesiredCapabilities();
			capabilities.setCapability("device",properties.getProperty("device"));
			capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
			capabilities.setCapability("deviceName",properties.getProperty("deviceName"));
			capabilities.setCapability("platformName",properties.getProperty("device"));
			capabilities.setCapability(CapabilityType.VERSION,properties.getProperty("platformVersion"));
			capabilities.setCapability("app", properties.getProperty("app"));
			capabilities.setCapability("appPackage",properties.getProperty("appPackage"));
			capabilities.setCapability("appActivity",properties.getProperty("appActivity"));
			capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION,true);
			AppLogs.info(properties.getProperty("device")+" , "+properties.getProperty("deviceName")+" , "+properties.getProperty("platformVersion")+" , "+properties.getProperty("app")+" , "+properties.getProperty("appPackage")+" , "+properties.getProperty("appActivity"));
		}
		AppLogs.info("generateAndroidDesiredCapabilities ends..");
		return capabilities;
	}

	/**
	 * Purpose : Loads .propertis file
	 * 
	 * @throws SeleniumException
	 */
	private void initPropertiesFile() throws SeleniumException {
		properties = new Properties();
		try {
			FileReader MobileDevicereader = new FileReader(
					System.getProperty("user.dir")
							+ "//src//test//resources//MobileDevice.properties");
			properties.load(MobileDevicereader);
			FileReader Seleniumreader = new FileReader(
					System.getProperty("user.dir")
							+ "//src//test//resources//Selenium.properties");
			properties.load(Seleniumreader);
		} catch (IOException e) {
			throw new SeleniumException("Failed to load Properties file" + e);
		}
	}
	
	//run this single time before a group
	@BeforeGroups("shopping")
	public void beforeGroups() {
		System.out.println("@BeforeGroups");
	}

	//run this single time after a group
	@AfterGroups("shopping")
	public void afterGroups() {
		System.out.println("@AfterGroups");
	}
	
	public void initLogs(Class<?> class1){
		//To be done
		
		FileAppender appender = new FileAppender();
		//Configure the appender here, with file location pic
		appender.setFile(System.getProperty("user.dir")+"\\logs\\"+class1.getName()+".log");
		appender.setLayout(new PatternLayout("%d %-5p[%c{1}] %m%n"));
		appender.setAppend(false);
		appender.activateOptions();
		
		//AppLogs = Logger.getLogger(class1);
		//AppLogs.setLevel(Level.INFO);
		//AppLogs.addAppender(appender);
	}
}

/**
 * Command to launch Hub & Node java -jar selenium-server-standalone-2.42.1.jar
 * -role hub -port 4444
 * 
 * java -jar selenium-server-standalone-2.42.1.jar -role node -hub
 * http://localhost:4444/grid/register -port 5555 -browser browserName=firefox
 * -maxSession 1
 * 
 * java -jar selenium-server-standalone-2.42.1.jar -role node -hub http://localhost:4444/grid/register -port 5555 -browser browserName=firefox, -browser browserName=chrome, -browser browserName=iexplore -maxSession 1 java -jar selenium-server-standalone-2.42.1.jar -role node -hub http://localhost:4444/grid/register -port 5556 -browser browserName=firefox  -maxSession 1
 * 
 */