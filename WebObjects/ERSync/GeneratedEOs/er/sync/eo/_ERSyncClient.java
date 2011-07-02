// $LastChangedRevision: 5773 $ DO NOT EDIT.  Make changes to ERSyncClient.java instead.
package er.sync.eo;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _ERSyncClient extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ERSyncClient";

	// Attributes
	public static final String DISABLE_KEY = "disable";
	public static final String NAME_KEY = "name";
	public static final String TYPE_KEY = "type";
	public static final String UUID_KEY = "uuid";

	// Relationships
	public static final String CAPABILITIES_KEY = "capabilities";

  private static Logger LOG = Logger.getLogger(_ERSyncClient.class);

  public ERSyncClient localInstanceIn(EOEditingContext editingContext) {
    ERSyncClient localInstance = (ERSyncClient)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public Boolean disable() {
    return (Boolean) storedValueForKey("disable");
  }

  public void setDisable(Boolean value) {
    if (_ERSyncClient.LOG.isDebugEnabled()) {
    	_ERSyncClient.LOG.debug( "updating disable from " + disable() + " to " + value);
    }
    takeStoredValueForKey(value, "disable");
  }

  public String name() {
    return (String) storedValueForKey("name");
  }

  public void setName(String value) {
    if (_ERSyncClient.LOG.isDebugEnabled()) {
    	_ERSyncClient.LOG.debug( "updating name from " + name() + " to " + value);
    }
    takeStoredValueForKey(value, "name");
  }

  public String type() {
    return (String) storedValueForKey("type");
  }

  public void setType(String value) {
    if (_ERSyncClient.LOG.isDebugEnabled()) {
    	_ERSyncClient.LOG.debug( "updating type from " + type() + " to " + value);
    }
    takeStoredValueForKey(value, "type");
  }

  public String uuid() {
    return (String) storedValueForKey("uuid");
  }

  public void setUuid(String value) {
    if (_ERSyncClient.LOG.isDebugEnabled()) {
    	_ERSyncClient.LOG.debug( "updating uuid from " + uuid() + " to " + value);
    }
    takeStoredValueForKey(value, "uuid");
  }

  public er.sync.eo.ERSyncCapabilities capabilities() {
    return (er.sync.eo.ERSyncCapabilities)storedValueForKey("capabilities");
  }

  public void setCapabilitiesRelationship(er.sync.eo.ERSyncCapabilities value) {
    if (_ERSyncClient.LOG.isDebugEnabled()) {
      _ERSyncClient.LOG.debug("updating capabilities from " + capabilities() + " to " + value);
    }
    if (value == null) {
    	er.sync.eo.ERSyncCapabilities oldValue = capabilities();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "capabilities");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "capabilities");
    }
  }
  

  public static ERSyncClient createERSyncClient(EOEditingContext editingContext, Boolean disable
, String name
, String type
) {
    ERSyncClient eo = (ERSyncClient) EOUtilities.createAndInsertInstance(editingContext, _ERSyncClient.ENTITY_NAME);    
		eo.setDisable(disable);
		eo.setName(name);
		eo.setType(type);
    return eo;
  }

  public static NSArray<ERSyncClient> fetchAllERSyncClients(EOEditingContext editingContext) {
    return _ERSyncClient.fetchAllERSyncClients(editingContext, null);
  }

  public static NSArray<ERSyncClient> fetchAllERSyncClients(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _ERSyncClient.fetchERSyncClients(editingContext, null, sortOrderings);
  }

  public static NSArray<ERSyncClient> fetchERSyncClients(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ERSyncClient.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<ERSyncClient> eoObjects = (NSArray<ERSyncClient>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ERSyncClient fetchERSyncClient(EOEditingContext editingContext, String keyName, Object value) {
    return _ERSyncClient.fetchERSyncClient(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ERSyncClient fetchERSyncClient(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<ERSyncClient> eoObjects = _ERSyncClient.fetchERSyncClients(editingContext, qualifier, null);
    ERSyncClient eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ERSyncClient)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ERSyncClient that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ERSyncClient fetchRequiredERSyncClient(EOEditingContext editingContext, String keyName, Object value) {
    return _ERSyncClient.fetchRequiredERSyncClient(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ERSyncClient fetchRequiredERSyncClient(EOEditingContext editingContext, EOQualifier qualifier) {
    ERSyncClient eoObject = _ERSyncClient.fetchERSyncClient(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ERSyncClient that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ERSyncClient localInstanceIn(EOEditingContext editingContext, ERSyncClient eo) {
    ERSyncClient localInstance = (eo == null) ? null : (ERSyncClient)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
