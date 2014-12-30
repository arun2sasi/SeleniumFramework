package com.framework.util;


/**
 * BrowserType.java contains list of browser & works as enum
 * @author Abhay Bharti
 *
 */
public enum BrowserType {
	FIREFOX("firefox"), CHROME("chrome"), IE("ie"), SAFARI("safari"), OPERA(
			"opera"), GHOSTDRIVER("ghostdriver"), REMOTE("remote"), HTMLUNIT(
			"htmlunit");

	private final String browser;

	/**
	 * Purpose : Constructor to initialize BrowserType class
	 * @param browser
	 */
	BrowserType(String browser) {
		this.browser = browser;
	}

	/**
	 * Purpose : This method returns browser from enum
	 * @return
	 */
	public String getBrowser() {
		return browser;
	}
}
