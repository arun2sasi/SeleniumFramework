package com.framework.util;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
/*
 * To be Corrected
 * 
 * The use of this Class is to hit the Jira REST API to update the jira test case. 
 * It has only one method "EditJiraStatus" to update the jira status and it 3 arguments.
 * Example Call
 *  	  JiraIntegration JI = new JiraIntegration();
 *		  JI.EditJiraStatus("TCM-6150","FAIL","2.10");
Developed by: Abhay Bharti

*/
public class JiraIntegration {
	/*
	HttpURLConnection con;
	public void EditJiraStatus(String IssusID,String Status,String ReleaseVersion){
		String DeviceType = CommonVariables.DeviceName.trim();
		String issid = IssusID;
		String release = ReleaseVersion;
		String Issue_Status = Status + "_" + DeviceType + "_" + release;
		String JiraURL ="http://jira.equinox.com//rest/api/2/issue/" + issid;
		try{
			URL url = new URL(JiraURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("PUT");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Authorization", "Basic " + "YXJhd2F0OmVxdWlub3gx");  //YXJhd2F0OmVxdWlub3gx
			String updateTCstatus_json = "{\"update\":{\"customfield_11314\":[" +
					//Normal Pass Fail
					"{\"remove\":\"PASS_" + release + "\"}," +
					"{\"remove\":\"FAIL_" + release + "\"}," +
					//Pass Fail for iPhone-Simulator
					"{\"remove\":\"FAIL_iPhone-Simulator_" + release + "\"}," +
					"{\"remove\":\"PASS_iPhone-Simulator_" + release + "\"}," +
					//Pass Fail for iPad-Simulator
					"{\"remove\":\"FAIL_iPad-Simulator_" + release + "\"}," +
					"{\"remove\":\"PASS_iPad-Simulator_" + release + "\"}," +					
					//Pass Fail for Android-chrome
					"{\"remove\":\"FAIL_Android-chrome_" + release + "\"}," +
					"{\"remove\":\"PASS_Android-chrome_" + release + "\"}," +
					
					"{\"add\":\"" + Issue_Status + "\"}]}}";
			OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream());
			os.write(updateTCstatus_json);
			os.flush();
			os.close();
			
//			System.out.println("Response Code: " + conn.getResponseCode());
			if(conn.getResponseCode() == 204){
				CommonVariables.CurrentTestCaseLog.info("Jira status is successfully updated for the issue_id: "+ issid);
			}else{
				CommonVariables.CurrentTestCaseLog.error("Jira status is not updated for the issue_id: "+ issid + " as the jira response code is not 204. Actual code is: " + conn.getResponseCode());
			}
//			BufferedReader br = new BufferedReader(new InputStreamReader(
//					(conn.getInputStream())));
//	 
//			String output;
//			System.out.println("Output from Server .... \n");
//			while ((output = br.readLine()) != null) {
//				System.out.println(output);
//			}
	 
			conn.disconnect();
		} catch (MalformedURLException e) {			  
				e.printStackTrace();
				CommonVariables.CurrentTestCaseLog.error("MalformedURLException while updating jira status: "+ issid + "  : " + e.toString());
		 
		} catch (IOException e) {		 
				e.printStackTrace();
				CommonVariables.CurrentTestCaseLog.error("IOException while updating jira status: "+ issid + "  : " + e.toString());
		} catch (Exception e) {		 
				e.printStackTrace();
				CommonVariables.CurrentTestCaseLog.error("Other Exception while updating jira status: "+ issid + "  : " + e.toString()); 
		}
	}
	public void LoadJiraMappingSheet() throws IOException{
		String JiraMappingSheet = System.getProperty("user.dir")+ "/testdata/JiraMapping.csv";
		BufferedReader br = new BufferedReader(new FileReader(JiraMappingSheet));
	    String line =  null;
	    CommonVariables.jiramap.clear();
	    HashMap<String,String> map = new HashMap<String, String>();
    	line=br.readLine();
	    while((line=br.readLine())!=null){
	        String str[] = line.split(",");
	        CommonVariables.jiramap.put(str[0], str[1]);
	    }
	}*/
	
}
