// $LastChangedRevision: 5810 $ DO NOT EDIT.  Make changes to User.java instead.
package net.terminalapp.notes.model.entities;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _User extends  EOGenericRecord {
	public static final String ENTITY_NAME = "User";

	// Attributes
	public static final String PASSWORD_KEY = "password";
	public static final String USERNAME_KEY = "username";

	// Relationships
	public static final String NOTES_KEY = "notes";

  private static Logger LOG = Logger.getLogger(_User.class);

  public User localInstanceIn(EOEditingContext editingContext) {
    User localInstance = (User)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public String password() {
    return (String) storedValueForKey("password");
  }

  public void setPassword(String value) {
    if (_User.LOG.isDebugEnabled()) {
    	_User.LOG.debug( "updating password from " + password() + " to " + value);
    }
    takeStoredValueForKey(value, "password");
  }

  public String username() {
    return (String) storedValueForKey("username");
  }

  public void setUsername(String value) {
    if (_User.LOG.isDebugEnabled()) {
    	_User.LOG.debug( "updating username from " + username() + " to " + value);
    }
    takeStoredValueForKey(value, "username");
  }

  public NSArray<net.terminalapp.notes.model.entities.Note> notes() {
    return (NSArray<net.terminalapp.notes.model.entities.Note>)storedValueForKey("notes");
  }

  public NSArray<net.terminalapp.notes.model.entities.Note> notes(EOQualifier qualifier) {
    return notes(qualifier, null, false);
  }

  public NSArray<net.terminalapp.notes.model.entities.Note> notes(EOQualifier qualifier, boolean fetch) {
    return notes(qualifier, null, fetch);
  }

  public NSArray<net.terminalapp.notes.model.entities.Note> notes(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<net.terminalapp.notes.model.entities.Note> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(net.terminalapp.notes.model.entities.Note.USER_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = net.terminalapp.notes.model.entities.Note.fetchNotes(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = notes();
      if (qualifier != null) {
        results = (NSArray<net.terminalapp.notes.model.entities.Note>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<net.terminalapp.notes.model.entities.Note>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToNotesRelationship(net.terminalapp.notes.model.entities.Note object) {
    if (_User.LOG.isDebugEnabled()) {
      _User.LOG.debug("adding " + object + " to notes relationship");
    }
    addObjectToBothSidesOfRelationshipWithKey(object, "notes");
  }

  public void removeFromNotesRelationship(net.terminalapp.notes.model.entities.Note object) {
    if (_User.LOG.isDebugEnabled()) {
      _User.LOG.debug("removing " + object + " from notes relationship");
    }
    removeObjectFromBothSidesOfRelationshipWithKey(object, "notes");
  }

  public net.terminalapp.notes.model.entities.Note createNotesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("Note");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "notes");
    return (net.terminalapp.notes.model.entities.Note) eo;
  }

  public void deleteNotesRelationship(net.terminalapp.notes.model.entities.Note object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "notes");
    editingContext().deleteObject(object);
  }

  public void deleteAllNotesRelationships() {
    Enumeration objects = notes().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteNotesRelationship((net.terminalapp.notes.model.entities.Note)objects.nextElement());
    }
  }


  public static User createUser(EOEditingContext editingContext, String password
, String username
) {
    User eo = (User) EOUtilities.createAndInsertInstance(editingContext, _User.ENTITY_NAME);    
		eo.setPassword(password);
		eo.setUsername(username);
    return eo;
  }

  public static NSArray<User> fetchAllUsers(EOEditingContext editingContext) {
    return _User.fetchAllUsers(editingContext, null);
  }

  public static NSArray<User> fetchAllUsers(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _User.fetchUsers(editingContext, null, sortOrderings);
  }

  public static NSArray<User> fetchUsers(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_User.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<User> eoObjects = (NSArray<User>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static User fetchUser(EOEditingContext editingContext, String keyName, Object value) {
    return _User.fetchUser(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static User fetchUser(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<User> eoObjects = _User.fetchUsers(editingContext, qualifier, null);
    User eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (User)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one User that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static User fetchRequiredUser(EOEditingContext editingContext, String keyName, Object value) {
    return _User.fetchRequiredUser(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static User fetchRequiredUser(EOEditingContext editingContext, EOQualifier qualifier) {
    User eoObject = _User.fetchUser(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no User that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static User localInstanceIn(EOEditingContext editingContext, User eo) {
    User localInstance = (eo == null) ? null : (User)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
