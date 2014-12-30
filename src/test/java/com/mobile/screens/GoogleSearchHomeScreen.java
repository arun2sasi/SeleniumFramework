package com.mobile.screens;

import io.appium.java_client.AppiumDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.framework.actions.MobileAction;

public class GoogleSearchHomeScreen extends MobileAction {

	public GoogleSearchHomeScreen(AppiumDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(xpath = ".//*[@id='lst-ib']")
	private WebElement searchBox;

	@FindBy(xpath = ".//*[@id='tsbb']")
	private WebElement searchButton;

	@FindBy(partialLinkText = "GitHub")
	private WebElement githubLink;

	/*public GoogleSearchHomeScreen(AndroidDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
		loadPage();
	}*/

	public GoogleSearchHomeScreen openGoogleSearch() {
		driver.get("http://google.com/");
		return this;
	}

	public GoogleSearchHomeScreen searchFor(String topicName) {
		// TODO Auto-generated method stub
		searchBox.sendKeys(topicName);
		searchButton.click();
		return this;
	}

	/*
	public AppiumGithubScreen openAppiumGitHub() {
		// TODO Auto-generated method stub
		githubLink.click();
		return new AppiumGithubScreen(driver);
	}*/
}
