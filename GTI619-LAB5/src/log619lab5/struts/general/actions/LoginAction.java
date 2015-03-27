package log619lab5.struts.general.actions;

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


public class LoginAction extends AbstractAction {

	private final String PAGE = "Login";
	
@Override
public ActionForward directive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	request.setAttribute("Page", PAGE);
	String randomString = generateHiddenRandomString();
	request.setAttribute("hidden", randomString);
	HttpSession session = request.getSession();
	session.setAttribute("loginHiddenString", randomString);
	session.setAttribute("Username", "");
	session.setAttribute("Role", "");
	session.setAttribute("idUser", "");
	session.setAttribute("LastLoggedInActionTime", "");
	request.getSession().setAttribute("from", "Login");
	pageSection = Section.GENERAL;	
	return mapping.findForward(SUCCESS);
    }
}