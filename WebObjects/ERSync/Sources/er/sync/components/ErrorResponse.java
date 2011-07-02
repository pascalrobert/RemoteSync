package er.sync.components;

import com.webobjects.appserver.WOContext;
import er.extensions.components.ERXComponent;

public class ErrorResponse extends ERXComponent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1063277227910345010L;
	
	private String errorMessage;
	
	public ErrorResponse(WOContext context) {
        super(context);
    }

	public void setErrorMessage( String errorMessage ) {
		this.errorMessage = errorMessage;
	}

	public String errorMessage() {
		return errorMessage;
	}
}