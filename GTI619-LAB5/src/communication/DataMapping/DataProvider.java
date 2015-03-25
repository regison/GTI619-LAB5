package communication.DataMapping;

import java.util.ArrayList;

import communication.DataObjects.Objects.Log;
import communication.DataObjects.Objects.LoginLog;
import communication.DataObjects.Objects.Role;
import communication.DataObjects.Objects.RoleLevel;
import communication.DataObjects.Objects.User;
import communication.DataObjects.Objects.*;
/**
 * Classe qui doit etre appelé par les modules externes
 * Pages JSP, modules de sécurité
 * @author r.registe
 *
 */

public class DataProvider { //Une interface c inutile dans notre cas, sauf si tu pense avoir de multiples dataprovider et un accesseur généralisé qui pourait avoir une liste de DataProvider

	private DataMapping data;
	public DataProvider(){
		data = new DataMapping();
	}
	
	//Select users from db
	public ArrayList<User> Users(){	  
	  return  data.Users();
	}
	//Select all logs from db	
	public ArrayList<Log> getLogs(){
	  return data.Logs();
	}
	public User GetUser(int userid){
		return data.GetUser(userid);
	}
	
	public Role GetRole(int roleid){
		return data.GetUserRole(roleid);
	}

	public void Dispose(){
	  if (data != null){
		  data.Close();
	  }
	}

	public User GetUserByUserName(String userName) {
		// TODO Auto-generated method stub
		if (userName != null){
			return data.GetUserByUserName(userName);
		}
		return null;
	}

	
	public User GetUserByUNameSaltPwd(User user, String uname, String pwd) {
		// TODO Auto-generated method stub
		return data.GetUserByUNameSaltPwd(user, uname, pwd);
	}

	
	public Role GetUserRole(int roleid) {
		// TODO Auto-generated method stub
		return data.GetUserRole(roleid);
	}

	
	public RoleLevel GetRoleLevel(int roleid) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public LoginLog GetLoginLogsByUserId(int user) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public boolean CreateLog(Log event) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean UpdateUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean UpdateUserRoleRights(int userid, int roleLevelId,
			int... rights) {
		// TODO Auto-generated method stub
		return false;
	}
  
}

