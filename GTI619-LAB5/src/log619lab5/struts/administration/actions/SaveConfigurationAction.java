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

import database.IDatabase;
import database.mysql.Mysql;
import communication.DataMapping.DataProvider;
import communication.DataObjects.Objects;
import communication.DataObjects.Objects.*;

public class SaveConfigurationAction extends AbstractAdminAction {

	private final String PAGE = "Admin";
	
	@Override
	public ActionForward directive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("Page", PAGE);
		HttpSession session = request.getSession();
		
		DataProvider dp = new DataProvider(false);
		
		if(request.getParameter("submit")!=null){
			Objects bob = new Objects();
			Objects.LoginPolitic lop = bob.new LoginPolitic();
			Objects.PasswordPolitic pwp = bob.new PasswordPolitic();
			
			String hidden = request.getParameter("hidden");
			if(hidden==null || hidden.isEmpty() || !hidden.equals(session.getAttribute("adminHiddenString")))
			{
				return mapping.findForward(FAILURE);
			}
			
			try{
				lop.maxTentative = Integer.parseInt(request.getParameter("tentativeMax"));
				lop.delais = Integer.parseInt(request.getParameter("delais"));
				lop.bloquage2tentatives = request.getParameter("bloquage").equals("true");
				pwp.changementOublie = request.getParameter("changementOublie")!=null;
				pwp.changementDepassement = request.getParameter("changementTentatives")!=null;
				pwp.min = Integer.parseInt(request.getParameter("lmin"));
				pwp.max = Integer.parseInt(request.getParameter("lmax"));
				pwp.complexity = 0;
				if(request.getParameter("politiqueMaj")!=null);
					pwp.complexity+=1;
				if(request.getParameter("politiqueCarac")!=null);
					pwp.complexity+=2;
				if(request.getParameter("politiqueChiffres")!=null);
					pwp.complexity+=4;
				if(request.getParameter("politiqueMin")!=null);
					pwp.complexity+=8;
				pwp.lastPasswords = Integer.parseInt(request.getParameter("tentativeMax"));
				
				if(dp.UpdatePolitics(pwp, lop))
					request.setAttribute("message", "Operation r�ussie");
				else
					request.setAttribute("message", "Operation Echou�e");
			}	
			catch(Exception e){
				request.setAttribute("message", "Operation Echou�e");
			}
		}
		
		
		String randomString = generateHiddenRandomString();
		request.setAttribute("hidden", randomString);
		session.setAttribute("adminHiddenString", randomString);
		session.setAttribute("from", "AdminPage");
		
		return mapping.findForward(SUCCESS);
    }
}