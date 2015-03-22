package log619lab5.struts.general.actions;

import java.sql.Time;
import java.util.ArrayList;

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
import communication.DataObjects.Objects;
import communication.DataObjects.Objects.*;


public class ExecuteLoginAction extends AbstractAction {

	private final String PAGE = "ExecuteLogin";
	SecurityModuleCore securityModule;
	
	@Override
	public ActionForward directive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userName = (String) request.getParameter("username");
		String password = request.getParameter("password").toString();
		HttpSession session = request.getSession();
		
		securityModule = new SecurityModuleCore(null, session);
		
		if(userName.equals("") || password.equals("")){
			loginFailedLogic();
			pageSection = Section.LOGIN;	
			return mapping.findForward("failure");
		}
		
		request.setAttribute("Page", PAGE);
		
		String hidden = request.getParameter("hidden");
		String random = session.getAttribute("loginHiddenString").toString();
		session.setAttribute("loginHiddenString", "");
		
		if(hidden.equals("") || !hidden.equals(random)){
			loginFailedLogic();
			pageSection = Section.LOGIN;	
			return mapping.findForward("failure");
		}
		
		// TODO enlever cette section
		//Temporaire!
		Mysql connexion = new Mysql(Mysql.MYSQL_DATABASE_LOG619LAB5);
		connexion.Open();
		Objects obj = new Objects();
		User user = obj.new User();
		try {
			ArrayList<ArrayList<Object>> result = connexion.Select("Select * from log619lab5.User where name=?;", new String[] {userName}, "ndMd5Iteration", "saltNumber", "saltCounter", "idUser");
			if(result.size() != 1){
				loginFailedLogic();
				pageSection = Section.LOGIN;
				connexion.Close();
				return mapping.findForward("failure");
			}
			
			user.ndCryptIteration = Integer.parseInt(result.get(0).get(0).toString());
			user.salt = result.get(0).get(1).toString();
			user.saltCounter = Integer.parseInt(result.get(0).get(2).toString());
			user.idUser = Integer.parseInt(result.get(0).get(0).toString());
			
			securityModule.setUser(user);
			
			String query = "SELECT * FROM log619lab5.User where name= ? and saltPassword=SHA2(";
			for(int i = 1; i < user.ndCryptIteration; i++){
				query += "SHA2(";
			}
			query += "'";
			for(int i = 0; i < user.saltCounter; i++){
				query += user.salt;
			}
			query += "'?'";
			for(int j = 0; j < user.saltCounter; j++){
				query += user.salt;
			}
			query += "'";
			for(int i = 1; i < user.ndCryptIteration; i++){
				query += ", 512)";
			}
			query += ", 512);";
			System.out.println(query);
			result = connexion.Select(query, new String[] {userName, password}, "idUser", "name", "roleId", "enabled");
			if(result.size() != 1){
				loginFailedLogic();
				pageSection = Section.LOGIN;	
				connexion.Close();
				return mapping.findForward("failure");
			}
			user.idUser = Integer.parseInt(result.get(0).get(0).toString());
			user.name = result.get(0).get(1).toString();
			user.roleId = Integer.parseInt(result.get(0).get(2).toString());
			user.enabled = Boolean.parseBoolean(result.get(0).get(3).toString());
			if(!user.enabled){
				loginFailedLogic();
				pageSection = Section.LOGIN;
				connexion.Close();
				return mapping.findForward("failure");
			}
			result = connexion.Select("Select * from log619lab5.Role where idRole= ? ;", new String[] {user.roleId + ""}, "idRole", "roleLevelId", "roleName", "timeConnexion");
			user.role = obj.new Role();
			user.role.idRole = Integer.parseInt(result.get(0).get(0).toString());
			user.role.roleLevelId = Integer.parseInt(result.get(0).get(1).toString());
			user.role.roleName = result.get(0).get(2).toString();
			user.role.timeConnexion = Time.valueOf(result.get(0).get(3).toString());
			
			result = connexion.Select("Select * from log619lab5.RoleLevel where idRoleLevel= ? ;", new String[] {user.role.roleLevelId + ""}, "idRoleLevel", "caneEditOwnAccount", "canChangeMdp", "canEditAll", 
						"canModifyDelay", "canModifynbTentative", "canModifyBlocage", "canModifyComplexiteMdp");
			user.role.roleLevel = obj.new RoleLevel();
			user.role.roleLevel.idRoleLevel = Integer.parseInt(result.get(0).get(0).toString());
			user.role.roleLevel.caneEditOwnAccount = Integer.parseInt(result.get(0).get(1).toString()) == 1 ? true : false;
			user.role.roleLevel.canChangeMdp = Integer.parseInt(result.get(0).get(2).toString()) == 1 ? true : false;
			user.role.roleLevel.canEditAll = Integer.parseInt(result.get(0).get(3).toString()) == 1 ? true : false;
			user.role.roleLevel.canModifyDelay = Integer.parseInt(result.get(0).get(4).toString()) == 1 ? true : false;
			user.role.roleLevel.canModifynbTentative = Integer.parseInt(result.get(0).get(5).toString()) == 1 ? true : false;
			user.role.roleLevel.canModifyBlocage = Integer.parseInt(result.get(0).get(6).toString()) == 1 ? true : false;
			user.role.roleLevel.canModifyComplexiteMdp = Integer.parseInt(result.get(0).get(7).toString()) == 1 ? true : false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connexion.Close();
		//
		securityModule.setUser(user);
		securityModule.updateSuccessfullLoginTime();
		
		if(user.role.roleName.equals(Objects.Role.AdministratorRoleName)){
			pageSection = Section.LOGIN;	
			return mapping.findForward("admin");
		}
		else if(user.role.roleName.equals(Objects.Role.CercleRoleName)){
			pageSection = Section.LOGIN;	
			return mapping.findForward("cercle");
		}
		else if(user.role.roleName.equals(Objects.Role.SquareRoleName)){
			pageSection = Section.LOGIN;	
			return mapping.findForward("carre");
		}
		else{
			loginFailedLogic();
			pageSection = Section.LOGIN;	
			return mapping.findForward("failure");
		}
	}

	private void loginFailedLogic(){
		securityModule.manageUnsuccessfullLogin();
	}
}