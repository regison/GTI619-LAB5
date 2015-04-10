package database;

import java.util.ArrayList;

public interface IDatabase {
	void Open();
	void Close();
	
	ArrayList<ArrayList<Object>> Select(boolean hideParameters, String p_request, String[] params, String... p_parameters);
	
	int insert(boolean hideParameters, String p_request,String[] params);
	int update(boolean hideParameters, String p_request,String[] params);
	int delete(boolean hideParameters, String p_request,String[] params);
}
