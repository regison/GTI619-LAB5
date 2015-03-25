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

import securityLayer.securityModule.gestionPassword.SecurityModulePassword;
import communication.DataMapping.DataProvider;
import database.IDatabase;
import database.mysql.Mysql;


public class GestionUtilisateursAction extends AbstractAction {

	private final String PAGE = "GestionUtilisateur";
	
@Override
public ActionForward directive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	request.setAttribute("Page", PAGE);
	HttpSession session = request.getSession();
	String submit= request.getParameter("submit");
	pageSection = Section.ADMIN;
	
	if(submit != null){
		String hidden = request.getParameter("hidden");
		String pw = request.getParameter("password");
		if(hidden==null || hidden.isEmpty() || !hidden.equals(session.getAttribute("gestionUtilisateuriddenString")) /*|| !ValidateUser*/)
		{
			return mapping.findForward("carre");
		}	
		
		if("Ajouter".equals(submit)){
			String username = request.getParameter("username");
			String tpw = request.getParameter("tpassword");
			String type = request.getParameter("acces");
			
			request.setAttribute("ajoutMessage", "Operation réuisse");
			
		}
		
		if(submit.equals("Reactiver")){
			String username = request.getParameter("user");		
			request.setAttribute("activateMessage", "Operation réuisse");
			
		}
		
		if(submit.equals("Supprimer")){
			String username = request.getParameter("user");		
			request.setAttribute("suppMessage", "Operation réuisse");	
		}
	}
	
	String randomString = generateHiddenRandomString();
	request.setAttribute("hidden", randomString);
	request.setAttribute("hidden", randomString);
	session.setAttribute("gestionUtilisateuriddenString", randomString);
	return mapping.findForward(SUCCESS);
    }
}