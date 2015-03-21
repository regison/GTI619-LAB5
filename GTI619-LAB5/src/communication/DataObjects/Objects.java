package communication.DataObjects;

import java.sql.Time;
import java.util.Date;

public class Objects {

	public class User {
		public int idUser;
	    public String name;
	    public int roleId;
	    public String saltPassword;
	    public int ndMd5Iteration;
	    public Date ModifiedDate;
	    public String ModifiedBy;
	    public Date CreateDate;
	    public String CreateBy;
	    public int saltNumber;
	    public int saltCounter;
	}
	
	public class Role {
		public int idRole;
	    public int roleLevelId;
	    public String roleName;
	    public Time timeConnexion;
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
		public Date logDate;
	}

	public Objects() {
		// TODO Auto-generated constructor stub
	}

}
