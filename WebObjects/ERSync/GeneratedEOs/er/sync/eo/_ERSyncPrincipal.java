// $LastChangedRevision: 5773 $ DO NOT EDIT.  Make changes to ERSyncPrincipal.java instead.
package er.sync.eo;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _ERSyncPrincipal extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ERSyncPrincipal";

	// Attributes
	public static final String DEVICE_UUID_KEY = "deviceUUID";
	public static final String LAST_SYNC_KEY = "lastSync";
	public static final String PRINCIPAL_UUID_KEY = "principalUUID";

	// Relationships
	public static final String APPLICATION_KEY = "application";
	public static final String AUTH_REFERENCE_KEY = "authReference";
	public static final String DEVICE_TYPE_KEY = "deviceType";

  private static Logger LOG = Logger.getLogger(_ERSyncPrincipal.class);

  public ERSyncPrincipal localInstanceIn(EOEditingContext editingContext) {
    ERSyncPrincipal localInstance = (ERSyncPrincipal)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public String deviceUUID() {
    return (String) storedValueForKey("deviceUUID");
  }

  public void setDeviceUUID(String value) {
    if (_ERSyncPrincipal.LOG.isDebugEnabled()) {
    	_ERSyncPrincipal.LOG.debug( "updating deviceUUID from " + deviceUUID() + " to " + value);
    }
    takeStoredValueForKey(value, "deviceUUID");
  }

  public NSTimestamp lastSync() {
    return (NSTimestamp) storedValueForKey("lastSync");
  }

  public void setLastSync(NSTimestamp value) {
    if (_ERSyncPrincipal.LOG.isDebugEnabled()) {
    	_ERSyncPrincipal.LOG.debug( "updating lastSync from " + lastSync() + " to " + value);
    }
    takeStoredValueForKey(value, "lastSync");
  }

  public String principalUUID() {
    return (String) storedValueForKey("principalUUID");
  }

  public void setPrincipalUUID(String value) {
    if (_ERSyncPrincipal.LOG.isDebugEnabled()) {
    	_ERSyncPrincipal.LOG.debug( "updating principalUUID from " + principalUUID() + " to " + value);
    }
    takeStoredValueForKey(value, "principalUUID");
  }

  public er.sync.eo.ERSyncClientApp application() {
    return (er.sync.eo.ERSyncClientApp)storedValueForKey("application");
  }

  public void setApplicationRelationship(er.sync.eo.ERSyncClientApp value) {
    if (_ERSyncPrincipal.LOG.isDebugEnabled()) {
      _ERSyncPrincipal.LOG.debug("updating application from " + application() + " to " + value);
    }
    if (value == null) {
    	er.sync.eo.ERSyncClientApp oldValue = application();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "application");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "application");
    }
  }
  
  public er.sync.eo.ERSyncAuthReference authReference() {
    return (er.sync.eo.ERSyncAuthReference)storedValueForKey("authReference");
  }

  public void setAuthReferenceRelationship(er.sync.eo.ERSyncAuthReference value) {
    if (_ERSyncPrincipal.LOG.isDebugEnabled()) {
      _ERSyncPrincipal.LOG.debug("updating authReference from " + authReference() + " to " + value);
    }
    if (value == null) {
    	er.sync.eo.ERSyncAuthReference oldValue = authReference();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "authReference");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "authReference");
    }
  }
  
  public er.sync.eo.ERSyncClientDevice deviceType() {
    return (er.sync.eo.ERSyncClientDevice)storedValueForKey("deviceType");
  }

  public void setDeviceTypeRelationship(er.sync.eo.ERSyncClientDevice value) {
    if (_ERSyncPrincipal.LOG.isDebugEnabled()) {
      _ERSyncPrincipal.LOG.debug("updating deviceType from " + deviceType() + " to " + value);
    }
    if (value == null) {
    	er.sync.eo.ERSyncClientDevice oldValue = deviceType();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "deviceType");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "deviceType");
    }
  }
  

  public static ERSyncPrincipal createERSyncPrincipal(EOEditingContext editingContext, String deviceUUID
, String principalUUID
, er.sync.eo.ERSyncClientApp application, er.sync.eo.ERSyncAuthReference authReference, er.sync.eo.ERSyncClientDevice deviceType) {
    ERSyncPrincipal eo = (ERSyncPrincipal) EOUtilities.createAndInsertInstance(editingContext, _ERSyncPrincipal.ENTITY_NAME);    
		eo.setDeviceUUID(deviceUUID);
		eo.setPrincipalUUID(principalUUID);
    eo.setApplicationRelationship(application);
    eo.setAuthReferenceRelationship(authReference);
    eo.setDeviceTypeRelationship(deviceType);
    return eo;
  }

  public static NSArray<ERSyncPrincipal> fetchAllERSyncPrincipals(EOEditingContext editingContext) {
    return _ERSyncPrincipal.fetchAllERSyncPrincipals(editingContext, null);
  }

  public static NSArray<ERSyncPrincipal> fetchAllERSyncPrincipals(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _ERSyncPrincipal.fetchERSyncPrincipals(editingContext, null, sortOrderings);
  }

  public static NSArray<ERSyncPrincipal> fetchERSyncPrincipals(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ERSyncPrincipal.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<ERSyncPrincipal> eoObjects = (NSArray<ERSyncPrincipal>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ERSyncPrincipal fetchERSyncPrincipal(EOEditingContext editingContext, String keyName, Object value) {
    return _ERSyncPrincipal.fetchERSyncPrincipal(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ERSyncPrincipal fetchERSyncPrincipal(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<ERSyncPrincipal> eoObjects = _ERSyncPrincipal.fetchERSyncPrincipals(editingContext, qualifier, null);
    ERSyncPrincipal eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ERSyncPrincipal)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ERSyncPrincipal that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ERSyncPrincipal fetchRequiredERSyncPrincipal(EOEditingContext editingContext, String keyName, Object value) {
    return _ERSyncPrincipal.fetchRequiredERSyncPrincipal(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ERSyncPrincipal fetchRequiredERSyncPrincipal(EOEditingContext editingContext, EOQualifier qualifier) {
    ERSyncPrincipal eoObject = _ERSyncPrincipal.fetchERSyncPrincipal(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ERSyncPrincipal that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ERSyncPrincipal localInstanceIn(EOEditingContext editingContext, ERSyncPrincipal eo) {
    ERSyncPrincipal localInstance = (eo == null) ? null : (ERSyncPrincipal)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
