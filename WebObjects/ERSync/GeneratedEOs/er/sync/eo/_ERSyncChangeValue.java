// $LastChangedRevision: 5773 $ DO NOT EDIT.  Make changes to ERSyncChangeValue.java instead.
package er.sync.eo;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _ERSyncChangeValue extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ERSyncChangeValue";

	// Attributes
	public static final String ATTRIBUTE_NAME_KEY = "attributeName";
	public static final String VALUE_TYPE_KEY = "valueType";

	// Relationships
	public static final String CHANGE_ENTITY_KEY = "changeEntity";
	public static final String CHANGESET_KEY = "changeset";

  private static Logger LOG = Logger.getLogger(_ERSyncChangeValue.class);

  public ERSyncChangeValue localInstanceIn(EOEditingContext editingContext) {
    ERSyncChangeValue localInstance = (ERSyncChangeValue)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public String attributeName() {
    return (String) storedValueForKey("attributeName");
  }

  public void setAttributeName(String value) {
    if (_ERSyncChangeValue.LOG.isDebugEnabled()) {
    	_ERSyncChangeValue.LOG.debug( "updating attributeName from " + attributeName() + " to " + value);
    }
    takeStoredValueForKey(value, "attributeName");
  }

  public String valueType() {
    return (String) storedValueForKey("valueType");
  }

  public void setValueType(String value) {
    if (_ERSyncChangeValue.LOG.isDebugEnabled()) {
    	_ERSyncChangeValue.LOG.debug( "updating valueType from " + valueType() + " to " + value);
    }
    takeStoredValueForKey(value, "valueType");
  }

  public er.sync.eo.ERSyncEntity changeEntity() {
    return (er.sync.eo.ERSyncEntity)storedValueForKey("changeEntity");
  }

  public void setChangeEntityRelationship(er.sync.eo.ERSyncEntity value) {
    if (_ERSyncChangeValue.LOG.isDebugEnabled()) {
      _ERSyncChangeValue.LOG.debug("updating changeEntity from " + changeEntity() + " to " + value);
    }
    if (value == null) {
    	er.sync.eo.ERSyncEntity oldValue = changeEntity();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "changeEntity");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "changeEntity");
    }
  }
  
  public er.sync.eo.ERSyncChangeset changeset() {
    return (er.sync.eo.ERSyncChangeset)storedValueForKey("changeset");
  }

  public void setChangesetRelationship(er.sync.eo.ERSyncChangeset value) {
    if (_ERSyncChangeValue.LOG.isDebugEnabled()) {
      _ERSyncChangeValue.LOG.debug("updating changeset from " + changeset() + " to " + value);
    }
    if (value == null) {
    	er.sync.eo.ERSyncChangeset oldValue = changeset();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "changeset");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "changeset");
    }
  }
  

  public static ERSyncChangeValue createERSyncChangeValue(EOEditingContext editingContext, String attributeName
, er.sync.eo.ERSyncEntity changeEntity, er.sync.eo.ERSyncChangeset changeset) {
    ERSyncChangeValue eo = (ERSyncChangeValue) EOUtilities.createAndInsertInstance(editingContext, _ERSyncChangeValue.ENTITY_NAME);    
		eo.setAttributeName(attributeName);
    eo.setChangeEntityRelationship(changeEntity);
    eo.setChangesetRelationship(changeset);
    return eo;
  }

  public static NSArray<ERSyncChangeValue> fetchAllERSyncChangeValues(EOEditingContext editingContext) {
    return _ERSyncChangeValue.fetchAllERSyncChangeValues(editingContext, null);
  }

  public static NSArray<ERSyncChangeValue> fetchAllERSyncChangeValues(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _ERSyncChangeValue.fetchERSyncChangeValues(editingContext, null, sortOrderings);
  }

  public static NSArray<ERSyncChangeValue> fetchERSyncChangeValues(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ERSyncChangeValue.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<ERSyncChangeValue> eoObjects = (NSArray<ERSyncChangeValue>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ERSyncChangeValue fetchERSyncChangeValue(EOEditingContext editingContext, String keyName, Object value) {
    return _ERSyncChangeValue.fetchERSyncChangeValue(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ERSyncChangeValue fetchERSyncChangeValue(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<ERSyncChangeValue> eoObjects = _ERSyncChangeValue.fetchERSyncChangeValues(editingContext, qualifier, null);
    ERSyncChangeValue eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ERSyncChangeValue)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ERSyncChangeValue that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ERSyncChangeValue fetchRequiredERSyncChangeValue(EOEditingContext editingContext, String keyName, Object value) {
    return _ERSyncChangeValue.fetchRequiredERSyncChangeValue(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ERSyncChangeValue fetchRequiredERSyncChangeValue(EOEditingContext editingContext, EOQualifier qualifier) {
    ERSyncChangeValue eoObject = _ERSyncChangeValue.fetchERSyncChangeValue(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ERSyncChangeValue that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ERSyncChangeValue localInstanceIn(EOEditingContext editingContext, ERSyncChangeValue eo) {
    ERSyncChangeValue localInstance = (eo == null) ? null : (ERSyncChangeValue)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
