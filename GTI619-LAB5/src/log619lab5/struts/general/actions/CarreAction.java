package log619lab5.struts.general.actions;

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


public class CarreAction extends AbstractAction {

	private final String PAGE = "Login";
	
	@Override
	public ActionForward directive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setAttribute("Page", "Carre");

		request.getSession().setAttribute("from", "Carre");
		return mapping.findForward("success");
	}

	@Override
	public void setPageSection() {
		pageSection = Section.CARRE;
	}
}