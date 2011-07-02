// $LastChangedRevision: 5773 $ DO NOT EDIT.  Make changes to ERSyncEntity.java instead.
package er.sync.eo;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _ERSyncEntity extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ERSyncEntity";

	// Attributes
	public static final String DATA_SOURCE_TOKEN_KEY = "dataSourceToken";
	public static final String STATUS_KEY = "status";
	public static final String UPDATED_DATE_KEY = "updatedDate";
	public static final String UUID_KEY = "uuid";

	// Relationships
	public static final String AUTH_REFERENCES_KEY = "authReferences";
	public static final String CHANGE_VALUES_KEY = "changeValues";
	public static final String TO_MANY_CHANGES_KEY = "toManyChanges";
	public static final String TO_ONE_RELATIONS_KEY = "toOneRelations";

  private static Logger LOG = Logger.getLogger(_ERSyncEntity.class);

  public ERSyncEntity localInstanceIn(EOEditingContext editingContext) {
    ERSyncEntity localInstance = (ERSyncEntity)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public String dataSourceToken() {
    return (String) storedValueForKey("dataSourceToken");
  }

  public void setDataSourceToken(String value) {
    if (_ERSyncEntity.LOG.isDebugEnabled()) {
    	_ERSyncEntity.LOG.debug( "updating dataSourceToken from " + dataSourceToken() + " to " + value);
    }
    takeStoredValueForKey(value, "dataSourceToken");
  }

  public String status() {
    return (String) storedValueForKey("status");
  }

  public void setStatus(String value) {
    if (_ERSyncEntity.LOG.isDebugEnabled()) {
    	_ERSyncEntity.LOG.debug( "updating status from " + status() + " to " + value);
    }
    takeStoredValueForKey(value, "status");
  }

  public NSTimestamp updatedDate() {
    return (NSTimestamp) storedValueForKey("updatedDate");
  }

  public void setUpdatedDate(NSTimestamp value) {
    if (_ERSyncEntity.LOG.isDebugEnabled()) {
    	_ERSyncEntity.LOG.debug( "updating updatedDate from " + updatedDate() + " to " + value);
    }
    takeStoredValueForKey(value, "updatedDate");
  }

  public String uuid() {
    return (String) storedValueForKey("uuid");
  }

  public void setUuid(String value) {
    if (_ERSyncEntity.LOG.isDebugEnabled()) {
    	_ERSyncEntity.LOG.debug( "updating uuid from " + uuid() + " to " + value);
    }
    takeStoredValueForKey(value, "uuid");
  }

  public NSArray<er.sync.eo.ERSyncAuthReference> authReferences() {
    return (NSArray<er.sync.eo.ERSyncAuthReference>)storedValueForKey("authReferences");
  }

  public NSArray<er.sync.eo.ERSyncAuthReference> authReferences(EOQualifier qualifier) {
    return authReferences(qualifier, null);
  }

  public NSArray<er.sync.eo.ERSyncAuthReference> authReferences(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    NSArray<er.sync.eo.ERSyncAuthReference> results;
      results = authReferences();
      if (qualifier != null) {
        results = (NSArray<er.sync.eo.ERSyncAuthReference>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<er.sync.eo.ERSyncAuthReference>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToAuthReferencesRelationship(er.sync.eo.ERSyncAuthReference object) {
    if (_ERSyncEntity.LOG.isDebugEnabled()) {
      _ERSyncEntity.LOG.debug("adding " + object + " to authReferences relationship");
    }
    addObjectToBothSidesOfRelationshipWithKey(object, "authReferences");
  }

  public void removeFromAuthReferencesRelationship(er.sync.eo.ERSyncAuthReference object) {
    if (_ERSyncEntity.LOG.isDebugEnabled()) {
      _ERSyncEntity.LOG.debug("removing " + object + " from authReferences relationship");
    }
    removeObjectFromBothSidesOfRelationshipWithKey(object, "authReferences");
  }

  public er.sync.eo.ERSyncAuthReference createAuthReferencesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ERSyncAuthReference");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "authReferences");
    return (er.sync.eo.ERSyncAuthReference) eo;
  }

  public void deleteAuthReferencesRelationship(er.sync.eo.ERSyncAuthReference object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "authReferences");
    editingContext().deleteObject(object);
  }

  public void deleteAllAuthReferencesRelationships() {
    Enumeration objects = authReferences().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteAuthReferencesRelationship((er.sync.eo.ERSyncAuthReference)objects.nextElement());
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
      EOQualifier inverseQualifier = new EOKeyValueQualifier(er.sync.eo.ERSyncChangeValue.CHANGE_ENTITY_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
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
    if (_ERSyncEntity.LOG.isDebugEnabled()) {
      _ERSyncEntity.LOG.debug("adding " + object + " to changeValues relationship");
    }
    addObjectToBothSidesOfRelationshipWithKey(object, "changeValues");
  }

  public void removeFromChangeValuesRelationship(er.sync.eo.ERSyncChangeValue object) {
    if (_ERSyncEntity.LOG.isDebugEnabled()) {
      _ERSyncEntity.LOG.debug("removing " + object + " from changeValues relationship");
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

  public NSArray<er.sync.eo.ERSyncChangeToMany> toManyChanges() {
    return (NSArray<er.sync.eo.ERSyncChangeToMany>)storedValueForKey("toManyChanges");
  }

  public NSArray<er.sync.eo.ERSyncChangeToMany> toManyChanges(EOQualifier qualifier) {
    return toManyChanges(qualifier, null);
  }

  public NSArray<er.sync.eo.ERSyncChangeToMany> toManyChanges(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    NSArray<er.sync.eo.ERSyncChangeToMany> results;
      results = toManyChanges();
      if (qualifier != null) {
        results = (NSArray<er.sync.eo.ERSyncChangeToMany>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<er.sync.eo.ERSyncChangeToMany>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToToManyChangesRelationship(er.sync.eo.ERSyncChangeToMany object) {
    if (_ERSyncEntity.LOG.isDebugEnabled()) {
      _ERSyncEntity.LOG.debug("adding " + object + " to toManyChanges relationship");
    }
    addObjectToBothSidesOfRelationshipWithKey(object, "toManyChanges");
  }

  public void removeFromToManyChangesRelationship(er.sync.eo.ERSyncChangeToMany object) {
    if (_ERSyncEntity.LOG.isDebugEnabled()) {
      _ERSyncEntity.LOG.debug("removing " + object + " from toManyChanges relationship");
    }
    removeObjectFromBothSidesOfRelationshipWithKey(object, "toManyChanges");
  }

  public er.sync.eo.ERSyncChangeToMany createToManyChangesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ERSyncChangeToMany");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "toManyChanges");
    return (er.sync.eo.ERSyncChangeToMany) eo;
  }

  public void deleteToManyChangesRelationship(er.sync.eo.ERSyncChangeToMany object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "toManyChanges");
    editingContext().deleteObject(object);
  }

  public void deleteAllToManyChangesRelationships() {
    Enumeration objects = toManyChanges().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteToManyChangesRelationship((er.sync.eo.ERSyncChangeToMany)objects.nextElement());
    }
  }

  public NSArray<er.sync.eo.ERSyncChangeToOne> toOneRelations() {
    return (NSArray<er.sync.eo.ERSyncChangeToOne>)storedValueForKey("toOneRelations");
  }

  public NSArray<er.sync.eo.ERSyncChangeToOne> toOneRelations(EOQualifier qualifier) {
    return toOneRelations(qualifier, null, false);
  }

  public NSArray<er.sync.eo.ERSyncChangeToOne> toOneRelations(EOQualifier qualifier, boolean fetch) {
    return toOneRelations(qualifier, null, fetch);
  }

  public NSArray<er.sync.eo.ERSyncChangeToOne> toOneRelations(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<er.sync.eo.ERSyncChangeToOne> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(er.sync.eo.ERSyncChangeToOne.TO_ONE_VALUE_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = er.sync.eo.ERSyncChangeToOne.fetchERSyncChangeToOnes(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = toOneRelations();
      if (qualifier != null) {
        results = (NSArray<er.sync.eo.ERSyncChangeToOne>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<er.sync.eo.ERSyncChangeToOne>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToToOneRelationsRelationship(er.sync.eo.ERSyncChangeToOne object) {
    if (_ERSyncEntity.LOG.isDebugEnabled()) {
      _ERSyncEntity.LOG.debug("adding " + object + " to toOneRelations relationship");
    }
    addObjectToBothSidesOfRelationshipWithKey(object, "toOneRelations");
  }

  public void removeFromToOneRelationsRelationship(er.sync.eo.ERSyncChangeToOne object) {
    if (_ERSyncEntity.LOG.isDebugEnabled()) {
      _ERSyncEntity.LOG.debug("removing " + object + " from toOneRelations relationship");
    }
    removeObjectFromBothSidesOfRelationshipWithKey(object, "toOneRelations");
  }

  public er.sync.eo.ERSyncChangeToOne createToOneRelationsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ERSyncChangeToOne");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "toOneRelations");
    return (er.sync.eo.ERSyncChangeToOne) eo;
  }

  public void deleteToOneRelationsRelationship(er.sync.eo.ERSyncChangeToOne object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "toOneRelations");
    editingContext().deleteObject(object);
  }

  public void deleteAllToOneRelationsRelationships() {
    Enumeration objects = toOneRelations().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteToOneRelationsRelationship((er.sync.eo.ERSyncChangeToOne)objects.nextElement());
    }
  }


  public static ERSyncEntity createERSyncEntity(EOEditingContext editingContext, String status
, NSTimestamp updatedDate
) {
    ERSyncEntity eo = (ERSyncEntity) EOUtilities.createAndInsertInstance(editingContext, _ERSyncEntity.ENTITY_NAME);    
		eo.setStatus(status);
		eo.setUpdatedDate(updatedDate);
    return eo;
  }

  public static NSArray<ERSyncEntity> fetchAllERSyncEntities(EOEditingContext editingContext) {
    return _ERSyncEntity.fetchAllERSyncEntities(editingContext, null);
  }

  public static NSArray<ERSyncEntity> fetchAllERSyncEntities(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _ERSyncEntity.fetchERSyncEntities(editingContext, null, sortOrderings);
  }

  public static NSArray<ERSyncEntity> fetchERSyncEntities(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ERSyncEntity.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<ERSyncEntity> eoObjects = (NSArray<ERSyncEntity>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ERSyncEntity fetchERSyncEntity(EOEditingContext editingContext, String keyName, Object value) {
    return _ERSyncEntity.fetchERSyncEntity(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ERSyncEntity fetchERSyncEntity(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<ERSyncEntity> eoObjects = _ERSyncEntity.fetchERSyncEntities(editingContext, qualifier, null);
    ERSyncEntity eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ERSyncEntity)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ERSyncEntity that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ERSyncEntity fetchRequiredERSyncEntity(EOEditingContext editingContext, String keyName, Object value) {
    return _ERSyncEntity.fetchRequiredERSyncEntity(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ERSyncEntity fetchRequiredERSyncEntity(EOEditingContext editingContext, EOQualifier qualifier) {
    ERSyncEntity eoObject = _ERSyncEntity.fetchERSyncEntity(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ERSyncEntity that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ERSyncEntity localInstanceIn(EOEditingContext editingContext, ERSyncEntity eo) {
    ERSyncEntity localInstance = (eo == null) ? null : (ERSyncEntity)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
