package log619lab5.struts;

import org.apache.struts.action.ActionForm;

public class AbstractForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
    protected String errorMessage;
    protected String successMessage;
    
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String p_errorMessage) {
        this.errorMessage = p_errorMessage;
    }
    
    public String getSuccessMessage() {
    	return this.successMessage;
    }
    
    public void setSuccessMessage(String p_successMessage) {
    	this.successMessage = p_successMessage;
    }
}

