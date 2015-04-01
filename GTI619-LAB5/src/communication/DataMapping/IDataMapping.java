package communication.DataMapping;

import java.util.ArrayList;

import communication.DataObjects.Objects.User;
import communication.DataObjects.Objects.*;

public interface IDataMapping{	

	User GetUserByID(int userid);	

	User AuthenticateUser(String uname, String pwd);
	ArrayList<User> GetAllUsersFromAUserName(String uname);
	
	Role GetRole(int roleid);
	RoleLevel GetRoleLevel (int rolelevelid);
	LoginLog GetLoginLogsByUserId(int userid);

	
	boolean CreateLog(Log event);
	boolean CreateLoginLog(boolean incrementFailedLoginTriesCount,LoginLog llog);
	boolean CreateUser(String username, String password, int userType);
	
	boolean UpdateUser(User user);
	boolean UpdateUserRoleRights(int userid, int roleLevelId, int... rights);
	int UpdateUserInfos(boolean incrementFailedLoginTriesCount, boolean loggedIn, long userFailedTriesCount,boolean LogoutNeeded, int user);
	//boolean Update
	
	
}
