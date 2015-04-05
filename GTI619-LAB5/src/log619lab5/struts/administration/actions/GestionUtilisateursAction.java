package log619lab5.struts.administration.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import log619lab5.domain.enumType.Section;
import log619lab5.struts.AbstractAction;
import log619lab5.struts.AbstractForm;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import securityLayer.securityModule.XSSProtection.HiddenStringGenerator;
import securityLayer.securityModule.gestionPassword.SecurityModulePassword;
import communication.DataMapping.DataProvider;
import database.IDatabase;
import database.mysql.Mysql;


public class GestionUtilisateursAction extends AbstractAdminAction {

	private final String PAGE = "GestionUtilisateur";
	private DataProvider dtp;
	
	@Override
	public ActionForward directive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("Page", PAGE);
		HttpSession session = request.getSession();
		String submit= request.getParameter("submit");
		
		dtp = new DataProvider(false);
		
		if(submit != null){
			String hidden = request.getParameter("hidden");
			String pw = request.getParameter("password");
			if(hidden==null || hidden.isEmpty() || !hidden.equals(session.getAttribute("gestionUtilisateuriddenString")) )
			{
				return mapping.findForward("AccessDenied");
			}	
			boolean reauthentification = dtp.Authenticate((String) session.getAttribute("Username"), pw) !=null;
			if("Ajouter".equals(submit)){
				if(reauthentification){
					String username = request.getParameter("username");
					String tpw = request.getParameter("tpassword");
					String type = request.getParameter("acces");
					
					int newUserRole = 0;
					int userlevel = 0;
					
					switch (type){
					case "admin" 	: userlevel = Section.ADMIN.ordinal() + 1;
						break;
					case "cercle" 	: userlevel = Section.CERCLE.ordinal() + 1;
						break;
					case "carre" 	: userlevel = Section.CARRE.ordinal() + 1;
						break;			
				}
					String salt = new HiddenStringGenerator().generateRandomString();			
					
					//TODO: Vérifier avec la politique de mot de passe
					boolean check = dtp.CreateUser(username, tpw, userlevel, salt);
					
					if (check)			
						request.setAttribute("ajoutMessage", "Operation réuisse");
					else
						request.setAttribute("ajoutMessage", "Cet utilisateur existe déjà");
				}
				else
					request.setAttribute("ajoutMessage", "Mauvais mot de Passe ");
			}
			
			if(submit.equals("Reactiver")){
				if(reauthentification){
					//TODO: vérifier que le user et le userid correspondent
					String username = request.getParameter("user");		
					request.setAttribute("activateMessage", "Operation réuisse");
				}
				else
					request.setAttribute("activateMessage", "Mauvais mot de Passe ");				
			}
			
			if(submit.equals("Supprimer")){
				if(reauthentification){
					//TODO: vérifier que le user et le userid correspondent
					String username = request.getParameter("user");		
					request.setAttribute("suppMessage", "Operation réuisse");	
				}
				else
					request.setAttribute("suppMessage", "Mauvais mot de Passe ");			
			}
			if(submit.equals("Valider")){
				if(reauthentification){
					String username = request.getParameter("user");
					String type = request.getParameter("privilege");	
					request.setAttribute("privMessage", "Operation réuisse");	
				}
				else
					request.setAttribute("privMessage", "Mauvais mot de Passe ");			
			}
		}
		
		String randomString = generateHiddenRandomString();
		request.setAttribute("hidden", randomString);
		session.setAttribute("gestionUtilisateuriddenString", randomString);
		return mapping.findForward(SUCCESS);
	}
}