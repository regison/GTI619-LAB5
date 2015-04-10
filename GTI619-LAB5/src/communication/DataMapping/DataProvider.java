package communication.DataMapping;

import java.util.ArrayList;

import securityLayer.securityModule.Core.SecurityModuleCore;
import communication.DataObjects.Objects;
import communication.DataObjects.QueryFactory;
import communication.DataObjects.Objects.*;
/**
 * Classe qui doit etre appelé par les modules externes
 * Pages JSP, modules de sécurité
 * @author r.registe
 *
 */

public class DataProvider {

	private DataMapping data;
	public DataProvider(boolean bypasLogs){
		data = new DataMapping(bypasLogs);
	}
	
	public DataProvider(){
		data = new DataMapping(false);
	}
	
	//Select users from db
	public ArrayList<User> Users(){	  
	  return  data.Users();
	}
	
	public ArrayList<Role> Roles(){	  
		  return  data.Roles();
	}
	
	//Select all logs from db	
	public ArrayList<Log> getLogs(){
	  return data.Logs();
	}
	
	//Select 10 most recent logs from db	
		public ArrayList<Log> getMostRecentLogs(){
		  return data.mostRecentLogs();
		}
	
	public User GetUser(int userid){
		return data.GetUserByID(userid);
	}
	
	public Role GetRole(int roleid){
		return data.GetRole(roleid);
	}
	
	public PasswordLoginPolitic getPasswordLoginPolitic(){
		return data.getPasswordLoginPolitic();
	}
	
	public ArrayList<PreviousPassword> GetUserPreviousPasswordByID(int userid){
		return data.GetUserPreviousPasswordByID(userid);
	}
	
	public ArrayList<Objects.User> GetAllUsersFromAUserName(String userName) {
		return data.GetAllUsersFromAUserName(userName);
	}
	
	public User GetUserByUsername(String userName) {
		return data.GetUserByUsername(userName);
	}
	
	public User Authenticate(String uname, String pwd, SecurityModuleCore secMod) {
		return data.AuthenticateUser(uname, pwd, secMod);
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

	
	public boolean UpdateUserRoleLevel(RoleLevel rlevel){
		return data.UpdateUserRoleLevel(rlevel);
	}
	
	public int UpdateLoginLogsByUserId(boolean incrementFailedLoginTriesCount, boolean loggedIn, long userFailedTriesCount,boolean LogoutNeeded, int user){
		return data.UpdateUserInfos(incrementFailedLoginTriesCount, loggedIn, userFailedTriesCount, LogoutNeeded, user);
	}
	
	public boolean CreateLoginLog(boolean incrementFailedLoginTriesCount,LoginLog llog){
		return data.CreateLoginLog(incrementFailedLoginTriesCount, llog);
	}

	public boolean CreateUser(String username, String password, int userType, String salt, String actualUser, Boolean changePw){
		return data.CreateUser(username, password, userType, salt, actualUser, changePw);		
	}
	
	public boolean UpdatePolitics(PasswordLoginPolitic pwp){
		return data.UpdatePolitics(pwp);
	}
	public boolean RemoveUser(int userid){
		return data.RemoveUser(userid);
	}

	public boolean CreatePreviousPasswordHistory(PreviousPassword pp) {
		return data.CreatePreviousPasswordHistory(pp);
	}
	
	public ArrayList<PreviousPassword> selectAllPreviousPasswordsUnauthorised(int userid, String oldPassword){
		return data.selectAllPreviousPasswordsUnauthorised(userid, oldPassword);
	}
	
	public boolean UpdateUserPassword(User user, String password){
		return data.UpdateUserPassword(user, password);
	}
	
	public boolean IpIsBlackListed(String ipAddress){
		return data.IpIsBlackListed(ipAddress);
	}
}

