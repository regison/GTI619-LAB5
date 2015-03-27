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

import securityLayer.securityModule.gestionPassword.SecurityModulePassword;
import database.IDatabase;
import database.mysql.Mysql;


public class ChangePasswordAction extends AbstractAction {

	private final String PAGE = "Login";
	
@Override
public ActionForward directive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("Page", "ChangePassword");

		HttpSession session = request.getSession();
		
		pageSection = Section.GENERAL;
		
		if(request.getParameter("updatePassword") != null){

			
			String oldPassword = request.getParameter("opassword");
			String newPassword = request.getParameter("npassword");
			String copynewPassword = request.getParameter("cnpassword");
			String hidden = request.getParameter("hidden");
			SecurityModulePassword sPass = new SecurityModulePassword();
			
			if(hidden==null || hidden.isEmpty() || !hidden.equals(session.getAttribute("updatePWHiddenString")))
			{
				return mapping.findForward("OperationDenied");
			}
			
			if(newPassword.equals(copynewPassword))
			{
				//On va passer le id au lieu du username
				if(sPass.updatePassword((int) request.getSession().getAttribute("userId"), (String) request.getSession().getAttribute("Username"), oldPassword, newPassword)){
					request.setAttribute("message", "ca  a marché");
				}
				else{
					request.setAttribute("message", "ca pas marché");
				}
			}
			else{
				request.setAttribute("message", "les deux mots de passes doivent etre pareils");
			}
		}
		
		String randomString = generateHiddenRandomString();
		request.setAttribute("hidden", randomString);
		session.setAttribute("updatePWHiddenString", randomString);
		return mapping.findForward("success");
    }

	@Override
	public void setPageSection() {
		pageSection = Section.CONNECTED;
	}
}