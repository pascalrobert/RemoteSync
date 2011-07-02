package er.sync.eo;

import org.apache.log4j.Logger;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOKeyGlobalID;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSComparator;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableSet;
import com.webobjects.foundation.NSTimestamp;

import er.extensions.foundation.ERXArrayUtilities;
import er.sync.ERXSyncConstants;
import er.sync.ERXSyncUtilities;

public class ERSyncEntity extends _ERSyncEntity 
{
	public static String INSERTED = "I";
	public static String UPDATED = "U";
	public static String DELETED = "D";
	public static String VIRGIN = "V";
	
	private static Logger log = Logger.getLogger(ERSyncEntity.class);

	public void awakeFromInsertion( EOEditingContext ec ) 
	{
		super.awakeFromInsertion( ec );
		setStatus( VIRGIN );
		setUpdatedDate(new NSTimestamp());
	}

	/* finds an existing ERSyncEntity for a given EOEnterpriseObject, or creates one as needed */
	@SuppressWarnings("unchecked")
    public static ERSyncEntity findOrCreateForToken( EOEditingContext ec, String token)
	{
		ERSyncEntity syncEntity = ERSyncEntity.fetchERSyncEntity(ec, ERSyncEntity.DATA_SOURCE_TOKEN_KEY, token);
		if ( syncEntity == null )
		{
			NSArray possibleInserts = ERXArrayUtilities.filteredArrayWithQualifierEvaluation(ec.insertedObjects(), new ERXSyncUtilities.EOEntityFilter(ERSyncEntity.ENTITY_NAME) );
			if ( possibleInserts != null )
			{
				// check the inserted objects
				syncEntity = (ERSyncEntity)ERXArrayUtilities.firstObjectWithValueForKeyPath(possibleInserts, token, ERSyncEntity.DATA_SOURCE_TOKEN_KEY);
			}
			
			if ( syncEntity == null )
			{
				syncEntity = ERSyncEntity.createERSyncEntity(ec, ERSyncEntity.VIRGIN, new NSTimestamp());
				syncEntity.setDataSourceToken( token );
				syncEntity.setUuid( token );
			}
		}

		return syncEntity;
	}

	/* finds an existing ERSyncEntity for a given EOEnterpriseObject, or creates one as needed */
	public static ERSyncEntity findOrCreateForGID( EOEditingContext ec, EOKeyGlobalID gid )
	{
		return findOrCreateForToken(ec, ERXSyncUtilities.syncGIDHashCode(gid));
	}

	/* finds an existing ERSyncEntity for a given EOEnterpriseObject, or creates one as needed */
	public static ERSyncEntity findOrCreateForEO( EOEditingContext ec, EOEnterpriseObject eo )
	{
		return findOrCreateForToken(ec, ERXSyncUtilities.syncEOHashCode(eo));
	}

	/* finds an existing ERSyncEntity for a given EOEnterpriseObject, or creates one as needed */
	public static NSArray<ERSyncEntity> findOrCreateForEOArray( EOEditingContext ec, NSArray eoArray )
	{
		NSMutableArray<ERSyncEntity> list = new NSMutableArray<ERSyncEntity>();
		for ( Object obj : eoArray )
		{
			list.addObject( findOrCreateForEO( ec, (EOEnterpriseObject)obj ));
		}
		return list;
	}

	/* fetchs to match qualifier with refreshed objects */
	@SuppressWarnings("unchecked")
	public static NSArray<ERSyncEntity> refreshERSyncEntities(EOEditingContext editingContext, EOQualifier qualifier, NSArray<EOSortOrdering> sortOrderings) 
	{
		EOFetchSpecification fetchSpec = new EOFetchSpecification(_ERSyncEntity.ENTITY_NAME, qualifier, sortOrderings);
		fetchSpec.setRefreshesRefetchedObjects(true);
		fetchSpec.setIsDeep(true);
		return (NSArray<ERSyncEntity>)editingContext.objectsWithFetchSpecification(fetchSpec);
	}

	public EOEnterpriseObject enterpriseObject()
	{
		return (EOEnterpriseObject) ERXSyncUtilities.FaultForOwnerHashCode(editingContext(), dataSourceToken());
	}
	
	public NSArray<ERSyncChangeset> distinctChangeset()
	{
		NSMutableSet<ERSyncChangeset> set = new NSMutableSet<ERSyncChangeset>();
		for ( ERSyncChangeValue cv : changeValues() )
		{
			if ( set.containsObject(cv.changeset()) == false )
				set.addObject(cv.changeset());
		}
		return set.allObjects();
	}
	
	public NSTimestamp lastUpdatedDate()
	{
		NSTimestamp timestamp = null;
		
		NSArray<ERSyncChangeset> changesetList =  distinctChangeset();
		for ( ERSyncChangeset cs : changesetList )
		{
			if ((timestamp == null) || (timestamp.compare(cs.updatedDate()) == NSComparator.OrderedDescending))
				timestamp = cs.updatedDate();
		}
		
		return timestamp;
	}

	public String statusForClient()
    {
		String statusForClient = ERXSyncConstants.CLIENT_INSERT;
		
		if (status().equals(DELETED) == true)
		{
			statusForClient = ERXSyncConstants.CLIENT_DELETE;
		}
		else if (status().equals(UPDATED) == true)
		{
			statusForClient = ERXSyncConstants.CLIENT_UPDATE;
		}
	    return statusForClient;
    }
}
