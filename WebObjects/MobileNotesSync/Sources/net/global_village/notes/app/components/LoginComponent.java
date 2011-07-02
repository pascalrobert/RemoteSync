package net.global_village.notes.app.components;

import net.global_village.notes.Session;
import net.global_village.notes.model.entities.User;

import com.webobjects.appserver.WOComponent;
import com.webobjects.appserver.WOContext;

public class LoginComponent extends WOComponent 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2960969494414671987L;
	
	public String userName;
    public String password;
    public String errorMessage;

	public LoginComponent(WOContext context) 
	{
        super(context);
    }
	
    public String userName()
    {
        return userName;
    }

    public void setUserName(String newUserName)
    {
        userName = newUserName;
    }

    public String password()
    {
        return password;
    }

    public void setPassword(String newPassword)
    {
        password = newPassword;
    }

    public com.webobjects.appserver.WOComponent doLogin()
    {
        com.webobjects.appserver.WOComponent nextPageAfterLogin = null;
        
        if ((userName != null) && (password != null) && (password.length() > 0))
        {
            User existing = User.userForUsernameWithPassword( userName, password, session().defaultEditingContext());
            if (existing != null)
            {
            	((Session)session()).setUser(existing);
                    nextPageAfterLogin = pageWithName( "NotesPage" );
            }
            else
            {
            	errorMessage = "Login failed.  Please retry";
                nextPageAfterLogin = (com.webobjects.appserver.WOComponent) context().page();
            }
        }
        
        return nextPageAfterLogin;
    }

}