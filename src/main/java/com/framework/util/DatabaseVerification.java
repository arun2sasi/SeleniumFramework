package com.framework.util;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.*;

/**
 * DatabaseVerification.java contains database operation methods
 * 
 * @author Abhay Bharti 
 */
public class DatabaseVerification {
 
	/**
	 * Purpose : 
	 * @param firstName
	 * @return
	 */
	public List<String> getSAVDatafromDB(String firstName){
		
		String db_connect_string="jdbc:sqlserver://EQXTESTSQL";
		String db_userid="MyEquinoxSvc";
		String db_password="MyEquinoxSvc";
		String queryString = "use equinoxfitness_mscrm select top 1 FirstName, LastName, FacilityID, EmailAddress, PhoneNumber, Notes from eqx_leadentry where FirstName='"+firstName+"' order by createdOn desc";
		List<String> resultSet=new ArrayList<String>();
		try{
		ResultSet rs=getDataFromDB(db_connect_string, db_userid, db_password, queryString);
		
		ResultSetMetaData rsmd=rs.getMetaData();
         while (rs.next()) {
        	 for(Integer i=1;i<= rsmd.getColumnCount()-1; i++){
        		 resultSet.add(rs.getString(i));
        	}
        	String notes=rs.getString(rsmd.getColumnCount());
        	String[]notesValues=notes.split("\\:");
        	String regionTemp[]=notesValues[1].split("\n");
        	String region=regionTemp[0];
        	resultSet.add(region.trim());
        	String levelTemp[]=notesValues[2].split("\n");
        	String level=levelTemp[0];
        	resultSet.add(level.trim());
        	String interestTemp[]=notesValues[3].split("\n");
        	String interest=interestTemp[0];
        	resultSet.add(interest.trim());
         }
         
      } catch (Exception e) {
    	  //CommonVariables.CurrentTestCaseLog.error("Error connecting database.");
      }
	return resultSet;
   }
	
	/**
	 * 
	 * @param firstName
	 * @return
	 */
	public List<String> getDataSourceIdfromDB(String firstName){
		
		String db_connect_string="jdbc:sqlserver://EQXTESTSQL";
		String db_userid="MyEquinoxSvc";
		String db_password="MyEquinoxSvc";
		String queryString = "use equinoxfitness_mscrm select top 1 dataSourceID from eqx_leadentry where FirstName='"+firstName+"' order by createdOn desc";
		List<String> resultSet=new ArrayList<String>();
		try{
		ResultSet rs=getDataFromDB(db_connect_string, db_userid, db_password, queryString);
		
		ResultSetMetaData rsmd=rs.getMetaData();
         while (rs.next()) {
        	 for(Integer i=1;i<= rsmd.getColumnCount(); i++){
        		 resultSet.add(rs.getString(i).toLowerCase());
        	}
         }
         
      } catch (Exception e) {
    	  //CommonVariables.CurrentTestCaseLog.error("Error connecting database.");
      }
	return resultSet;
   }
	/**
	 * Purpose : 
	 * @param db_connect_string
	 * @param db_userid
	 * @param db_password
	 * @param queryString
	 * @return
	 * @throws Exception
	 */
	public ResultSet getDataFromDB(String db_connect_string, String db_userid, String db_password, String queryString) throws Exception{
		 	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		 	Connection conn = DriverManager.getConnection(db_connect_string, db_userid, db_password);
    		/*if(CommonVariables.CurrentTestCaseLog != null){
    			CommonVariables.CurrentTestCaseLog.info("DB connected");}
    		else
    		{CommonVariables.CurrentTestClassLog.info("DB connected");}*/
		    Statement statement = conn.createStatement();
		    ResultSet rs = statement.executeQuery(queryString);
		return rs;
		
	}
}