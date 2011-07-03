// $LastChangedRevision: 5773 $ DO NOT EDIT.  Make changes to ERSyncClientDevice.java instead.
package er.sync.eo;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _ERSyncClientDevice extends er.sync.eo.ERSyncClient {
	public static final String ENTITY_NAME = "ERSyncClientDevice";

	// Attributes
	public static final String DISABLE_KEY = "disable";
	public static final String NAME_KEY = "name";
	public static final String TYPE_KEY = "type";
	public static final String UUID_KEY = "uuid";

	// Relationships
	public static final String APPLICATIONS_KEY = "applications";
	public static final String CAPABILITIES_KEY = "capabilities";
	public static final String PRINCIPALS_KEY = "principals";

  private static Logger LOG = Logger.getLogger(_ERSyncClientDevice.class);

  public ERSyncClientDevice localInstanceIn(EOEditingContext editingContext) {
    ERSyncClientDevice localInstance = (ERSyncClientDevice)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public NSArray<er.sync.eo.ERSyncClientApp> applications() {
    return (NSArray<er.sync.eo.ERSyncClientApp>)storedValueForKey("applications");
  }

  public NSArray<er.sync.eo.ERSyncClientApp> applications(EOQualifier qualifier) {
    return applications(qualifier, null);
  }

  public NSArray<er.sync.eo.ERSyncClientApp> applications(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    NSArray<er.sync.eo.ERSyncClientApp> results;
      results = applications();
      if (qualifier != null) {
        results = (NSArray<er.sync.eo.ERSyncClientApp>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<er.sync.eo.ERSyncClientApp>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToApplicationsRelationship(er.sync.eo.ERSyncClientApp object) {
    if (_ERSyncClientDevice.LOG.isDebugEnabled()) {
      _ERSyncClientDevice.LOG.debug("adding " + object + " to applications relationship");
    }
    addObjectToBothSidesOfRelationshipWithKey(object, "applications");
  }

  public void removeFromApplicationsRelationship(er.sync.eo.ERSyncClientApp object) {
    if (_ERSyncClientDevice.LOG.isDebugEnabled()) {
      _ERSyncClientDevice.LOG.debug("removing " + object + " from applications relationship");
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
      EOQualifier inverseQualifier = new EOKeyValueQualifier(er.sync.eo.ERSyncPrincipal.DEVICE_TYPE_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
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
    if (_ERSyncClientDevice.LOG.isDebugEnabled()) {
      _ERSyncClientDevice.LOG.debug("adding " + object + " to principals relationship");
    }
    addObjectToBothSidesOfRelationshipWithKey(object, "principals");
  }

  public void removeFromPrincipalsRelationship(er.sync.eo.ERSyncPrincipal object) {
    if (_ERSyncClientDevice.LOG.isDebugEnabled()) {
      _ERSyncClientDevice.LOG.debug("removing " + object + " from principals relationship");
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


  public static ERSyncClientDevice createERSyncClientDevice(EOEditingContext editingContext, Boolean disable
, String name
) {
    ERSyncClientDevice eo = (ERSyncClientDevice) EOUtilities.createAndInsertInstance(editingContext, _ERSyncClientDevice.ENTITY_NAME);    
		eo.setDisable(disable);
		eo.setName(name);
    return eo;
  }

  public static NSArray<ERSyncClientDevice> fetchAllERSyncClientDevices(EOEditingContext editingContext) {
    return _ERSyncClientDevice.fetchAllERSyncClientDevices(editingContext, null);
  }

  public static NSArray<ERSyncClientDevice> fetchAllERSyncClientDevices(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _ERSyncClientDevice.fetchERSyncClientDevices(editingContext, null, sortOrderings);
  }

  public static NSArray<ERSyncClientDevice> fetchERSyncClientDevices(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ERSyncClientDevice.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<ERSyncClientDevice> eoObjects = (NSArray<ERSyncClientDevice>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ERSyncClientDevice fetchERSyncClientDevice(EOEditingContext editingContext, String keyName, Object value) {
    return _ERSyncClientDevice.fetchERSyncClientDevice(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ERSyncClientDevice fetchERSyncClientDevice(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<ERSyncClientDevice> eoObjects = _ERSyncClientDevice.fetchERSyncClientDevices(editingContext, qualifier, null);
    ERSyncClientDevice eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ERSyncClientDevice)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ERSyncClientDevice that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ERSyncClientDevice fetchRequiredERSyncClientDevice(EOEditingContext editingContext, String keyName, Object value) {
    return _ERSyncClientDevice.fetchRequiredERSyncClientDevice(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ERSyncClientDevice fetchRequiredERSyncClientDevice(EOEditingContext editingContext, EOQualifier qualifier) {
    ERSyncClientDevice eoObject = _ERSyncClientDevice.fetchERSyncClientDevice(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ERSyncClientDevice that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ERSyncClientDevice localInstanceIn(EOEditingContext editingContext, ERSyncClientDevice eo) {
    ERSyncClientDevice localInstance = (eo == null) ? null : (ERSyncClientDevice)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
