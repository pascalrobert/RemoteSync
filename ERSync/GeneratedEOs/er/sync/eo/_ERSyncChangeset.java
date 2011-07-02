// $LastChangedRevision: 5773 $ DO NOT EDIT.  Make changes to ERSyncChangeset.java instead.
package er.sync.eo;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _ERSyncChangeset extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ERSyncChangeset";

	// Attributes
	public static final String UPDATED_DATE_KEY = "updatedDate";
	public static final String UUID_KEY = "uuid";

	// Relationships
	public static final String AUTH_REFERENCE_KEY = "authReference";
	public static final String CHANGE_VALUES_KEY = "changeValues";

  private static Logger LOG = Logger.getLogger(_ERSyncChangeset.class);

  public ERSyncChangeset localInstanceIn(EOEditingContext editingContext) {
    ERSyncChangeset localInstance = (ERSyncChangeset)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public NSTimestamp updatedDate() {
    return (NSTimestamp) storedValueForKey("updatedDate");
  }

  public void setUpdatedDate(NSTimestamp value) {
    if (_ERSyncChangeset.LOG.isDebugEnabled()) {
    	_ERSyncChangeset.LOG.debug( "updating updatedDate from " + updatedDate() + " to " + value);
    }
    takeStoredValueForKey(value, "updatedDate");
  }

  public String uuid() {
    return (String) storedValueForKey("uuid");
  }

  public void setUuid(String value) {
    if (_ERSyncChangeset.LOG.isDebugEnabled()) {
    	_ERSyncChangeset.LOG.debug( "updating uuid from " + uuid() + " to " + value);
    }
    takeStoredValueForKey(value, "uuid");
  }

  public er.sync.eo.ERSyncAuthReference authReference() {
    return (er.sync.eo.ERSyncAuthReference)storedValueForKey("authReference");
  }

  public void setAuthReferenceRelationship(er.sync.eo.ERSyncAuthReference value) {
    if (_ERSyncChangeset.LOG.isDebugEnabled()) {
      _ERSyncChangeset.LOG.debug("updating authReference from " + authReference() + " to " + value);
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
  
  public NSArray<er.sync.eo.ERSyncChangeValue> changeValues() {
    return (NSArray<er.sync.eo.ERSyncChangeValue>)storedValueForKey("changeValues");
  }

  public NSArray<er.sync.eo.ERSyncChangeValue> changeValues(EOQualifier qualifier) {
    return changeValues(qualifier, null, false);
  }

  public NSArray<er.sync.eo.ERSyncChangeValue> changeValues(EOQualifier qualifier, boolean fetch) {
    return changeValues(qualifier, null, fetch);
  }

  public NSArray<er.sync.eo.ERSyncChangeValue> changeValues(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<er.sync.eo.ERSyncChangeValue> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(er.sync.eo.ERSyncChangeValue.CHANGESET_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = er.sync.eo.ERSyncChangeValue.fetchERSyncChangeValues(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = changeValues();
      if (qualifier != null) {
        results = (NSArray<er.sync.eo.ERSyncChangeValue>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<er.sync.eo.ERSyncChangeValue>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToChangeValuesRelationship(er.sync.eo.ERSyncChangeValue object) {
    if (_ERSyncChangeset.LOG.isDebugEnabled()) {
      _ERSyncChangeset.LOG.debug("adding " + object + " to changeValues relationship");
    }
    addObjectToBothSidesOfRelationshipWithKey(object, "changeValues");
  }

  public void removeFromChangeValuesRelationship(er.sync.eo.ERSyncChangeValue object) {
    if (_ERSyncChangeset.LOG.isDebugEnabled()) {
      _ERSyncChangeset.LOG.debug("removing " + object + " from changeValues relationship");
    }
    removeObjectFromBothSidesOfRelationshipWithKey(object, "changeValues");
  }

  public er.sync.eo.ERSyncChangeValue createChangeValuesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ERSyncChangeValue");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "changeValues");
    return (er.sync.eo.ERSyncChangeValue) eo;
  }

  public void deleteChangeValuesRelationship(er.sync.eo.ERSyncChangeValue object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "changeValues");
    editingContext().deleteObject(object);
  }

  public void deleteAllChangeValuesRelationships() {
    Enumeration objects = changeValues().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteChangeValuesRelationship((er.sync.eo.ERSyncChangeValue)objects.nextElement());
    }
  }


  public static ERSyncChangeset createERSyncChangeset(EOEditingContext editingContext) {
    ERSyncChangeset eo = (ERSyncChangeset) EOUtilities.createAndInsertInstance(editingContext, _ERSyncChangeset.ENTITY_NAME);    
    return eo;
  }

  public static NSArray<ERSyncChangeset> fetchAllERSyncChangesets(EOEditingContext editingContext) {
    return _ERSyncChangeset.fetchAllERSyncChangesets(editingContext, null);
  }

  public static NSArray<ERSyncChangeset> fetchAllERSyncChangesets(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _ERSyncChangeset.fetchERSyncChangesets(editingContext, null, sortOrderings);
  }

  public static NSArray<ERSyncChangeset> fetchERSyncChangesets(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ERSyncChangeset.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<ERSyncChangeset> eoObjects = (NSArray<ERSyncChangeset>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ERSyncChangeset fetchERSyncChangeset(EOEditingContext editingContext, String keyName, Object value) {
    return _ERSyncChangeset.fetchERSyncChangeset(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ERSyncChangeset fetchERSyncChangeset(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<ERSyncChangeset> eoObjects = _ERSyncChangeset.fetchERSyncChangesets(editingContext, qualifier, null);
    ERSyncChangeset eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ERSyncChangeset)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ERSyncChangeset that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ERSyncChangeset fetchRequiredERSyncChangeset(EOEditingContext editingContext, String keyName, Object value) {
    return _ERSyncChangeset.fetchRequiredERSyncChangeset(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ERSyncChangeset fetchRequiredERSyncChangeset(EOEditingContext editingContext, EOQualifier qualifier) {
    ERSyncChangeset eoObject = _ERSyncChangeset.fetchERSyncChangeset(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ERSyncChangeset that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ERSyncChangeset localInstanceIn(EOEditingContext editingContext, ERSyncChangeset eo) {
    ERSyncChangeset localInstance = (eo == null) ? null : (ERSyncChangeset)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
