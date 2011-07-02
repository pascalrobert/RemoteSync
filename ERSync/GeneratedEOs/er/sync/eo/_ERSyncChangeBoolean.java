// $LastChangedRevision: 5773 $ DO NOT EDIT.  Make changes to ERSyncChangeBoolean.java instead.
package er.sync.eo;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _ERSyncChangeBoolean extends er.sync.eo.ERSyncChangeValue {
	public static final String ENTITY_NAME = "ERSyncChangeBoolean";

	// Attributes
	public static final String ATTRIBUTE_NAME_KEY = "attributeName";
	public static final String BOOLEAN_VALUE_KEY = "booleanValue";
	public static final String VALUE_TYPE_KEY = "valueType";

	// Relationships
	public static final String CHANGE_ENTITY_KEY = "changeEntity";
	public static final String CHANGESET_KEY = "changeset";

  private static Logger LOG = Logger.getLogger(_ERSyncChangeBoolean.class);

  public ERSyncChangeBoolean localInstanceIn(EOEditingContext editingContext) {
    ERSyncChangeBoolean localInstance = (ERSyncChangeBoolean)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public Boolean booleanValue() {
    return (Boolean) storedValueForKey("booleanValue");
  }

  public void setBooleanValue(Boolean value) {
    if (_ERSyncChangeBoolean.LOG.isDebugEnabled()) {
    	_ERSyncChangeBoolean.LOG.debug( "updating booleanValue from " + booleanValue() + " to " + value);
    }
    takeStoredValueForKey(value, "booleanValue");
  }


  public static ERSyncChangeBoolean createERSyncChangeBoolean(EOEditingContext editingContext, String attributeName
, er.sync.eo.ERSyncEntity changeEntity, er.sync.eo.ERSyncChangeset changeset) {
    ERSyncChangeBoolean eo = (ERSyncChangeBoolean) EOUtilities.createAndInsertInstance(editingContext, _ERSyncChangeBoolean.ENTITY_NAME);    
		eo.setAttributeName(attributeName);
    eo.setChangeEntityRelationship(changeEntity);
    eo.setChangesetRelationship(changeset);
    return eo;
  }

  public static NSArray<ERSyncChangeBoolean> fetchAllERSyncChangeBooleans(EOEditingContext editingContext) {
    return _ERSyncChangeBoolean.fetchAllERSyncChangeBooleans(editingContext, null);
  }

  public static NSArray<ERSyncChangeBoolean> fetchAllERSyncChangeBooleans(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _ERSyncChangeBoolean.fetchERSyncChangeBooleans(editingContext, null, sortOrderings);
  }

  public static NSArray<ERSyncChangeBoolean> fetchERSyncChangeBooleans(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ERSyncChangeBoolean.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<ERSyncChangeBoolean> eoObjects = (NSArray<ERSyncChangeBoolean>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ERSyncChangeBoolean fetchERSyncChangeBoolean(EOEditingContext editingContext, String keyName, Object value) {
    return _ERSyncChangeBoolean.fetchERSyncChangeBoolean(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ERSyncChangeBoolean fetchERSyncChangeBoolean(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<ERSyncChangeBoolean> eoObjects = _ERSyncChangeBoolean.fetchERSyncChangeBooleans(editingContext, qualifier, null);
    ERSyncChangeBoolean eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ERSyncChangeBoolean)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ERSyncChangeBoolean that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ERSyncChangeBoolean fetchRequiredERSyncChangeBoolean(EOEditingContext editingContext, String keyName, Object value) {
    return _ERSyncChangeBoolean.fetchRequiredERSyncChangeBoolean(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ERSyncChangeBoolean fetchRequiredERSyncChangeBoolean(EOEditingContext editingContext, EOQualifier qualifier) {
    ERSyncChangeBoolean eoObject = _ERSyncChangeBoolean.fetchERSyncChangeBoolean(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ERSyncChangeBoolean that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ERSyncChangeBoolean localInstanceIn(EOEditingContext editingContext, ERSyncChangeBoolean eo) {
    ERSyncChangeBoolean localInstance = (eo == null) ? null : (ERSyncChangeBoolean)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
