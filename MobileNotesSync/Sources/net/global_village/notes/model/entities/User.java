package net.global_village.notes.model.entities;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;

import net.global_village.notes.Util;

import er.extensions.eof.ERXQ;
import er.extensions.validation.ERXValidationException;
import er.extensions.validation.ERXValidationFactory;
import er.sync.api.ERXSyncUser;

@SuppressWarnings("serial")
public class User extends _User implements ERXSyncUser
{
	//private static Logger log = Logger.getLogger(User.class);
	
	public void setPassword(String password) {
        super.setPassword(Util.encryptString(password));
    }
	
	public boolean matchesPassword(String password){
		if( password == null ) {
			return false;
		}
		
    	return password().equals(Util.encryptString(password));
    }

	@SuppressWarnings("unchecked")
	public static User userForUsernameWithPassword( String username, String password, EOEditingContext ec ) {
		EOQualifier userQualifier = ERXQ.equals( USERNAME_KEY, username.trim() );
		EOFetchSpecification spec = new EOFetchSpecification( ENTITY_NAME, userQualifier, null );
		NSArray<User> result = ec.objectsWithFetchSpecification( spec );
		
		if( result.count() == 1 ) {
			User user = result.lastObject();
			if( user.matchesPassword( password ) ) {
				return user;
			}
		} else if( result.count() > 1 ) {
			throw new RuntimeException("Expected 0 or 1 objects, got " + result.count() );
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	public static boolean userExistsForUsername( String username, EOEditingContext ec ) 
	{
		EOQualifier userQualifier = ERXQ.equals( USERNAME_KEY, username.trim() );
		EOFetchSpecification spec = new EOFetchSpecification( ENTITY_NAME, userQualifier, null );
		NSArray<User> result = ec.objectsWithFetchSpecification( spec );
		
		return result.count() > 0;
	}

	public String validateUsername( String name ) {
		if( Util.isEmptyString(name) ) {
            ERXValidationFactory vFactory = ERXValidationFactory.defaultFactory();
            ERXValidationException exception = vFactory.createException(this, USERNAME_KEY, name, ERXValidationException.NullPropertyException);
            throw exception;           
        }
		
		return name.trim();
	}
	
	public String sync_userName()
	{
		return username();
	}

}
