package log619lab5.struts.administration.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import log619lab5.domain.enumType.Section;
import log619lab5.struts.AbstractAction;
import log619lab5.struts.AbstractForm;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import database.IDatabase;
import database.mysql.Mysql;


public class AdminPageAction extends AbstractAdminAction {

	private final String PAGE = "Login";
	
	@Override
	public ActionForward directive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		String randomString = generateHiddenRandomString();
		request.setAttribute("hidden", randomString);
		request.getSession().setAttribute("adminHiddenString", randomString);
		request.getSession().setAttribute("from", "AdminPage");
		return mapping.findForward("success");
	}
}