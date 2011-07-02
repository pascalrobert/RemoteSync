// $LastChangedRevision: 5773 $ DO NOT EDIT.  Make changes to ERSyncChangeString.java instead.
package er.sync.eo;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _ERSyncChangeString extends er.sync.eo.ERSyncChangeValue {
	public static final String ENTITY_NAME = "ERSyncChangeString";

	// Attributes
	public static final String ATTRIBUTE_NAME_KEY = "attributeName";
	public static final String STRING_VALUE_KEY = "stringValue";
	public static final String VALUE_TYPE_KEY = "valueType";

	// Relationships
	public static final String CHANGE_ENTITY_KEY = "changeEntity";
	public static final String CHANGESET_KEY = "changeset";

  private static Logger LOG = Logger.getLogger(_ERSyncChangeString.class);

  public ERSyncChangeString localInstanceIn(EOEditingContext editingContext) {
    ERSyncChangeString localInstance = (ERSyncChangeString)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public String stringValue() {
    return (String) storedValueForKey("stringValue");
  }

  public void setStringValue(String value) {
    if (_ERSyncChangeString.LOG.isDebugEnabled()) {
    	_ERSyncChangeString.LOG.debug( "updating stringValue from " + stringValue() + " to " + value);
    }
    takeStoredValueForKey(value, "stringValue");
  }


  public static ERSyncChangeString createERSyncChangeString(EOEditingContext editingContext, String attributeName
, er.sync.eo.ERSyncEntity changeEntity, er.sync.eo.ERSyncChangeset changeset) {
    ERSyncChangeString eo = (ERSyncChangeString) EOUtilities.createAndInsertInstance(editingContext, _ERSyncChangeString.ENTITY_NAME);    
		eo.setAttributeName(attributeName);
    eo.setChangeEntityRelationship(changeEntity);
    eo.setChangesetRelationship(changeset);
    return eo;
  }

  public static NSArray<ERSyncChangeString> fetchAllERSyncChangeStrings(EOEditingContext editingContext) {
    return _ERSyncChangeString.fetchAllERSyncChangeStrings(editingContext, null);
  }

  public static NSArray<ERSyncChangeString> fetchAllERSyncChangeStrings(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _ERSyncChangeString.fetchERSyncChangeStrings(editingContext, null, sortOrderings);
  }

  public static NSArray<ERSyncChangeString> fetchERSyncChangeStrings(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ERSyncChangeString.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<ERSyncChangeString> eoObjects = (NSArray<ERSyncChangeString>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ERSyncChangeString fetchERSyncChangeString(EOEditingContext editingContext, String keyName, Object value) {
    return _ERSyncChangeString.fetchERSyncChangeString(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ERSyncChangeString fetchERSyncChangeString(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<ERSyncChangeString> eoObjects = _ERSyncChangeString.fetchERSyncChangeStrings(editingContext, qualifier, null);
    ERSyncChangeString eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ERSyncChangeString)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ERSyncChangeString that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ERSyncChangeString fetchRequiredERSyncChangeString(EOEditingContext editingContext, String keyName, Object value) {
    return _ERSyncChangeString.fetchRequiredERSyncChangeString(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ERSyncChangeString fetchRequiredERSyncChangeString(EOEditingContext editingContext, EOQualifier qualifier) {
    ERSyncChangeString eoObject = _ERSyncChangeString.fetchERSyncChangeString(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ERSyncChangeString that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ERSyncChangeString localInstanceIn(EOEditingContext editingContext, ERSyncChangeString eo) {
    ERSyncChangeString localInstance = (eo == null) ? null : (ERSyncChangeString)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
