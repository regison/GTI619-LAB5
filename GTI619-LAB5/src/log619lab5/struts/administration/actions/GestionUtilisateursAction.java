package log619lab5.struts.administration.actions;

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

import securityLayer.securityModule.XSSProtection.HiddenStringGenerator;
import securityLayer.securityModule.gestionPassword.SecurityModulePassword;
import communication.DataMapping.DataProvider;
import communication.DataObjects.Objects.PasswordLoginPolitic;
import communication.DataObjects.Objects.User;
import database.IDatabase;
import database.mysql.Mysql;


public class GestionUtilisateursAction extends AbstractAdminAction {

	private final String PAGE = "GestionUtilisateur";
	private DataProvider dtp;
	
	@Override
	public ActionForward directive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute(SessionAttributeIdentificator.PAGE, PAGE);
		HttpSession session = request.getSession();
		String submit= request.getParameter("submit");
		
		dtp = new DataProvider(false);
		
		if(submit != null){
			String hidden = request.getParameter(SessionAttributeIdentificator.HIDDEN);
			String pw = request.getParameter("password");
			if(hidden==null || hidden.isEmpty() || !hidden.equals(session.getAttribute(SessionAttributeIdentificator.GESTIONUTILISATEURHIDDENSTRING)) )
			{
				return mapping.findForward("AccessDenied");
			}	
			boolean reauthentification = dtp.Authenticate((String) session.getAttribute(SessionAttributeIdentificator.USERNAME), pw, null) !=null;
			if (reauthentification) {
				if ("Ajouter".equals(submit)) {
						String username = request.getParameter("username");
						String tpw = sendNewPassWord(request.getParameter("courriel"));
						String type = request.getParameter("acces");

						int newUserRole = 0;
						int userlevel = 0;

						switch (type) {
						case "admin":
							userlevel = Section.ADMIN.ordinal() + 1;
							break;
						case "cercle":
							userlevel = Section.CERCLE.ordinal() + 1;
							break;
						case "carre":
							userlevel = Section.CARRE.ordinal() + 1;
							break;
						}
						String salt = new HiddenStringGenerator()
								.generateRandomString();

						
						boolean check = dtp.CreateUser(username, tpw,
								userlevel, salt,
								(String) session.getAttribute(SessionAttributeIdentificator.USERNAME));

						if (check)
							request.setAttribute("ajoutMessage",
									"Operation réussie");
						else
							request.setAttribute("ajoutMessage",
									"Cet utilisateur existe déjà");
				}
				else if (submit.equals("Modifier")) {
						String username = request.getParameter("username");
						String tpw = sendNewPassWord(request.getParameter("courriel"));
						PasswordLoginPolitic pwp = dtp.getPasswordLoginPolitic();

						User user = dtp.GetUserByUsername(username);
						user.changepw = pwp.changementOublie;
						user.saltPassword = tpw;
						
						if(dtp.UpdateUser(user))
							request.setAttribute("oubliMessage",
									"Operation réussie");
						else
							request.setAttribute("oubliMessage",
									"Operation échouée");
						
				} else if (submit.equals("Reactiver")) {
					
						//TODO: vérifier que le user et le userid correspondent
						String username = request.getParameter("username");
						User user = dtp.GetUserByUsername(username);
						PasswordLoginPolitic pwp = dtp
								.getPasswordLoginPolitic();
						user.enabled = true;
						user.changepw = pwp.changementBloquage;
						if(dtp.UpdateUser(user))
							request.setAttribute("activateMessage",
								"Operation réussie");
						else
							request.setAttribute("activateMessage",
									"Operation échouée");
				}

				else if (submit.equals("Supprimer")) {
						//TODO: vérifier que le user et le userid correspondent
						String username = request.getParameter("username");
						if (!session.getAttribute("Username").equals(username)) {
							User user = dtp.GetUserByUsername(username);
							if(dtp.RemoveUser(user.idUser))

								request.setAttribute("suppMessage",
									"Operation réussie");
							else
								request.setAttribute("suppMessage",
										"Operation échouée");
						}
				} else if (submit.equals("Valider")) {
						String username = request.getParameter("username");
						String type = request.getParameter("privilege");
						User user = dtp.GetUserByUsername(username);
						PasswordLoginPolitic pwp = dtp
								.getPasswordLoginPolitic();
						user.roleId = Integer.parseInt(type);
						if(dtp.UpdateUser(user))
							request.setAttribute("privMessage", "Operation réussie");
						else
							request.setAttribute("privMessage", "Operation échouée");
				} else if(submit.equals("Activer") || submit.equals("D&eacute;sactiver") ){
					String username = request.getParameter("username");
					User user = dtp.GetUserByUsername(username);
					user.crypVersion = submit.equals("Activer") ? 2 : 1;
					if(dtp.UpdateUser(user))
						request.setAttribute("reauthMessage", "Operation réuisse");
					else
						request.setAttribute("reauthMessage", "Operation échouée");
				}
			}
			else{
				request.setAttribute("ajoutMessage", "Mauvais mot de Passe ");
				request.setAttribute("privMessage", "Mauvais mot de Passe");
				request.setAttribute("suppMessage", "Mauvais mot de Passe");
				request.setAttribute("activateMessage",
						"Mauvais mot de Passe");
				request.setAttribute("oubMessage",
						"Mauvais mot de Passe");
			}
		}
		
		String randomString = generateHiddenRandomString();
		request.setAttribute(SessionAttributeIdentificator.HIDDEN, randomString);
		session.setAttribute(SessionAttributeIdentificator.GESTIONUTILISATEURHIDDENSTRING, randomString);
		return mapping.findForward(SUCCESS);
	}
	
	/**
	 * On simule le fait que ca envoie le mot de passe par courriel
	 * @param courriel
	 * @return
	 */
	private String sendNewPassWord(String courriel){
		//on met test pour connaitre le mot de passe pour tester.
		return "test"; //new HiddenStringGenerator().generateRandomString().substring(3,23);
	}
	
	
}