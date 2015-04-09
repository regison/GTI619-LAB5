package log619lab5.struts;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import log619lab5.domain.enumType.Section;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import communication.DataObjects.Objects;

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
		setSessionWithCookies(request, response, "Language");
		//setSessionWithCookies(request, response, "UserName");
		setPageSection();
		if(validatePageAccess(request, response))
			action = directive(mapping, form, request, response);
		else
			redirectPage(request, response, "AccessDenied.do");
		
		
		/*Logger logger = Logger.getLogger("MyLog");  
	    FileHandler fh;  

	    try {  

	        // This block configure the logger with handler and formatter  
	        fh = new FileHandler("C:/temp/test/MyLogFile.log");  
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);  

	        // the following statement is used to log any messages  
	        logger.info("My first log");  

	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  

	    logger.info("Hi How r u?");*/
		
		return action;
	}
	
	private boolean validatePageAccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(true);
    	boolean validate = true;
		if (pageSection != null) {
			if(!pageSection.equals(Section.GENERAL)){
				if (pageSection.equals(Section.CARRE)
						&& (session.getAttribute("Role") == null || !(session.getAttribute("Role").equals(Objects.Role.AdministratorRoleName) 
								|| session.getAttribute("Role").equals(Objects.Role.SquareRoleName)))) {
					validate = false;
				} else if (pageSection.equals(Section.CERCLE)
						&& (session.getAttribute("Role") == null || !(session.getAttribute("Role").equals(Objects.Role.AdministratorRoleName) 
								|| session.getAttribute("Role").equals(Objects.Role.CercleRoleName)))){
					validate = false;
				} else if (pageSection.equals(Section.ADMIN) 
						&& (session.getAttribute("Role") == null || !session.getAttribute("Role").equals(Objects.Role.AdministratorRoleName))){
					validate = false;
				} else if (pageSection.equals(Section.CONNECTED) && (session.getAttribute("Username") == null || session.getAttribute("Username").equals("")))
					validate = false;
				
			}
		} else {
			validate = false;
		}
		
		return validate;
		
//		if (pageSection != null) {
//			if (pageSection.equals(Section.LOGIN) 
//					&& session.getAttribute("Username") != null
//					&& session.getAttribute("Role") != null) {
//				redirectPage(request, response, "Login.do");
//			} else {
//				if (pageSection.equals(Section.CARRE)
//						&& (session.getAttribute("Role") == null || !(session.getAttribute("Role").equals(Objects.Role.AdministratorRoleName) 
//								|| session.getAttribute("Role").equals(Objects.Role.SquareRoleName)))) {
//					redirectPage(request, response, "AccessDenied.do");
//				} else if (pageSection.equals(Section.CERCLE)
//						&& (session.getAttribute("Role") == null || !(session.getAttribute("Role").equals(Objects.Role.AdministratorRoleName) 
//								|| session.getAttribute("Role").equals(Objects.Role.CercleRoleName)))){
//					redirectPage(request, response, "AccessDenied.do");
//				} else if (pageSection.equals(Section.ADMIN) 
//						&& (session.getAttribute("Role") == null || !session.getAttribute("Role").equals(Objects.Role.AdministratorRoleName))){
//					redirectPage(request, response, "AccessDenied.do");
//				}
//			}
//		} else {
//			redirectPage(request, response, "Login.do");
//		}
		
	}
	
	private void redirectPage(HttpServletRequest request, HttpServletResponse response, String p_page) throws IOException {		
		request.setAttribute("Page", p_page); 
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
		HiddenStringGenerator hiddenGenerator = new HiddenStringGenerator();
		return hiddenGenerator.generateRandomString();
	}
	
	public abstract void setPageSection();
}
