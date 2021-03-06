package log619lab5.struts.general.actions;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

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
import communication.DataMapping.ExceptionLogger;
import communication.DataObjects.Objects;
import communication.DataObjects.Objects.*;


public class ExecuteLoginAction extends AbstractAction {

	private final String PAGE = "ExecuteLogin";
	SecurityModuleCore securityModule;
	
	private DataProvider dtP;


	
	@Override
	public ActionForward directive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("Start Execute Login section ------------------------------------------------ ");
		String userName = (String) request.getParameter("username");
		String password = (String) request.getParameter("password");
		HttpSession session = request.getSession(true);
		
		session.setAttribute(SessionAttributeIdentificator.USERNAME, "");
		session.setAttribute(SessionAttributeIdentificator.ROLE, "");

		securityModule = new SecurityModuleCore(null, session);
		
		if(session.getAttribute(SessionAttributeIdentificator.LOGINHIDDENSTRING) == null){
			System.out.println("hidden string null");
			System.out.println("End Login section ------------------------------------------------ ");
			loginFailedLogic(request.getRemoteAddr());
			pageSection = Section.GENERAL;	
			return mapping.findForward("failure");
		}
		
		String hidden = request.getParameter(SessionAttributeIdentificator.HIDDEN);
		String random = session.getAttribute(SessionAttributeIdentificator.LOGINHIDDENSTRING).toString();
		session.setAttribute(SessionAttributeIdentificator.LOGINHIDDENSTRING, "");
		session.setAttribute("WaitingForAuth" + SessionAttributeIdentificator.LOGINHIDDENSTRING, "");
		
		if(userName == null || password == null || userName.equals("") || password.equals("")){
			System.out.println("Username or password: null or empty");
			loginFailedLogic(request.getRemoteAddr());
			pageSection = Section.GENERAL;	
			return mapping.findForward("failure");
		}
		
		request.setAttribute(SessionAttributeIdentificator.PAGE, PAGE);
		
		if(hidden.equals("") || !hidden.equals(random)){
			System.out.println("hidden is not the same as in session or is null. hidden: " + hidden + " random : " + random);
			System.out.println("End Login section ------------------------------------------------ ");
			loginFailedLogic(request.getRemoteAddr());
			pageSection = Section.GENERAL;	
			return mapping.findForward("failure");
		}
		
		dtP = new DataProvider(false);
		
		Objects obj = new Objects();
		User _currentUser = obj.new User();
		
		try {
			//User _currentUsertest = dtP.G
			_currentUser = dtP.Authenticate(userName, password, securityModule);			
			 
			if(_currentUser == null){
				System.out.println("Currrent user is null");
				System.out.println("End Login section ------------------------------------------------ ");
				loginFailedLogic(request.getRemoteAddr());
				pageSection = Section.GENERAL;
				return mapping.findForward("failure");
			}
			if(!_currentUser.enabled){
				System.out.println("Current user disabled");
				System.out.println("End Login section ------------------------------------------------ ");
				loginFailedLogic(request.getRemoteAddr());
				pageSection = Section.GENERAL;
				return mapping.findForward("bloque");
			}
			if(_currentUser.crypVersion==2){
				Random rnd = new Random();
				int[] indexes = new int[rnd.nextInt(11)+10];
				for(int i=0;i<indexes.length;i++){
					indexes[i] = rnd.nextInt(50);
				}
				handleHidden(request, SessionAttributeIdentificator.LOGINHIDDENSTRING);
				session.setAttribute("indexes", indexes);
				session.setAttribute(SessionAttributeIdentificator.USERNAME, _currentUser.name);
				session.setAttribute(SessionAttributeIdentificator.IDUSER, _currentUser.idUser);
				
				System.out.println("Second login needed");
				System.out.println("End Login section ------------------------------------------------ ");
				return mapping.findForward("secondLogin");
			}
			 
			if(_currentUser.changepw){
				System.out.println("Password change needed");
				System.out.println("End Login section ------------------------------------------------ ");
				session.setAttribute(SessionAttributeIdentificator.USERNAME, _currentUser.name);
				session.setAttribute(SessionAttributeIdentificator.IDUSER, _currentUser.idUser);
				pageSection = Section.CONNECTED;
				return mapping.findForward("changepw");
			}
			_currentUser.role = dtP.GetRole(_currentUser.roleId);			
			_currentUser.role.roleLevel = dtP.GetRoleLevel(_currentUser.role.roleLevelId);	

		} catch (Exception e) {
			ExceptionLogger.LogException(e);
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Exception Cought");
			System.out.println("End Login section ------------------------------------------------ ");
			loginFailedLogic(request.getRemoteAddr());
			pageSection = Section.GENERAL;
			return mapping.findForward("failure");
		}
		securityModule.updateSuccessfullLoginTime(_currentUser.idUser, request.getRemoteAddr());
		
		// Login successful, invalidate old session and create a new one
		session.invalidate();
		session = request.getSession(true);	

		session.setAttribute(SessionAttributeIdentificator.USERNAME, _currentUser.name);
		session.setAttribute(SessionAttributeIdentificator.ROLE, _currentUser.role.roleName);
		session.setAttribute(SessionAttributeIdentificator.LASTLOGGEDINACTIONTIME, Calendar.getInstance().getTimeInMillis());
		session.setAttribute(SessionAttributeIdentificator.IDUSER, _currentUser.idUser);

		System.out.println("End Login section ------------------------------------------------ ");
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
		if(securityModule != null)
			securityModule.manageUnsuccessfullLogin(remoteIP);
	}
	
	@Override
	public void setPageSection() {
		pageSection = Section.GENERAL;
	}
}
