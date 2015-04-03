package communication.DataMapping;

import java.util.ArrayList;

import communication.DataObjects.Objects;
import communication.DataObjects.Objects.*;
/**
 * Classe qui doit etre appelé par les modules externes
 * Pages JSP, modules de sécurité
 * @author r.registe
 *
 */

public class DataProvider {

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
		return data.GetUserByID(userid);
	}
	
	public Role GetRole(int roleid){
		return data.GetRole(roleid);
	}
	
	public PasswordPolitic getPasswordPolitic(){
		return data.getPasswordPolitic();
	}
	
	public LoginPolitic getLoginPolitic(){
		return data.getLoginPolitic();
	}
	
	public ArrayList<Objects.User> GetAllUsersFromAUserName(String userName) {
		return data.GetAllUsersFromAUserName(userName);
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

	
	public boolean CreateLog(Log event, boolean byPass) {
		return data.CreateLog(event, byPass);
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
	
	public boolean UpdatePolitics(PasswordPolitic pwp, LoginPolitic lop){
		//TODO
		return true;
		
	}
  
}

