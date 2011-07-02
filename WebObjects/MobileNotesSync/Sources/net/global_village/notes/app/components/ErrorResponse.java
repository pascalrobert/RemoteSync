package net.global_village.notes.app.components;

import com.webobjects.appserver.WOContext;

import er.extensions.components.ERXComponent;

@SuppressWarnings("serial")
public class ErrorResponse extends ERXComponent {
    
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