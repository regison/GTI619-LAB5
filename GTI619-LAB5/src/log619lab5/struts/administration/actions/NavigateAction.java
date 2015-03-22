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


public class NavigateAction extends AbstractAction {

	private final String PAGE = "Login";
	
	@Override
	public ActionForward directive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String nav = (String) request.getParameter("navigation");
		if(nav.equals("carre")){
			request.setAttribute("Page", "Carre");
			pageSection = Section.CARRE;
			return mapping.findForward("carre");
		}
		else if(nav.equals("cercle")){
			request.setAttribute("Page", "Cercle");
			pageSection = Section.CERCLE;
			return mapping.findForward("cercle");
		}
		else{
			request.setAttribute("Page", "404");
			pageSection = Section.GENERAL;
			return mapping.findForward("failure");
		}
	}
}