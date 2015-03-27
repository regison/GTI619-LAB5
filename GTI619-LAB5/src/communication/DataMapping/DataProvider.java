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
	public DataProvider(short database){
		data = new DataMapping(database);
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
		return data.GetUserByID(userid);
	}
	
	public Role GetRole(int roleid){
		return data.GetRole(roleid);
	}

	public void Dispose(){
	  if (data != null){
		  data.Close();
	  }
	}
	public void Open(){		
	}

	public User GetUserByUserName(String userName) {
		return data.GetUserByUserName(userName);
	}
	
	public User Authenticate(String uname, String pwd) {
		return data.AuthenticateUser(uname, pwd);
	}

	
	public RoleLevel GetRoleLevel(int roleLevelId) {
		return data.GetRoleLevel(roleLevelId);
	}

	
	public LoginLog GetLoginLogsByUserId(int userID) {
		return data.GetLoginLogsByUserId(userID);
	}

	
	public boolean CreateLog(Log event) {
		return data.CreateLog(event);
	}

	
	public boolean UpdateUser(User user) {

		return data.UpdateUser(user);
	}

	
	public boolean UpdateUserRoleRights(int userid, int roleLevelId,
			int... rights) {
		return false;
	}
	
	public int UpdateLoginLogsByUserId(boolean incrementFailedLoginTriesCount, boolean loggedIn, long userFailedTriesCount,boolean LogoutNeeded, int user){
		return data.UpdateUserInfos(incrementFailedLoginTriesCount, loggedIn, userFailedTriesCount, LogoutNeeded, user);
	}
	
	public boolean CreateLoginLog(boolean incrementFailedLoginTriesCount,LoginLog llog){
		return data.CreateLoginLog(incrementFailedLoginTriesCount, llog);
	}

	public boolean CreateUser(String username, String tpw, int type) {

		return data.CreateUser(username, tpw, type);
		
	}
  
}

