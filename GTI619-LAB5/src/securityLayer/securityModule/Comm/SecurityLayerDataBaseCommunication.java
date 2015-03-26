package securityLayer.securityModule.Comm;

import java.sql.Timestamp;
import java.util.ArrayList;

import communication.DataMapping.DataProvider;

import database.mysql.Mysql;

public class SecurityLayerDataBaseCommunication {

	private Mysql cnx;
	
	private boolean userNeedToLogout = false;
	private boolean userIsLoggedIn = false;
	private long userFailedTriesCount = -1;
	private Timestamp userLastLoginTime;
	private int userID = -1;
	private int originalUserID = -1;
	private boolean initialised = false;
	
	private DataProvider dtp;
	public SecurityLayerDataBaseCommunication(){		
		dtp = new DataProvider(Mysql.MYSQL_DATABASE_LOG619LAB5);
	}
	
	public void initialise(int userID){
		this.originalUserID = userID;
		initialised = true;
		cnx.Open();
		try {
			
			ArrayList<ArrayList<Object>> result = cnx.Select("SELECT * FROM log619lab5.LoginLogs WHERE idUser= ? ;", new String[] {userID + ""}, "idUser", "LastLoginTime", "FailedTriesCount", "LoggedIn", "LogoutNeeded");
			if(result.size() < 1)
				return;
			userNeedToLogout = Boolean.parseBoolean(result.get(0).get(4).toString());
			userIsLoggedIn = Boolean.parseBoolean(result.get(0).get(3).toString());
			userFailedTriesCount = Long.parseLong(result.get(0).get(2).toString());
			userLastLoginTime = java.sql.Timestamp.valueOf(result.get(0).get(1).toString());
			this.userID = Integer.parseInt(result.get(0).get(0).toString());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cnx.Close();
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

	
	public void UpdateUserInfo(boolean incrementFailedLoginTriesCount, boolean loggedIn, boolean LogoutNeeded) throws Exception{
		if(!initialised)
			throw new Exception("Not initialised!");
		if(userID > -1){ //Update
			cnx.Open();
			try {
				cnx.update("UPDATE log619lab5.LoginLogs SET FailedTriesCount=? , LoggedIn=? , LogoutNeeded=? WHERE idUser=? ;", 
						new String[] {((incrementFailedLoginTriesCount ? this.userFailedTriesCount + 1 : (loggedIn ? 0 : this.userFailedTriesCount)) + ""), 
							(loggedIn ? "1" : "0"), (LogoutNeeded ? "1" : "0"), userID + ""});
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cnx.Close();
		}
		else{ //Insert
			cnx.Open();
			try {
				cnx.insert("INSERT INTO log619lab5.LoginLogs (idUser, FailedTriesCount, LoggedIn, LogoutNeeded) VALUES ( ? , ? , ? , ? );", 
						new String[] {originalUserID + "", (incrementFailedLoginTriesCount ? "1" : "0"), (loggedIn ? "1" : "0"), (LogoutNeeded ? "1" : "0") });
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cnx.Close();
		}
	}
	
	private void UpdateUserLastLoginTimeToNow(){
		if(userID < 0)
			return;
		cnx.Open();
		try {
			cnx.update("UPDATE log619lab5.LoginLogs SET idUser= ? WHERE userID= ? ;", new String[]{userID + "", userID + ""});
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cnx.Close();
	}
}
