package database.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import database.IDatabase;

public class Mysql implements IDatabase {
	private Connection conn;

	private String url;
	private String username;
	private String password;

	public static final short MYSQL_TABLE_LOG619LAB5 = 0;
	
	public Mysql(short mysql_table_type){
		if(mysql_table_type == MYSQL_TABLE_LOG619LAB5)
			this.url = "jdbc:mysql://log619lab5.no-ip.org:3306/log619lab5";
		else
			this.url = "jdbc:mysql://log619lab5.no-ip.org:3306/log619lab5"; //default
		this.username = "mysqllog619lab5";
		this.password = "fsid$ofuosidf7s7fd987f!@";
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
			e.printStackTrace();
		}
	}

	@Override
	public void Close() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<ArrayList<Object>> Select(String p_request,String[] params, 
			String... p_parameters) {
		try {
			ArrayList<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>();

			//Statement st = conn.createStatement();
			PreparedStatement prepStmt = conn.prepareStatement(p_request);
			
			for(int i = 0; i < params.length; i++){
				prepStmt.setString(i+1, params[i]);
			}
			
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				ArrayList<Object> row = new ArrayList<Object>();

				for (int i = 0; i != p_parameters.length; ++i) {
					row.add(rs.getObject(p_parameters[i]));
				}

				data.add(row);
			}

			prepStmt.close();
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private int executeUpdate(String p_request) {
		try {
			Statement st = conn.createStatement();
			int row = st.executeUpdate(p_request);
			st.close();
			return row;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int insert(String p_request) {
		return executeUpdate(p_request);
	}

	@Override
	public int update(String p_request) {
		return executeUpdate(p_request);
	}

	@Override
	public int delete(String p_request) {
		return executeUpdate(p_request);
	}
}
