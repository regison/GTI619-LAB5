package securityLayer.securityModule.gestionPassword;

import communication.DataMapping.DataProvider;
import communication.DataObjects.Objects.PasswordPolitic;

public class SecurityModulePassword {

	private final int upperCase= 1;
	private final int special = 2;
	private final int chiffre = 4;
	private final int lowerCase = 8;
	
	DataProvider dp = new DataProvider();
	
	public boolean validatePassword(String password){
		
		boolean result = true;
		
		PasswordPolitic pwp = dp.getPasswordPolitic();
		result = password.length() >= pwp.min && password.length() <= pwp.max;
		
		if(result && (pwp.complexity & upperCase) == upperCase){
			result = password.matches(".*[A-Z]+.*");
		}
		if(result && (pwp.complexity & special) == upperCase){
			result = password.matches(".*(?=[^a-z])(?=[^A-Z])(?=[^0-9])");
		}
		if(result && (pwp.complexity & chiffre) == chiffre){
			result = password.matches(".*[0-9]");
		}
		if(result && (pwp.complexity & lowerCase) == lowerCase){
			result = password.matches(".*[a-z]");
		}
		return result;
	}
	
	public void savePasswordConfiguration(){
		//TODO
	}
	
	public boolean updatePassword(String username, String oldPassword, String newPassword){
		//TODO
		return true;
	}
}
