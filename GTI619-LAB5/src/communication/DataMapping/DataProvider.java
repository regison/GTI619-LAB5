package communication.DataMapping;

import java.util.ArrayList;

import communication.DataObjects.Objects.*;
/**
 * Classe qui doit etre appelé par les modules externes
 * Pages JSP, modules de sécurité
 * @author r.registe
 *
 */
public class DataProvider {
	
	private DataMapping data;
	public DataProvider(){
		data = new DataMapping();
	}
	
	//Select users from db
	public ArrayList<User> Users(){	  
	  return  data.Users();
	}
	//Select all logs from db	
	public ArrayList<Log> getLogs(){
	  return data.Logs();
	}
	public User GetUser(int userid){
		return data.GetUser(userid);
	}
	
	public Role GetRole(int roleid){
		return data.GetUserRole(roleid);
	}
	  
	public void Dispose(){
	  if (data != null){
		  data.Close();
	  }
	}
  
}
