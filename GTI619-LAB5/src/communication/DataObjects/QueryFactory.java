package communication.DataObjects;

public class QueryFactory {

	//SELECT SECTION
	public static final String SELECT_ALL_USERS = "SELECT u.*, ll.LoggedIn, ll.LogoutNeeded FROM User u INNER JOIN LoginLogs ll ON u.idUser = ll.idUser";
	public static final String SELECT_ALL_ROLELEVEL = "SELECT * FROM RoleLevel";
	public static final String SELECT_ALL_ROLES = "SELECT * FROM RoleLevel";
	public static final String SELECT_ALL_LOGS = "SELECT * FROM Log";
	public static final String SELECT_ALL_LOGINLOGS = "SELECT * FROM LoginLog";
	
	public static final String SELECT_USER_BY_UNAME_PWD = "SELECT u.*, ll.LoggedIn, ll.LogoutNeeded FROM User u INNER JOIN LoginLogs ll ON u.idUser = ll.idUser WHERE u.name= ? and u.saltPassword=SHA2(";
	public static final String SELECT_USER_BY_UNAME = "SELECT u.*, ll.LoggedIn, ll.LogoutNeeded FROM User u INNER JOIN LoginLogs ll ON u.idUser = ll.idUser WHERE u.name= ? ;";
	public static final String SELECT_USER_BYID = "SELECT u.* , ll.LoggedIn, ll.LogoutNeeded FROM User u INNER JOIN LoginLogs ll ON u.idUser = ll.idUser WHERE u.idUser = ? ;";
	public static final String SELECT_USER_ROLE = "SELECT * FROM ROLE WHERE idRole = ? ;";	
	public static final String SELECT_USER_ROLELEVEL = "SELECT * FROM RoleLevel WHERE idRoleLevel = ? ;";
	public static final String SELECT_USER_LOGINLOGS_BY_USERID = "SELECT * FROM LoginLogs WHERE idUser= ? ;"; 
	
	
	//UPDATE SECTION	
	public static final String UPDATE_LOGING_lOG = "UPDATE log619lab5.LoginLogs SET FailedTriesCount=? , LoggedIn=? , LogoutNeeded=? WHERE idUser=? ;";
	public static final String UDPATE_USER_LAST_LOGIN_TIME = "UPDATE log619lab5. SET LastLoginTime= ? WHERE userID= ? ;";
	
	//INSERT SECTION	
	public static final String INSERT_LOG = "INSERT INTO Log Values ({0},'{1}','{2}',{3})";
	public static final String INSERT_LOGINLOG = "INSERT INTO log619lab5.LoginLogs (idUser, FailedTriesCount, LoggedIn, LogoutNeeded) VALUES ( ? , ? , ? , ? );";
}
