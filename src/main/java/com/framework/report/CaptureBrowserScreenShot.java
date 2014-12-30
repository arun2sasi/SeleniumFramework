package com.framework.report;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import com.framework.exception.SeleniumException;


public class CaptureBrowserScreenShot{
	
	DetailedLogs AppLogs = new DetailedLogs();

	
	public CaptureBrowserScreenShot(){
		
	}
	
/**
 * Purpose : This method generates unique file name
 * 
 * @return GetDateTime
 * @throws SeleniumException
 */
private String getDateTime() throws SeleniumException {
	AppLogs.info("TestBase -> getDateTime() - starts..");
	try {
		SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
		Date now = new Date();
		String strDate = sdfDate.format(now);
		String strTime = sdfTime.format(now);
		strTime = strTime.replace(":", "-");
		AppLogs.info("TestBase -> getDateTime() - ends..");
		return (strDate + "_" + strTime);
	}
	catch (Exception e) {
		throw new SeleniumException("TestBase -> getDateTime() - ", e);
	}
}

/**
 * Purpose : This method takes screenshot
 * 
 * @throws IOException 
 * @throws Exception 
 */
private File takeScreenShots(WebDriver driver) throws SeleniumException, IOException {
	AppLogs.info("TestBase -> takeScreenShots() - starts..");
    try {
        File temp = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File stored = new File(System.getProperty("user.dir")+ "\\Report\\Screenshot\\" + getDateTime() + ".png");
        FileUtils.copyFile(temp, stored);
        AppLogs.info("TestBase -> takeScreenShots() - ends..");
        return stored;
    }catch (WebDriverException somePlatformsDontSupportScreenshots) {
    	throw new SeleniumException("TestBase -> takeScreenShots() - ", somePlatformsDontSupportScreenshots);
    }catch(IOException e){
    	throw new SeleniumException("TestBase -> takeScreenShots() - ", e);
    }
}

/**
 * Purpose : This method embeds screenshot into Cucumber Jenkins report
 * 
 * @param scenario
 * @throws SeleniumException
 * @throws IOException 
 */
 public void embedScreenShotIntoReport(WebDriver driver) throws SeleniumException, IOException{
	AppLogs.info("TestBase -> embedScreenShotIntoReport() - starts..");
	     try {  
	    	 takeScreenShots(driver);
         } catch (WebDriverException wde) {  
        	 throw new SeleniumException("TestBase -> embedScreenShotIntoReport() - ", wde);
         } catch (ClassCastException cce) {  
        	 throw new SeleniumException("TestBase -> embedScreenShotIntoReport() - ", cce);
         } catch(IOException Io){
        	 throw new SeleniumException("TestBase -> embedScreenShotIntoReport() - ", Io);
         } catch(Exception e){
        	 throw new SeleniumException("TestBase -> embedScreenShotIntoReport() - ", e);
         }
     AppLogs.info("TestBase -> embedScreenShotIntoReport() - ends..");
  }  
}