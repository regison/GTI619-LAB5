package securityLayer.securityModule.gestionPassword;

import java.text.SimpleDateFormat;

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
	String passValidity= "";
	
	public String getPasswordConstraint(){
		validatePassword("aA%5");
		return passValidity;
	}
	
	public boolean validatePassword(String password){
		
		DataProvider dp = new DataProvider(Mysql.MYSQL_DATABASE_LOG619LAB5);
		boolean result = true;
		passValidity ="";
		PasswordPolitic pwp = dp.getPasswordPolitic();
		result = password.length() >= pwp.min && password.length() <= pwp.max;
		if(!result)
			passValidity+="Doit contenir entre "+ pwp.min + " et " + pwp.max + "caractères speciaux. ";
		if(result && (pwp.complexity & lowerCase) == lowerCase){
			result = password.matches(".*[a-z]");
			passValidity+="Doit contenir minuscules. ";
		}
		if(result && (pwp.complexity & upperCase) == upperCase){
			result = password.matches(".*[A-Z]+.*");
			passValidity+="Doit contenir majuscules. ";
		}
		if(result && (pwp.complexity & special) == special){
			result = password.matches(".*(?=[^a-z])(?=[^A-Z])(?=[^0-9])");
			passValidity+="Doit contenir caractères speciaux";
		}
		if(result && (pwp.complexity & chiffre) == chiffre){
			result = password.matches(".*[0-9]");
			passValidity+="Doit contenir chiffres";
		}
		return result;
	}
	
	public void savePasswordConfiguration(){
		//TODO
	}
	
	public boolean updatePassword(int userid, String currenUserName, String oldPassword, String newPassword){
		
		DataProvider dp = new DataProvider(Mysql.MYSQL_DATABASE_LOG619LAB5);
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
