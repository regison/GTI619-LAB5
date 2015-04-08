package communication.DataObjects;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import communication.DataMapping.DataProvider;
import communication.DataObjects.Objects.Log;
import database.IDatabase;
import database.mysql.Mysql;


public class Objects {

	public class User {
		public int idUser;
	    public String name;
	    public int roleId;
	    public String saltPassword;
	    public int nbCryptIteration;

	    public String ModifiedDate;
	    public String ModifiedBy;
	    public String CreateDate;
	    public String CreateBy;
	    public String salt;
	    public int saltCounter;
	    public boolean enabled;
	    public Role role;
	    public boolean isAuthenticated;
	    public boolean isLogOutNeeded;
	    public int crypVersion;
	    public Date blockTime;
	    public boolean isChangePassword;
		public boolean changepw;
	    
	    
	    public User(){
	    	role = new Role();
	    }
	}
	
	public class Role {
		public int idRole;
	    public int roleLevelId;
	    public String roleName;
	    public SimpleDateFormat timeConnexion;
	    public RoleLevel roleLevel;
	    public SimpleDateFormat ModifiedDate;
	    public String ModifiedBy;
	    public SimpleDateFormat CreateDate;
	    public String CreateBy;
	    public int saltCountAuthorize;
	    
	    public Role(){
	    	roleLevel = new RoleLevel();
	    }
	    
	    public static final String AdministratorRoleName = "Administrateur";
	    public static final String CercleRoleName = "Préposé au cercle";
	    public static final String SquareRoleName = "Préposé au carré";
	}
	
	public class RoleLevel {
		public int idRoleLevel;
	    public boolean caneEditOwnAccount;
	    public boolean canChangeMdp;
	    public boolean canEditAll;
	    public boolean canModifyDelay;
	    public boolean canModifynbTentative;
	    public boolean canModifyBlocage;
	    public boolean canModifyComplexiteMdp;
	}
	public class Log {
		public int logId;
		public String logName;
		public int userLogId;
		public String logDate;
		
		public void CreateLog(Log event, boolean byPass) {
			DataProvider dataProvider = new DataProvider(byPass);
			dataProvider.CreateLog(event, byPass);			
		}
	}
	
	public class PasswordPolitic{
		public int complexity;
		public int max;
		public int min;
		public boolean changementOublie;
		public boolean changementDepassement;
		public boolean changementBloquage;
		public int lastPasswords;
	}
	
	public class LoginPolitic{
		public int maxTentative;
		public int delais;
		public boolean bloquage2tentatives;
	}

	public class LoginLog{
		public int loginlogId;
		public int userId;
		public Timestamp lastloginTime;
		public long failedTriesCount;
		public boolean loggedIn;
		public boolean logoutNeeded;
		
	}
	public class PreviousPassword {
		public int idPreviousPassword;
		public int userID;
		public String previousPassword;
		public String ModifiedDate;
	}
	public Objects() {
		// TODO Auto-generated constructor stub
	}

}
