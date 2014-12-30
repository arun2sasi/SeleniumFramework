package com.rediff.testcases;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.framework.exception.SeleniumException;
import com.rediff.pages.HomePage;
import com.rediff.pages.MailPage;
import com.framework.testtemplate.TestBase;

/**
 * LoginTest.java is test class for login feature test
 * @author AbhayB
 *
 */
public class LoginTest extends TestBase {
	
	protected LoginTest() throws SeleniumException {
		super();
		// TODO Auto-generated constructor stub
	}

	MailPage mailPage = null;
	HomePage homePage = null;
		
	@BeforeMethod
	public void LoginTestSetUp(){
		try{
		AppLogs.info("LoginTestSetUp starts..");
		homePage = PageFactory.initElements(driver, HomePage.class);
		mailPage = homePage.openRediffMail();
		AppLogs.info("LoginTestSetUp ends..");
		}catch(Exception e){
			AppLogs.error("LoginTestSetup()"+e);
		}
	}
	
	@Test
	public void sucessfullLoginTest() throws SeleniumException {
		AppLogs.info("sucessfullLoginTest() starts..");
		mailPage.doLogin("abcd", "aaae");
		AppLogs.info("sucessfullLoginTest() ends..");
	}

	@Test
	public void rightUserNameWrongPasswordLoginTest() throws SeleniumException {
		mailPage.doLogin("qamate", "qamaet");
	}

	@Test
	public void rightUserNameBlankPasswordLoginTest() throws SeleniumException {
		mailPage.doLogin("qamate", "");
		// Assert.assertTrue(HomePage.getErrorMessage(driver).getText().contains("Password cannot be empty"));
	}

	@Test
	public void wrongUserNameRightPasswordLoginTest() throws SeleniumException {
		mailPage.doLogin("qamaet", "qamate");
		// Assert.assertTrue(HomePage.getErrorMessage(driver).getText().contains("Invalid credentials"));
	}

	@Test
	public void wrongUserNameWrongPasswordLoginTest() throws SeleniumException {
		mailPage.doLogin("qamaet", "qamet");
		// Assert.assertTrue(HomePage.getErrorMessage(driver).getText().contains("Invalid credentials"));
	}

	@Test
	public void wrongUserNameBlankPasswordLoginTest() throws SeleniumException {
		mailPage.doLogin("qamaet", "");
		// Assert.assertTrue(HomePage.getErrorMessage(driver).getText().contains("Password cannot be empty"));
	}

	@Test
	public void blankUserNameRightPasswordLoginTest() throws SeleniumException {
		mailPage.doLogin("", "qamate");
		// Assert.assertTrue(HomePage.getErrorMessage(driver).getText().contains("Username cannot be empty"));
	}

	@Test
	public void blankUserNameWrongPasswordLoginTest() throws SeleniumException {
		mailPage.doLogin("", "qamaet");
		// Assert.assertTrue(HomePage.getErrorMessage(driver).getText().contains("Username cannot be empty"));
	}

	@Test
	public void blankUserNameBlankPasswordLoginTest() throws SeleniumException {
		mailPage.doLogin("", "");
		// Assert.assertTrue(HomePage.getErrorMessage(driver).getText().contains("Username cannot be empty"));
	}
}
