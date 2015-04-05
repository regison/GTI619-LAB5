package securityLayer.securityModule.Comm;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import java.util.Date;

import communication.DataMapping.DataProvider;
import communication.DataObjects.Objects;
import communication.DataObjects.Objects.Log;
import communication.DataObjects.Objects.LoginLog;
import database.mysql.Mysql;

public class SecurityLayerDataBaseCommunication {

	
	
	private boolean userNeedToLogout = false;
	private boolean userIsLoggedIn = false;
	private long userFailedTriesCount = -1;
	private Timestamp userLastLoginTime;
	private int userID = -1;
	private int originalUserID = -1;
	private boolean initialised = false;
	
	private DataProvider dtp;
	public SecurityLayerDataBaseCommunication(){		
		dtp = new DataProvider();
	}
	
	public void initialise(int userID){
		this.originalUserID = userID;
		initialised = true;
		
		try {
			
			dtp.GetLoginLogsByUserId(userID);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean UserNeedToLogout(){
		return userNeedToLogout;
	}
	
	public boolean UserIsLoggedIn(){
		return userIsLoggedIn;
	}
	
	public long UserFailedTriesCount(){
		return userFailedTriesCount;
	}
	
	public Timestamp UserLastLoginTime(){
		return userLastLoginTime;
	}
	
	public boolean UserIDExists(){
		return userID > -1;
	}

	
	public void UpdateUserInfo(int userID, boolean incrementFailedLoginTriesCount, boolean loggedIn, boolean LogoutNeeded) throws Exception{
		if(!initialised)
			throw new Exception("Not initialised!");
		
		LoginLog llog = new Objects().new LoginLog();

		llog.failedTriesCount = userFailedTriesCount;
		llog.loggedIn = loggedIn;
		llog.logoutNeeded = LogoutNeeded;
		llog.userId = userID;
		llog.lastloginTime =  new Timestamp(new Date().getTime());
		
		if(userID > -1){ //Update			
			try {
				int value = dtp.UpdateLoginLogsByUserId(incrementFailedLoginTriesCount, loggedIn, userFailedTriesCount, LogoutNeeded, userID);
				if (value == 1){
					//SuccessUpdate
					
				}
				else{
					dtp.CreateLoginLog(incrementFailedLoginTriesCount,llog);
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		else{ //Insert
			
			try {
				
				dtp.CreateLoginLog(incrementFailedLoginTriesCount,llog);
				
			
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
	}
	
	private void UpdateUserLastLoginTimeToNow(){
		if(userID < 0)
			return;
		dtp = new DataProvider();
		try {
			
			//cnx.update("UPDATE log619lab5.LoginLogs SET idUser= ? WHERE userID= ? ;", new String[]{userID + "", userID + ""});

			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
