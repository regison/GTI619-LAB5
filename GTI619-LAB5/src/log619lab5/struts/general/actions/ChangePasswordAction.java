package log619lab5.struts.general.actions;

import java.util.List;

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
import securityLayer.securityModule.gestionPassword.SecurityModulePassword;
import database.IDatabase;
import database.mysql.Mysql;


public class ChangePasswordAction extends AbstractAction {

	private final String PAGE = "ChangePassword";
	
@Override
public ActionForward directive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute(SessionAttributeIdentificator.PAGE, "ChangePassword");

		HttpSession session = request.getSession(true);
		SecurityModulePassword sPass = new SecurityModulePassword();
		
		if(request.getParameter("updatePassword") != null){

			
			String oldPassword = request.getParameter("opassword");
			String newPassword = request.getParameter("npassword");
			String copynewPassword = request.getParameter("cnpassword");
			String hidden = request.getParameter(SessionAttributeIdentificator.HIDDEN);
			DataProvider dtp =  new DataProvider(false);
			
			if(hidden==null || hidden.isEmpty() || !hidden.equals(session.getAttribute(SessionAttributeIdentificator.UPDATEPWHIDDENSTRING)))
			{
				return mapping.findForward("OperationDenied");
			}
			
			boolean reauthentification = dtp.Authenticate((String) session.getAttribute(SessionAttributeIdentificator.USERNAME), oldPassword, null) !=null;
			if(reauthentification){
				if(newPassword.equals(copynewPassword))
				{
					List<String> errors = sPass.validatePassword(newPassword);
					
					if(errors.size()==0){
						//On va passer le id au lieu du username
						if(sPass.updatePassword((int) request.getSession(true).getAttribute(SessionAttributeIdentificator.IDUSER), (String) request.getSession(true).getAttribute(SessionAttributeIdentificator.USERNAME), newPassword)){
							
							request.setAttribute("message", "ca  a marché");
						}
						else{
							request.setAttribute("message", "ca pas marché");
						}
					}
					else{
						request.setAttribute("error", errors);
					}
				}
				else{
					request.setAttribute("message", "les deux mots de passes doivent etre pareils");
				}
			}
			else{
				request.setAttribute("message", "Mauvais mot de passe actuel");
			}
				
		}
		else{
			request.setAttribute("error", sPass.getPasswordConstraintMessage());
		}
		
		String randomString = generateHiddenRandomString();
		request.setAttribute(SessionAttributeIdentificator.HIDDEN, randomString);
		session.setAttribute(SessionAttributeIdentificator.UPDATEPWHIDDENSTRING, randomString);
		return mapping.findForward("success");
    }

	@Override
	public void setPageSection() {
		pageSection = Section.CONNECTED;
	}
}