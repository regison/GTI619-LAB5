package communication.DataMapping;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import log619lab5.domain.enumType.Section;
import communication.DataObjects.Objects.*;
import communication.DataObjects.Objects;
import communication.DataObjects.Objects.LoginPolitic;
import communication.DataObjects.Objects.PasswordPolitic;
import communication.DataObjects.Objects.LoginLog;
import communication.DataObjects.Objects.PreviousPassword;
import communication.DataObjects.QueryFactory;
import database.mysql.Mysql;

public class DataMapping implements IDataMapping {

	private Mysql cnx;
	public DataMapping() {		
		cnx = new Mysql(Mysql.MYSQL_DATABASE_LOG619LAB5, false);
	}
	/**
	 * Return all users with isAuthenticated and Needlogout from loginlog table
	 * @return
	 */
	public ArrayList<User> Users() {
		cnx.Open();
		ArrayList<ArrayList<Object>> usersMapping =  cnx.Select(QueryFactory.SELECT_ALL_USERS, null, "idUser","name","roleId","saltPassword","ndMd5Iteration", 
																								"ModifiedDate", "ModifiedBy","CreateDate","CreateBy","saltNumber", "saltCounter","enabled","LoggedIn","LogoutNeeded");
		cnx.Close();
		ArrayList<User> usersToShow = new ArrayList<User>();
		User toAdd = null;
		if (usersMapping.size() > 0){	
			System.out.println(usersMapping.size());
			
			for ( ArrayList<Object> user : usersMapping ){				
				toAdd = new Objects().new User();
				toAdd.idUser = Integer.parseInt(user.get(0).toString());
				toAdd.name = user.get(1).toString();
				toAdd.roleId = Integer.parseInt(user.get(2).toString());
				toAdd.saltPassword = user.get(3).toString();
				toAdd.nbCryptIteration = Integer.parseInt(user.get(4).toString());
				toAdd.ModifiedDate = user.get(5).toString();
				toAdd.ModifiedBy = user.get(6).toString();
				toAdd.CreateDate = user.get(7).toString();
				toAdd.CreateBy = user.get(8).toString();
				toAdd.salt = user.get(9).toString();
				toAdd.saltCounter = Integer.parseInt(user.get(10).toString());
				toAdd.enabled = Boolean.valueOf(user.get(11).toString());
				
				try {
					toAdd.isAuthenticated =  Boolean.valueOf(user.get(12).toString());
					toAdd.isLogOutNeeded = Boolean.valueOf(user.get(13).toString());
				} catch (Exception e) {
					toAdd.isAuthenticated =  false;
					toAdd.isLogOutNeeded = false;
				}				
				
				usersToShow.add(toAdd);
			}
		}		
		return usersToShow;
	}

	/**
	 * Returns all logs from the system
	 * @return
	 */
	public ArrayList<Log> Logs() {
		cnx.Open();
		ArrayList<ArrayList<Object>> systemLogMappingObject =  cnx.Select(QueryFactory.SELECT_ALL_LOGS, null, "idLog", "LogAction", "LogDate", "LogUserId");
		ArrayList<Log> systemLogs = null;
		cnx.Close();
		if (systemLogMappingObject.size() > 0 || systemLogMappingObject != null){
			systemLogs = new ArrayList<Log>();
			
			for (ArrayList<Object> log : systemLogMappingObject){
				Log logToAdd = new Objects().new Log();
				logToAdd.logId = Integer.parseInt(log.get(0).toString());
				logToAdd.logName = log.get(1).toString();
				logToAdd.logDate =log.get(2).toString();
				logToAdd.userLogId = Integer.parseInt(log.get(3).toString());
				
				systemLogs.add(logToAdd);
			}	
		}
		return systemLogs;		
	}

	/**
	 * Return an array of all roles in the DB
	 * @return
	 */
	public ArrayList<Role> Roles() {
		cnx.Open();
		ArrayList<ArrayList<Object>> rolesMapping =  cnx.Select(QueryFactory.SELECT_ALL_ROLES, null, "idog", "LogAction", "LogDate", "LogUserId");
		ArrayList<Role> roles = null;
		
		if (rolesMapping.size() > 0 || rolesMapping != null){
			roles = new ArrayList<Role>();
			for (ArrayList<Object> role : rolesMapping){
				Role roleToAdd = new Objects().new Role();
				roleToAdd.idRole = Integer.parseInt(role.get(0).toString());
				roleToAdd.roleLevelId = Integer.parseInt(role.get(1).toString());
				roleToAdd.roleName = role.get(2).toString();
				roleToAdd.timeConnexion = new SimpleDateFormat(role.get(3).toString());
				
				roles.add(roleToAdd);
			}		
		}	
		cnx.Close();
		return roles;
	}

	/**
	 * Returns an array of all rights in the DB
	 * @return
	 */
	public ArrayList<RoleLevel> RoleLevels() {
		cnx.Open();
		ArrayList<ArrayList<Object>> result =  cnx.Select(QueryFactory.SELECT_ALL_ROLELEVEL, null, 
															"idRoleLevel", "canEditOwnAccount", "canChangeMdp", 
															"canEditAll", "canModifyDelay","canModifynbTentative",
															"canModifyBlocage","canModifyComplexiteMdp");
		ArrayList<RoleLevel> rightLevels = null;
		
		if (result.size() > 0 || result != null){
			rightLevels = new ArrayList<RoleLevel>();
			for (ArrayList<Object> rights : result){
				RoleLevel rightToAdd = new Objects().new RoleLevel();
				rightToAdd.idRoleLevel = Integer.parseInt(rights.get(0).toString());
				rightToAdd.caneEditOwnAccount  = Boolean.parseBoolean(rights.get(1).toString());
				rightToAdd.canChangeMdp  = Boolean.parseBoolean(rights.get(2).toString());
				rightToAdd.canEditAll = Boolean.parseBoolean(rights.get(3).toString());
				rightToAdd.canModifyDelay =Boolean.parseBoolean(rights.get(5).toString());
				rightToAdd.canModifynbTentative =Boolean.parseBoolean(rights.get(6).toString());
				rightToAdd.canModifyBlocage =Boolean.parseBoolean(rights.get(7).toString());
				rightToAdd.canModifyComplexiteMdp =Boolean.parseBoolean(rights.get(8).toString());
				
				rightLevels.add(rightToAdd);
			}			
		}		
		
		cnx.Close();		
		return rightLevels;
	}

	/***
	 *  Get a role from his role ID
	 */
	@Override
	public Role GetRole(int roleid) {
		cnx.Open();
		ArrayList<ArrayList<Object>> result = cnx.Select(QueryFactory.SELECT_USER_ROLE, new String[] {roleid + ""}, "idRole", "roleLevelId", "roleName", "timeConnexion");
		Role role = null;
		
		if (result.size() == 1 && result != null){
			role = new Objects().new Role();
			role.idRole = Integer.parseInt(result.get(0).get(0).toString());
			role.roleLevelId = Integer.parseInt(result.get(0).get(1).toString());
			role.roleName = result.get(0).get(2).toString();
			role.timeConnexion = new SimpleDateFormat(result.get(0).get(3).toString());
		}
		cnx.Close();
		return role;
	}


	/***
	 *  Get a rolelevel from his role ID
	 * */
	@Override
	public RoleLevel GetRoleLevel(int roleLevelId) {
		cnx.Open();

		ArrayList<ArrayList<Object>> result = cnx.Select(QueryFactory.SELECT_USER_ROLELEVEL, new String[] {roleLevelId + ""}, "idRoleLevel", "caneEditOwnAccount", "canChangeMdp", "canEditAll", 
				"canModifyDelay", "canModifynbTentative", "canModifyBlocage", "canModifyComplexiteMdp");

		RoleLevel userRL = null;
		if (result.size() == 1){
			userRL = new Objects().new RoleLevel();
		
			userRL.idRoleLevel = Integer.parseInt(result.get(0).get(0).toString());
			userRL.caneEditOwnAccount = Integer.parseInt(result.get(0).get(1).toString()) == 1 ? true : false;
			userRL.canChangeMdp = Integer.parseInt(result.get(0).get(2).toString()) == 1 ? true : false;
			userRL.canEditAll = Integer.parseInt(result.get(0).get(3).toString()) == 1 ? true : false;
			userRL.canModifyDelay = Integer.parseInt(result.get(0).get(4).toString()) == 1 ? true : false;
			userRL.canModifynbTentative = Integer.parseInt(result.get(0).get(5).toString()) == 1 ? true : false;
			userRL.canModifyBlocage = Integer.parseInt(result.get(0).get(6).toString()) == 1 ? true : false;
			userRL.canModifyComplexiteMdp = Integer.parseInt(result.get(0).get(7).toString()) == 1 ? true : false;
		}
		cnx.Close();
		return userRL;
	}

	
	@Override
	public User GetUserByID(int userid) {
		cnx.Open();
		ArrayList<ArrayList<Object>> result = cnx.Select(QueryFactory.SELECT_USER_BYID, new String[] {userid + ""}, "idUser","name","roleId","saltPassword","ndMd5Iteration", 
		 																								"ModifiedDate", "ModifiedBy","CreateDate","CreateBy","saltNumber", "saltCounter","enabled","LoggedIn","LogoutNeeded");
		User user = null;
		if (result.size() == 1){
			user = new Objects().new User();
			user.idUser = Integer.parseInt(result.get(0).get(0).toString());
			user.name = result.get(0).get(1).toString();
			user.roleId = Integer.parseInt(result.get(0).get(2).toString());
			user.saltPassword = result.get(0).get(3).toString();
			user.nbCryptIteration = Integer.parseInt(result.get(0).get(4).toString());
			user.ModifiedDate = result.get(0).get(5).toString();
			user.ModifiedBy = result.get(0).get(6).toString();
			user.CreateDate = result.get(0).get(7).toString();
			user.CreateBy = result.get(0).get(8).toString();
			user.salt = result.get(0).get(9).toString();
			user.saltCounter = Integer.parseInt(result.get(0).get(10).toString());
			user.enabled = Boolean.valueOf(result.get(0).get(11).toString());	
			user.isAuthenticated =  Boolean.valueOf(result.get(0).get(12).toString());
			user.isLogOutNeeded =  Boolean.valueOf(result.get(0).get(13).toString());
		}
		cnx.Close();
		return user;
	}


	public boolean CreateLog(Log event, boolean byPass) {
	
		cnx.Open();
		int value = cnx.insert(QueryFactory.INSERT_LOG, 
						new String[] { String.valueOf(event.logId), event.logName, String.valueOf(event.logDate), String.valueOf(event.userLogId) });
		cnx.Close();
		if (value == 1)
			return true;
		
		return false;
	}


	@Override
	public boolean UpdateUser(User user) {
		cnx.Open();
		
		cnx.Close();
		return false;
	}

		
	public static void main (String [] args){
		DataProvider m = new DataProvider();

		/*ArrayList<User> users = m.Users();
		
		for (User u : users){
			System.out.println(u.idUser);		
			}*/
		//m.RemoveUser(4);
		
	}

	@Override
	public boolean UpdateUserRoleRights(int userid, int roleLevelId, int... rights) {
		cnx.Open();
		// TODO Auto-generated method stub
		cnx.Close();
		return false;
	}

	@Override
	public ArrayList<Objects.User> GetAllUsersFromAUserName(String uname) {
		cnx.Open();
		ArrayList<ArrayList<Object>> result = cnx.Select(QueryFactory.SELECT_USER_BY_UNAME, new String[] {uname}, "idUser","name","roleId","ndMd5Iteration", 
																										"ModifiedDate", "ModifiedBy","CreateDate","CreateBy","saltNumber", "saltCounter","enabled");
		cnx.Close();
		
		if(result == null)
			return null;
		
		ArrayList<Objects.User> allUsers = new ArrayList<Objects.User>();
		
		User user;
		
		for (int i = 0; i < result.size(); i++) {
			user = null;
			if (result.get(i) != null && result.get(i).size() > 0) {
				user = new Objects().new User();

				user.idUser = Integer.parseInt(result.get(i).get(0).toString());
				user.name = result.get(i).get(1).toString();
				user.roleId = Integer.parseInt(result.get(i).get(2).toString());
				user.nbCryptIteration = Integer.parseInt(result.get(i).get(3)
						.toString());
				user.ModifiedDate = result.get(i).get(4)
						.toString();
				user.ModifiedBy = result.get(i).get(5).toString();
				user.CreateDate = result.get(i).get(6)
						.toString();
				user.CreateBy = result.get(i).get(7).toString();
				user.salt = result.get(i).get(8).toString();
				user.saltCounter = Integer.parseInt(result.get(i).get(9)
						.toString());
				user.enabled = Boolean
						.valueOf(result.get(i).get(10).toString());
				
				allUsers.add(user);
			}
		}
		return allUsers;
	}

	@Override
	public User AuthenticateUser(String uname, String pwd) {
		
		ArrayList<Objects.User> users = GetAllUsersFromAUserName(uname);
		
		if (users != null){
			for (int j = 0; j < users.size(); j++) {
				if (users.get(j) != null) {
					String query = QueryFactory.SELECT_USER_BY_UNAME_PWD;
					for (int i = 1; i < users.get(j).nbCryptIteration; i++) {
						query += "SHA2(";
					}
					query += "'";
					for (int i = 0; i < users.get(j).saltCounter; i++) {
						query += users.get(j).salt;
					}
					query += "'?'";
					for (int i = 0; i < users.get(j).saltCounter; i++) {
						query += users.get(j).salt;
					}
					query += "'";
					for (int i = 1; i < users.get(j).nbCryptIteration; i++) {
						query += ", 512)";
					}
					query += ", 512);";
					cnx.Open();
					ArrayList<ArrayList<Object>> result = cnx.Select(query,
							new String[] { uname, pwd }, "idUser", "name",
							"roleId", "enabled");
					cnx.Close();
					if(result != null && result.size() == 1){
						return users.get(j);
					}
				}
			}
		}
		
		return null;
	
		
	}	

	@Override
	public LoginLog GetLoginLogsByUserId(int userID) {
		cnx.Open();
		ArrayList<ArrayList<Object>> result = cnx.Select(QueryFactory.SELECT_USER_LOGINLOGS_BY_USERID, new String[] {userID + ""}, "idUser", "LastLoginTime", "FailedTriesCount", "LoggedIn", "LogoutNeeded");
		LoginLog loginLog = null;
		if (result.size() == 1 && result != null){
			loginLog = new Objects().new LoginLog();
			
			 loginLog.logoutNeeded = Boolean.parseBoolean(result.get(0).get(4).toString());
			 loginLog.loggedIn = Boolean.parseBoolean(result.get(0).get(3).toString());
			 loginLog.failedTriesCount = Long.parseLong(result.get(0).get(2).toString());
			 loginLog.lastloginTime = java.sql.Timestamp.valueOf(result.get(0).get(1).toString());
			 loginLog.userId = Integer.parseInt(result.get(0).get(0).toString());
			 
		 }
		cnx.Close();
		return loginLog;
	}

	@Override
	public int UpdateUserInfos(boolean incrementFailedLoginTriesCount,
			boolean loggedIn, long userFailedTriesCount, boolean LogoutNeeded, int userID) {
		cnx.Open();
		
		int toreturn = cnx.update(QueryFactory.UPDATE_LOGING_lOG, 
				new String[] {((incrementFailedLoginTriesCount ? userFailedTriesCount + 1 : (loggedIn ? 0 : userFailedTriesCount)) + ""), 
							   (loggedIn ? "1" : "0"), (LogoutNeeded ? "1" : "0"), userID + ""});
		cnx.Close();
		return toreturn;
	}

	@Override
	public boolean CreateLoginLog(boolean incrementFailedLoginTriesCount,LoginLog llog) {
		cnx.Open();
		int value = cnx.insert(QueryFactory.INSERT_LOGINLOG, 
						new String[] {llog.userId + "", (incrementFailedLoginTriesCount ? "1" : "0"), 
														(llog.loggedIn ? "1" : "0"), (llog.logoutNeeded ? "1" : "0") });
		cnx.Close();
		if(value==1)
			return true;
		return false;	
		
	}	
	
	@Override
	public boolean CreateUser(String username, String password, int userType, String salt) {
	
		cnx.Open();
		boolean isUserNameExist = AuthenticateUser(username, password) != null;			
		cnx.Close();
		
		if (isUserNameExist)			
			return false;
		else{	
			User user = new Objects().new User();
			
			user.name = username;
			user.saltCounter = (int) Math.floor (Math.random() * (1 + 10 - 1)) + 1;
			user.nbCryptIteration = (int) Math.floor (Math.random() * (1 + 10 - 5)) + 5;
			user.salt = salt;
			
			String saltPwdBuilder = "SHA2(";
			for(int i = 1; i < user.nbCryptIteration; i++){
				saltPwdBuilder += "SHA2(";
			}
			saltPwdBuilder += "'";
			for(int i = 0; i < user.saltCounter; i++){
				saltPwdBuilder += user.salt;
			}
			saltPwdBuilder += "'?'";
			for(int j = 0; j < user.saltCounter; j++){
				saltPwdBuilder += user.salt;
			}
			saltPwdBuilder += "'";
			for(int i = 1; i < user.nbCryptIteration; i++){
				saltPwdBuilder += ", 512)";
			}
			saltPwdBuilder += ", 512));";
			
			user.CreateBy = "currentUser";
			user.CreateDate = new SimpleDateFormat().format(new Date());
			user.ModifiedBy = "currentUSer";
			user.ModifiedDate = new SimpleDateFormat().format(new Date());
			user.isAuthenticated = false;
			user.enabled = true;
			user.isLogOutNeeded = false;
			user.roleId = userType;
			user.saltPassword = password;
			user.crypVersion = 1;
			
			cnx.Open();
			int row = cnx.insert(QueryFactory.INSERT_USER + saltPwdBuilder, new String[] {  user.name, String.valueOf(user.roleId) , user.nbCryptIteration +"",
															user.ModifiedDate,user.ModifiedBy,user.CreateDate, user.CreateBy, user.salt, user.saltCounter + "", user.enabled ? "1" : "0", 
															String.valueOf( user.crypVersion), user.saltPassword});
			cnx.Close();
			if(row > -1)
				return true;
		}
		return false;
	}

	public PasswordPolitic getPasswordPolitic() {
		// TODO Auto-generated method stub
		PasswordPolitic pwp =  new Objects().new PasswordPolitic();
		return pwp;
	}
	public LoginPolitic getLoginPolitic() {
		// TODO Auto-generated method stub
		LoginPolitic pwp =  new Objects().new LoginPolitic();
		return pwp;
	}
	@Override
	public ArrayList<PreviousPassword> GetUserPreviousPasswordByID(int userid) {
		// TODO Auto-generated method stub
		cnx.Open();
		ArrayList<ArrayList<Object>> result = cnx.Select(QueryFactory.SELECT_USER_PREVIOUS_PASSWORDS, new String[] {userid + ""}, "idpreviousPasswords","userID","previousPassword","dateModified");
		cnx.Close();
		 if (result.size() <= 0)
			 return null;
		 
		 ArrayList<PreviousPassword> upp = new ArrayList<PreviousPassword>();
		 	if (result.size() > 0){
				
				PreviousPassword pPwd = null ;
				
				for(ArrayList<Object> obj : result){
					pPwd = new Objects().new PreviousPassword();
					
					pPwd.idPreviousPassword = Integer.parseInt(obj.get(0).toString());
					pPwd.userID = Integer.parseInt(obj.get(1).toString());
					pPwd.previousPassword = obj.get(2).toString();
					pPwd.ModifiedDate = obj.get(3).toString();
					
					upp.add(pPwd);
				}	
		}
		return upp;
	}
	@Override
	public boolean CreatePreviousPasswordHistory(PreviousPassword pp) {
		cnx.Open();
		
		int value = cnx.insert(QueryFactory.INSERT_PREVIOUS_PASSWORD, 
				new String[] { String.valueOf(pp.userID), pp.previousPassword, pp.ModifiedDate });
		cnx.Close();

		return false;
	}
	@Override
	public boolean UpdateUserPassword(int userid, String oldPassword, String password) {
		
		return false;
	}
	@Override
	public boolean RemoveUser(int userid) {

	
		
		User user = GetUserByID(userid);
		
		if (user != null)
		{
			System.out.println("user exists");
			//Un utilisateur connecté ne doit pas se supprimer lui-meme
			if (user.isAuthenticated)
				return false;
	
			cnx.Open();
			//int value = cnx.delete(QueryFactory.DELETE_USER, new String[] { userid + ""});
			System.out.println(cnx.delete(QueryFactory.DELETE_USER, new String[] { userid + ""}));
			//if (value == 0)
			cnx.Close();
			return true;
		}
		cnx.Close();
		return false;
	}

	
}
