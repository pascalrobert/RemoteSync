package net.global_village.notes;

import net.global_village.notes.model.entities.User;
import er.extensions.appserver.ERXSession;

public class Session extends ERXSession {
	private static final long serialVersionUID = 1L;

	public User user;

	public Session() 
	{
	}
	
    public User user()
    {
        return user;
    }

    public void setUser(User newUser)
    {
        user = newUser;
    }
}
