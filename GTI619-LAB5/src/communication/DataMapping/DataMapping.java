package communication.DataMapping;

import java.util.ArrayList;

import communication.DataObjects.Objects;
import communication.DataObjects.Objects.Log;
import communication.DataObjects.Objects.Role;
import communication.DataObjects.Objects.RoleLevel;
import communication.DataObjects.Objects.User;
import communication.DataObjects.Queries;
import database.IDatabase;
import database.mysql.Mysql;

public class DataMapping implements IDataMapping {

	private Mysql cnx;
	public DataMapping() {		
		cnx= new Mysql((short) 0);
		cnx.Open();
	}


	public ArrayList<User> Users() {		
		ArrayList<ArrayList<Object>> usersMapping =  cnx.Select(Queries.SELECT_ALL_USERS, null, "idUser","name","roleId","CreateDate");
		ArrayList<User> usersToShow = new ArrayList<User>();
		
		if (usersMapping.size() > 0){
			Objects obj = new Objects();
			for ( ArrayList<Object> u : usersMapping ){				
				User toAdd = obj.new User();
				toAdd.idUser = Integer.parseInt(u.get(0).toString());
				usersToShow.add(toAdd);
			}
		}
		
		return usersToShow;
	}


	public ArrayList<ArrayList<Object>> Logs() {
		return cnx.Select(Queries.SELECT_ALL_LOGS, null, "");
	}


	public ArrayList<Role> Roles() {
		// TODO Auto-generated method stub
		return null;
	}


	public ArrayList<RoleLevel> RoleLevels() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void Open() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void Close() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public ArrayList<ArrayList<Object>> Select(String p_request,
			String[] params, String... p_parameters) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int insert(String p_request) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int update(String p_request) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int delete(String p_request) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public Role GetUserRole(int userid) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public RoleLevel GetRoleLevel(int roleid, int userid) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public User GetUser(int userid) {
		// TODO Auto-generated method stub
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


	@Override
	public boolean UpdateUserRoleRights(int userid, int roleLevelId,
			boolean[] rights) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public static void main (String [] args){
		DataMapping m = new DataMapping();
		m.Users();
	}

}
