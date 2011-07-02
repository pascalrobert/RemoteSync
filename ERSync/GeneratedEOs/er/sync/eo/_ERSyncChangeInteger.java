// $LastChangedRevision: 5773 $ DO NOT EDIT.  Make changes to ERSyncChangeInteger.java instead.
package er.sync.eo;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _ERSyncChangeInteger extends er.sync.eo.ERSyncChangeValue {
	public static final String ENTITY_NAME = "ERSyncChangeInteger";

	// Attributes
	public static final String ATTRIBUTE_NAME_KEY = "attributeName";
	public static final String INT_VALUE_KEY = "intValue";
	public static final String VALUE_TYPE_KEY = "valueType";

	// Relationships
	public static final String CHANGE_ENTITY_KEY = "changeEntity";
	public static final String CHANGESET_KEY = "changeset";

  private static Logger LOG = Logger.getLogger(_ERSyncChangeInteger.class);

  public ERSyncChangeInteger localInstanceIn(EOEditingContext editingContext) {
    ERSyncChangeInteger localInstance = (ERSyncChangeInteger)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public Integer intValue() {
    return (Integer) storedValueForKey("intValue");
  }

  public void setIntValue(Integer value) {
    if (_ERSyncChangeInteger.LOG.isDebugEnabled()) {
    	_ERSyncChangeInteger.LOG.debug( "updating intValue from " + intValue() + " to " + value);
    }
    takeStoredValueForKey(value, "intValue");
  }


  public static ERSyncChangeInteger createERSyncChangeInteger(EOEditingContext editingContext, String attributeName
, er.sync.eo.ERSyncEntity changeEntity, er.sync.eo.ERSyncChangeset changeset) {
    ERSyncChangeInteger eo = (ERSyncChangeInteger) EOUtilities.createAndInsertInstance(editingContext, _ERSyncChangeInteger.ENTITY_NAME);    
		eo.setAttributeName(attributeName);
    eo.setChangeEntityRelationship(changeEntity);
    eo.setChangesetRelationship(changeset);
    return eo;
  }

  public static NSArray<ERSyncChangeInteger> fetchAllERSyncChangeIntegers(EOEditingContext editingContext) {
    return _ERSyncChangeInteger.fetchAllERSyncChangeIntegers(editingContext, null);
  }

  public static NSArray<ERSyncChangeInteger> fetchAllERSyncChangeIntegers(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _ERSyncChangeInteger.fetchERSyncChangeIntegers(editingContext, null, sortOrderings);
  }

  public static NSArray<ERSyncChangeInteger> fetchERSyncChangeIntegers(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ERSyncChangeInteger.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<ERSyncChangeInteger> eoObjects = (NSArray<ERSyncChangeInteger>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ERSyncChangeInteger fetchERSyncChangeInteger(EOEditingContext editingContext, String keyName, Object value) {
    return _ERSyncChangeInteger.fetchERSyncChangeInteger(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ERSyncChangeInteger fetchERSyncChangeInteger(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<ERSyncChangeInteger> eoObjects = _ERSyncChangeInteger.fetchERSyncChangeIntegers(editingContext, qualifier, null);
    ERSyncChangeInteger eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ERSyncChangeInteger)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ERSyncChangeInteger that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ERSyncChangeInteger fetchRequiredERSyncChangeInteger(EOEditingContext editingContext, String keyName, Object value) {
    return _ERSyncChangeInteger.fetchRequiredERSyncChangeInteger(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ERSyncChangeInteger fetchRequiredERSyncChangeInteger(EOEditingContext editingContext, EOQualifier qualifier) {
    ERSyncChangeInteger eoObject = _ERSyncChangeInteger.fetchERSyncChangeInteger(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ERSyncChangeInteger that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ERSyncChangeInteger localInstanceIn(EOEditingContext editingContext, ERSyncChangeInteger eo) {
    ERSyncChangeInteger localInstance = (eo == null) ? null : (ERSyncChangeInteger)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
