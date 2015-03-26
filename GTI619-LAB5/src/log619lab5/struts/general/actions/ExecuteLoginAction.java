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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import securityLayer.securityModule.Core.SecurityModuleCore;
import database.IDatabase;
import database.mysql.Mysql;
import communication.DataMapping.DataProvider;
import communication.DataObjects.Objects;
import communication.DataObjects.Objects.*;


public class ExecuteLoginAction extends AbstractAction {

	private final String PAGE = "ExecuteLogin";
	SecurityModuleCore securityModule;
	
	private DataProvider dtP;


	
	@Override
	public ActionForward directive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		String userName = (String) request.getParameter("username");
		String password = (String) request.getParameter("password");

		HttpSession session = request.getSession();
		
		session.setAttribute("Username", "");
		session.setAttribute("Role", "");
		
		securityModule = new SecurityModuleCore(null, session);
		
		if(userName == null || password == null || userName.equals("") || password.equals("")){
			loginFailedLogic();
			pageSection = Section.GENERAL;	
			return mapping.findForward("failure");
		}
		
		request.setAttribute("Page", PAGE);
		
		String hidden = request.getParameter("hidden");
		String random = session.getAttribute("loginHiddenString").toString();
		session.setAttribute("loginHiddenString", "");
		
		if(hidden.equals("") || !hidden.equals(random)){
			loginFailedLogic();
			pageSection = Section.GENERAL;	
			return mapping.findForward("failure");
		}
		
		dtP = new DataProvider(Mysql.MYSQL_DATABASE_LOG619LAB5);
		
		Objects obj = new Objects();
		User _currentUser = obj.new User();
		
		try {
			//User _currentUsertest = dtP.G
			_currentUser = dtP.Authenticate(userName, password);			
			 
			
			
			if(_currentUser == null){
				loginFailedLogic();
				pageSection = Section.GENERAL;
				dtP.Dispose();
				return mapping.findForward("failure");
			}			
			
			securityModule.setUser(_currentUser);		
			
			if(!_currentUser.enabled){
				loginFailedLogic();
				pageSection = Section.GENERAL;
				dtP.Dispose();
				return mapping.findForward("failure");
			}

			_currentUser.role = dtP.GetRole(_currentUser.roleId);			
			_currentUser.role.roleLevel = dtP.GetRoleLevel(_currentUser.role.roleLevelId);	

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Close the connection to DB
		dtP.Dispose();
		
		securityModule.setUser(_currentUser);
		securityModule.updateSuccessfullLoginTime();	

		session.setAttribute("Username", _currentUser.name);
		session.setAttribute("Role", _currentUser.role.roleName);
		session.setAttribute("LastLoggedInActionTime", Calendar.getInstance().getTimeInMillis());
		session.setAttribute("idUser", _currentUser.idUser);
		
		if(_currentUser.role.roleName.equals(Objects.Role.AdministratorRoleName)){
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
			loginFailedLogic();
			pageSection = Section.GENERAL;	
			return mapping.findForward("failure");
		}
	}

	private void loginFailedLogic(){
		securityModule.manageUnsuccessfullLogin();
	}
}
