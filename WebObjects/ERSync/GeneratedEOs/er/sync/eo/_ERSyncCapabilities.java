// $LastChangedRevision: 5773 $ DO NOT EDIT.  Make changes to ERSyncCapabilities.java instead.
package er.sync.eo;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _ERSyncCapabilities extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ERSyncCapabilities";

	// Attributes
	public static final String LARGE_OBJECTS_KEY = "large_objects";
	public static final String MANUFACTURER_KEY = "manufacturer";
	public static final String MODEL_KEY = "model";

	// Relationships
	public static final String SYNC_CLIENTS_KEY = "syncClients";

  private static Logger LOG = Logger.getLogger(_ERSyncCapabilities.class);

  public ERSyncCapabilities localInstanceIn(EOEditingContext editingContext) {
    ERSyncCapabilities localInstance = (ERSyncCapabilities)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public Boolean large_objects() {
    return (Boolean) storedValueForKey("large_objects");
  }

  public void setLarge_objects(Boolean value) {
    if (_ERSyncCapabilities.LOG.isDebugEnabled()) {
    	_ERSyncCapabilities.LOG.debug( "updating large_objects from " + large_objects() + " to " + value);
    }
    takeStoredValueForKey(value, "large_objects");
  }

  public String manufacturer() {
    return (String) storedValueForKey("manufacturer");
  }

  public void setManufacturer(String value) {
    if (_ERSyncCapabilities.LOG.isDebugEnabled()) {
    	_ERSyncCapabilities.LOG.debug( "updating manufacturer from " + manufacturer() + " to " + value);
    }
    takeStoredValueForKey(value, "manufacturer");
  }

  public String model() {
    return (String) storedValueForKey("model");
  }

  public void setModel(String value) {
    if (_ERSyncCapabilities.LOG.isDebugEnabled()) {
    	_ERSyncCapabilities.LOG.debug( "updating model from " + model() + " to " + value);
    }
    takeStoredValueForKey(value, "model");
  }

  public NSArray<er.sync.eo.ERSyncClient> syncClients() {
    return (NSArray<er.sync.eo.ERSyncClient>)storedValueForKey("syncClients");
  }

  public NSArray<er.sync.eo.ERSyncClient> syncClients(EOQualifier qualifier) {
    return syncClients(qualifier, null, false);
  }

  public NSArray<er.sync.eo.ERSyncClient> syncClients(EOQualifier qualifier, boolean fetch) {
    return syncClients(qualifier, null, fetch);
  }

  public NSArray<er.sync.eo.ERSyncClient> syncClients(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<er.sync.eo.ERSyncClient> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(er.sync.eo.ERSyncClient.CAPABILITIES_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = er.sync.eo.ERSyncClient.fetchERSyncClients(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = syncClients();
      if (qualifier != null) {
        results = (NSArray<er.sync.eo.ERSyncClient>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<er.sync.eo.ERSyncClient>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToSyncClientsRelationship(er.sync.eo.ERSyncClient object) {
    if (_ERSyncCapabilities.LOG.isDebugEnabled()) {
      _ERSyncCapabilities.LOG.debug("adding " + object + " to syncClients relationship");
    }
    addObjectToBothSidesOfRelationshipWithKey(object, "syncClients");
  }

  public void removeFromSyncClientsRelationship(er.sync.eo.ERSyncClient object) {
    if (_ERSyncCapabilities.LOG.isDebugEnabled()) {
      _ERSyncCapabilities.LOG.debug("removing " + object + " from syncClients relationship");
    }
    removeObjectFromBothSidesOfRelationshipWithKey(object, "syncClients");
  }

  public er.sync.eo.ERSyncClient createSyncClientsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ERSyncClient");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "syncClients");
    return (er.sync.eo.ERSyncClient) eo;
  }

  public void deleteSyncClientsRelationship(er.sync.eo.ERSyncClient object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "syncClients");
    editingContext().deleteObject(object);
  }

  public void deleteAllSyncClientsRelationships() {
    Enumeration objects = syncClients().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteSyncClientsRelationship((er.sync.eo.ERSyncClient)objects.nextElement());
    }
  }


  public static ERSyncCapabilities createERSyncCapabilities(EOEditingContext editingContext, Boolean large_objects
, String manufacturer
, String model
) {
    ERSyncCapabilities eo = (ERSyncCapabilities) EOUtilities.createAndInsertInstance(editingContext, _ERSyncCapabilities.ENTITY_NAME);    
		eo.setLarge_objects(large_objects);
		eo.setManufacturer(manufacturer);
		eo.setModel(model);
    return eo;
  }

  public static NSArray<ERSyncCapabilities> fetchAllERSyncCapabilitieses(EOEditingContext editingContext) {
    return _ERSyncCapabilities.fetchAllERSyncCapabilitieses(editingContext, null);
  }

  public static NSArray<ERSyncCapabilities> fetchAllERSyncCapabilitieses(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _ERSyncCapabilities.fetchERSyncCapabilitieses(editingContext, null, sortOrderings);
  }

  public static NSArray<ERSyncCapabilities> fetchERSyncCapabilitieses(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ERSyncCapabilities.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<ERSyncCapabilities> eoObjects = (NSArray<ERSyncCapabilities>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ERSyncCapabilities fetchERSyncCapabilities(EOEditingContext editingContext, String keyName, Object value) {
    return _ERSyncCapabilities.fetchERSyncCapabilities(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ERSyncCapabilities fetchERSyncCapabilities(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<ERSyncCapabilities> eoObjects = _ERSyncCapabilities.fetchERSyncCapabilitieses(editingContext, qualifier, null);
    ERSyncCapabilities eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ERSyncCapabilities)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ERSyncCapabilities that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ERSyncCapabilities fetchRequiredERSyncCapabilities(EOEditingContext editingContext, String keyName, Object value) {
    return _ERSyncCapabilities.fetchRequiredERSyncCapabilities(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ERSyncCapabilities fetchRequiredERSyncCapabilities(EOEditingContext editingContext, EOQualifier qualifier) {
    ERSyncCapabilities eoObject = _ERSyncCapabilities.fetchERSyncCapabilities(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ERSyncCapabilities that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ERSyncCapabilities localInstanceIn(EOEditingContext editingContext, ERSyncCapabilities eo) {
    ERSyncCapabilities localInstance = (eo == null) ? null : (ERSyncCapabilities)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
