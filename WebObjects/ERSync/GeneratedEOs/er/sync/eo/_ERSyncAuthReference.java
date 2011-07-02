// $LastChangedRevision: 5773 $ DO NOT EDIT.  Make changes to ERSyncAuthReference.java instead.
package er.sync.eo;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _ERSyncAuthReference extends  EOGenericRecord {
	public static final String ENTITY_NAME = "ERSyncAuthReference";

	// Attributes
	public static final String DATA_SOURCE_TOKEN_KEY = "dataSourceToken";
	public static final String NAME_KEY = "name";
	public static final String UUID_KEY = "uuid";

	// Relationships
	public static final String CHANGESETS_KEY = "changesets";
	public static final String ENTITY_RECORDS_KEY = "entityRecords";
	public static final String PRINCIPALS_KEY = "principals";

  private static Logger LOG = Logger.getLogger(_ERSyncAuthReference.class);

  public ERSyncAuthReference localInstanceIn(EOEditingContext editingContext) {
    ERSyncAuthReference localInstance = (ERSyncAuthReference)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public String dataSourceToken() {
    return (String) storedValueForKey("dataSourceToken");
  }

  public void setDataSourceToken(String value) {
    if (_ERSyncAuthReference.LOG.isDebugEnabled()) {
    	_ERSyncAuthReference.LOG.debug( "updating dataSourceToken from " + dataSourceToken() + " to " + value);
    }
    takeStoredValueForKey(value, "dataSourceToken");
  }

  public String name() {
    return (String) storedValueForKey("name");
  }

  public void setName(String value) {
    if (_ERSyncAuthReference.LOG.isDebugEnabled()) {
    	_ERSyncAuthReference.LOG.debug( "updating name from " + name() + " to " + value);
    }
    takeStoredValueForKey(value, "name");
  }

  public String uuid() {
    return (String) storedValueForKey("uuid");
  }

  public void setUuid(String value) {
    if (_ERSyncAuthReference.LOG.isDebugEnabled()) {
    	_ERSyncAuthReference.LOG.debug( "updating uuid from " + uuid() + " to " + value);
    }
    takeStoredValueForKey(value, "uuid");
  }

  public NSArray<er.sync.eo.ERSyncChangeset> changesets() {
    return (NSArray<er.sync.eo.ERSyncChangeset>)storedValueForKey("changesets");
  }

  public NSArray<er.sync.eo.ERSyncChangeset> changesets(EOQualifier qualifier) {
    return changesets(qualifier, null, false);
  }

  public NSArray<er.sync.eo.ERSyncChangeset> changesets(EOQualifier qualifier, boolean fetch) {
    return changesets(qualifier, null, fetch);
  }

  public NSArray<er.sync.eo.ERSyncChangeset> changesets(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings, boolean fetch) {
    NSArray<er.sync.eo.ERSyncChangeset> results;
    if (fetch) {
      EOQualifier fullQualifier;
      EOQualifier inverseQualifier = new EOKeyValueQualifier(er.sync.eo.ERSyncChangeset.AUTH_REFERENCE_KEY, EOQualifier.QualifierOperatorEqual, this);
    	
      if (qualifier == null) {
        fullQualifier = inverseQualifier;
      }
      else {
        NSMutableArray qualifiers = new NSMutableArray();
        qualifiers.addObject(qualifier);
        qualifiers.addObject(inverseQualifier);
        fullQualifier = new EOAndQualifier(qualifiers);
      }

      results = er.sync.eo.ERSyncChangeset.fetchERSyncChangesets(editingContext(), fullQualifier, sortOrderings);
    }
    else {
      results = changesets();
      if (qualifier != null) {
        results = (NSArray<er.sync.eo.ERSyncChangeset>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<er.sync.eo.ERSyncChangeset>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    }
    return results;
  }
  
  public void addToChangesetsRelationship(er.sync.eo.ERSyncChangeset object) {
    if (_ERSyncAuthReference.LOG.isDebugEnabled()) {
      _ERSyncAuthReference.LOG.debug("adding " + object + " to changesets relationship");
    }
    addObjectToBothSidesOfRelationshipWithKey(object, "changesets");
  }

  public void removeFromChangesetsRelationship(er.sync.eo.ERSyncChangeset object) {
    if (_ERSyncAuthReference.LOG.isDebugEnabled()) {
      _ERSyncAuthReference.LOG.debug("removing " + object + " from changesets relationship");
    }
    removeObjectFromBothSidesOfRelationshipWithKey(object, "changesets");
  }

  public er.sync.eo.ERSyncChangeset createChangesetsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ERSyncChangeset");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "changesets");
    return (er.sync.eo.ERSyncChangeset) eo;
  }

  public void deleteChangesetsRelationship(er.sync.eo.ERSyncChangeset object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "changesets");
    editingContext().deleteObject(object);
  }

  public void deleteAllChangesetsRelationships() {
    Enumeration objects = changesets().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteChangesetsRelationship((er.sync.eo.ERSyncChangeset)objects.nextElement());
    }
  }

  public NSArray<er.sync.eo.ERSyncEntity> entityRecords() {
    return (NSArray<er.sync.eo.ERSyncEntity>)storedValueForKey("entityRecords");
  }

  public NSArray<er.sync.eo.ERSyncEntity> entityRecords(EOQualifier qualifier) {
    return entityRecords(qualifier, null);
  }

  public NSArray<er.sync.eo.ERSyncEntity> entityRecords(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    NSArray<er.sync.eo.ERSyncEntity> results;
      results = entityRecords();
      if (qualifier != null) {
        results = (NSArray<er.sync.eo.ERSyncEntity>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<er.sync.eo.ERSyncEntity>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToEntityRecordsRelationship(er.sync.eo.ERSyncEntity object) {
    if (_ERSyncAuthReference.LOG.isDebugEnabled()) {
      _ERSyncAuthReference.LOG.debug("adding " + object + " to entityRecords relationship");
    }
    addObjectToBothSidesOfRelationshipWithKey(object, "entityRecords");
  }

  public void removeFromEntityRecordsRelationship(er.sync.eo.ERSyncEntity object) {
    if (_ERSyncAuthReference.LOG.isDebugEnabled()) {
      _ERSyncAuthReference.LOG.debug("removing " + object + " from entityRecords relationship");
    }
    removeObjectFromBothSidesOfRelationshipWithKey(object, "entityRecords");
  }

  public er.sync.eo.ERSyncEntity createEntityRecordsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ERSyncEntity");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "entityRecords");
    return (er.sync.eo.ERSyncEntity) eo;
  }

  public void deleteEntityRecordsRelationship(er.sync.eo.ERSyncEntity object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "entityRecords");
    editingContext().deleteObject(object);
  }

  public void deleteAllEntityRecordsRelationships() {
    Enumeration objects = entityRecords().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteEntityRecordsRelationship((er.sync.eo.ERSyncEntity)objects.nextElement());
    }
  }

  public NSArray<er.sync.eo.ERSyncPrincipal> principals() {
    return (NSArray<er.sync.eo.ERSyncPrincipal>)storedValueForKey("principals");
  }

  public NSArray<er.sync.eo.ERSyncPrincipal> principals(EOQualifier qualifier) {
    return principals(qualifier, null);
  }

  public NSArray<er.sync.eo.ERSyncPrincipal> principals(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    NSArray<er.sync.eo.ERSyncPrincipal> results;
      results = principals();
      if (qualifier != null) {
        results = (NSArray<er.sync.eo.ERSyncPrincipal>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<er.sync.eo.ERSyncPrincipal>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToPrincipalsRelationship(er.sync.eo.ERSyncPrincipal object) {
    if (_ERSyncAuthReference.LOG.isDebugEnabled()) {
      _ERSyncAuthReference.LOG.debug("adding " + object + " to principals relationship");
    }
    addObjectToBothSidesOfRelationshipWithKey(object, "principals");
  }

  public void removeFromPrincipalsRelationship(er.sync.eo.ERSyncPrincipal object) {
    if (_ERSyncAuthReference.LOG.isDebugEnabled()) {
      _ERSyncAuthReference.LOG.debug("removing " + object + " from principals relationship");
    }
    removeObjectFromBothSidesOfRelationshipWithKey(object, "principals");
  }

  public er.sync.eo.ERSyncPrincipal createPrincipalsRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ERSyncPrincipal");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "principals");
    return (er.sync.eo.ERSyncPrincipal) eo;
  }

  public void deletePrincipalsRelationship(er.sync.eo.ERSyncPrincipal object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "principals");
    editingContext().deleteObject(object);
  }

  public void deleteAllPrincipalsRelationships() {
    Enumeration objects = principals().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deletePrincipalsRelationship((er.sync.eo.ERSyncPrincipal)objects.nextElement());
    }
  }


  public static ERSyncAuthReference createERSyncAuthReference(EOEditingContext editingContext, String dataSourceToken
, String name
) {
    ERSyncAuthReference eo = (ERSyncAuthReference) EOUtilities.createAndInsertInstance(editingContext, _ERSyncAuthReference.ENTITY_NAME);    
		eo.setDataSourceToken(dataSourceToken);
		eo.setName(name);
    return eo;
  }

  public static NSArray<ERSyncAuthReference> fetchAllERSyncAuthReferences(EOEditingContext editingContext) {
    return _ERSyncAuthReference.fetchAllERSyncAuthReferences(editingContext, null);
  }

  public static NSArray<ERSyncAuthReference> fetchAllERSyncAuthReferences(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _ERSyncAuthReference.fetchERSyncAuthReferences(editingContext, null, sortOrderings);
  }

  public static NSArray<ERSyncAuthReference> fetchERSyncAuthReferences(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ERSyncAuthReference.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<ERSyncAuthReference> eoObjects = (NSArray<ERSyncAuthReference>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ERSyncAuthReference fetchERSyncAuthReference(EOEditingContext editingContext, String keyName, Object value) {
    return _ERSyncAuthReference.fetchERSyncAuthReference(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ERSyncAuthReference fetchERSyncAuthReference(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<ERSyncAuthReference> eoObjects = _ERSyncAuthReference.fetchERSyncAuthReferences(editingContext, qualifier, null);
    ERSyncAuthReference eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ERSyncAuthReference)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ERSyncAuthReference that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ERSyncAuthReference fetchRequiredERSyncAuthReference(EOEditingContext editingContext, String keyName, Object value) {
    return _ERSyncAuthReference.fetchRequiredERSyncAuthReference(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ERSyncAuthReference fetchRequiredERSyncAuthReference(EOEditingContext editingContext, EOQualifier qualifier) {
    ERSyncAuthReference eoObject = _ERSyncAuthReference.fetchERSyncAuthReference(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ERSyncAuthReference that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ERSyncAuthReference localInstanceIn(EOEditingContext editingContext, ERSyncAuthReference eo) {
    ERSyncAuthReference localInstance = (eo == null) ? null : (ERSyncAuthReference)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
