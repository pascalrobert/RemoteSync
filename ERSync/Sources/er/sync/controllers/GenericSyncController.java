package er.sync.controllers;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WORequest;
import com.webobjects.appserver.WOResponse;
import com.webobjects.eoaccess.EOAttribute;
import com.webobjects.eoaccess.EOEntity;
import com.webobjects.eoaccess.EOProperty;
import com.webobjects.eoaccess.EORelationship;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOKeyGlobalID;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSLog;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSTimestamp;

import er.rest.ERXRestRequestNode;
import er.rest.ERXRestUtils;
import er.rest.routes.ERXDefaultRouteController;
import er.sync.ERXSyncConstants;
import er.sync.ERXSyncHandler;
import er.sync.ERXSyncUtilities;
import er.sync.api.ERXSyncAuthenticator;
import er.sync.components.ErrorResponse;
import er.sync.eo.ERSyncChangeValue;
import er.sync.eo.ERSyncChangeset;
import er.sync.eo.ERSyncEntity;
import er.sync.eo.ERSyncPrincipal;

/**
 * Once the client has registered the remote device for synchronization, we no longer receive username/password.  
 * Instead we receive a uuid identifiers for
 * developerId - a unique identifier for a client developer 
 * applicationId - a unique identifier for the client app (in case the same developer has multiple apps)
 * instanceId - a unique identifier for the 
 */
public abstract class GenericSyncController extends ERXDefaultRouteController 
{
	private ERSyncPrincipal principal;

	public GenericSyncController( WORequest request )
	{
		super( request );
		initAuthentication();
	}
	
	@SuppressWarnings("unchecked")
	protected void initAuthentication() 
	{
		ERXRestRequestNode principalUUID = requestNode().childNamed(ERSyncPrincipal.PRINCIPAL_UUID_KEY);
		if( principalUUID != null )
		{
			try {
				setPrincipal( ERSyncPrincipal.principalForUUID( (String)principalUUID.value(), editingContext()) );
			} catch ( Exception e ) {
				log.error( "Could not decode basic auth data: " + e.getMessage() );
				e.printStackTrace();
			}
		}
	}
	
	public ERXSyncAuthenticator authorizer()
	{
		return ((ERXSyncHandler)requestHandler()).syncAuthenticator();
	}

	
	@Override
	protected void checkAccess() throws SecurityException 
	{
		if (principal() == null ) 
		{
			NSLog.debug.appendln("Route " + route() );

			throw new SecurityException();
		}

		super.checkAccess();
	}

	
	protected WOActionResults errorPageForStatusAndMessage( int status, String message )
	{
		ErrorResponse error = pageWithName( ErrorResponse.class );
		WOResponse response = error.generateResponse();
		if( message != null ) 
		{
			response.appendHeader( message, "X-Restnotes-Error" );
		}
		response.setStatus( status );
		return response;
	}

	public void setPrincipal( ERSyncPrincipal principal ) 
	{
		this.principal = principal;
	}

	public ERSyncPrincipal principal() 
	{
		return principal;
	}

	public NSArray<ERSyncEntity> updateSyncEntityArray(NSArray<EOKeyGlobalID> gidArray)
	{
		NSMutableArray<ERSyncEntity> syncList = new NSMutableArray<ERSyncEntity>();
		
		for ( EOKeyGlobalID gid : gidArray )
		{
			syncList.addObject(updateSyncEntity(gid));
		}
		editingContext().saveChanges();

		return syncList;
	}

	public ERSyncEntity updateSyncEntity(EOKeyGlobalID gid)
	{
		ERSyncEntity syncEntity = ERSyncEntity.findOrCreateForGID(editingContext(), gid);
		if ( syncEntity.authReferences().containsObject(principal().authReference()) == false )
		{
			syncEntity.addToAuthReferencesRelationship(principal().authReference());
		}
		return syncEntity;
	}

	public ERXRestRequestNode syncStatusForPrincipal()
	{
		ERXRestRequestNode statusSyncList = new ERXRestRequestNode(ERXSyncConstants.STATUS, null, false);
		ERSyncPrincipal p = principal();
		String uid = p.principalUUID();
		NSTimestamp ts = p.lastSync();
		statusSyncList.addChild(new ERXRestRequestNode(ERSyncPrincipal.PRINCIPAL_UUID_KEY, uid, false));
		statusSyncList.addChild(new ERXRestRequestNode(ERSyncPrincipal.LAST_SYNC_KEY, ts, false));
		return statusSyncList;
	}

	public ERXRestRequestNode syncIndexNodeForEntity(ERSyncEntity syncEntity)
    {
		ERXRestRequestNode dataNode = new ERXRestRequestNode( ERXSyncUtilities.entityNameForOwnerHashCode( syncEntity.dataSourceToken()), null, false);
		dataNode.setID(syncEntity.uuid());
		dataNode.setAttributeForKey(syncEntity.lastUpdatedDate(), ERSyncEntity.UPDATED_DATE_KEY);
		dataNode.setAttributeForKey(syncEntity.statusForClient(), ERSyncEntity.STATUS_KEY);
		return dataNode;
    }

	public ERXRestRequestNode syncPropertyNodeForEO( EOProperty property, EOEnterpriseObject eo )
	{
		ERXRestRequestNode propertyNode = null;
		if ( property instanceof EOAttribute )
		{
			propertyNode = new ERXRestRequestNode(property.name(), eo.valueForKey(property.name()), false);
		}
		else if ( property instanceof EORelationship )
		{
			EORelationship rel = (EORelationship)property;
            if (authorizer().syncEntityNames().containsObject(rel.destinationEntity().name()) == true )
            {
            	propertyNode = new ERXRestRequestNode(property.name(), null, false);

				if ( rel.isToMany() == false )
				{
            		EOEnterpriseObject relatedEO = (EOEnterpriseObject)eo.valueForKey(rel.name());
            		if ( relatedEO != null )
            		{
		            	ERSyncEntity relatedEntity = ERSyncEntity.findOrCreateForEO(editingContext(), relatedEO);
		            	propertyNode.addChild(syncIndexNodeForEntity(relatedEntity));
            		}
				}
            	else
            	{
            		NSArray list = (NSArray)eo.valueForKey(rel.name());
    				NSArray<ERSyncEntity> syncEntityList = ERSyncEntity.findOrCreateForEOArray(editingContext(), list);
    				for (ERSyncEntity relatedEntity : syncEntityList)
    				{
    					propertyNode.addChild(syncIndexNodeForEntity(relatedEntity));
    				}
            	}
            }
		}

		return propertyNode;
	}
	
	public ERXRestRequestNode syncPropertyNodeForChangeValue( ERSyncChangeValue cv)
    {
		ERXRestRequestNode propertyNode = null;
		Object value = cv.changeValue();
		if ( value instanceof NSArray )
		{
			propertyNode = new ERXRestRequestNode(cv.attributeName(), null, false);

			NSArray<ERSyncEntity> syncEntityList = (NSArray<ERSyncEntity>)value;
			for (ERSyncEntity relatedEntity : syncEntityList)
			{
				propertyNode.addChild(syncIndexNodeForEntity(relatedEntity));
			}
		}
		else if ( value instanceof ERSyncEntity )
		{
			propertyNode = new ERXRestRequestNode(cv.attributeName(), null, false);
			propertyNode.addChild(syncIndexNodeForEntity((ERSyncEntity)value));
		}
		else
		{
			propertyNode = new ERXRestRequestNode(cv.attributeName(), value, false);
		}

		return propertyNode;
    }

	
	public ERXRestRequestNode syncDeltaDataNodeForEntity(EOEntity eoEntity, ERSyncEntity syncEntity, NSTimestamp lastSync)
    {
		ERXRestRequestNode deltaDataNode = new ERXRestRequestNode(eoEntity.name(), null, false);
		deltaDataNode.setID(syncEntity.uuid());
		deltaDataNode.setAttributeForKey(syncEntity.statusForClient(), ERSyncEntity.STATUS_KEY);
		
		// this is the same as if it was inserted, just get the data from the master
		if ( syncEntity.status().equals(ERSyncEntity.VIRGIN) == true )
		{
			EOEnterpriseObject eo = syncEntity.enterpriseObject();
			NSArray<String> attributeNames = (NSArray<String>)eoEntity.classPropertyNames();
			for ( String attrName : attributeNames )
			{
				EOAttribute attr = eoEntity.attributeNamed(attrName);
				ERXRestRequestNode node = syncPropertyNodeForEO(attr, eo);
				if (node != null)
					deltaDataNode.addChild(node);
			}
		}
		else
		{
			NSArray<ERSyncChangeValue> changes = syncEntity.changeValues();
			for ( ERSyncChangeValue cv : changes )
			{
				ERSyncChangeset changeset = cv.changeset();
				if ( changeset.updatedDate().after(lastSync) == true )
				{
					deltaDataNode.addChild( syncPropertyNodeForChangeValue(cv));
				}
			}
		}
		return deltaDataNode;
    }

	public ERXRestRequestNode syncFullDataNodeForEntity(EOEntity eoEntity, ERSyncEntity syncEntity)
	{
		ERXRestRequestNode fulDataNode = new ERXRestRequestNode(eoEntity.name(), null, false);
		fulDataNode.setID(syncEntity.uuid());
		fulDataNode.setAttributeForKey(syncEntity.statusForClient(), ERSyncEntity.STATUS_KEY);
		
		if ( syncEntity.status().equals(ERSyncEntity.VIRGIN) == true )
		{
			EOEnterpriseObject eo = syncEntity.enterpriseObject();
			NSArray<String> attributeNames = (NSArray<String>)eoEntity.classPropertyNames();
			for ( String attrName : attributeNames )
			{
				EOProperty attr = eoEntity.propertyNamed(attrName);
				ERXRestRequestNode node = syncPropertyNodeForEO(attr, eo);
				if (node != null)
					fulDataNode.addChild(node);
			}
		}
		else
		{
			NSArray<ERSyncChangeValue> changes = syncEntity.changeValues();
			for ( ERSyncChangeValue cv : changes )
			{
				fulDataNode.addChild( syncPropertyNodeForChangeValue(cv));
			}
		}
		return fulDataNode;
	}

	@Override
    public WOActionResults createAction() throws Throwable
    {
	    return null;
    }

	@Override
    public WOActionResults destroyAction() throws Throwable
    {
	    return null;
    }

	@Override
    public WOActionResults indexAction() throws Throwable
    {
	    return null;
    }

	@Override
    public WOActionResults newAction() throws Throwable
    {
	    return null;
    }

	@Override
    public WOActionResults showAction() throws Throwable
    {
	    return null;
    }

	@Override
    public WOActionResults updateAction() throws Throwable
    {
	    return null;
    }
}
