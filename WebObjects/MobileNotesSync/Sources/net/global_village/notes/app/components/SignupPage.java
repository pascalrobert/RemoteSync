package net.global_village.notes.app.components;


import net.global_village.notes.Session;
import net.global_village.notes.model.entities.Category;
import net.global_village.notes.model.entities.Note;
import net.global_village.notes.model.entities.User;

import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOComponent;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSTimestamp;

public class SignupPage extends WOComponent 
{
    public String userName;
    public String password;
    public String passwordCheck;
    public String errorMessage;

    public SignupPage(WOContext context) {
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

    public String passwordCheck()
    {
        return passwordCheck;
    }

    public void setPasswordCheck(String newPassword)
    {
        passwordCheck = newPassword;
    }

    public com.webobjects.appserver.WOComponent doSignup()
    {
        com.webobjects.appserver.WOComponent nextPageAfterLogin = null;
        
        if ((userName != null) && (password != null) && (password.length() > 0) && (password.equals(passwordCheck) == true))
        {
            boolean existing = User.userExistsForUsername( userName, session().defaultEditingContext());
            if (existing)
            {
            	errorMessage = "Username already taken. Please try again";
                nextPageAfterLogin = (com.webobjects.appserver.WOComponent) context().page();
            }
            else
            {
            	User newUser = User.createUser(session().defaultEditingContext(), password, userName );
            	Category one = Category.createCategory(session().defaultEditingContext(), "Home", newUser);
            	Category two = Category.createCategory(session().defaultEditingContext(), "Work", newUser);
            	Category three = Category.createCategory(session().defaultEditingContext(), "Mobile", newUser);
            	
            	Note welcomeNote = Note.createNote(session().defaultEditingContext(), new NSTimestamp(), newUser);
            	welcomeNote.setSubject("Welcome to Mobile Note Sync");
            	welcomeNote.setContent("Welcome to Mobile Notes demo application.  Please create some notes here and then login using the mobile demo application.");
            	welcomeNote.setCategoryRelationship(one);
            	session().defaultEditingContext().saveChanges();
            	
            	((Session)session()).setUser(newUser);
                nextPageAfterLogin = pageWithName( "NotesPage" );
            }
        }
        
        return nextPageAfterLogin;
    }
}