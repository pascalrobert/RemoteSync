// $LastChangedRevision: 5773 $ DO NOT EDIT.  Make changes to Category.java instead.
package net.global_village.notes.model.entities;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _Category extends  EOGenericRecord {
	public static final String ENTITY_NAME = "Category";

	// Attributes
	public static final String NAME_KEY = "name";

	// Relationships
	public static final String NOTES_KEY = "notes";
	public static final String USER_KEY = "user";

  private static Logger LOG = Logger.getLogger(_Category.class);

  public Category localInstanceIn(EOEditingContext editingContext) {
    Category localInstance = (Category)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public String name() {
    return (String) storedValueForKey("name");
  }

  public void setName(String value) {
    if (_Category.LOG.isDebugEnabled()) {
    	_Category.LOG.debug( "updating name from " + name() + " to " + value);
    }
    takeStoredValueForKey(value, "name");
  }

  public net.global_village.notes.model.entities.User user() {
    return (net.global_village.notes.model.entities.User)storedValueForKey("user");
  }

  public void setUserRelationship(net.global_village.notes.model.entities.User value) {
    if (_Category.LOG.isDebugEnabled()) {
      _Category.LOG.debug("updating user from " + user() + " to " + value);
    }
    if (value == null) {
    	net.global_village.notes.model.entities.User oldValue = user();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "user");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "user");
    }
  }
  
  public NSArray<net.global_village.notes.model.entities.Note> notes() {
    return (NSArray<net.global_village.notes.model.entities.Note>)storedValueForKey("notes");
  }

  public NSArray<net.global_village.notes.model.entities.Note> notes(EOQualifier qualifier) {
    return notes(qualifier, null, false);
  }

  public NSArray<net.global_village.notes.model.entities.Note> notes(EOQualifier qualifier, boolean fetch) {
    return notes(qualifier, null, fetch);
  }

  public NSArray<net.global_village.notes.model.entities.Note> notes(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<net.global_village.notes.model.entities.Note> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(net.global_village.notes.model.entities.Note.CATEGORY_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = net.global_village.notes.model.entities.Note.fetchNotes(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = notes();
      if (qualifier != null) {
        results = (NSArray<net.global_village.notes.model.entities.Note>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<net.global_village.notes.model.entities.Note>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToNotesRelationship(net.global_village.notes.model.entities.Note object) {
    if (_Category.LOG.isDebugEnabled()) {
      _Category.LOG.debug("adding " + object + " to notes relationship");
    }
    addObjectToBothSidesOfRelationshipWithKey(object, "notes");
  }

  public void removeFromNotesRelationship(net.global_village.notes.model.entities.Note object) {
    if (_Category.LOG.isDebugEnabled()) {
      _Category.LOG.debug("removing " + object + " from notes relationship");
    }
    removeObjectFromBothSidesOfRelationshipWithKey(object, "notes");
  }

  public net.global_village.notes.model.entities.Note createNotesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("Note");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "notes");
    return (net.global_village.notes.model.entities.Note) eo;
  }

  public void deleteNotesRelationship(net.global_village.notes.model.entities.Note object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "notes");
    editingContext().deleteObject(object);
  }

  public void deleteAllNotesRelationships() {
    Enumeration objects = notes().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteNotesRelationship((net.global_village.notes.model.entities.Note)objects.nextElement());
    }
  }


  public static Category createCategory(EOEditingContext editingContext, String name
, net.global_village.notes.model.entities.User user) {
    Category eo = (Category) EOUtilities.createAndInsertInstance(editingContext, _Category.ENTITY_NAME);    
		eo.setName(name);
    eo.setUserRelationship(user);
    return eo;
  }

  public static NSArray<Category> fetchAllCategories(EOEditingContext editingContext) {
    return _Category.fetchAllCategories(editingContext, null);
  }

  public static NSArray<Category> fetchAllCategories(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _Category.fetchCategories(editingContext, null, sortOrderings);
  }

  public static NSArray<Category> fetchCategories(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_Category.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<Category> eoObjects = (NSArray<Category>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static Category fetchCategory(EOEditingContext editingContext, String keyName, Object value) {
    return _Category.fetchCategory(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Category fetchCategory(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<Category> eoObjects = _Category.fetchCategories(editingContext, qualifier, null);
    Category eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (Category)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one Category that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Category fetchRequiredCategory(EOEditingContext editingContext, String keyName, Object value) {
    return _Category.fetchRequiredCategory(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static Category fetchRequiredCategory(EOEditingContext editingContext, EOQualifier qualifier) {
    Category eoObject = _Category.fetchCategory(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no Category that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static Category localInstanceIn(EOEditingContext editingContext, Category eo) {
    Category localInstance = (eo == null) ? null : (Category)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
