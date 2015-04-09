package securityLayer.securityModule.gestionPassword;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import securityLayer.securityModule.XSSProtection.HiddenStringGenerator;
import communication.DataMapping.DataProvider;
import communication.DataObjects.Objects;
import communication.DataObjects.Objects.*;
import database.mysql.Mysql;

public class SecurityModulePassword {
	
	private final int upperCase= 1;
	private final int special = 2;
	private final int chiffre = 4;
	private final int lowerCase = 8;
	
	public List<String> getPasswordConstraintMessage(){		
		return validatePassword("");
	}
			
	public List<String> validatePassword(String password){
		
		DataProvider dp = new DataProvider(false);
		List<String> errors =  new ArrayList<String>();
		try{
			PasswordLoginPolitic pwp = dp.getPasswordLoginPolitic();
			int complexity = pwp.complexity;
			errors = new ArrayList<String>();
			if(!(password.length() >= pwp.min && password.length() <= pwp.max))
				errors.add("Doit contenir entre "+ pwp.min + " et " + pwp.max + "caractères.");
			if((complexity & lowerCase) == lowerCase){
				if(!password.matches(".*[a-z]+.*"))
					errors.add("Doit contenir minuscules.");
			}
			if((complexity & upperCase) == upperCase){
				if(!password.matches(".*[A-Z]+.*"))
					errors.add("Doit contenir majuscules.");
			}
			if((complexity & special) == special){
				if(!password.matches(".*(?=[^a-z])(?=[^A-Z])(?=[^0-9])+.*"))
					errors.add("Doit contenir caractères speciaux.");
			}
			if((complexity & chiffre) == chiffre){
				if(!password.matches(".*[0-9]+.*"))
					errors.add("Doit contenir chiffres.");
			}
		}
		catch(Exception e){
			errors.add("configuration pas trouvée");
		}
		return errors;
	}
	
	public void savePasswordConfiguration(){
		//TODO
	}
	
	public boolean updatePassword(int userid, String currenUserName, String newPassword){
		
		DataProvider dp = new DataProvider(false);
		User u = dp.GetUser(userid);
	
		ArrayList<PreviousPassword> allpp = dp.selectAllPreviousPasswordsUnauthorised(u.idUser, newPassword);
		
		if(allpp != null && allpp.size() > 0)
			return false;
		
		Objects obj = new Objects();
		PreviousPassword pp = obj.new PreviousPassword();
		pp.userID = u.idUser;
		pp.previousPassword = u.saltPassword;
		pp.ModifiedDate = new SimpleDateFormat().format(new Date());
		pp.nbCryptIteration = u.nbCryptIteration;
		pp.salt = u.salt;
		pp.saltCounter = u.saltCounter;
		pp.cryptVersion = u.crypVersion;
		dp.CreatePreviousPasswordHistory(pp);
		
		u.saltCounter = (int) Math.floor (Math.random() * (1 + 10 - 1)) + 1;
		u.nbCryptIteration = (int) Math.floor (Math.random() * (1 + 10 - 5)) + 5;
		u.salt = generateHiddenRandomString();
		
		dp.UpdateUser(u);
		dp.UpdateUserPassword(u, newPassword);
		
		return true;
		 
	}
	
	public String generateHiddenRandomString(){
		HiddenStringGenerator hiddenGenerator = new HiddenStringGenerator();
		return hiddenGenerator.generateRandomString();
	}
}
