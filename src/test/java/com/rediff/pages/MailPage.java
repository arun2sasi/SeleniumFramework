package com.rediff.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.framework.actions.WebAction;
import com.framework.exception.SeleniumException;

public abstract class MailPage extends WebAction{
	
public WebDriver driver;
	
	@FindBy(how = How.XPATH, using = "//*[@id='login1']")
	private WebElement Txt_MailPage_User;
	
	@FindBy(how = How.XPATH, using = "//*[@id='div_rediffmail']/form/div[3]/input")
	private WebElement Txt_MailPage_Password;
	
	@FindBy(how = How.XPATH, using = "//*[@id='div_rediffmail']/form/div[4]/input[2]")
	private WebElement Btn_MailPage_Go;
	
	/**
	 * Purpose : Initialize MailPage object
	 * 
	 * @param driver
	 * @throws SeleniumException
	 */
	
	public MailPage(WebDriver driver) {
		super(driver);
		AppLogs.info("MailPage() starts.."+driver.getWindowHandle()+" , "+driver.getCurrentUrl());
		this.driver = driver;
		PageFactory.initElements(driver, this);
		AppLogs.info("MailPage() ends..");
	} 
	
	/**
	 * Purpose : Click on Log In button
	 * 
	 * @throws SeleniumException
	 */
	public void clickLogin() throws SeleniumException {
		AppLogs.info("clickLogin() starts..");
		try {
			ClickOnWebElement(Btn_MailPage_Go);
		} catch (NoSuchElementException e) {
			throw new SeleniumException("LoginPage -> clickLogin() - ", e);
		}
		AppLogs.info("clickLogin() ends..");
	}

	/**
	 * Purpose : Set userName in Username edit field
	 * 
	 * @param userName
	 * @throws SeleniumException
	 */
	public void setUserName(String userName) throws SeleniumException {
		try {
			inputText(Txt_MailPage_User, userName);
		} catch (NoSuchElementException e) {
			throw new SeleniumException("LoginPage -> setUserName() - ", e);
		}
	}

	/**
	 * Purpose : set password in Password Edit field
	 * 
	 * @param password
	 * @throws SeleniumException
	 */
	public void setPassword(String password) throws SeleniumException {
		try {
			inputText(Txt_MailPage_Password, password);
		} catch (NoSuchElementException e) {
			throw new SeleniumException("LoginPage -> setPassword() - ", e);
		}
	}
	
	// Buisness Flow
	/**
	 * Purpose : do login into SDG website
	 * 
	 * @param userName
	 * @param Password
	 * @throws SeleniumException
	 */
	public void doLogin(String userName, String Password) throws SeleniumException {
		String userName1 = "abc";
		String userName2 = "11abc";

		setUserName(userName1);
		setPassword(userName2);
		clickLogin();
	}
	
}
