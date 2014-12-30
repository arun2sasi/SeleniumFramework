/**
 * 
 */
package com.framework.util;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.framework.report.CaptureBrowserScreenShot;
import com.framework.report.DetailedLogs;
import com.google.common.base.Optional;

/**
 * CommonFunctionLib.java contains general purpose functions
 * 
 * @author Abhay Bharti
 * 
 */
public class CommonFunctionLib {
	public WebDriver driver;
	WebDriverWait wait;
	Properties properties;
	DesiredCapabilities objCapabilities;
	ChromeOptions chromeoptions;
	DetailedLogs AppLogs = new DetailedLogs();
	/**
	 * These objects are made non-static. The objects of this class are
	 * re-created at some point of time.
	 */
	public Set<String> arrKnownBrowserHwnd; // Stores windows handle when
											// launching a new browser
	public String hwndFirstWindow; // This will store handle of original window
	public String hwndMostRecentWindow; // This will store handle of most
										// recently known window
	public Boolean locationServiceEnabled;
	public Boolean doFullReset;

	/**
	 * Purpose : Constructor with WebDriver argument
	 * 
	 * @param driver
	 */
	public CommonFunctionLib(WebDriver driver) {
		this.driver = driver;
		locationServiceEnabled = Boolean.parseBoolean(properties
				.getProperty("locationServiceEnabled").trim().toLowerCase());
		doFullReset = true;
	}

	/**
	 * Purpose : Constructor with no argument
	 */
	public CommonFunctionLib() {

	}



	/**
	 * Purpose : Set implicit wait to dirver object
	 * 
	 * @param time
	 */
	public void WebdriverWaitForPage(String time) {
		driver.manage().timeouts()
				.implicitlyWait(Long.parseLong(time), TimeUnit.SECONDS);
	}

	/**
	 * Purpose : set global timeout for page
	 */
	public void WebdriverWaitForPage() {
		WebdriverWaitForPage(properties.getProperty("globalTimeOut"));
	}

	/**
	 * Purpose : set focus to newly opened browser
	 */
	public void SetWindowHandles() {
		try {
			Thread.sleep(3500);
		} catch (InterruptedException e1) {
		}
		try {
			// Retrieve handles for all opened browser windows
			arrKnownBrowserHwnd = driver.getWindowHandles();
			if (arrKnownBrowserHwnd.size() >= 1) {
				for (String winHandle : arrKnownBrowserHwnd) {
					hwndMostRecentWindow = winHandle; // Set variable value to
														// the newly opened
														// window
				}
			} else if (arrKnownBrowserHwnd.size() == 0) {

				// No window appeared
				hwndMostRecentWindow = null;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	 /**
	 * Purpose : swithc to most recent browser
	 * @param windowTitle
	 * @return
	 */
	public String switchToMostRecentBrowser(String windowTitle) {
		try {
			SetWindowHandles();
			if (windowTitle.isEmpty()) {
				driver.switchTo().window(hwndMostRecentWindow);
				// If its IE browser and locally running, set focus as well
			if (properties.getProperty("seleniumGrid").toLowerCase().trim()
					.contains("false")) {
					activateCurrentBrowserWindow();
				}
			} else {
				windowTitle.trim().toLowerCase();
				for (String winHandle : arrKnownBrowserHwnd) {
					if (driver.switchTo().window(winHandle).getTitle().trim()
							.toLowerCase().contains(windowTitle)) {
						WebdriverWaitForPage();
						break;
					}
				}
			}
		} catch (Exception e) {
			return null;
		}
		return hwndMostRecentWindow;
	}

	public String activateCurrentBrowserWindow() {
		// Try to switch to most recent browser window, if require
		return hwndMostRecentWindow;
	}

	public String GetElementAttributeValue(WebElement objWebElement,
			String attribute) {
		return attribute;
		/*
		 * try {
		 * CommonVariables.CurrentTestCaseLog.info("Info: Get '"+attribute+
		 * "' Attribute value for '"+objWebElement+"' object "); return
		 * objWebElement.getAttribute(attribute); }
		 * catch(org.openqa.selenium.NoSuchElementException e) {
		 * CommonVariables.CurrentTestCaseLog.error(
		 * "Error: caught 'ElementNotFoundException' exception. Failed to get '"
		 * + attribute
		 * +"' value for '"+objWebElement+"' on '"+driver.getTitle()+"' page");
		 * return ""; } catch(ElementNotVisibleException e) {
		 * CommonVariables.CurrentTestCaseLog.error(
		 * "Error: caught 'ElementNotVisibleException' exception. Failed to get '"
		 * + attribute
		 * +"' value for '"+objWebElement+"' on '"+driver.getTitle()+"' page");
		 * return ""; } catch(WebDriverException e) {
		 * CommonVariables.CurrentTestCaseLog
		 * .error("Error: caught 'WebDriverException' exception. Failed to get '"
		 * + attribute
		 * +"' value for '"+objWebElement+"' on '"+driver.getTitle()+"' page");
		 * return ""; } catch(NullPointerException e5) {
		 * CommonVariables.CurrentTestCaseLog.error(
		 * "Info. Caught 'NullPointerException' exception while try to get Element Attribute ("
		 * +attribute+") value on '"+driver.getTitle()+"'."); return ""; }
		 * catch(Exception e) {
		 * System.out.println("Failed to find object ("+objWebElement
		 * +") property'"+attribute+"' value.");
		 * CommonVariables.CurrentTestCaseLog.error("Failed to get '"+ attribute
		 * +"' value. Error Message: "+e.getMessage()); return ""; }
		 */
	}

	public boolean IsElementExist(By by, Optional<Long> timeoutInSeconds) {
		long timeout = timeoutInSeconds.isPresent() ? timeoutInSeconds.get()
				: 9999999;
		if (timeout == 9999999) {
			timeout = Long.parseLong(properties.getProperty("globalTimeOut"));
		}
		try {
			// AcceptAlert();
			/*
			 * if( FindElement(by, Optional.of(timeout))!= null){
			 * if(CommonVariables.CurrentTestCaseLog != null){
			 * CommonVariables.CurrentTestCaseLog
			 * .info("Info. Element '"+by+"' exists on '"
			 * +driver.getTitle()+"' page.");} else{
			 * CommonVariables.CurrentTestClassLog
			 * .info("Info. Element '"+by+"' exists on '"
			 * +driver.getTitle()+"' page.");}
			 * 
			 * return true;} else{ if(CommonVariables.CurrentTestCaseLog !=
			 * null){
			 * CommonVariables.CurrentTestCaseLog.info("Info. Element '"+by
			 * +"' deos not exist on '"+driver.getTitle()+"' page.");} else{
			 * CommonVariables.CurrentTestClassLog.info("Info. Element '"+by+
			 * "' deos not exist on '"+driver.getTitle()+"' page.");}
			 * 
			 * return false;}
			 */
		} catch (NullPointerException e) {
			return false;
		}
		return doFullReset;
	}

	

	public void SwipeRight(WebElement element) {
		// Executing swipe on in the case of iOS simulators. Skipping it for
		// Android Chrome as this swipe will not yet implemented on it.
		if (!GetDriverInfo().get("DriverName").equals("androidchrome")) {
			double browser_top_offset = 0.0;
			if (GetDriverInfo().get("DriverType").trim()
					.equalsIgnoreCase("mobile")) {
				browser_top_offset = 0;
			} else if (GetDriverInfo().get("DriverType").trim()
					.equalsIgnoreCase("tablet")) {
				browser_top_offset = 80;
			}
			RemoteWebElement remoteelem = ((RemoteWebElement) element);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String script = "return Math.max(document.documentElement.clientHeight, window.innerHeight || 0)";
			Long pageheight1 = (Long) js.executeScript(script);
			Long pagewidth1 = (Long) js
					.executeScript("return Math.max(document.documentElement.clientWidth, window.innerWidth || 0)");
			// Long
			// pageheight2=(Long)js.executeScript("return window.innerHeight");
			Point eloc = remoteelem.getLocation();
			double yloc = eloc.getY();
			double xstartloc = eloc.getX();
			double xendloc = eloc.getX() + remoteelem.getSize().width;
			double swipe_startxratio = xstartloc / pagewidth1;
			double swipe_endxratio = xendloc / pagewidth1;
			double elemheight = remoteelem.getSize().getHeight() / 2;
			double yratio = (yloc + elemheight / 2 + browser_top_offset)
					/ pageheight1;
			if (swipe_startxratio < 0.1) {
				swipe_startxratio = 0.1;
			}
			if (swipe_endxratio > 0.9) {
				swipe_endxratio = 0.9;
			}
			HashMap<String, Double> swipeObject = new HashMap<String, Double>();
			swipeObject.put("startX", swipe_endxratio);
			swipeObject.put("startY", yratio);
			swipeObject.put("endX", swipe_startxratio);
			swipeObject.put("endY", yratio);
			swipeObject.put("duration", 0.8);
			js.executeScript("mobile: swipe", swipeObject);
		}

	}

	public void SwipeBottom(WebElement element) {
		if (!GetDriverInfo().get("DriverName").equals("androidchrome")) {
			double browser_top_offset = 0.0;
			if (GetDriverInfo().get("DriverType").trim()
					.equalsIgnoreCase("mobile")) {
				browser_top_offset = 0;
			} else if (GetDriverInfo().get("DriverType").trim()
					.equalsIgnoreCase("tablet")) {
				browser_top_offset = 80;
			}
			RemoteWebElement remoteelem = ((RemoteWebElement) element);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			Point eloc = remoteelem.getLocation();
			double yloc = eloc.getY();
			double xloc = eloc.getX() + remoteelem.getSize().width / 2;
			double swipe_xratio = xloc;
			double elemheight = remoteelem.getSize().getHeight();
			double yStartRatio = (yloc + elemheight + browser_top_offset) / 2;
			double yEndRatio = (eloc.getY() + browser_top_offset);
			if (swipe_xratio < 10.0) {
				swipe_xratio = 10.0;
			}
			if (yEndRatio < 50.0) {
				yEndRatio = 50.0;
			}
			HashMap<String, Double> swipeObject = new HashMap<String, Double>();
			swipeObject.put("startX", swipe_xratio);
			swipeObject.put("startY", yStartRatio);
			swipeObject.put("endX", swipe_xratio);
			swipeObject.put("endY", yEndRatio);
			swipeObject.put("duration", 1.0);
			js.executeScript("mobile: swipe", swipeObject);
		}

	}

	public void SwipeLeft(WebElement element) {
		// Executing swipe on in the case of iOS simulators. Skipping it for
		// Android Chrome as this swipe will not yet implemented on it.
		if (!GetDriverInfo().get("DriverName").equals("androidchrome")) {
			double browser_top_offset = 0.0;
			if (GetDriverInfo().get("DriverType").trim()
					.equalsIgnoreCase("mobile")) {
				browser_top_offset = 0;
			} else if (GetDriverInfo().get("DriverType").trim()
					.equalsIgnoreCase("tablet")) {
				browser_top_offset = 80;
			}
			RemoteWebElement remoteelem = ((RemoteWebElement) element);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String script = "return Math.max(document.documentElement.clientHeight, window.innerHeight || 0)";
			Long pageheight1 = (Long) js.executeScript(script);
			Long pagewidth1 = (Long) js
					.executeScript("return Math.max(document.documentElement.clientWidth, window.innerWidth || 0)");
			// Long
			// pageheight2=(Long)js.executeScript("return window.innerHeight");
			Point eloc = remoteelem.getLocation();
			double yloc = eloc.getY();
			double xstartloc = eloc.getX();
			double xendloc = eloc.getX() + remoteelem.getSize().width;
			double swipe_startxratio = xstartloc / pagewidth1;
			double swipe_endxratio = xendloc / pagewidth1;
			double elemheight = remoteelem.getSize().getHeight() / 2;
			double yratio = (yloc + elemheight / 2 + browser_top_offset)
					/ pageheight1;
			if (swipe_startxratio < 0.05) {
				swipe_startxratio = 0.05;
			}
			if (swipe_endxratio > .95) {
				swipe_endxratio = 0.95;
			}

			HashMap<String, Double> swipeObject = new HashMap<String, Double>();
			swipeObject.put("startX", swipe_startxratio);
			swipeObject.put("startY", yratio);
			swipeObject.put("endX", swipe_endxratio);
			swipeObject.put("endY", yratio);
			swipeObject.put("duration", 0.8);
			js.executeScript("mobile: swipe", swipeObject);
		}

	}

	public void PinchOpen() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, Double> swipeObject = new HashMap<String, Double>();
		swipeObject.put("startX", (double) 114);
		swipeObject.put("startY", (double) 198);
		swipeObject.put("endX", (double) 257);
		swipeObject.put("endY", (double) 256);
		swipeObject.put("duration", 1.8);
		js.executeScript("mobile: pinchOpen", swipeObject);
	}

	public void PinchClose() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, Double> swipeObject = new HashMap<String, Double>();
		swipeObject.put("startX", (double) 150);
		swipeObject.put("startY", (double) 230);
		swipeObject.put("endX", (double) 200);
		swipeObject.put("endY", (double) 260);
		swipeObject.put("duration", 1.8);
		js.executeScript("mobile: pinchOpen", swipeObject);
	}

	public void Scroll(String Direction) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", Direction);
		js.executeScript("mobile: scroll", scrollObject);
	}

	public void ChangeOrientation(String Orientation) { // Valid values are:
														// "LANDSCAPELEFT" ,
														// "LANDSCAPERIGHT" ,
														// "PORTRAIT"
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// String script = "target.setDeviceOrientation(UIA_DEVICE_ORIENTATION_"
		// + Orientation + ");";
		// js.executeScript(script);
		js.executeScript("target.setDeviceOrientation(UIA_DEVICE_ORIENTATION_LANDSCAPERIGHT);");
	}

	public void WaitForElementVisible(final By by, int waitTime) {
		wait = new WebDriverWait(driver, waitTime);
		for (int attempt = 0; attempt < waitTime; attempt++) {
			try {
				driver.findElement(by);
				break;
			} catch (Exception e) {
				driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			}
		}
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (TimeoutException e) {
			/*
			 * if(CommonVariables.CurrentTestCaseLog != null){
			 * CommonVariables.CurrentTestCaseLog.info(
			 * "Info: caught 'TimeoutException' exception while wait for element ( "
			 * +by.toString()+" ) visibility at '"+driver.getTitle()+"' page");
			 * } else{ CommonVariables.CurrentTestClassLog.info(
			 * "Info: caught 'TimeoutException' exception while wait for element ( "
			 * +by.toString()+" ) visibility at '"+driver.getTitle()+"' page");
			 * }
			 */
		}
	}

	// public void saveScreenshot(String screenshotFileName) throws IOException
	// {
	// File screenshot = ((TakesScreenshot) driver)
	// .getScreenshotAs(OutputType.FILE);
	// FileUtils.copyFile(screenshot, new File(screenshotFileName));
	// }

	public void saveScreenshot(String ImgPath) {
		try {
			File screenshot = null;
			;
			if (driver.getClass().toString().toLowerCase()
					.contains("chromedriver")) {
				screenshot = ((TakesScreenshot) driver)
						.getScreenshotAs(OutputType.FILE);
			} else if (driver.getClass().toString().toLowerCase()
					.contains("remotewebdriver")) {
				org.openqa.selenium.WebDriver augmentedDriver = new Augmenter()
						.augment(driver);
				screenshot = ((TakesScreenshot) augmentedDriver)
						.getScreenshotAs(OutputType.FILE);
			}
			File screenshotfile = new File(ImgPath);
			try {
				FileUtils.copyFile(screenshot, screenshotfile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void saveFullScreenShot(String ImgPath) {
		CaptureBrowserScreenShot fullscreenshot = new CaptureBrowserScreenShot();
		/*
		 * try { //fullscreenshot.seleniumCaptureBrowserScreenShot(driver,
		 * ImgPath); } catch (InterruptedException | IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
	}

	public boolean IsElementVisible(final By by) {
		try {
			wait = new WebDriverWait(driver, 2);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			if ((driver.findElement(by).getSize().height == 0)
					&& (driver.findElement(by).getSize().width == 0)) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e1) {
			return false;
		}
	}

	// Function will return an hashmap with the driverinfo.
	// @author: Amit Rawat
	// Usage: System.out.println(GetDriverInfo().get("DriverType") +
	// System.out.println(GetDriverInfo().get("DriverName"));
	public Map<String, String> GetDriverInfo() {
		Map<String, String> DriverInfo = new HashMap<String, String>();
		try {
			String DriverType = "";
			String DriverName = "";
			if (driver.getClass().toString().toLowerCase().contains("chrome")) {
				DriverType = "Desktop";
				DriverName = "Chrome";
			} else if (driver.getClass().toString().toLowerCase()
					.contains("remotewebdriver")) {
				Capabilities caps = ((RemoteWebDriver) driver)
						.getCapabilities();
				try {
					DriverName = caps.getCapability("device").toString();
				} catch (NullPointerException e) { // to handle Android Chrome
													// case
					String browsername = caps.getCapability("browserName")
							.toString();
					if (browsername.equals("chrome")) {
						DriverName = "androidchrome";
					}
				}

				if (DriverName.toLowerCase().contains("chrome")) {
					DriverType = "Mobile";
				} else if (DriverName.toLowerCase().contains("ipad")) {
					DriverType = "Tablet";
				} else {
					DriverType = "Mobile";
				}
			}
			DriverInfo.put("DriverType", DriverType);
			DriverInfo.put("DriverName", DriverName);
			return DriverInfo;
		} catch (Exception e) {
			String DriverType = "Mobile";
			String DriverName = "Android";
			DriverInfo.put("DriverType", DriverType);
			DriverInfo.put("DriverName", DriverName);
			return DriverInfo;
			// return null;
		}
	}

	// Function will play the video on mobile/tablet devices
	// @author: Amit Rawat
	/*
	 * Usage: ; el = this.FindElement(By.xpath(
	 * "//div[@class='video-js vjs-default-skin vjs-equinox vjs-paused vjs-using-native-controls vjs-controls-disabled vjs-user-inactive']/video"
	 * ), Optional.of(Long.parseLong("5"))); commnfunc.StartVideo(el); boolean
	 * bl = this.IsElementExist(By.xpath(
	 * "//div[@class='video-js vjs-default-skin vjs-equinox vjs-using-native-controls vjs-controls-disabled vjs-user-inactive vjs-has-started vjs-playing']/video"
	 * ), Optional.of(Long.parseLong("5")));
	 */

	public void StartVideo(WebElement element) {
		try {
			double browser_top_offset = 80;
			if (GetDriverInfo().get("DriverName").contains("iphone")) {
				browser_top_offset = 50;
			} else if (GetDriverInfo().get("DriverName").contains("ipad")) {
				browser_top_offset = 80;
			}
			RemoteWebElement remoteelem = ((RemoteWebElement) element);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String script = "return Math.max(document.documentElement.clientHeight, window.innerHeight || 0)";
			Long pageheight1 = (Long) js.executeScript(script);
			Long pageheight2 = (Long) js
					.executeScript("return window.innerHeight");
			Point eloc = remoteelem.getLocation();
			double yloc = eloc.getY();
			double elemheight = remoteelem.getSize().getHeight() / 2;
			double yratio = (yloc + elemheight + browser_top_offset)
					/ pageheight1;
			HashMap<String, Double> tapObject = new HashMap<String, Double>();
			tapObject.put("x", 0.5);
			tapObject.put("y", yratio);
			js.executeScript("mobile: tap", tapObject);
		} catch (Exception e) {

		}

	}

	public void ScrollToTop() {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollTo(0,0);");
		} catch (Exception e) {

		}

	}

	public void ScrollToBottom() {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollTo(0,document.documentElement.scrollHeight);");
		} catch (Exception e) {

		}
	}

	public void SwipeTop(WebElement element) {
		double browser_top_offset = 0.0;
		if (GetDriverInfo().get("DriverType").trim().equalsIgnoreCase("mobile")) {
			browser_top_offset = 0;
		} else if (GetDriverInfo().get("DriverType").trim()
				.equalsIgnoreCase("tablet")) {
			browser_top_offset = 80;
		}
		RemoteWebElement remoteelem = ((RemoteWebElement) element);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Point eloc = remoteelem.getLocation();
		double yloc = eloc.getY();
		double xloc = eloc.getX() + remoteelem.getSize().width / 2;
		double swipe_xratio = xloc;
		double elemheight = remoteelem.getSize().getHeight();
		double yStartRatio = (yloc + elemheight / 2 + browser_top_offset) / 2;
		double yEndRatio = (eloc.getY() + browser_top_offset);
		if (swipe_xratio < 10.0) {
			swipe_xratio = 10.0;
		}
		if (yEndRatio < 50.0) {
			yEndRatio = 50.0;
		}
		HashMap<String, Double> swipeObject = new HashMap<String, Double>();
		swipeObject.put("startX", swipe_xratio);
		swipeObject.put("startY", yEndRatio);
		swipeObject.put("endX", swipe_xratio);
		swipeObject.put("endY", yStartRatio);
		swipeObject.put("duration", 1.0);
		js.executeScript("mobile: swipe", swipeObject);
	}

	public void SetiOSGeoLocation(double latitude, double longitude) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, Double> locationObject = new HashMap<String, Double>();
		locationObject.put("latitude", latitude);
		locationObject.put("longitude", longitude);
		js.executeScript("mobile: setLocation", locationObject);
	}

	public boolean assertLocationAlertPresent(boolean alertShouldBeThereOrNot,
			Long waitingTime) {
		WebDriverWait wait = new WebDriverWait(driver, waitingTime);
		Alert alert = null;
		try {
			alert = wait.until(ExpectedConditions.alertIsPresent());
		} catch (TimeoutException e) {

		}
		Set<String> windowHandles = driver.getWindowHandles();
		if (windowHandles.size() > 1 && alertShouldBeThereOrNot == true) {
			alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (!alertText
					.equals("\"Safari\" Would Like to Use Your Current Location")) {
				alert = null;
			}
		}

		if (alert != null) {
			System.out.println("alert is present");
		} else {
			System.out.println("alert is not present");
		}

		if (alertShouldBeThereOrNot == true && alert != null) {
			return true;
		} else if (alertShouldBeThereOrNot == true && alert == null) {
			return false;
		}
		if (alertShouldBeThereOrNot == false && alert != null) {
			return false;
		} else if (alertShouldBeThereOrNot == true && alert == null) {
			return true;
		} else
			return false;
	}

	/**
	 * 
	 * @param testName
	 * @param xls
	 * @return
	 */
	public static Object[][] readData(String testName, Reader xls) {
		// find the row num from which test starts
		// number of columns
		// number of rows
		// put the data in hashtable and put hashtable in array
		int testStartRowNum = 0;
		String sheetName = null;
		//String result[] = getTestDataRow(testName,xls);
		String temp[];
		temp = xls.getTestDataRow(testName,xls);
		sheetName = temp[0];
		testName= temp[1];
		testStartRowNum = Integer.parseInt(temp[2]);
		
		// find the row num from which test start
		for (int rNum = 1; rNum <= xls.getRowCount(sheetName); rNum++) {
			if (xls.getCellData(sheetName, 0, rNum).equals(testName)) {
				testStartRowNum = rNum;
				break;
			}
		}
		
		// cols
		int colStartRowNum = testStartRowNum + 1;
		int totalCols = 0;
		while (!xls.getCellData(sheetName, totalCols, colStartRowNum).equals("")) {
			totalCols++;
		}
	
		// rows
		int dataStartRowNum = testStartRowNum + 2;
		int totalRows = 0;
		while (!xls.getCellData(sheetName, 0, dataStartRowNum + totalRows)
				.equals("")) {
			totalRows++;
		}

		// extract data
		Object[][] data = new Object[totalRows][1];
		Hashtable<String, String> table = null;
		int index = 0;
		for (int rNum = dataStartRowNum; rNum < (dataStartRowNum + totalRows); rNum++) {
			table = new Hashtable<String, String>();
			for (int cNum = 0; cNum < totalCols; cNum++) {
				table.put(xls.getCellData(sheetName, cNum, colStartRowNum),
						xls.getCellData(sheetName, cNum, rNum));
				System.out.print(xls.getCellData(sheetName, cNum, rNum)
						+ " -- ");
			}
			data[index][0] = table;
			index++;
			System.out.println();
		}
		System.out.println("Done");
		return data;
	}

	/**
	 * Purpose : This function injects file path into Windows File Upload dialog
	 * 
	 * @param filePath
	 * @throws AWTException
	 */
	public static void UploadFile(String filePath) throws AWTException {
		StringSelection stringSelection = new StringSelection(filePath);
		Toolkit.getDefaultToolkit().getSystemClipboard()
				.setContents(stringSelection, null);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	/***
	 * 
	 * @param location
	 */
	public void CreateFolder(String location) {
		File Log = new File(location);
		if (Log.exists()) {
			AppLogs.debug("Directory already exists ...");
		} else {
			new File(location).mkdir();
			AppLogs.debug("Successfully created new directory");
		}
	}
}