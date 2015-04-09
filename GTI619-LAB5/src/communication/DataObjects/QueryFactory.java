package communication.DataObjects;

public class QueryFactory {

	//SELECT SECTION
	public static final String SELECT_ALL_USERS = "SELECT u.*, ll.LoggedIn, ll.LogoutNeeded FROM User u LEFT JOIN LoginLogs ll ON u.idUser = ll.idUser";
	public static final String SELECT_ALL_ROLELEVEL = "SELECT * FROM RoleLevel";
	public static final String SELECT_ALL_ROLES = "SELECT * FROM Role";
	public static final String SELECT_ALL_LOGS = "SELECT * FROM Log";
	public static final String SELECT_ALL_LOGINLOGS = "SELECT * FROM LoginLog";
	public static final String SELECT_ALL_PREVIOUS_PASSWORDS = "SELECT * FROM previousPasswords";
	
	public static final String SELECT_USER_BY_UNAME_PWD = "SELECT u.*, ll.LoggedIn, ll.LogoutNeeded FROM User u LEFT JOIN LoginLogs ll ON u.idUser = ll.idUser WHERE u.name= ? and u.saltPassword=SHA2(";
	public static final String SELECT_USER_BY_UNAME = "SELECT u.*, ll.LoggedIn, ll.LogoutNeeded FROM User u LEFT JOIN LoginLogs ll ON u.idUser = ll.idUser WHERE u.name= ? ;";
	public static final String SELECT_USER_BYID = "SELECT u.* , ll.LoggedIn, ll.LogoutNeeded FROM User u LEFT JOIN LoginLogs ll ON u.idUser = ll.idUser WHERE u.idUser = ? ;";
	public static final String SELECT_USER_ROLE = "SELECT * FROM Role WHERE idRole = ? ;";	
	public static final String SELECT_USER_ROLELEVEL = "SELECT * FROM RoleLevel WHERE idRoleLevel = ? ;";
	public static final String SELECT_USER_LOGINLOGS_BY_USERID = "SELECT * FROM LoginLogs WHERE idUser= ? ;"; 
	public static final String SELECT_USER_PREVIOUS_PASSWORDS = "SELECT * FROM previousPasswords WHERE userID= ? ;";
	
	public static final String SELECT_PASSWORDPOLITIC = "SELECT * FROM PaswordPolitic WHERE idPasswordPolitic = '1';";
	
	//UPDATE SECTION	
	public static final String UPDATE_LOGING_lOG = "UPDATE log619lab5.LoginLogs SET FailedTriesCount=? , LoggedIn=? , LogoutNeeded=? WHERE idUser=? ;";
	public static final String UDPATE_USER_LAST_LOGIN_TIME = "UPDATE log619lab5. SET LastLoginTime= ? WHERE userID= ? ;";
	public static final String UPDATE_USER_ROLELVEL = "UPDATE RoleLevel SET caneEditOwnAccount=? , canChangeMdp=? , canEditAll=?, canModifyDelay=?, canModifynbTentative=?, canModifyBlocage=?, canModifyComplexiteMdp=?   WHERE idRoleLevel=? ;";
	public static final String UPDAE_PASSWORDPOLITIC = "UPDATE PaswordPolitic SET complexity = ? , max = ? , min = ? , changementOublié = ? , changementDepassement = ? , changementBloquage = ? , lastPasswords = ? , maxTentative = ? , delais = ? , bloquage2Tentatives = ? WHERE idPasswordPolitic = '1';";
	
	//INSERT SECTION	
	public static final String INSERT_USER = "INSERT INTO User (name, roleId, ndMd5Iteration, ModifiedDate, ModifiedBy, CreateDate, CreateBy, saltNumber, saltCounter, enabled, cryptVersion, saltPassword) Values ( ? ,? ,? , ? ,? ,? ,? ,? ,? ,? ,?, ";
	public static final String INSERT_LOG = "INSERT INTO Log Values (?, ?, ?, ?)";
	public static final String INSERT_LOGINLOG = "INSERT INTO LoginLogs (idUser, FailedTriesCount, LoggedIn, LogoutNeeded) VALUES ( ? , ? , ? , ? );";
	public static final String INSERT_PREVIOUS_PASSWORD = "INSERT INTO previousPasswords Values ( ? ,? ,? ,? )";

	public static final String DELETE_USER = "UPDATE User SET enabled = 0 WHERE idUser= ? ;";
}
