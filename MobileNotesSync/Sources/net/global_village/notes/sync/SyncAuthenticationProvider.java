package net.global_village.notes.sync;

import com.webobjects.eoaccess.EOEntity;
import com.webobjects.eoaccess.EOModelGroup;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOGlobalID;
import com.webobjects.eocontrol.EOKeyGlobalID;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSTimestamp;

import net.global_village.notes.model.entities.Category;
import net.global_village.notes.model.entities.Note;
import net.global_village.notes.model.entities.User;
import er.extensions.eof.ERXKeyFilter;
import er.extensions.eof.ERXQ;
import er.rest.ERXRestFetchSpecification;
import er.sync.ERXSyncUtilities;
import er.sync.api.ERXSyncAuthenticator;
import er.sync.api.ERXSyncUser;

public class SyncAuthenticationProvider implements ERXSyncAuthenticator
{
	public ERXSyncUser userForCredentials(String username, String password, EOEditingContext ec)
	{
		return User.userForUsernameWithPassword(username, password, ec);
	}
	
	public NSArray<String> syncEntityNames()
	{
		return new NSArray<String>( Note.ENTITY_NAME, Category.ENTITY_NAME );
	}

	public NSArray<EOKeyGlobalID> syncObjectsForEntityUser(String entityName, ERXSyncUser usr, EOEditingContext ec)
	{
		NSArray<EOKeyGlobalID> objects = null;
		if ( entityName.equals(Note.ENTITY_NAME) )
		{
			EOModelGroup defaultModelGroup = EOModelGroup.defaultGroup();
			objects = ERXSyncUtilities.globalIDForQualifier(ec, defaultModelGroup.entityNamed(Note.ENTITY_NAME), ERXQ.equals( Note.USER_KEY, (User)usr ), true);
		}
		else if ( entityName.equals(Category.ENTITY_NAME) )
		{
			EOModelGroup defaultModelGroup = EOModelGroup.defaultGroup();
			objects = ERXSyncUtilities.globalIDForQualifier(ec, defaultModelGroup.entityNamed(Category.ENTITY_NAME), ERXQ.equals( Note.USER_KEY, (User)usr ), true);
		}
		return objects;
	}

	public void syncDeleteObject(EOEnterpriseObject eo, ERXSyncUser user)
    {
		if ( eo instanceof Note)
		{
			Note note = (Note)eo;
			if ( note.user().equals( (User)user ) == true)
			{
				note.editingContext().deleteObject(note);
			}
			else
			{
				throw new RuntimeException("Not a note for this user");
			}
		}
		else if ( eo instanceof Category)
		{
			Category cat = (Category)eo;
			if ( cat.user().equals( (User)user ) == true)
			{
				if ((cat.notes() == null) || (cat.notes().count() == 0))
				{
					cat.editingContext().deleteObject(cat);
				}
				else
				{
					throw new RuntimeException("Category still attached to notes");
				}
			}
			else
			{
				throw new RuntimeException("Not a category for this user");
			}
		}
		else
		{
			throw new RuntimeException("Not a category");
		}
    }

	public EOEnterpriseObject syncInsertObject(EOEditingContext editingContext, EOEntity eoEntity, NSMutableDictionary dict, ERXSyncUser user)
    {
		if ( eoEntity.name().equals(Note.ENTITY_NAME) == true )
		{
			Note note = Note.createNote(editingContext, new NSTimestamp(), (User)user);
			note.takeValuesFromDictionary(dict);
		    return note;
		}
		else if ( eoEntity.name().equals(Category.ENTITY_NAME) == true )
		{
			Category cat = Category.createCategory(editingContext, Category.ENTITY_NAME, (User)user);
			cat.takeValuesFromDictionary(dict);
		    return cat;
		}
		else
		{
			throw new RuntimeException("Not a note, or category");
		}
    }

	public void syncUpdateObject(EOEnterpriseObject eo, NSMutableDictionary dict, ERXSyncUser user)
    {
		if ( eo instanceof Note)
		{
			Note note = (Note)eo;
			if ( note.user().equals( (User)user ) == true)
			{
				note.takeValuesFromDictionary(dict);
			}
			else
			{
				throw new RuntimeException("Not a note for this user");
			}
		}
		else if ( eo instanceof Category)
		{
			Category cat = (Category)eo;
			if ( cat.user().equals( (User)user ) == true)
			{
				cat.takeValuesFromDictionary(dict);
			}
			else
			{
				throw new RuntimeException("Not a note for this user");
			}
		}
		else
		{
			throw new RuntimeException("Not a note");
		}
    }

}
