package communication.DataObjects;

public class Queries {

	//SELECT SECTION
	public static final String SELECT_ALL_USERS = "SELECT * FROM User";
	public static final String SELECT_ALL_ROLE_RIGHTS = "SELECT * FROM RoleLevel";
	public static final String SELECT_ALL_ROLES = "SELECT * FROM RoleLevel";
	public static final String SELECT_ALL_LOGS = "SELECT * FROM LOGS";
	
	public static final String SELECT_USER = "SELECT * FROM User WHERE name='{0]' and password='{1}";
	public static final String SELECT_USER_ROLES = "SELECT * FROM ROLE WHERE roleId = {0]";	
	public static final String SELECT_USER_ROLE_LEVEL = "SELECT * FROM RoleLevel WHERE roleid = {0}";
	
	
	//UPDATE SECTION
	
	
	//INSERT SECTION
	
	public static final String INSERT_LOG = "INSERT INTO Log Values ({0},'{1}','{2}',{3})";
}
