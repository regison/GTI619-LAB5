package securityLayer.securityModule.Core;

import javax.servlet.http.HttpSession;

import communication.DataObjects.Objects.Role;
import communication.DataObjects.Objects.RoleLevel;
import communication.DataObjects.Objects.User;
import securityLayer.securityModule.verifyUserValidity.VerifyUserValidity;

public class SecurityModuleCore {

	private User user;
	private Role role;
	private RoleLevel roleLevel;
	private HttpSession session;
	
	public SecurityModuleCore(User user, Role role, RoleLevel roleLevel, HttpSession session){
		this.user = user;
		this.role = role;
		this.roleLevel = roleLevel;
		this.session = session;
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
}
