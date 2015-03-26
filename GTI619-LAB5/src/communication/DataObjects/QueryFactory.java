package communication.DataObjects;

public class QueryFactory {

	//SELECT SECTION
	public static final String SELECT_ALL_USERS = "SELECT u.*, ll.LoggedIn, ll.LogoutNeeded FROM User u INNER JOIN LoginLog ll ON u.idUser = ll.idUser";
	public static final String SELECT_ALL_ROLELEVEL = "SELECT * FROM RoleLevel";
	public static final String SELECT_ALL_ROLES = "SELECT * FROM RoleLevel";
	public static final String SELECT_ALL_LOGS = "SELECT * FROM Log";
	public static final String SELECT_ALL_LOGINLOGS = "SELECT * FROM LoginLog";
	
	public static final String SELECT_USER_BY_UNAME_PWD = "SELECT * FROM User WHERE name= ? and password= ?";
	public static final String SELECT_USER_BYID = "SELECT u.* , ll.LoggedIn, ll.LogoutNeeded FROM User u INNER JOIN LoginLog ll ON u.idUser = ll.idUser WHERE u.idUser = ? ;";
	public static final String SELECT_USER_ROLE = "SELECT * FROM ROLE WHERE idRole = ? ;";	
	public static final String SELECT_USER_ROLELEVEL = "SELECT * FROM RoleLevel WHERE idRoleLevel = ? ;";
	
	
	//UPDATE SECTION
	
	
	//INSERT SECTION	
	public static final String INSERT_LOG = "INSERT INTO Log Values ({0},'{1}','{2}',{3})";
	public static final String INSERT_LOGINLOG = "INSERT INTO LoginLog Values ({0},'{1}','{2}',{3})";
}
