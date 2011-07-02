// $LastChangedRevision: 5773 $ DO NOT EDIT.  Make changes to ERSyncChangeToMany.java instead.
package er.sync.eo;

import com.webobjects.eoaccess.*;
import com.webobjects.eocontrol.*;
import com.webobjects.foundation.*;
import java.math.*;
import java.util.*;
import org.apache.log4j.Logger;

@SuppressWarnings("all")
public abstract class _ERSyncChangeToMany extends er.sync.eo.ERSyncChangeValue {
	public static final String ENTITY_NAME = "ERSyncChangeToMany";

	// Attributes
	public static final String ATTRIBUTE_NAME_KEY = "attributeName";
	public static final String VALUE_TYPE_KEY = "valueType";

	// Relationships
	public static final String CHANGE_ENTITY_KEY = "changeEntity";
	public static final String CHANGESET_KEY = "changeset";
	public static final String TO_MANY_ENTITIES_KEY = "toManyEntities";

  private static Logger LOG = Logger.getLogger(_ERSyncChangeToMany.class);

  public ERSyncChangeToMany localInstanceIn(EOEditingContext editingContext) {
    ERSyncChangeToMany localInstance = (ERSyncChangeToMany)EOUtilities.localInstanceOfObject(editingContext, this);
    if (localInstance == null) {
      throw new IllegalStateException("You attempted to localInstance " + this + ", which has not yet committed.");
    }
    return localInstance;
  }

  public NSArray<er.sync.eo.ERSyncEntity> toManyEntities() {
    return (NSArray<er.sync.eo.ERSyncEntity>)storedValueForKey("toManyEntities");
  }

  public NSArray<er.sync.eo.ERSyncEntity> toManyEntities(EOQualifier qualifier) {
    return toManyEntities(qualifier, null);
  }

  public NSArray<er.sync.eo.ERSyncEntity> toManyEntities(EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    NSArray<er.sync.eo.ERSyncEntity> results;
      results = toManyEntities();
      if (qualifier != null) {
        results = (NSArray<er.sync.eo.ERSyncEntity>)EOQualifier.filteredArrayWithQualifier(results, qualifier);
      }
      if (sortOrderings != null) {
        results = (NSArray<er.sync.eo.ERSyncEntity>)EOSortOrdering.sortedArrayUsingKeyOrderArray(results, sortOrderings);
      }
    return results;
  }
  
  public void addToToManyEntitiesRelationship(er.sync.eo.ERSyncEntity object) {
    if (_ERSyncChangeToMany.LOG.isDebugEnabled()) {
      _ERSyncChangeToMany.LOG.debug("adding " + object + " to toManyEntities relationship");
    }
    addObjectToBothSidesOfRelationshipWithKey(object, "toManyEntities");
  }

  public void removeFromToManyEntitiesRelationship(er.sync.eo.ERSyncEntity object) {
    if (_ERSyncChangeToMany.LOG.isDebugEnabled()) {
      _ERSyncChangeToMany.LOG.debug("removing " + object + " from toManyEntities relationship");
    }
    removeObjectFromBothSidesOfRelationshipWithKey(object, "toManyEntities");
  }

  public er.sync.eo.ERSyncEntity createToManyEntitiesRelationship() {
    EOClassDescription eoClassDesc = EOClassDescription.classDescriptionForEntityName("ERSyncEntity");
    EOEnterpriseObject eo = eoClassDesc.createInstanceWithEditingContext(editingContext(), null);
    editingContext().insertObject(eo);
    addObjectToBothSidesOfRelationshipWithKey(eo, "toManyEntities");
    return (er.sync.eo.ERSyncEntity) eo;
  }

  public void deleteToManyEntitiesRelationship(er.sync.eo.ERSyncEntity object) {
    removeObjectFromBothSidesOfRelationshipWithKey(object, "toManyEntities");
    editingContext().deleteObject(object);
  }

  public void deleteAllToManyEntitiesRelationships() {
    Enumeration objects = toManyEntities().immutableClone().objectEnumerator();
    while (objects.hasMoreElements()) {
      deleteToManyEntitiesRelationship((er.sync.eo.ERSyncEntity)objects.nextElement());
    }
  }


  public static ERSyncChangeToMany createERSyncChangeToMany(EOEditingContext editingContext, String attributeName
, er.sync.eo.ERSyncEntity changeEntity, er.sync.eo.ERSyncChangeset changeset) {
    ERSyncChangeToMany eo = (ERSyncChangeToMany) EOUtilities.createAndInsertInstance(editingContext, _ERSyncChangeToMany.ENTITY_NAME);    
		eo.setAttributeName(attributeName);
    eo.setChangeEntityRelationship(changeEntity);
    eo.setChangesetRelationship(changeset);
    return eo;
  }

  public static NSArray<ERSyncChangeToMany> fetchAllERSyncChangeToManies(EOEditingContext editingContext) {
    return _ERSyncChangeToMany.fetchAllERSyncChangeToManies(editingContext, null);
  }

  public static NSArray<ERSyncChangeToMany> fetchAllERSyncChangeToManies(EOEditingContext editingContext, NSArray<EOSortOrdering> sortOrderings) {
    return _ERSyncChangeToMany.fetchERSyncChangeToManies(editingContext, null, sortOrderings);
  }

  public static NSArray<ERSyncChangeToMany> fetchERSyncChangeToManies(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) {
    EOFetchSpecification fetchSpec = new EOFetchSpecification(_ERSyncChangeToMany.ENTITY_NAME, qualifier, sortOrderings);
    fetchSpec.setIsDeep(true);
    NSArray<ERSyncChangeToMany> eoObjects = (NSArray<ERSyncChangeToMany>)editingContext.objectsWithFetchSpecification(fetchSpec);
    return eoObjects;
  }

  public static ERSyncChangeToMany fetchERSyncChangeToMany(EOEditingContext editingContext, String keyName, Object value) {
    return _ERSyncChangeToMany.fetchERSyncChangeToMany(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ERSyncChangeToMany fetchERSyncChangeToMany(EOEditingContext editingContext, EOQualifier qualifier) {
    NSArray<ERSyncChangeToMany> eoObjects = _ERSyncChangeToMany.fetchERSyncChangeToManies(editingContext, qualifier, null);
    ERSyncChangeToMany eoObject;
    int count = eoObjects.count();
    if (count == 0) {
      eoObject = null;
    }
    else if (count == 1) {
      eoObject = (ERSyncChangeToMany)eoObjects.objectAtIndex(0);
    }
    else {
      throw new IllegalStateException("There was more than one ERSyncChangeToMany that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ERSyncChangeToMany fetchRequiredERSyncChangeToMany(EOEditingContext editingContext, String keyName, Object value) {
    return _ERSyncChangeToMany.fetchRequiredERSyncChangeToMany(editingContext, new EOKeyValueQualifier(keyName, EOQualifier.QualifierOperatorEqual, value));
  }

  public static ERSyncChangeToMany fetchRequiredERSyncChangeToMany(EOEditingContext editingContext, EOQualifier qualifier) {
    ERSyncChangeToMany eoObject = _ERSyncChangeToMany.fetchERSyncChangeToMany(editingContext, qualifier);
    if (eoObject == null) {
      throw new NoSuchElementException("There was no ERSyncChangeToMany that matched the qualifier '" + qualifier + "'.");
    }
    return eoObject;
  }

  public static ERSyncChangeToMany localInstanceIn(EOEditingContext editingContext, ERSyncChangeToMany eo) {
    ERSyncChangeToMany localInstance = (eo == null) ? null : (ERSyncChangeToMany)EOUtilities.localInstanceOfObject(editingContext, eo);
    if (localInstance == null && eo != null) {
      throw new IllegalStateException("You attempted to localInstance " + eo + ", which has not yet committed.");
    }
    return localInstance;
  }
}
