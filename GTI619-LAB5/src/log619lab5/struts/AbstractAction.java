package log619lab5.struts;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.mail.Session;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import log619lab5.domain.enumType.Section;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import communication.DataMapping.DataProvider;
import communication.DataObjects.Objects;
import communication.DataObjects.Objects.User;
import communication.DataMapping.*;
import securityLayer.securityModule.XSSProtection.HiddenStringGenerator;

public abstract class AbstractAction extends Action {
	protected static final String SUCCESS = "success";
	protected static final String FAILURE = "failure";
	protected static final String FRENCH = "fr";
	protected static final String ENGLISH = "en";
	
	protected Section pageSection;
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward action = null;
		try {
			DataProvider dtp = new DataProvider();
			if(dtp.IpIsBlackListed(request.getRemoteAddr())){
				return null;
			}
			
			//setSessionWithCookies(request, response, "Language");
			//setSessionWithCookies(request, response, "UserName");
			setPageSection();
			if(request.getSession(true).getAttribute(SessionAttributeIdentificator.LASTACTIVITY) == null)
				request.getSession(true).setAttribute(SessionAttributeIdentificator.LASTACTIVITY,new Date().getTime());
			if(new Date().getTime() - (long) request.getSession(true).getAttribute(SessionAttributeIdentificator.LASTACTIVITY) >= 20*60*1000){
				request.getSession(true).invalidate();
				redirectPage(request, response, "Login.do");
			}
			else if(validatePageAccess(request, response))
				action = directive(mapping, form, request, response);
			else
				redirectPage(request, response, "AccessDenied.do");
		} catch (Exception e) {
			ExceptionLogger.LogException(e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return action;
	}
	
	private boolean validatePageAccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(true);
		DataProvider dtp = new DataProvider();
    	boolean validate = true;
		if (pageSection != null) {
			if(!pageSection.equals(Section.GENERAL)){
				if(!pageSection.equals(Section.CONNECTED) && session.getAttribute(SessionAttributeIdentificator.IDUSER) == null || session.getAttribute(SessionAttributeIdentificator.IDUSER).equals("") 
						|| !dtp.GetUser(Integer.parseInt(session.getAttribute(SessionAttributeIdentificator.IDUSER).toString())).enabled
						|| dtp.GetUser(Integer.parseInt(session.getAttribute(SessionAttributeIdentificator.IDUSER).toString())).isLogOutNeeded){
					validate = false;
					if (session.getAttribute(SessionAttributeIdentificator.IDUSER) != null && !session.getAttribute(SessionAttributeIdentificator.IDUSER).equals("")) {
						User u = dtp.GetUser(Integer.parseInt(session.getAttribute(SessionAttributeIdentificator.IDUSER).toString()));
						u.isAuthenticated = false;
						dtp.UpdateUser(u);
					}
					session.invalidate();
				}
				else if (pageSection.equals(Section.CARRE)
						&& (session.getAttribute(SessionAttributeIdentificator.ROLE) == null || !(session.getAttribute(SessionAttributeIdentificator.ROLE).equals(Objects.Role.AdministratorRoleName) 
								|| session.getAttribute(SessionAttributeIdentificator.ROLE).equals(Objects.Role.SquareRoleName)))) {
					validate = false;
				} else if (pageSection.equals(Section.CERCLE)
						&& (session.getAttribute(SessionAttributeIdentificator.ROLE) == null || !(session.getAttribute(SessionAttributeIdentificator.ROLE).equals(Objects.Role.AdministratorRoleName) 
								|| session.getAttribute(SessionAttributeIdentificator.ROLE).equals(Objects.Role.CercleRoleName)))){
					validate = false;
				} else if (pageSection.equals(Section.ADMIN) 
						&& (session.getAttribute(SessionAttributeIdentificator.ROLE) == null || !session.getAttribute(SessionAttributeIdentificator.ROLE).equals(Objects.Role.AdministratorRoleName))){
					validate = false;
				} else if (pageSection.equals(Section.CONNECTED) && (session.getAttribute(SessionAttributeIdentificator.USERNAME) == null || session.getAttribute(SessionAttributeIdentificator.USERNAME).equals("")))
					validate = false;
				
			}
		} else {
			validate = false;
		}
		
		return validate;
		
	}
	
	private void redirectPage(HttpServletRequest request, HttpServletResponse response, String p_page) throws IOException {		
		request.setAttribute(SessionAttributeIdentificator.PAGE, p_page); 
		response.sendRedirect(p_page);
	}
	
	public ActionForward directive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return mapping.findForward(SUCCESS);
	}
	
	protected void generateCookies(HttpServletRequest request, HttpServletResponse response, String p_name, String p_value) {
		Cookie cook = new Cookie(p_name, p_value);
		int totalValidityMinutes = 30;
		cook.setMaxAge(totalValidityMinutes * 60);
		cook.setHttpOnly(true);
		response.addCookie(cook); 
	}
	
	
	protected void setSessionWithCookies(HttpServletRequest request, HttpServletResponse response, String p_name) {
		Cookie[] cookies = request.getCookies();

	    for(int i = 0; cookies != null && i < cookies.length; i++) { 
	        Cookie cook = cookies[i];
	        if (cook.getName().equals(p_name)) {
	            HttpSession session = request.getSession(true);
	        	session.setAttribute(p_name, cook.getValue());
	        }
	    }  
	}
	
	public String generateHiddenRandomString(){
		System.out.println("GenerationRandom");
		HiddenStringGenerator hiddenGenerator = new HiddenStringGenerator();
		return hiddenGenerator.generateRandomString().replace("", "");
	}
	
	public abstract void setPageSection();
}
