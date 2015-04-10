package securityLayer.securityModule.Core;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpSession;

import log619lab5.struts.SessionAttributeIdentificator;
import communication.DataMapping.DataProvider;
import communication.DataMapping.ExceptionLogger;
import communication.DataObjects.Objects;
import communication.DataObjects.Objects.Log;
import communication.DataObjects.Objects.Role;
import communication.DataObjects.Objects.RoleLevel;
import communication.DataObjects.Objects.User;
import database.mysql.Mysql;
import securityLayer.securityModule.Comm.SecurityLayerDataBaseCommunication;
import securityLayer.securityModule.bruteforceProtection.BruteForceProtection;
import securityLayer.securityModule.verifyUserValidity.VerifyUserValidity;

public class SecurityModuleCore {

	private User user;
	private HttpSession session;
	private SecurityLayerDataBaseCommunication dbComm;
	private DataProvider dtp;
	
	public SecurityModuleCore(User user, HttpSession session){
		this.user = user;
		this.session = session;
		if(user != null){
			dbComm = new SecurityLayerDataBaseCommunication();
			dbComm.initialise(user.idUser);
		}
	}
	
	public void setUser(User user){
		this.user = user;
		if(user != null){
			dbComm = new SecurityLayerDataBaseCommunication();
			dbComm.initialise(user.idUser);
		}
	}
	
	public void verifyUserValidity(){
		VerifyUserValidity verifyUserThread = new VerifyUserValidity(user);
		
	}
	
	public boolean userCanLogin(){
		
		return true;
	}
	
	public boolean identifiedUserMustLogOut(){
		
		return user == null ? true : user.isLogOutNeeded;
	}
	
	public void updateSuccessfullLoginTime(int userID, String remoteIP){
		
		dtp = new DataProvider(false);
		Objects obj = new Objects();
		Log event = obj.new Log();
		event.logName = "Successfull Login Try For User " + dtp.GetUser(userID).name + " from IP " + remoteIP;
		event.userLogId = 1;
		event.logDate = new SimpleDateFormat().format(new Date());
		dtp.CreateLog(event, false);
		try {	
			session.setAttribute(SessionAttributeIdentificator.FAILEDLOGINCOUNT, "0");
			dbComm.UpdateUserInfo(userID, false, true, false);
		} catch (Exception e) {
			ExceptionLogger.LogException(e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void incrementUnsuccessfullLogin(){
		
		dtp = new DataProvider(false);
		
		try {
			dbComm.UpdateUserInfo(user.idUser, true, false, false);
		} catch (Exception e) {
			ExceptionLogger.LogException(e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void manageUnsuccessfullLogin(String remoteIP){
		if(user != null){
			incrementUnsuccessfullLogin();
		}
		dtp = new DataProvider(false);
		Objects obj = new Objects();
		Log event = obj.new Log();
		event.logName = "Failed Login Try For " + (user == null ? "no existing user" : user.name) + " from IP " + remoteIP;
		event.userLogId = 1;
		event.logDate = new SimpleDateFormat().format(new Date());
		dtp.CreateLog(event, false);
		BruteForceProtection bruteProtect = new BruteForceProtection();
		bruteProtect.manageLoginBruteForce(session, dbComm == null ? 0 : dbComm.UserFailedTriesCount(), user);
	}
	
	public void manageUnsuccessfullLogin(String username, String remoteIP){
		if(user != null){
			incrementUnsuccessfullLogin();
		}
		dtp = new DataProvider(false);
		Objects obj = new Objects();
		Log event = obj.new Log();
		event.logName = "Failed Login Try For " + (user == null ? "no existing user" : user.name) + " from IP " + remoteIP;
		event.userLogId = 1;
		event.logDate = new SimpleDateFormat().format(new Date());
		dtp.CreateLog(event, false);
		BruteForceProtection bruteProtect = new BruteForceProtection();
		bruteProtect.manageLoginBruteForce(session, dbComm == null ? 0 : dbComm.UserFailedTriesCount(), user);
	}
}
