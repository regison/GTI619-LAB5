package communication.DataMapping;

import java.util.ArrayList;

import communication.DataObjects.Objects.*;

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
	public ArrayList<ArrayList<Object>> getLogs(){
	  return data.Logs();
	}
	  
	public void Dispose(){
	  if (data != null){
		  data.Close();
	  }
	}
  
}
