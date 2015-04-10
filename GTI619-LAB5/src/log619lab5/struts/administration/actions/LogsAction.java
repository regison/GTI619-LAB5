package log619lab5.struts.administration.actions;

import java.util.ArrayList;

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

import communication.DataMapping.DataProvider;
import communication.DataObjects.Objects.Log;
import database.IDatabase;
import database.mysql.Mysql;


public class LogsAction extends AbstractAdminAction {

	private final String PAGE = "Logs";
	
	@Override
	public ActionForward directive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute(SessionAttributeIdentificator.PAGE, PAGE);
		return mapping.findForward(SUCCESS);
    }
}