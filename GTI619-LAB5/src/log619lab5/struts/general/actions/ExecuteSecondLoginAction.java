package log619lab5.struts.general.actions;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import log619lab5.domain.enumType.Section;
import log619lab5.struts.AbstractAction;
import log619lab5.struts.SessionAttributeIdentificator;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import securityLayer.securityModule.Core.SecurityModuleCore;
import communication.DataMapping.DataProvider;
import communication.DataObjects.Objects;
import communication.DataObjects.Objects.*;


public class ExecuteSecondLoginAction extends AbstractAction {

	private final String PAGE = "ExecuteSecondLogin";
	SecurityModuleCore securityModule;
	
	private DataProvider dtP;


	
	@Override
	public ActionForward directive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("Start second login --------------------- ");
		String password = (String) request.getParameter("password");
		dtP = new DataProvider();
		HttpSession session = request.getSession(true);
		
		User _currentUser = dtP.GetUserByUsername(session.getAttribute("Username").toString());
		securityModule = new SecurityModuleCore(_currentUser, session);
		
		request.setAttribute("Page", PAGE);
		
		String hidden = request.getParameter(SessionAttributeIdentificator.HIDDEN);
		String random = session.getAttribute(SessionAttributeIdentificator.LOGINHIDDENSTRING).toString();
		session.setAttribute(SessionAttributeIdentificator.LOGINHIDDENSTRING, "");
		
		if(hidden.equals("") || !hidden.equals(random)){
			System.out.println("Start second login failed. Hidden not the same.");
			System.out.println("End second login --------------------- ");
			loginFailedLogic(request.getRemoteAddr());
			pageSection = Section.GENERAL;	
			return mapping.findForward("failure");
		}
		
		dtP = new DataProvider(false);
		
		int[] in = (int[]) session.getAttribute("indexes");
		
		String text = "";
		for(int i=0;i<in.length;i++){
			text += _currentUser.secondFactorPW.charAt(in[i]);
		}
		
		if(!text.equals(password)){
			loginFailedLogic(request.getRemoteAddr());
			pageSection = Section.GENERAL;	
			return mapping.findForward("failure");
		}
		
		if(_currentUser.changepw){
			System.out.println("Change pw");
			System.out.println("End second login --------------------- ");
			return mapping.findForward("changepw");
		}
		
		_currentUser.role = dtP.GetRole(_currentUser.roleId);			
		_currentUser.role.roleLevel = dtP.GetRoleLevel(_currentUser.role.roleLevelId);
		
		session.removeAttribute("indexes");
		securityModule.updateSuccessfullLoginTime(_currentUser.idUser, request.getRemoteAddr());
				
		// Login successful, instantiate old session and create a new one
		session.invalidate();
		session = request.getSession(true);	

		session.setAttribute(SessionAttributeIdentificator.USERNAME, _currentUser.name);
		session.setAttribute(SessionAttributeIdentificator.ROLE, _currentUser.role.roleName);
		session.setAttribute(SessionAttributeIdentificator.LASTLOGGEDINACTIONTIME, Calendar.getInstance().getTimeInMillis());
		session.setAttribute(SessionAttributeIdentificator.IDUSER, _currentUser.idUser);
		
		System.out.println("Role selectionlogin --------------------- ");
		System.out.println("End second login --------------------- ");
		if(_currentUser.role == null){
			pageSection = Section.GENERAL;
			return mapping.findForward("norole");
		}
		else if(_currentUser.role.roleName.equals(Objects.Role.AdministratorRoleName)){
			pageSection = Section.ADMIN;	
			return mapping.findForward("admin");
		}

		else if(_currentUser.role.roleName.equals(Objects.Role.CercleRoleName)){
			pageSection = Section.CERCLE;	
			return mapping.findForward("cercle");
		}

		else if(_currentUser.role.roleName.equals(Objects.Role.SquareRoleName)){
			pageSection = Section.CARRE;	
			return mapping.findForward("carre");
		}
		else{
			loginFailedLogic(request.getRemoteAddr());
			pageSection = Section.GENERAL;	
			return mapping.findForward("failure");
		}
	}

	private void loginFailedLogic(String remoteIP){
		securityModule.manageUnsuccessfullLogin(remoteIP);
	}
	
	@Override
	public void setPageSection() {
		pageSection = Section.CONNECTED;
	}
}
