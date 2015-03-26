package database;

import java.util.ArrayList;

public interface IDatabase {
	void Open();
	void Close();
	
	ArrayList<ArrayList<Object>> Select(String p_request, String[] params, String... p_parameters);
	
	int insert(String p_request,String[] params);
	int update(String p_request,String[] params);
	int delete(String p_request,String[] params);
}
