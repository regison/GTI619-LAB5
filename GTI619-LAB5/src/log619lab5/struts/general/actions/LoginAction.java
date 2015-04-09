package log619lab5.struts.general.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import log619lab5.domain.enumType.Section;
import log619lab5.struts.AbstractAction;
import log619lab5.struts.AbstractForm;
import log619lab5.struts.SessionAttributeIdentificator;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import database.IDatabase;
import database.mysql.Mysql;


public class LoginAction extends AbstractAction {

	private final String PAGE = "Login";
	
@Override
public ActionForward directive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute(SessionAttributeIdentificator.PAGE, PAGE);
		String randomString = generateHiddenRandomString();
		request.setAttribute(SessionAttributeIdentificator.HIDDEN, randomString);
		HttpSession session = request.getSession();
		// Ne pas invalider! J'en ai besoin pour gérer le bruteforce session.invalidate();
		session = request.getSession();
		session.setAttribute(SessionAttributeIdentificator.LOGINHIDDENSTRING, randomString);
		session.setAttribute(SessionAttributeIdentificator.USERNAME, "");
		session.setAttribute(SessionAttributeIdentificator.ROLE, "");
		session.setAttribute(SessionAttributeIdentificator.IDUSER, "");
		session.setAttribute(SessionAttributeIdentificator.LASTLOGGEDINACTIONTIME, "");
		request.getSession().setAttribute(SessionAttributeIdentificator.FROM, "Login");
		return mapping.findForward(SUCCESS);
    }

	@Override
	public void setPageSection() {
		pageSection = Section.GENERAL;
	}
}