package log619lab5.struts.general.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import log619lab5.domain.enumType.Section;
import log619lab5.struts.AbstractAction;
import log619lab5.struts.AbstractForm;
import log619lab5.struts.SessionAttributeIdentificator;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import database.IDatabase;
import database.mysql.Mysql;


public class CercleAction extends AbstractAction {

	private final String PAGE = "Login";
	
	@Override
	public ActionForward directive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute(SessionAttributeIdentificator.PAGE, "Cercle");
		request.getSession(true).setAttribute(SessionAttributeIdentificator.FROM, "Cercle");
		return mapping.findForward("success");
	}
	
	@Override
	public void setPageSection() {
		pageSection = Section.CERCLE;
	}
}