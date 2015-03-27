package communication.DataObjects;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class Objects {

	public class User {
		public int idUser;
	    public String name;
	    public int roleId;
	    public String saltPassword;
	    public int nbCryptIteration;

	    public SimpleDateFormat ModifiedDate;
	    public String ModifiedBy;
	    public SimpleDateFormat CreateDate;
	    public String CreateBy;
	    public String salt;
	    public int saltCounter;
	    public boolean enabled;
	    public Role role;
	    public boolean isAuthenticated;
	    public boolean isLogOutNeeded;
	    
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
	    public static final String CercleRoleName = "Pr�pos� au cercle";
	    public static final String SquareRoleName = "Pr�pos� au carr�";
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
	}

	public class LoginLog{
		public int loginlogId;
		public int userId;
		public Timestamp lastloginTime;
		public long failedTriesCount;
		public boolean loggedIn;
		public boolean logoutNeeded;
		
	}
	public Objects() {
		// TODO Auto-generated constructor stub
	}

}
