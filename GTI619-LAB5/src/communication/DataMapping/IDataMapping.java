package communication.DataMapping;

import java.util.ArrayList;

import communication.DataObjects.Objects.*;
import database.IDatabase;

public interface IDataMapping extends IDatabase{	
	//Lists	
	User GetUser(int userid);	
	Role GetUserRole(int userid);
	RoleLevel GetRoleLevel (int roleid, int userid);
	
	boolean CreateLog(Log event);
	boolean UpdateUser(User user);
	boolean UpdateUserRoleRights(int userid, int roleLevelId, boolean[] rights);
	//boolean Update
	
	
}
