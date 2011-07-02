// $LastChangedRevision: 5773 $ DO NOT EDIT.  Make changes to ERSyncChangeToOne.java instead.
package er.sync.eo;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _ERSyncChangeToOne extends er.sync.eo.ERSyncChangeValue {
	public static final String ENTITY_NAME = "ERSyncChangeToOne";

	// Attributes
	public static final String ATTRIBUTE_NAME_KEY = "attributeName";
	public static final String VALUE_TYPE_KEY = "valueType";

	// Relationships
	public static final String CHANGE_ENTITY_KEY = "changeEntity";
	public static final String CHANGESET_KEY = "changeset";
	public static final String TO_ONE_VALUE_KEY = "toOneValue";

  private static Logger LOG = Logger.getLogger(_ERSyncChangeToOne.class);

  public ERSyncChangeToOne localInstanceIn(EOEditingContext editingContext) {
    ERSyncChangeToOne localInstance = (ERSyncChangeToOne)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public er.sync.eo.ERSyncEntity toOneValue() {
    return (er.sync.eo.ERSyncEntity)storedValueForKey("toOneValue");
  }

  public void setToOneValueRelationship(er.sync.eo.ERSyncEntity value) {
    if (_ERSyncChangeToOne.LOG.isDebugEnabled()) {
      _ERSyncChangeToOne.LOG.debug("updating toOneValue from " + toOneValue() + " to " + value);
    }
    if (value == null) {
    	er.sync.eo.ERSyncEntity oldValue = toOneValue();
    	if (oldValue != null) {
    		removeObjectFromBothSidesOfRelationshipWithKey(oldValue, "toOneValue");
      }
    } else {
    	addObjectToBothSidesOfRelationshipWithKey(value, "toOneValue");
    }
  }
  

  public static ERSyncChangeToOne createERSyncChangeToOne(EOEditingContext editingContext, String attributeName
, er.sync.eo.ERSyncEntity changeEntity, er.sync.eo.ERSyncChangeset changeset) {
    ERSyncChangeToOne eo = (ERSyncChangeToOne) EOUtilities.createAndInsertInstance(editingContext, _ERSyncChangeToOne.ENTITY_NAME);    
		eo.setAttributeName(attributeName);
    eo.setChangeEntityRelationship(changeEntity);
    eo.setChangesetRelationship(changeset);
    return eo;
  }

  public static NSArray<ERSyncChangeToOne> fetchAllERSyncChangeToOnes(EOEditingContext editingContext) {
    return _ERSyncChangeToOne.fetchAllERSyncChangeToOnes(editingContext, null);
  }

  public static NSArray<ERSyncChangeToOne> fetchAllERSyncChangeToOnes(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _ERSyncChangeToOne.fetchERSyncChangeToOnes(editingContext, null, sortOrderings);
  }

  public static NSArray<ERSyncChangeToOne> fetchERSyncChangeToOnes(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ERSyncChangeToOne.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<ERSyncChangeToOne> eoObjects = (NSArray<ERSyncChangeToOne>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ERSyncChangeToOne fetchERSyncChangeToOne(EOEditingContext editingContext, String keyName, Object value) {
    return _ERSyncChangeToOne.fetchERSyncChangeToOne(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ERSyncChangeToOne fetchERSyncChangeToOne(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<ERSyncChangeToOne> eoObjects = _ERSyncChangeToOne.fetchERSyncChangeToOnes(editingContext, qualifier, null);
    ERSyncChangeToOne eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ERSyncChangeToOne)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ERSyncChangeToOne that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ERSyncChangeToOne fetchRequiredERSyncChangeToOne(EOEditingContext editingContext, String keyName, Object value) {
    return _ERSyncChangeToOne.fetchRequiredERSyncChangeToOne(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ERSyncChangeToOne fetchRequiredERSyncChangeToOne(EOEditingContext editingContext, EOQualifier qualifier) {
    ERSyncChangeToOne eoObject = _ERSyncChangeToOne.fetchERSyncChangeToOne(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ERSyncChangeToOne that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ERSyncChangeToOne localInstanceIn(EOEditingContext editingContext, ERSyncChangeToOne eo) {
    ERSyncChangeToOne localInstance = (eo == null) ? null : (ERSyncChangeToOne)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
