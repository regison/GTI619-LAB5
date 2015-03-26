package securityLayer.securityModule.gestionPassword;

import java.text.SimpleDateFormat;

import communication.DataMapping.DataProvider;
import communication.DataObjects.Objects.User;
import database.mysql.Mysql;


public class SecurityModulePassword {

	public boolean validatePassword(String password){
		//TODO
		return true;
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
