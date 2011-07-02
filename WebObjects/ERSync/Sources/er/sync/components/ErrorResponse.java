/*
 * ErrorResponse.java
 *
 * This class is public domain software - that is, you can do whatever you want
 * with it, and include it software that is licensed under the BSD license
 *
 * If you make modifications to this code that you think would benefit the
 * wider community, please send me a copy and I'll post it on my site.
 *
 */
package er.sync.components;

import com.webobjects.appserver.WOContext;
import er.extensions.components.ERXComponent;

/**
 * Global Village Consulting
 * @author	DavidA
 */
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