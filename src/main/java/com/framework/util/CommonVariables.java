package com.framework.util;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.Log4JLogger;
import org.openqa.selenium.WebDriver;
import org.testng.log4testng.Logger;

import com.framework.report.DetailedLogs;

/**
 * CommonVariable.java contains variable & path to be used across automation suite
 * 
 * @author Abhay Bharti
 */
public class CommonVariables {
	public static DetailedLogs DL;
	public static WebDriver CommonDriver;
	public static String CurrentTestCaseName = "";
	public static String CurrentTestClassName = "";
	public static String CurrentTestClassResult = "PASS";
	public static String CurrentTestCaseResult = "";
	public static Log4JLogger CurrentTestCaseLog = null;
	public static Log4JLogger CurrentTestClassLog;
	public static Log CurrentGlobalLog;
	public static Logger CurrentSiteCoreLog;
	public static BufferedWriter HighLevelLog;
	public static String RootResultFolderPath = "";
	public static String ScenarioResultFolderPath = "";
	public static String TCResultFolderPath = "";
	public static String CurrentTCLogPath = "";
	public static int TestCase_Data_Iterator = 0;

	public static List<String> ScenariosHighLevelLog = new ArrayList<String>();
	public static List<String> TestCasessHighLevelLog = new ArrayList<String>();

	public static JiraIntegration JI = new JiraIntegration();
	public static HashMap<String, String> jiramap = new HashMap<String, String>();

	public static String DeviceName = "";
	public static int DataProviderIterator = 0;

	public static String TCStartTime = "";
	public static String TCEndTime = "";
}
