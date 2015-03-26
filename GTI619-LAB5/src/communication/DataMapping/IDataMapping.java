package communication.DataMapping;

import communication.DataObjects.Objects.*;

public interface IDataMapping{	
	//Lists	
	User GetUser(int userid);	

	User GetUserByUserName(String uname);
	User GetUserByUNameSaltPwd(User user,String uname, String pwd);
	
	Role GetUserRole(int roleid);
	RoleLevel GetRoleLevel (int roleid);
	LoginLog GetLoginLogsByUserId(int user);


	
	boolean CreateLog(Log event);
	boolean CreateLoginLog(LoginLog llog);
	boolean UpdateUser(User user);
	boolean UpdateUserRoleRights(int userid, int roleLevelId, int... rights);
	void UpdateUserInfos(boolean incrementFailedLoginTriesCount, boolean loggedIn, long userFailedTriesCount,boolean LogoutNeeded, int user);
	//boolean Update
	
	
}
