package securityLayer.securityModule.verifyUserValidity;

import communication.DataObjects.Objects.User;

public class VerifyUserValidity{
	
	private User user;
	public VerifyUserValidity(User user){
		
	}
	
	public boolean verifyUserHasTimedOut(){
		if(user == null)
			return false;
		
		return true;
	}
}
