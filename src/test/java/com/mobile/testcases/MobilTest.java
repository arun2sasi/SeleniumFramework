package com.mobile.testcases;

import java.net.MalformedURLException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.framework.exception.SeleniumException;
import com.framework.testtemplate.TestBase;

public class MobilTest extends TestBase{
	
	protected MobilTest() throws SeleniumException {
		super();
		// TODO Auto-generated constructor stub
	}

	@BeforeClass
	@Parameters(value = {"Mobile","Device"})
	public void classSetUp(@Optional("Yes") String Mobile, @Optional("Android") String Device) throws MalformedURLException, SeleniumException {
		startAppiumDriver(Mobile,Device);
	}
	
	@Test(groups = { "Mobile" })
	public void TestOne(){
		AppLogs.info("sucessfullLoginTest() starts..");
		System.out.println("Mobile Test Case");
	}
}
