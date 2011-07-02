// $LastChangedRevision: 5810 $ DO NOT EDIT.  Make changes to Note.java instead.
package net.terminalapp.notes.model.entities;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _Note extends  EOGenericRecord {
	public static final String ENTITY_NAME = "Note";

	// Attributes
	public static final String CONTENT_KEY = "content";
	public static final String CREATION_DATE_KEY = "creationDate";

	// Relationships
	public static final String USER_KEY = "user";

  private static Logger LOG = Logger.getLogger(_Note.class);

  public Note localInstanceIn(EOEditingContext editingContext) {
    Note localInstance = (Note)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public String content() {
    return (String) storedValueForKey("content");
  }

  public void setContent(String value) {
    if (_Note.LOG.isDebugEnabled()) {
    	_Note.LOG.debug( "updating content from " + content() + " to " + value);
    }
    takeStoredValueForKey(value, "content");
  }

  public NSTimestamp creationDate() {
    return (NSTimestamp) storedValueForKey("creationDate");
  }

  public void setCreationDate(NSTimestamp value) {
    if (_Note.LOG.isDebugEnabled()) {
    	_Note.LOG.debug( "updating creationDate from " + creationDate() + " to " + value);
    }
    takeStoredValueForKey(value, "creationDate");
  }

  public net.terminalapp.notes.model.entities.User user() {
    return (net.terminalapp.notes.model.entities.User)storedValueForKey("user");
  }

  public void setUserRelationship(net.terminalapp.notes.model.entities.User value) {
    if (_Note.LOG.isDebugEnabled()) {
      _Note.LOG.debug("updating user from " + user() + " to " + value);
    }
    if (value == null) {
    	net.terminalapp.notes.model.entities.User oldValue = user();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "user");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "user");
    }
  }
  

  public static Note createNote(EOEditingContext editingContext, NSTimestamp creationDate
, net.terminalapp.notes.model.entities.User user) {
    Note eo = (Note) EOUtilities.createAndInsertInstance(editingContext, _Note.ENTITY_NAME);    
		eo.setCreationDate(creationDate);
    eo.setUserRelationship(user);
    return eo;
  }

  public static NSArray<Note> fetchAllNotes(EOEditingContext editingContext) {
    return _Note.fetchAllNotes(editingContext, null);
  }

  public static NSArray<Note> fetchAllNotes(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _Note.fetchNotes(editingContext, null, sortOrderings);
  }

  public static NSArray<Note> fetchNotes(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_Note.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<Note> eoObjects = (NSArray<Note>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static Note fetchNote(EOEditingContext editingContext, String keyName, Object value) {
    return _Note.fetchNote(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Note fetchNote(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<Note> eoObjects = _Note.fetchNotes(editingContext, qualifier, null);
    Note eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (Note)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one Note that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Note fetchRequiredNote(EOEditingContext editingContext, String keyName, Object value) {
    return _Note.fetchRequiredNote(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Note fetchRequiredNote(EOEditingContext editingContext, EOQualifier qualifier) {
    Note eoObject = _Note.fetchNote(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no Note that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Note localInstanceIn(EOEditingContext editingContext, Note eo) {
    Note localInstance = (eo == null) ? null : (Note)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
