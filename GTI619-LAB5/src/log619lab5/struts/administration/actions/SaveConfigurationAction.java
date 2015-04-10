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

import database.IDatabase;
import database.mysql.Mysql;
import communication.DataMapping.DataProvider;
import communication.DataMapping.ExceptionLogger;
import communication.DataObjects.Objects;
import communication.DataObjects.Objects.*;

public class SaveConfigurationAction extends AbstractAdminAction {

	private final String PAGE = "Admin";
	
	@Override
	public ActionForward directive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute(SessionAttributeIdentificator.PAGE, PAGE);
		HttpSession session = request.getSession(true);
		
		DataProvider dp = new DataProvider(false);
		
		if(request.getParameter("submit")!=null){
			Objects bob = new Objects();
			Objects.PasswordLoginPolitic pwp = bob.new PasswordLoginPolitic();
			
			String hidden = request.getParameter("hidden");
			if(hidden==null || hidden.isEmpty() || !hidden.equals(session.getAttribute(SessionAttributeIdentificator.ADMINHIDDENSTRING)))
			{
				session.setAttribute("WaitingForAuth" + SessionAttributeIdentificator.ADMINHIDDENSTRING, "");
				return mapping.findForward(FAILURE);
			}
			session.setAttribute("WaitingForAuth" + SessionAttributeIdentificator.ADMINHIDDENSTRING, "");
			try{
				pwp.maxTentative = Integer.parseInt(request.getParameter("tentativeMax"));
				pwp.delais = Integer.parseInt(request.getParameter("delais"));
				pwp.bloquage2tentatives = request.getParameter("bloquage").equals("true");
				pwp.changementOublie = request.getParameter("changementOublie")!=null;
				pwp.changementDepassement = request.getParameter("changementDepassement")!=null;
				pwp.changementNouveau = request.getParameter("changementNouveau")!=null;
				pwp.min = Integer.parseInt(request.getParameter("lmin"));
				pwp.max = Integer.parseInt(request.getParameter("lmax"));
				pwp.complexity = 0;
				if(request.getParameter("politiqueMaj")!=null)
					pwp.complexity+=1;
				if(request.getParameter("politiqueCarac")!=null)
					pwp.complexity+=2;
				if(request.getParameter("politiqueChiffres")!=null)
					pwp.complexity+=4;
				if(request.getParameter("politiqueMin")!=null)
					pwp.complexity+=8;
				pwp.lastPasswords = Integer.parseInt(request.getParameter("ancien"));
				
				if(dp.UpdatePolitics(pwp))
					request.setAttribute("message", "Operation r�ussie");
				else
					request.setAttribute("message", "Operation Echou�e");
			}	
			catch(Exception e){
				ExceptionLogger.LogException(e);
				request.setAttribute("message", "Operation Echou�e");
			}
		}
		
		
		String randomString = generateHiddenRandomString();
		handleHidden(request, SessionAttributeIdentificator.ADMINHIDDENSTRING);
		session.setAttribute(SessionAttributeIdentificator.FROM, "AdminPage");
		
		return mapping.findForward(SUCCESS);
    }
}