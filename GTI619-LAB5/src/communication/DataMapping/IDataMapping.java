package communication.DataMapping;

import java.util.ArrayList;

import communication.DataObjects.Objects.*;

public interface IDataMapping{	

	ArrayList<User> GetAllUsersFromAUserName(String uname);
	ArrayList<PreviousPassword> GetUserPreviousPasswordByID(int userid);
	User GetUserByID(int userid);
	User GetUserByUsername(String uname);
	
	User AuthenticateUser(String uname, String pwd);	
	Role GetRole(int roleid);
	RoleLevel GetRoleLevel (int rolelevelid);	
	LoginLog GetLoginLogsByUserId(int userid);

	
	boolean CreateLog(Log event, boolean byPass);
	boolean CreateLoginLog(boolean incrementFailedLoginTriesCount,LoginLog llog);
	boolean CreateUser(String username, String password, int userType, String salt);
	boolean CreatePreviousPasswordHistory(PreviousPassword pp);
	
	boolean UpdateUser(User user);
	boolean UpdateUserRoleLevel(RoleLevel rlevel);
	int UpdateUserInfos(boolean incrementFailedLoginTriesCount, boolean loggedIn, long userFailedTriesCount,boolean LogoutNeeded, int user);
	boolean UpdateUserPassword(int userid, String oldPassword, String password);
	
	
	boolean RemoveUser(int userid);
	
	
	
}
