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


public class SaveConfigurationAction extends AbstractAction {

	private final String PAGE = "Admin";
	
@Override
public ActionForward directive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	request.setAttribute("Page", PAGE);
	HttpSession session = request.getSession();
	
	String hidden = request.getParameter("hidden");
	
	request.setAttribute("hidden", "");
	session.setAttribute("adminHiddenString", "");
	
	if(hidden==null || hidden.isEmpty() || !hidden.equals(session.getAttribute("adminHiddenString")))
	{
		return mapping.findForward(FAILURE);
	}
	
	String tentativeMax = request.getParameter("tentativeMax");
	String delais = request.getParameter("delais");
	String bloquage = request.getParameter("bloquage");
	String changementOublie = request.getParameter("changementOublie");
	String changementTentatives = request.getParameter("changementTentatives");
	String longueurMinMDP = request.getParameter("lmin");
	String longueurMaxMDP = request.getParameter("lmax");
	String politiqueMaj = request.getParameter("politiqueMaj");
	String politiqueCarac = request.getParameter("politiqueCarac");
	String politiqueChiffres = request.getParameter("politiqueChiffres");
	String changePassConnect = request.getParameter("changePassConnect");
	String changePassPage = request.getParameter("changePassPage");
	
	request.setAttribute("message", "Operation réussie");
	pageSection = Section.ADMIN;
	String randomString = generateHiddenRandomString();
	request.setAttribute("hidden", randomString);
	session.setAttribute("adminHiddenString", randomString);
	session.setAttribute("from", "AdminPage");
	
	return mapping.findForward(SUCCESS);
    }
}