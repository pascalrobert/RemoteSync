// $LastChangedRevision: 5773 $ DO NOT EDIT.  Make changes to ERSyncClientApp.java instead.
package er.sync.eo;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _ERSyncClientApp extends er.sync.eo.ERSyncClient {
	public static final String ENTITY_NAME = "ERSyncClientApp";

	// Attributes
	public static final String API_KEY_KEY = "apiKey";
	public static final String DISABLE_KEY = "disable";
	public static final String NAME_KEY = "name";
	public static final String TYPE_KEY = "type";
	public static final String UUID_KEY = "uuid";

	// Relationships
	public static final String CAPABILITIES_KEY = "capabilities";
	public static final String DEVELOPER_KEY = "developer";
	public static final String DEVICES_KEY = "devices";
	public static final String PRINCIPALS_KEY = "principals";

  private static Logger LOG = Logger.getLogger(_ERSyncClientApp.class);

  public ERSyncClientApp localInstanceIn(EOEditingContext editingContext) {
    ERSyncClientApp localInstance = (ERSyncClientApp)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public String apiKey() {
    return (String) storedValueForKey("apiKey");
  }

  public void setApiKey(String value) {
    if (_ERSyncClientApp.LOG.isDebugEnabled()) {
    	_ERSyncClientApp.LOG.debug( "updating apiKey from " + apiKey() + " to " + value);
    }
    takeStoredValueForKey(value, "apiKey");
  }

  public er.sync.eo.ERSyncClientDeveloper developer() {
    return (er.sync.eo.ERSyncClientDeveloper)storedValueForKey("developer");
  }

  public void setDeveloperRelationship(er.sync.eo.ERSyncClientDeveloper value) {
    if (_ERSyncClientApp.LOG.isDebugEnabled()) {
      _ERSyncClientApp.LOG.debug("updating developer from " + developer() + " to " + value);
    }
    if (value == null) {
    	er.sync.eo.ERSyncClientDeveloper oldValue = developer();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "developer");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "developer");
    }
  }
  
  public NSArray<er.sync.eo.ERSyncClientDevice> devices() {
    return (NSArray<er.sync.eo.ERSyncClientDevice>)storedValueForKey("devices");
  }

  public NSArray<er.sync.eo.ERSyncClientDevice> devices(EOQualifier qualifier) {
    return devices(qualifier, null);
  }

  public NSArray<er.sync.eo.ERSyncClientDevice> devices(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    NSArray<er.sync.eo.ERSyncClientDevice> results;
      results = devices();
      if (qualifier != null) {
        results = (NSArray<er.sync.eo.ERSyncClientDevice>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<er.sync.eo.ERSyncClientDevice>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToDevicesRelationship(er.sync.eo.ERSyncClientDevice object) {
    if (_ERSyncClientApp.LOG.isDebugEnabled()) {
      _ERSyncClientApp.LOG.debug("adding " + object + " to devices relationship");
    }
    addObjectToBothSidesOfRelationshipWithKey(object, "devices");
  }

  public void removeFromDevicesRelationship(er.sync.eo.ERSyncClientDevice object) {
    if (_ERSyncClientApp.LOG.isDebugEnabled()) {
      _ERSyncClientApp.LOG.debug("removing " + object + " from devices relationship");
    }
    removeObjectFromBothSidesOfRelationshipWithKey(object, "devices");
  }

  public er.sync.eo.ERSyncClientDevice createDevicesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ERSyncClientDevice");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "devices");
    return (er.sync.eo.ERSyncClientDevice) eo;
  }

  public void deleteDevicesRelationship(er.sync.eo.ERSyncClientDevice object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "devices");
    editingContext().deleteObject(object);
  }

  public void deleteAllDevicesRelationships() {
    Enumeration objects = devices().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteDevicesRelationship((er.sync.eo.ERSyncClientDevice)objects.nextElement());
    }
  }

  public NSArray<er.sync.eo.ERSyncPrincipal> principals() {
    return (NSArray<er.sync.eo.ERSyncPrincipal>)storedValueForKey("principals");
  }

  public NSArray<er.sync.eo.ERSyncPrincipal> principals(EOQualifier qualifier) {
    return principals(qualifier, null, false);
  }

  public NSArray<er.sync.eo.ERSyncPrincipal> principals(EOQualifier qualifier, boolean fetch) {
    return principals(qualifier, null, fetch);
  }

  public NSArray<er.sync.eo.ERSyncPrincipal> principals(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<er.sync.eo.ERSyncPrincipal> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(er.sync.eo.ERSyncPrincipal.APPLICATION_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = er.sync.eo.ERSyncPrincipal.fetchERSyncPrincipals(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = principals();
      if (qualifier != null) {
        results = (NSArray<er.sync.eo.ERSyncPrincipal>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<er.sync.eo.ERSyncPrincipal>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToPrincipalsRelationship(er.sync.eo.ERSyncPrincipal object) {
    if (_ERSyncClientApp.LOG.isDebugEnabled()) {
      _ERSyncClientApp.LOG.debug("adding " + object + " to principals relationship");
    }
    addObjectToBothSidesOfRelationshipWithKey(object, "principals");
  }

  public void removeFromPrincipalsRelationship(er.sync.eo.ERSyncPrincipal object) {
    if (_ERSyncClientApp.LOG.isDebugEnabled()) {
      _ERSyncClientApp.LOG.debug("removing " + object + " from principals relationship");
    }
    removeObjectFromBothSidesOfRelationshipWithKey(object, "principals");
  }

  public er.sync.eo.ERSyncPrincipal createPrincipalsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ERSyncPrincipal");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "principals");
    return (er.sync.eo.ERSyncPrincipal) eo;
  }

  public void deletePrincipalsRelationship(er.sync.eo.ERSyncPrincipal object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "principals");
    editingContext().deleteObject(object);
  }

  public void deleteAllPrincipalsRelationships() {
    Enumeration objects = principals().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deletePrincipalsRelationship((er.sync.eo.ERSyncPrincipal)objects.nextElement());
    }
  }


  public static ERSyncClientApp createERSyncClientApp(EOEditingContext editingContext, String apiKey
, Boolean disable
, String name
, er.sync.eo.ERSyncClientDeveloper developer) {
    ERSyncClientApp eo = (ERSyncClientApp) EOUtilities.createAndInsertInstance(editingContext, _ERSyncClientApp.ENTITY_NAME);    
		eo.setApiKey(apiKey);
		eo.setDisable(disable);
		eo.setName(name);
    eo.setDeveloperRelationship(developer);
    return eo;
  }

  public static NSArray<ERSyncClientApp> fetchAllERSyncClientApps(EOEditingContext editingContext) {
    return _ERSyncClientApp.fetchAllERSyncClientApps(editingContext, null);
  }

  public static NSArray<ERSyncClientApp> fetchAllERSyncClientApps(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _ERSyncClientApp.fetchERSyncClientApps(editingContext, null, sortOrderings);
  }

  public static NSArray<ERSyncClientApp> fetchERSyncClientApps(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ERSyncClientApp.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<ERSyncClientApp> eoObjects = (NSArray<ERSyncClientApp>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ERSyncClientApp fetchERSyncClientApp(EOEditingContext editingContext, String keyName, Object value) {
    return _ERSyncClientApp.fetchERSyncClientApp(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ERSyncClientApp fetchERSyncClientApp(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<ERSyncClientApp> eoObjects = _ERSyncClientApp.fetchERSyncClientApps(editingContext, qualifier, null);
    ERSyncClientApp eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ERSyncClientApp)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ERSyncClientApp that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ERSyncClientApp fetchRequiredERSyncClientApp(EOEditingContext editingContext, String keyName, Object value) {
    return _ERSyncClientApp.fetchRequiredERSyncClientApp(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ERSyncClientApp fetchRequiredERSyncClientApp(EOEditingContext editingContext, EOQualifier qualifier) {
    ERSyncClientApp eoObject = _ERSyncClientApp.fetchERSyncClientApp(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ERSyncClientApp that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ERSyncClientApp localInstanceIn(EOEditingContext editingContext, ERSyncClientApp eo) {
    ERSyncClientApp localInstance = (eo == null) ? null : (ERSyncClientApp)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
