package communication.DataMapping;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import communication.DataObjects.Objects.*;
import communication.DataObjects.Objects;

import communication.DataObjects.QueryFactory;
import database.mysql.Mysql;

public class DataMapping implements IDataMapping {

	private Mysql cnx;
	public DataMapping() {		
		cnx= new Mysql(Mysql.MYSQL_DATABASE_LOG619LAB5);
	}

	public ArrayList<User> Users() {
		cnx.Open();
		ArrayList<ArrayList<Object>> usersMapping =  cnx.Select(QueryFactory.SELECT_ALL_USERS, null, "idUser","name","roleId","saltPassword","ndMd5Iteration", 
																								"ModifiedDate", "ModifiedBy","CreateDate","CreateBy",																						"saltNumber", "saltCounter","enabled");
		ArrayList<User> usersToShow = new ArrayList<User>();
		
		if (usersMapping != null && usersMapping.size() > 0){		
			for ( ArrayList<Object> user : usersMapping ){				
				User toAdd = new Objects().new User();
				toAdd.idUser = Integer.parseInt(user.get(0).toString());
				toAdd.name = user.get(1).toString();
				toAdd.roleId = Integer.parseInt(user.get(2).toString());
				toAdd.saltPassword = user.get(3).toString();
				toAdd.nbCryptIteration = Integer.parseInt(user.get(4).toString());
				toAdd.ModifiedDate = new SimpleDateFormat(user.get(5).toString());
				toAdd.ModifiedBy = user.get(6).toString();
				toAdd.CreateDate = new SimpleDateFormat(user.get(7).toString());
				toAdd.CreateBy = user.get(8).toString();
				toAdd.salt = user.get(9).toString();
				toAdd.saltCounter = Integer.parseInt(user.get(10).toString());
				toAdd.enabled = Boolean.valueOf(user.get(11).toString());
				
				usersToShow.add(toAdd);

			}
		}
			cnx.Close();
		
		return usersToShow;
	}

	/**
	 * Returns all logs from the system
	 * @return
	 */
	public ArrayList<Log> Logs() {
		cnx.Open();
		ArrayList<ArrayList<Object>> systemLogMappingObject =  cnx.Select(QueryFactory.SELECT_ALL_LOGS, null, "idLog", "LogAction", "LogDate", "LogUserId");
		ArrayList<Log> systemLogs = new ArrayList<Log>();
		
		if (systemLogMappingObject != null && systemLogMappingObject.size() > 0){
			for (ArrayList<Object> log : systemLogMappingObject){
				Log logToAdd = new Objects().new Log();
				logToAdd.logId = Integer.parseInt(log.get(0).toString());
				logToAdd.logName = log.get(1).toString();
				logToAdd.logDate = new SimpleDateFormat(log.get(2).toString());
				logToAdd.userLogId = Integer.parseInt(log.get(3).toString());
				
				systemLogs.add(logToAdd);
			}
			cnx.Close();
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
		ArrayList<Role> roles = new ArrayList<Role>();
		
		if (rolesMapping != null && rolesMapping.size() > 0){
			for (ArrayList<Object> role : rolesMapping){
				Role roleToAdd = new Objects().new Role();
				roleToAdd.idRole = Integer.parseInt(role.get(0).toString());
				roleToAdd.roleLevelId = Integer.parseInt(role.get(1).toString());
				roleToAdd.roleName = role.get(2).toString();
				roleToAdd.timeConnexion = new SimpleDateFormat(role.get(3).toString());
				
				roles.add(roleToAdd);
			}
			cnx.Close();
		}
		
		return roles;
	}

	/**
	 * Returns an array of all rights in the DB
	 * @return
	 */
	public ArrayList<RoleLevel> RoleLevels() {
		cnx.Open();
		ArrayList<ArrayList<Object>> rightsMapping =  cnx.Select(QueryFactory.SELECT_ALL_ROLE_RIGHTS, null, 
															"idRoleLevel", "canEditOwnAccount", "canChangeMdp", 
															"canEditAll", "canModifyDelay","canModifynbTentative",
															"canModifyBlocage","canModifyComplexiteMdp");
		ArrayList<RoleLevel> rightLevels = new ArrayList<RoleLevel>();

		
		if (rightsMapping != null && rightsMapping.size() > 0){
			for (ArrayList<Object> rights : rightsMapping){
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
			cnx.Close();
		}
		
		return rightLevels;
	}

	/***
	 *  Get a role from his role ID
	 */
	@Override
	public Role GetUserRole(int roleid) {
		cnx.Open();
		ArrayList<ArrayList<Object>> result = cnx.Select("Select * from log619lab5.Role where idRole= ? ;", new String[] {roleid + ""}, "idRole", "roleLevelId", "roleName", "timeConnexion");
		cnx.Close();
		if (result != null && result.size() == 1){
			User user = new Objects().new User();
			user.role.idRole = Integer.parseInt(result.get(0).get(0).toString());
			user.role.roleLevelId = Integer.parseInt(result.get(0).get(1).toString());
			user.role.roleName = result.get(0).get(2).toString();
			user.role.timeConnexion = new SimpleDateFormat(result.get(0).get(3).toString());
		
			return user.role;
		}
		return null;
	}


	/***
	 *  Get a rolelevel from his role ID
	 * */
	@Override
	public RoleLevel GetRoleLevel(int roleLevelId) {

		cnx.Open();
		ArrayList<ArrayList<Object>> result = cnx.Select("Select * from log619lab5.RoleLevel where idRoleLevel= ? ;", new String[] {roleLevelId + ""}, "idRoleLevel", "caneEditOwnAccount", "canChangeMdp", "canEditAll", 
				"canModifyDelay", "canModifynbTentative", "canModifyBlocage", "canModifyComplexiteMdp");
		cnx.Close();

		if (result != null && result.size() == 1){
			User user = new Objects().new User();
		
			user.role.roleLevel.idRoleLevel = Integer.parseInt(result.get(0).get(0).toString());
			user.role.roleLevel.caneEditOwnAccount = Integer.parseInt(result.get(0).get(1).toString()) == 1 ? true : false;
			user.role.roleLevel.canChangeMdp = Integer.parseInt(result.get(0).get(2).toString()) == 1 ? true : false;
			user.role.roleLevel.canEditAll = Integer.parseInt(result.get(0).get(3).toString()) == 1 ? true : false;
			user.role.roleLevel.canModifyDelay = Integer.parseInt(result.get(0).get(4).toString()) == 1 ? true : false;
			user.role.roleLevel.canModifynbTentative = Integer.parseInt(result.get(0).get(5).toString()) == 1 ? true : false;
			user.role.roleLevel.canModifyBlocage = Integer.parseInt(result.get(0).get(6).toString()) == 1 ? true : false;
			user.role.roleLevel.canModifyComplexiteMdp = Integer.parseInt(result.get(0).get(7).toString()) == 1 ? true : false;
			
			return user.role.roleLevel;
		}
		return null;
	}

	
	@Override
	public User GetUser(int userid) {
		if(userid != 0){
			for(User u : Users()){
				if (u.idUser == userid)
					return u;
				}
		}
		return null;
	}


	@Override
	public boolean CreateLog(Log event) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean UpdateUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}
	public void Close(){
		
	}

		
	public static void main (String [] args){
		DataProvider m = new DataProvider();

		ArrayList<User> users = m.Users();
		
		for (User u : users){
			System.out.println(u.idUser);		
			}
		
	}

	@Override
	public boolean UpdateUserRoleRights(int userid, int roleLevelId, int... rights) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User GetUserByUserName(String uname) {
		cnx.Open();
		ArrayList<ArrayList<Object>> result = cnx.Select("Select * from log619lab5.User where name=?;", new String[] {uname}, "ndMd5Iteration", "saltNumber", "saltCounter", "idUser");
		cnx.Close();
		User user = new Objects().new User();
		
		if (result !=null && result.size() > 0){
			user.nbCryptIteration = Integer.parseInt(result.get(0).get(0).toString());
			user.salt = result.get(0).get(1).toString();
			user.saltCounter = Integer.parseInt(result.get(0).get(2).toString());
			user.idUser = Integer.parseInt(result.get(0).get(3).toString());
			
			return  user;
		}
		return null;
	}

	@Override
	public User GetUserByUNameSaltPwd(User user, String uname, String pwd) {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM log619lab5.User where name= ? and saltPassword=SHA2(";
		for(int i = 1; i < user.nbCryptIteration; i++){
			query += "SHA2(";
		}
		query += "'";
		for(int i = 0; i < user.saltCounter; i++){
			query += user.salt;
		}
		query += "'?'";
		for(int j = 0; j < user.saltCounter; j++){
			query += user.salt;
		}
		query += "'";
		for(int i = 1; i < user.nbCryptIteration; i++){
			query += ", 512)";
		}
		query += ", 512);";
		System.out.println(query);

		cnx.Open();
		ArrayList<ArrayList<Object>> result = cnx.Select(query, new String[] {uname, pwd}, "idUser", "name", "roleId", "enabled");
		cnx.Close();
		
		if (result != null && result.size() == 1){
			user.idUser = Integer.parseInt(result.get(0).get(0).toString());
			user.name = result.get(0).get(1).toString();
			user.roleId = Integer.parseInt(result.get(0).get(2).toString());
			user.enabled = Boolean.parseBoolean(result.get(0).get(3).toString());
			
			return user;
		}
			
		return null;
	}

	@Override
	public LoginLog GetLoginLogsByUserId(int user) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
