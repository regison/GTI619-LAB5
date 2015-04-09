package securityLayer.securityModule.Core;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import communication.DataMapping.DataProvider;
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
	
	public void updateSuccessfullLoginTime(int userID){
		
		dtp = new DataProvider(false);
		
		try {	
			session.setAttribute("failedLoginCount", "0");
			dbComm.UpdateUserInfo(userID, false, true, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void incrementUnsuccessfullLogin(){
		
		dtp = new DataProvider(false);
		
		try {
			dbComm.UpdateUserInfo(user.idUser, true, false, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void manageUnsuccessfullLogin(){
		if(user != null){
			incrementUnsuccessfullLogin();
		}
		BruteForceProtection bruteProtect = new BruteForceProtection();
		bruteProtect.manageLoginBruteForce(session, dbComm == null ? 0 : dbComm.UserFailedTriesCount());
	}
	
	public void manageUnsuccessfullLogin(String username){
		if(user != null){
			incrementUnsuccessfullLogin();
		}
		BruteForceProtection bruteProtect = new BruteForceProtection();
		bruteProtect.manageLoginBruteForce(session, dbComm == null ? 0 : dbComm.UserFailedTriesCount());
	}
}
