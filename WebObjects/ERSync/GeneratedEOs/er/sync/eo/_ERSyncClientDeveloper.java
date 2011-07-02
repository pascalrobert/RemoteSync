// $LastChangedRevision: 5773 $ DO NOT EDIT.  Make changes to ERSyncClientDeveloper.java instead.
package er.sync.eo;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _ERSyncClientDeveloper extends er.sync.eo.ERSyncClient {
	public static final String ENTITY_NAME = "ERSyncClientDeveloper";

	// Attributes
	public static final String DISABLE_KEY = "disable";
	public static final String NAME_KEY = "name";
	public static final String TYPE_KEY = "type";
	public static final String UUID_KEY = "uuid";

	// Relationships
	public static final String APPLICATIONS_KEY = "applications";
	public static final String CAPABILITIES_KEY = "capabilities";

  private static Logger LOG = Logger.getLogger(_ERSyncClientDeveloper.class);

  public ERSyncClientDeveloper localInstanceIn(EOEditingContext editingContext) {
    ERSyncClientDeveloper localInstance = (ERSyncClientDeveloper)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public NSArray<er.sync.eo.ERSyncClientApp> applications() {
    return (NSArray<er.sync.eo.ERSyncClientApp>)storedValueForKey("applications");
  }

  public NSArray<er.sync.eo.ERSyncClientApp> applications(EOQualifier qualifier) {
    return applications(qualifier, null, false);
  }

  public NSArray<er.sync.eo.ERSyncClientApp> applications(EOQualifier qualifier, boolean fetch) {
    return applications(qualifier, null, fetch);
  }

  public NSArray<er.sync.eo.ERSyncClientApp> applications(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<er.sync.eo.ERSyncClientApp> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(er.sync.eo.ERSyncClientApp.DEVELOPER_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = er.sync.eo.ERSyncClientApp.fetchERSyncClientApps(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = applications();
      if (qualifier != null) {
        results = (NSArray<er.sync.eo.ERSyncClientApp>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<er.sync.eo.ERSyncClientApp>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToApplicationsRelationship(er.sync.eo.ERSyncClientApp object) {
    if (_ERSyncClientDeveloper.LOG.isDebugEnabled()) {
      _ERSyncClientDeveloper.LOG.debug("adding " + object + " to applications relationship");
    }
    addObjectToBothSidesOfRelationshipWithKey(object, "applications");
  }

  public void removeFromApplicationsRelationship(er.sync.eo.ERSyncClientApp object) {
    if (_ERSyncClientDeveloper.LOG.isDebugEnabled()) {
      _ERSyncClientDeveloper.LOG.debug("removing " + object + " from applications relationship");
    }
    removeObjectFromBothSidesOfRelationshipWithKey(object, "applications");
  }

  public er.sync.eo.ERSyncClientApp createApplicationsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ERSyncClientApp");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "applications");
    return (er.sync.eo.ERSyncClientApp) eo;
  }

  public void deleteApplicationsRelationship(er.sync.eo.ERSyncClientApp object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "applications");
    editingContext().deleteObject(object);
  }

  public void deleteAllApplicationsRelationships() {
    Enumeration objects = applications().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteApplicationsRelationship((er.sync.eo.ERSyncClientApp)objects.nextElement());
    }
  }


  public static ERSyncClientDeveloper createERSyncClientDeveloper(EOEditingContext editingContext, Boolean disable
, String name
) {
    ERSyncClientDeveloper eo = (ERSyncClientDeveloper) EOUtilities.createAndInsertInstance(editingContext, _ERSyncClientDeveloper.ENTITY_NAME);    
		eo.setDisable(disable);
		eo.setName(name);
    return eo;
  }

  public static NSArray<ERSyncClientDeveloper> fetchAllERSyncClientDevelopers(EOEditingContext editingContext) {
    return _ERSyncClientDeveloper.fetchAllERSyncClientDevelopers(editingContext, null);
  }

  public static NSArray<ERSyncClientDeveloper> fetchAllERSyncClientDevelopers(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _ERSyncClientDeveloper.fetchERSyncClientDevelopers(editingContext, null, sortOrderings);
  }

  public static NSArray<ERSyncClientDeveloper> fetchERSyncClientDevelopers(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ERSyncClientDeveloper.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<ERSyncClientDeveloper> eoObjects = (NSArray<ERSyncClientDeveloper>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ERSyncClientDeveloper fetchERSyncClientDeveloper(EOEditingContext editingContext, String keyName, Object value) {
    return _ERSyncClientDeveloper.fetchERSyncClientDeveloper(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ERSyncClientDeveloper fetchERSyncClientDeveloper(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<ERSyncClientDeveloper> eoObjects = _ERSyncClientDeveloper.fetchERSyncClientDevelopers(editingContext, qualifier, null);
    ERSyncClientDeveloper eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ERSyncClientDeveloper)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ERSyncClientDeveloper that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ERSyncClientDeveloper fetchRequiredERSyncClientDeveloper(EOEditingContext editingContext, String keyName, Object value) {
    return _ERSyncClientDeveloper.fetchRequiredERSyncClientDeveloper(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ERSyncClientDeveloper fetchRequiredERSyncClientDeveloper(EOEditingContext editingContext, EOQualifier qualifier) {
    ERSyncClientDeveloper eoObject = _ERSyncClientDeveloper.fetchERSyncClientDeveloper(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ERSyncClientDeveloper that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ERSyncClientDeveloper localInstanceIn(EOEditingContext editingContext, ERSyncClientDeveloper eo) {
    ERSyncClientDeveloper localInstance = (eo == null) ? null : (ERSyncClientDeveloper)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
