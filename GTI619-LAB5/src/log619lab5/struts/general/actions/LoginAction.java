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

import communication.DataMapping.DataProvider;
import communication.DataObjects.Objects.User;
import database.IDatabase;
import database.mysql.Mysql;


public class LoginAction extends AbstractAction {

	private final String PAGE = "Login";
	
	@Override
	public ActionForward directive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("LoginAction");
		request.setAttribute(SessionAttributeIdentificator.PAGE, PAGE);
		String randomString = generateHiddenRandomString();
		request.setAttribute(SessionAttributeIdentificator.HIDDEN, randomString);
		HttpSession session = request.getSession(true);
		// Ne pas invalider! J'en ai besoin pour gérer le bruteforce session.invalidate();
		session = request.getSession(true);

		if (session.getAttribute(SessionAttributeIdentificator.IDUSER) != null && !session.getAttribute(SessionAttributeIdentificator.IDUSER).equals("")) {
			DataProvider dtp = new DataProvider();
			User u = dtp.GetUser(Integer.parseInt(session.getAttribute(
					SessionAttributeIdentificator.IDUSER).toString()));
			u.isAuthenticated = false;
			dtp.UpdateUser(u);
		}
		session.setAttribute(SessionAttributeIdentificator.LOGINHIDDENSTRING, randomString);
		session.setAttribute(SessionAttributeIdentificator.USERNAME, "");
		session.setAttribute(SessionAttributeIdentificator.ROLE, "");
		session.setAttribute(SessionAttributeIdentificator.IDUSER, "");
		session.setAttribute(SessionAttributeIdentificator.LASTLOGGEDINACTIONTIME, "");
		session.setAttribute(SessionAttributeIdentificator.FROM, "Login");
		
		return mapping.findForward(SUCCESS);
    }

	@Override
	public void setPageSection() {
		pageSection = Section.GENERAL;
	}
}