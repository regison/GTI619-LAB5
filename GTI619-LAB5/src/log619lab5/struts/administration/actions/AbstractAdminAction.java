package log619lab5.struts.administration.actions;

import log619lab5.domain.enumType.Section;
import log619lab5.struts.AbstractAction;

public class AbstractAdminAction extends AbstractAction {

	@Override
	public void setPageSection() {
		pageSection = Section.ADMIN;
	}

}
