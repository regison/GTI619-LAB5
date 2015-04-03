package securityLayer.securityModule.gestionPassword;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import communication.DataMapping.DataProvider;
import communication.DataObjects.Objects;
import communication.DataObjects.Objects.Log;
import communication.DataObjects.Objects.User;
import communication.DataObjects.Objects.PasswordPolitic;
import database.mysql.Mysql;

public class SecurityModulePassword {
	
	private final int upperCase= 1;
	private final int special = 2;
	private final int chiffre = 4;
	private final int lowerCase = 8;
	private Objects.PasswordPolitic pwp;
	
	public List<String> getPasswordConstraintMessage(){		
		return validatePassword("");
	}
			
	public List<String> validatePassword(String password){
		
		DataProvider dp = new DataProvider();
		PasswordPolitic pwp = dp.getPasswordPolitic();
		List<String> errors = new ArrayList<String>();
		if(!(password.length() >= pwp.min && password.length() <= pwp.max))
			errors.add("Doit contenir entre "+ pwp.min + " et " + pwp.max + "caractères.");
		if((pwp.complexity & lowerCase) == lowerCase){
			if(!password.matches(".*[a-z]"))
				errors.add("Doit contenir minuscules.");
		}
		if((pwp.complexity & upperCase) == upperCase){
			if(!password.matches(".*[A-Z]+.*"))
				errors.add("Doit contenir majuscules.");
		}
		if((pwp.complexity & special) == special){
			if(!password.matches(".*(?=[^a-z])(?=[^A-Z])(?=[^0-9])"))
				errors.add("Doit contenir caractères speciaux.");
		}
		if((pwp.complexity & chiffre) == chiffre){
			if(!password.matches(".*[0-9]"))
				errors.add("Doit contenir chiffres.");
		}
		return errors;
	}
	
	public void savePasswordConfiguration(){
		//TODO
	}
	
	public boolean updatePassword(int userid, String currenUserName, String oldPassword, String newPassword){
		
		DataProvider dp = new DataProvider();
		User u = dp.GetUser(userid);
		//Dependement du user on va lui set un saltcounter
		u.saltCounter = 0;
		//on va salter sur le nouveau password (selon le rolelevel)
		u.saltPassword = newPassword;
		
		u.ModifiedBy = currenUserName;
		u.ModifiedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");		
		
		return dp.UpdateUser(u);
		//TODO
		 
	}
}
