package log619lab5.struts.general.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import log619lab5.domain.enumType.Section;
import log619lab5.struts.AbstractAction;
import log619lab5.struts.AbstractForm;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import database.IDatabase;
import database.mysql.Mysql;


public class ExecuteLoginAction extends AbstractAction {

	private final String PAGE = "ExecuteLogin";
	
@Override
public ActionForward directive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	String user = (String) request.getParameter("username");
	String password = request.getParameter("password").toString();
	
	HttpSession session = request.getSession();
	request.setAttribute("Page", PAGE);
	
	String hidden = request.getParameter("hidden");
	String random = session.getAttribute("loginHiddenString").toString();
	session.setAttribute("loginHiddenString", "");
	
	if(hidden.equals("") || !hidden.equals(random)){
		pageSection = Section.LOGIN;	
		return mapping.findForward("failure");
	}
	
	// TODO enlever cette section
	//Temporaire!
	Mysql connexion = new Mysql(Mysql.MYSQL_DATABASE_LOG619LAB5);
	connexion.Open();
	try {
		ArrayList<ArrayList<Object>> result = connexion.Select("Select * from log619lab5.User where name=?;", new String[] {user}, "ndMd5Iteration", "saltNumber", "saltCounter");
		if(result.size() > 1){
			pageSection = Section.LOGIN;	
			return mapping.findForward("failure");
		}
		int nbrCryptIteration = Integer.parseInt(result.get(0).get(0).toString());
		String salt = result.get(0).get(1).toString();
		int saltCounter = Integer.parseInt(result.get(0).get(2).toString());
		
		String query = "SELECT * FROM log619lab5.User where name= ? and saltPassword=SHA2(";
		for(int i = 1; i < nbrCryptIteration; i++){
			query += "SHA2(";
		}
		query += "'";
		for(int i = 0; i < saltCounter; i++){
			query += salt;
		}
		query += "'?'";
		for(int j = 0; j < saltCounter; j++){
			query += salt;
		}
		query += "'";
		for(int i = 1; i < nbrCryptIteration; i++){
			query += ", 512)";
		}
		query += ", 512);";
		System.out.println(query);
		result = connexion.Select(query, new String[] {user, password}, "idUser", "name", "roleId", "enabled");
		if(result.size() < 1){
			pageSection = Section.LOGIN;	
			return mapping.findForward("failure");
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	connexion.Close();
	//
	
	
	
	pageSection = Section.LOGIN;	
	return mapping.findForward(user);
    }
}