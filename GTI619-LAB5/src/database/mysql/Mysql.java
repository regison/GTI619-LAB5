package database.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import communication.DataMapping.ExceptionLogger;
import communication.DataObjects.Objects;
import communication.DataObjects.Objects.Log;
import database.IDatabase;

public class Mysql implements IDatabase {
	private Connection conn;

	private String url;
	private String username;
	private String password;
	private boolean byPassLog = true;

	public static final short MYSQL_DATABASE_LOG619LAB5 = 0;
	
	public Mysql(short mysql_table_type, boolean byPassLog){
		if(mysql_table_type == MYSQL_DATABASE_LOG619LAB5)
			this.url = "jdbc:mysql://log619lab5.no-ip.org:3306/log619lab5";
		else
			this.url = "jdbc:mysql://log619lab5.no-ip.org:3306/log619lab5"; //default
		this.username = "mysqllog619lab5";
		this.password = "fsid$ofuosidf7s7fd987f!@";
		this.byPassLog = byPassLog;
	}
	
	private Mysql(String p_url, String p_username, String p_password) {
		this.url = p_url;
		this.username = p_username;
		this.password = p_password;
	}

	@Override
	public void Open() {
		try {
			Class.forName("com.mysql.jdbc.Driver");

			/*
			 * conn =
			 * DriverManager.getConnection("jdbc:mysql://localhost/novutech",
			 * "root", "");
			 */
			conn = DriverManager.getConnection(this.url, this.username,
					this.password);

		} catch (Exception e) {
			ExceptionLogger.LogException(e);
			e.printStackTrace();
		}
	}

	@Override
	public void Close() {
		try {
			conn.close();
		} catch (SQLException e) {
			ExceptionLogger.LogException(e);
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<ArrayList<Object>> Select(boolean hideParameters, String p_request,String[] params, 
			String... p_parameters) {
		try {
			ArrayList<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>();

			//Statement st = conn.createStatement();
			
			//Empeche SQL INJECTION *** IMPORTANT**
			PreparedStatement prepStmt = conn.prepareStatement(p_request);
			if (params != null){
				for(int i = 0; i < params.length; i++){
					prepStmt.setString(i+1, params[i]);
				}
			}
			
			ResultSet rs = prepStmt.executeQuery();
			
			//Tests: TODO remove!
//			System.out.println(prepStmt.toString());
			
			while (rs.next()) {
				ArrayList<Object> row = new ArrayList<Object>();

				for (int i = 0; i != p_parameters.length; ++i) {
					row.add(rs.getObject(p_parameters[i]));
				}
				data.add(row);
			}
			
			if (!byPassLog){
				
				//System.out.println("SELECT " + prepStmt.toString().substring( prepStmt.toString().indexOf(": ") + 2));
				
				Log event = new Objects().new Log();
				event.logDate = new SimpleDateFormat().format(new Date());
				event.logName = (hideParameters ? p_request : prepStmt.toString().substring( prepStmt.toString().indexOf(": ") + 2));
				event.userLogId = 1;
				//System.out.println("even.logName, longeur =" + event.logName.length());
				event.CreateLog(event, true);
			};
			prepStmt.close();
			return data;
		} catch (Exception e) {
			ExceptionLogger.LogException(e);
			e.printStackTrace();
		}
		return null;
	}
	
	private int executeUpdate(boolean hideParameters, String p_request,String[] params) {
		try {
			PreparedStatement prepStmt = conn.prepareStatement(p_request);
			if (params != null){
				for(int i = 0; i < params.length; i++){
					prepStmt.setString(i+1, params[i]);
				}
			}
			int row = prepStmt.executeUpdate();
			
			if (!byPassLog){
				//System.out.println("OTHER " + prepStmt.toString().substring( prepStmt.toString().indexOf(": ") + 2));
				
				Log event = new Objects().new Log();
				event.logDate = new SimpleDateFormat().format(new Date());
				event.userLogId = 1;
				event.logName = (hideParameters ? p_request : prepStmt.toString().substring( prepStmt.toString().indexOf(": ") + 2));
				//System.out.println("even.logName, longeur =" + event.logName.length());
				event.CreateLog(event, true);
			};
			prepStmt.close();
			return row;
		} catch (Exception e) {
			ExceptionLogger.LogException(e);
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int insert(boolean hideParameters, String p_request,String[] params) {
		return executeUpdate(hideParameters, p_request, params);
	}

	@Override
	public int update(boolean hideParameters, String p_request,String[] params) {
		return executeUpdate(hideParameters, p_request, params);
	}

	@Override
	public int delete(boolean hideParameters, String p_request,String[] params) {
		return executeUpdate(hideParameters, p_request, params);
	}
}
