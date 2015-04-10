package log619lab5.struts.general.actions;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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

import securityLayer.securityModule.Core.SecurityModuleCore;
import database.IDatabase;
import database.mysql.Mysql;
import communication.DataMapping.DataProvider;
import communication.DataObjects.Objects;
import communication.DataObjects.Objects.*;


public class ExecuteSecondLoginAction extends AbstractAction {

	private final String PAGE = "ExecuteSecondLogin";
	SecurityModuleCore securityModule;
	
	private DataProvider dtP;


	
	@Override
	public ActionForward directive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		String password = (String) request.getParameter("password");
		dtP = new DataProvider();
		HttpSession session = request.getSession();
		
		User _currentUser = dtP.GetUserByUsername(session.getAttribute("Username").toString());
		securityModule = new SecurityModuleCore(_currentUser, session);
		
		request.setAttribute("Page", PAGE);
		
		String hidden = request.getParameter(SessionAttributeIdentificator.HIDDEN);
		String random = session.getAttribute(SessionAttributeIdentificator.LOGINHIDDENSTRING).toString();
		session.setAttribute(SessionAttributeIdentificator.LOGINHIDDENSTRING, "");
		
		if(hidden.equals("") || !hidden.equals(random)){
			loginFailedLogic(request.getRemoteAddr());
			pageSection = Section.GENERAL;	
			return mapping.findForward("failure");
		}
		
		dtP = new DataProvider(false);
		
		int[] in = (int[]) session.getAttribute("indexes");
		
		
		//TODO
//		if(!getString(_currentUser.idUser,in).equals(password)){
//			loginFailedLogic();
//			pageSection = Section.GENERAL;	
//			return mapping.findForward("failure");
//		}
		
		session.removeAttribute("indexes");
		securityModule.updateSuccessfullLoginTime(_currentUser.idUser, request.getRemoteAddr());
		
		// Login successful, instantiate old session and create a new one
		session.invalidate();
		session = request.getSession();	

		session.setAttribute(SessionAttributeIdentificator.USERNAME, _currentUser.name);
		session.setAttribute(SessionAttributeIdentificator.ROLE, _currentUser.role.roleName);
		session.setAttribute(SessionAttributeIdentificator.LASTLOGGEDINACTIONTIME, Calendar.getInstance().getTimeInMillis());
		session.setAttribute(SessionAttributeIdentificator.IDUSER, _currentUser.idUser);
		
		if(_currentUser.changepw){
			return mapping.findForward("changepw");
		}
		
		_currentUser.role = dtP.GetRole(_currentUser.roleId);			
		_currentUser.role.roleLevel = dtP.GetRoleLevel(_currentUser.role.roleLevelId);

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
