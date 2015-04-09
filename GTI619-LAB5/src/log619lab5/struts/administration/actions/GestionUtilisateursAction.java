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
import communication.DataObjects.Objects.PasswordLoginPolitic;
import communication.DataObjects.Objects.User;
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
			boolean reauthentification = dtp.Authenticate((String) session.getAttribute("Username"), pw, null) !=null;
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

						//TODO: Vérifier avec la politique de mot de passe
						boolean check = dtp.CreateUser(username, tpw,
								userlevel, salt,
								(String) session.getAttribute("Username"));

						if (check)
							request.setAttribute("ajoutMessage",
									"Operation réuisse");
						else
							request.setAttribute("ajoutMessage",
									"Cet utilisateur existe déjà");
				}
				else if (submit.equals("Modifier")) {
						String username = request.getParameter("username");
						String tpw = sendNewPassWord(request.getParameter("courriel"));
						PasswordLoginPolitic pwp = dtp.getPasswordLoginPolitic();

						User user = dtp.GetAllUsersFromAUserName(username).get(0);
						user.changepw = pwp.changementOublie;
						user.saltPassword = tpw;
						
						dtp.UpdateUser(user);
						//TODO: Savoir ou mettre le mot de pass temporaire.
						
				} else if (submit.equals("Reactiver")) {
					
						//TODO: vérifier que le user et le userid correspondent
						String username = request.getParameter("user");
						User user = dtp.GetAllUsersFromAUserName(username).get(
								0);
						PasswordLoginPolitic pwp = dtp
								.getPasswordLoginPolitic();
						user.enabled = true;
						user.changepw = pwp.changementBloquage;
						dtp.UpdateUser(user);
						request.setAttribute("activateMessage",
								"Operation réuisse");
				}

				else if (submit.equals("Supprimer")) {
						//TODO: vérifier que le user et le userid correspondent
						String username = request.getParameter("user");
						if (!session.getAttribute("Username").equals(username)) {
							User user = dtp.GetAllUsersFromAUserName(username)
									.get(0);
							dtp.RemoveUser(user.idUser);

							request.setAttribute("suppMessage",
									"Operation réuisse");
						}
				} else if (submit.equals("Valider")) {
						String username = request.getParameter("user");
						String type = request.getParameter("privilege");
						User user = dtp.GetAllUsersFromAUserName(username).get(
								0);
						PasswordLoginPolitic pwp = dtp
								.getPasswordLoginPolitic();
						user.roleId = Integer.parseInt(type);
						dtp.UpdateUser(user);
						request.setAttribute("privMessage", "Operation réuisse");
				}
			}
			else
				request.setAttribute("ajoutMessage", "Mauvais mot de Passe ");
		}
		
		String randomString = generateHiddenRandomString();
		request.setAttribute("hidden", randomString);
		session.setAttribute("gestionUtilisateuriddenString", randomString);
		return mapping.findForward(SUCCESS);
	}
	
	/**
	 * On simule le fait que ca envoie le mot de passe par courriel
	 * @param courriel
	 * @return
	 */
	private String sendNewPassWord(String courriel){
		return new HiddenStringGenerator().generateRandomString().substring(3,23);
	}
	
	
}