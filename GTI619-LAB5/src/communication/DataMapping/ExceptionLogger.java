package communication.DataMapping;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import communication.DataObjects.QueryFactory;
import database.mysql.Mysql;

public class ExceptionLogger {

	public static void LogException(Exception ex){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		Mysql cnx = new Mysql(Mysql.MYSQL_DATABASE_LOG619LAB5, true);
		cnx.Open();
		cnx.insert("INSERT INTO `log619lab5`.`ExceptionLogs` (`ExceptionLogs`) VALUES ( ? );", 
				new String[] { new SimpleDateFormat().format(new Date()) + " " + sw.toString() });
		cnx.Close();
	}
}
