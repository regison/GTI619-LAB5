package securityLayer.securityModule.Core;

import javax.servlet.http.HttpSession;

import com.sun.org.apache.bcel.internal.generic.DMUL;

import communication.DataObjects.Objects.Role;
import communication.DataObjects.Objects.RoleLevel;
import communication.DataObjects.Objects.User;
import securityLayer.securityModule.Comm.SecurityLayerDataBaseCommunication;
import securityLayer.securityModule.bruteforceProtection.BruteForceProtection;
import securityLayer.securityModule.verifyUserValidity.VerifyUserValidity;

public class SecurityModuleCore {

	private User user;
	private HttpSession session;
	private SecurityLayerDataBaseCommunication dbComm;
	
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
	
	public boolean identifiedUserCanContinueAction(){
		
		return true;
	}
	
	public void updateSuccessfullLoginTime(){
		try {
			session.setAttribute("failedLoginCount", "0");
			dbComm.UpdateUserInfo(false, true, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void incrementUnsuccessfullLogin(){
		try {
			dbComm.UpdateUserInfo(true, false, false);
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
		bruteProtect.manageLoginBruteForce(user, session, dbComm == null ? 0 : dbComm.UserFailedTriesCount());
	}
}
