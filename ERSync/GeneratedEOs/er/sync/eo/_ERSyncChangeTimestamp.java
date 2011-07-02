// $LastChangedRevision: 5773 $ DO NOT EDIT.  Make changes to ERSyncChangeTimestamp.java instead.
package er.sync.eo;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _ERSyncChangeTimestamp extends er.sync.eo.ERSyncChangeValue {
	public static final String ENTITY_NAME = "ERSyncChangeTimestamp";

	// Attributes
	public static final String ATTRIBUTE_NAME_KEY = "attributeName";
	public static final String TIMESTAMP_VALUE_KEY = "timestampValue";
	public static final String VALUE_TYPE_KEY = "valueType";

	// Relationships
	public static final String CHANGE_ENTITY_KEY = "changeEntity";
	public static final String CHANGESET_KEY = "changeset";

  private static Logger LOG = Logger.getLogger(_ERSyncChangeTimestamp.class);

  public ERSyncChangeTimestamp localInstanceIn(EOEditingContext editingContext) {
    ERSyncChangeTimestamp localInstance = (ERSyncChangeTimestamp)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public NSTimestamp timestampValue() {
    return (NSTimestamp) storedValueForKey("timestampValue");
  }

  public void setTimestampValue(NSTimestamp value) {
    if (_ERSyncChangeTimestamp.LOG.isDebugEnabled()) {
    	_ERSyncChangeTimestamp.LOG.debug( "updating timestampValue from " + timestampValue() + " to " + value);
    }
    takeStoredValueForKey(value, "timestampValue");
  }


  public static ERSyncChangeTimestamp createERSyncChangeTimestamp(EOEditingContext editingContext, String attributeName
, er.sync.eo.ERSyncEntity changeEntity, er.sync.eo.ERSyncChangeset changeset) {
    ERSyncChangeTimestamp eo = (ERSyncChangeTimestamp) EOUtilities.createAndInsertInstance(editingContext, _ERSyncChangeTimestamp.ENTITY_NAME);    
		eo.setAttributeName(attributeName);
    eo.setChangeEntityRelationship(changeEntity);
    eo.setChangesetRelationship(changeset);
    return eo;
  }

  public static NSArray<ERSyncChangeTimestamp> fetchAllERSyncChangeTimestamps(EOEditingContext editingContext) {
    return _ERSyncChangeTimestamp.fetchAllERSyncChangeTimestamps(editingContext, null);
  }

  public static NSArray<ERSyncChangeTimestamp> fetchAllERSyncChangeTimestamps(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _ERSyncChangeTimestamp.fetchERSyncChangeTimestamps(editingContext, null, sortOrderings);
  }

  public static NSArray<ERSyncChangeTimestamp> fetchERSyncChangeTimestamps(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ERSyncChangeTimestamp.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<ERSyncChangeTimestamp> eoObjects = (NSArray<ERSyncChangeTimestamp>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ERSyncChangeTimestamp fetchERSyncChangeTimestamp(EOEditingContext editingContext, String keyName, Object value) {
    return _ERSyncChangeTimestamp.fetchERSyncChangeTimestamp(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ERSyncChangeTimestamp fetchERSyncChangeTimestamp(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<ERSyncChangeTimestamp> eoObjects = _ERSyncChangeTimestamp.fetchERSyncChangeTimestamps(editingContext, qualifier, null);
    ERSyncChangeTimestamp eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ERSyncChangeTimestamp)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ERSyncChangeTimestamp that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ERSyncChangeTimestamp fetchRequiredERSyncChangeTimestamp(EOEditingContext editingContext, String keyName, Object value) {
    return _ERSyncChangeTimestamp.fetchRequiredERSyncChangeTimestamp(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ERSyncChangeTimestamp fetchRequiredERSyncChangeTimestamp(EOEditingContext editingContext, EOQualifier qualifier) {
    ERSyncChangeTimestamp eoObject = _ERSyncChangeTimestamp.fetchERSyncChangeTimestamp(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ERSyncChangeTimestamp that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ERSyncChangeTimestamp localInstanceIn(EOEditingContext editingContext, ERSyncChangeTimestamp eo) {
    ERSyncChangeTimestamp localInstance = (eo == null) ? null : (ERSyncChangeTimestamp)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
