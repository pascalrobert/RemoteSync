package er.sync.controllers;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WORequest;
import com.webobjects.eoaccess.EOAttribute;
import com.webobjects.eoaccess.EOEntity;
import com.webobjects.eoaccess.EOModelGroup;
import com.webobjects.eoaccess.EOProperty;
import com.webobjects.eoaccess.EORelationship;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOKeyGlobalID;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSKeyValueCoding;
import com.webobjects.foundation.NSLog;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSTimestamp;

import er.extensions.eof.ERXKey;
import er.extensions.eof.ERXKeyFilter;
import er.extensions.eof.ERXQ;
import er.extensions.foundation.ERXValueUtilities;
import er.rest.ERXRestRequestNode;
import er.rest.ERXRestUtils;
import er.sync.ERXSyncUtilities;
import er.sync.ERXSyncConstants;
import er.sync.eo.ERSyncChangeValue;
import er.sync.eo.ERSyncEntity;
import er.sync.eo.ERSyncPrincipal;

public class SlowController extends GenericSyncController
{

	public SlowController(WORequest request)
    {
	    super(request);
    }
	

	@Override
	protected void checkAccess() throws SecurityException 
	{
		ERXRestRequestNode lastSyncNode = requestNode().childNamed(ERSyncPrincipal.LAST_SYNC_KEY);
		if ( lastSyncNode == null )
		{
			throw new SecurityException("Last sync not identified, please use initial sync");
		}

		NSTimestamp lastSync = (NSTimestamp) ERXRestUtils.coerceValueToTypeNamed(lastSyncNode.value(), "NSTimestamp", null);
		if ( lastSync == null )
		{
			throw new SecurityException("Last sync not identified, please use initial sync");
		}

		if ( principal().lastSync() == null )
		{
			throw new SecurityException("No previous sync recorded, please use initial");
		}
		
		long pls = Math.abs( principal().lastSync().getTime() - lastSync.getTime() );
		if ( pls > 1000 )
		{
			throw new SecurityException("Last sync does not match records, please use initial." );
		}

		super.checkAccess();
	}


	// process changes from client
	public int processDataNodes(NSArray <ERXRestRequestNode> pushList)
	{
		int count = 0;
		NSMutableDictionary<String, EOEnterpriseObject> insertMap = new NSMutableDictionary<String, EOEnterpriseObject>();

		NSArray <String> syncEntityNames = authorizer().syncEntityNames();
		for ( ERXRestRequestNode push : pushList )
		{
			count++;
			if ( syncEntityNames.containsObject(push.name()) == false )
			{
				throw new SecurityException("Pushed item " + count + " is not a valid data type (" + push.name() + ")");
			}
			
			EOEntity eoEntity = EOModelGroup.defaultGroup().entityNamed( push.name() );
			if ( eoEntity == null )
			{
				throw new SecurityException("Pushed item " + count + " is not a valid data type (" + push.name() + ")");
			}

			String entityUUID = (String) push.id();
			if ( entityUUID == null )
			{
				throw new SecurityException("Pushed item " + count + " does not have valid id");
			}

			String status = (String) push.attributeForKey(ERSyncEntity.STATUS_KEY) ;
			if ( entityUUID == null )
			{
				throw new SecurityException("Pushed item " + count + " does not have valid status (insert, update, delete)");
			}

			ERSyncEntity syncEntity = ERSyncEntity.fetchERSyncEntity(editingContext(), ERSyncEntity.UUID_KEY, entityUUID);

			if ( status.equals(ERXSyncConstants.CLIENT_INSERT) == true )
			{
				if ( syncEntity != null )
				{
					throw new SecurityException("Pushed item " + count + " requests insert but already exists (" + entityUUID + ")");
				}
				
				NSMutableDictionary dict = new NSMutableDictionary();
				// if inserting then child nodes should match the attributes
				NSArray<String> attributeNames = (NSArray<String>)eoEntity.classPropertyNames();
				for ( String attrName : attributeNames )
				{
					ERXRestRequestNode attNode = push.childNamed(attrName);
					EOProperty attr = eoEntity.propertyNamed(attrName);
					if ( attNode != null )
					{
						Object val = resolveClientValue( attNode, attr );

						dict.setObjectForKey(val, attr.name());
					}
					else if ((attr instanceof EOAttribute) && (((EOAttribute)attr).allowsNull() == true ))
					{
						dict.setObjectForKey(NSKeyValueCoding.NullValue, attr.name());
					}
					else if (attr instanceof EORelationship) 
					{
						EORelationship relationship = (EORelationship)attr;
						if ((relationship.isMandatory() == true) && (authorizer().syncEntityNames().containsObject( relationship.destinationEntity().name()) == true ))
						{
							throw new SecurityException("Pushed item " + count + " missing relationship (" + attr.name() + ")");
						}
					}
					else
					{
						throw new SecurityException("Pushed item " + count + " missing attribute (" + attr.name() + ")");
					}
				}

				EOEnterpriseObject eo = authorizer().syncInsertObject( editingContext(), eoEntity, dict, principal().authReference().user() );
				// save the inserted list, we have to match up the client UUID to the new eo for the SyncEntity
				insertMap.setObjectForKey(eo, entityUUID);
			}
			else if ( status.equals(ERXSyncConstants.CLIENT_UPDATE) == true )
			{
				if ( syncEntity == null )
				{
					throw new SecurityException("Pushed item " + count + " requests update but no record found (" + entityUUID + ")");
				}
				
				// if the master record has been deleted, then ignore updates
				if ( syncEntity.status().equals(ERSyncEntity.DELETED) == false)
				{
					NSMutableDictionary dict = new NSMutableDictionary();
					// if inserting then child nodes should match the attributes
					NSArray<String> attributeNames = (NSArray<String>)eoEntity.classPropertyNames();
					for ( String attrName : attributeNames )
					{
						EOAttribute attr = eoEntity.attributeNamed(attrName);
						if ( attr != null )
						{
							ERXRestRequestNode attNode = push.childNamed(attr.name());
							if ( attNode != null )
							{
								Object val = NSKeyValueCoding.NullValue;
								if ( ERXValueUtilities.isNull(attNode.value()) == false )
								{
									val = ERXRestUtils.coerceValueToTypeNamed(attNode.value(), attr.valueTypeClassName(), null); 
								}
								
								dict.setObjectForKey(val, attr.name());
							}
						}
					}
					
					// fetch the master record
					EOEnterpriseObject eo = syncEntity.enterpriseObject(); 
					authorizer().syncUpdateObject( eo, dict, principal().authReference().user());
				}
			}
			else if ( status.equals(ERXSyncConstants.CLIENT_DELETE) == true )
			{
				// if master has no record of client delete item, then ignore
				if ( syncEntity != null )
				{
					// if the master record has been deleted, then ignore new delete
					if ( syncEntity.status().equals(ERSyncEntity.DELETED) == false)
					{
						// fetch the master record
						EOEnterpriseObject eo = syncEntity.enterpriseObject(); 
						authorizer().syncDeleteObject( eo, principal().authReference().user());
					}
				}
			}
		}
		
		// commit changes to the master records
		editingContext().saveChanges();
		
		// update the ERSyncEntities with the client UUID
		for (String uuid : insertMap.allKeys())
		{
			EOEnterpriseObject eo = insertMap.objectForKey(uuid);
			ERSyncEntity syncEntity = updateSyncEntity((EOKeyGlobalID)ERXSyncUtilities.globalID(eo));
			syncEntity.setUuid(uuid);
		}

		// commit changes to the entity mapping
		editingContext().saveChanges();

		return count;
	}
	
	public Object resolveClientValue(ERXRestRequestNode attNode, EOProperty attr)
    {
		Object val = NSKeyValueCoding.NullValue;
		if ( attr instanceof EOAttribute )
		{
			if ( ERXValueUtilities.isNull(attNode.value()) == false )
			{
				val = ERXRestUtils.coerceValueToTypeNamed(attNode.value(), ((EOAttribute)attr).valueTypeClassName(), null);
			}
		}
		else if ( attr instanceof EORelationship )
		{
			EORelationship rel = (EORelationship)attr;
            if (authorizer().syncEntityNames().containsObject(rel.destinationEntity().name()) == true )
            {
        		NSArray<ERXRestRequestNode> children = attNode.children();
            	if ( ((EORelationship)attr).isToMany() == false )
				{
            		if ((children == null) || (children.count() != 1))
            		{
            			throw new RuntimeException("Failed to sync relationship to-one record '" + children + "'");
            		}
            		
            		ERXRestRequestNode child = children.lastObject();
            		if ( ERXValueUtilities.isNull(child.id()) == false )
            		{
	            		ERSyncEntity relatedEntity = ERSyncEntity.fetchERSyncEntity(editingContext(), ERSyncEntity.UUID_KEY, child.id());
	            		if ( relatedEntity != null )
	            		{
	            			val = relatedEntity.enterpriseObject();
	            		}
	            		else
	            		{
	            			throw new RuntimeException("Failed to sync relationship to invalid record '" + child.id() + "'");
	            		}
            		}
				}
            	else
            	{
            		// all the related children should identify ERSyncEntity
            		NSMutableArray<EOEnterpriseObject> working = new NSMutableArray<EOEnterpriseObject>();
            		
            		for ( ERXRestRequestNode relNode : children )
            		{
                		if ( ERXValueUtilities.isNull(relNode.id()) == false )
                		{
    	            		ERSyncEntity relatedEntity = ERSyncEntity.fetchERSyncEntity(editingContext(), ERSyncEntity.UUID_KEY, relNode.id());
    	            		if ( relatedEntity != null )
    	            		{
    	            			working.addObject( relatedEntity.enterpriseObject() );
    	            		}
    	            		else
    	            		{
    	            			throw new RuntimeException("Failed to sync relationship to invalid record '" + relNode.id() + "'");
    	            		}
                		}
                		else
                		{
                			throw new RuntimeException("Failed to sync relationship to null to-many record '" + attNode.name() + "'");
                		}
            		}
            		val = working;
            	}
            }
		}
		return val;
    }


	/** initial sync has not client data to push, just a dump of the existing records */
	public WOActionResults fullAction() throws Throwable
	{
		ERXRestRequestNode dataNode = requestNode().childNamed(ERXSyncConstants.DATA);
		if ((dataNode != null) && (dataNode.children() != null))
		{
			int count = processDataNodes(dataNode.children());
		}

		// generate the full sync response
		ERXRestRequestNode fullSyncList = new ERXRestRequestNode(ERXSyncConstants.SYNC, null, true);
		ERXRestRequestNode dataSyncList = new ERXRestRequestNode(ERXSyncConstants.DATA, null, false);

		// update sync entities
		NSArray<String> syncEntityNames = authorizer().syncEntityNames();
		for ( String entityName : syncEntityNames )
		{
			NSArray<EOKeyGlobalID> gidArray = authorizer().syncObjectsForEntityUser(entityName, principal().authReference().user(), editingContext());
			updateSyncEntityArray(gidArray);

			// use sync entities to generate response
			EOEntity entity = EOModelGroup.defaultGroup().entityNamed( entityName );
			
			EOQualifier userQualifier = ERXQ.equals(ERSyncEntity.AUTH_REFERENCES_KEY, principal().authReference());
			EOQualifier dataQualifier = ERXQ.startsWith(ERSyncEntity.DATA_SOURCE_TOKEN_KEY, entityName);
			
			NSArray<ERSyncEntity> syncList = ERSyncEntity.refreshERSyncEntities(editingContext(), ERXQ.and(userQualifier, dataQualifier), null);
			for ( ERSyncEntity syncEntity : syncList )
			{
				ERXRestRequestNode fullNode = syncFullDataNodeForEntity(entity, syncEntity);
				dataSyncList.addChild(fullNode);
			}
		}
		
		principal().setLastSync(new NSTimestamp());
		editingContext().saveChanges();

		fullSyncList.addChild(syncStatusForPrincipal());
		fullSyncList.addChild(dataSyncList);
		return response( format(), fullSyncList);
	}
}
