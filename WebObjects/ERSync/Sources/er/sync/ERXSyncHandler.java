/*
 * ERXSyncHandler.java
 *
 * This class is public domain software - that is, you can do whatever you want
 * with it, and include it software that is licensed under the BSD license
 *
 * If you make modifications to this code that you think would benefit the
 * wider community, please send me a copy and I'll post it on my site.
 *
 */

package er.sync;

import org.apache.log4j.Logger;

import com.webobjects.appserver.WOApplication;
import com.webobjects.eoaccess.EOAttribute;
import com.webobjects.eoaccess.EOEntity;
import com.webobjects.eoaccess.EOModelGroup;
import com.webobjects.eoaccess.EOProperty;
import com.webobjects.eoaccess.EORelationship;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOKeyGlobalID;
import com.webobjects.eocontrol.EOObjectStoreCoordinator;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOQualifierEvaluation;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSNotification;
import com.webobjects.foundation.NSNotificationCenter;
import com.webobjects.foundation.NSSelector;
import com.webobjects.foundation.NSTimestamp;

import er.extensions.eof.ERXQ;
import er.extensions.foundation.ERXArrayUtilities;
import er.rest.routes.ERXRoute;
import er.rest.routes.ERXRouteRequestHandler;
import er.sync.api.ERXSyncAuthenticator;
import er.sync.controllers.FastController;
import er.sync.controllers.InitialController;
import er.sync.controllers.RegistrationController;
import er.sync.controllers.SlowController;
import er.sync.eo.ERSyncChangeValue;
import er.sync.eo.ERSyncChangeset;
import er.sync.eo.ERSyncEntity;
import er.sync.eo.ERSyncPrincipal;

/**
 * Global Village Consulting
 * @author	David Aspinall
 */
public class ERXSyncHandler extends ERXRouteRequestHandler
{
	private static Logger log = Logger.getLogger(ERXSyncHandler.class);

	private ERXSyncAuthenticator syncAuthenticator = null;
	
	
	@SuppressWarnings("unchecked")
    public ERXSyncHandler()
	{
		super(ERXRouteRequestHandler.WO);
		
		ERXRoute route = new ERXRoute(ERSyncPrincipal.ENTITY_NAME, "/register/{action}", ERXRoute.Method.All, RegistrationController.class);
		ERXRoute slowSync = new ERXRoute(ERSyncPrincipal.ENTITY_NAME, "/slow/{action}", ERXRoute.Method.All, SlowController.class);
		ERXRoute fastSync = new ERXRoute(ERSyncPrincipal.ENTITY_NAME, "/fast/{action}", ERXRoute.Method.All, FastController.class);
		ERXRoute initSync = new ERXRoute(ERSyncPrincipal.ENTITY_NAME, "/initial/{action}", ERXRoute.Method.All, InitialController.class);
		this.addRoute(route);
		this.addRoute(slowSync);
		this.addRoute(fastSync);
		this.addRoute(initSync);

        NSNotificationCenter.defaultCenter().addObserver(
                this, 
                new NSSelector("initialize", new Class[] { NSNotification.class } ), 
                WOApplication.ApplicationDidFinishLaunchingNotification, 
                null);
	}
	
	@SuppressWarnings("unchecked")
    public void initialize(NSNotification note)
	{
		/** register for EO changes notification */
		NSSelector selector = new NSSelector("coordinateChanges", new Class[] { NSNotification.class } );
		NSNotificationCenter.defaultCenter().addObserver( this, selector, EOObjectStoreCoordinator.ObjectsChangedInStoreNotification, EOObjectStoreCoordinator.defaultCoordinator());
	}
	
	public ERXSyncAuthenticator syncAuthenticator()
	{
		return syncAuthenticator;
	}
	
	public void setSyncAuthenticator(ERXSyncAuthenticator auth)
	{
		syncAuthenticator = auth;
	}
	
	public static void register(ERXSyncHandler handler)
	{
		WOApplication.application().registerRequestHandler(handler, ERXSyncConstants.SYNC);
	}
	
	public class EOSyncEntityFilter implements EOQualifierEvaluation
	{
        public boolean evaluateWithObject(Object object) 
        {
        	EOKeyGlobalID eokgid = (EOKeyGlobalID)object;
            return syncAuthenticator.syncEntityNames().containsObject(eokgid.entityName());
        }
	}
	
    @SuppressWarnings("unchecked")
    public NSArray syncFilteredGlobalId(NSArray candidates) 
    {
        NSArray result = ERXArrayUtilities.filteredArrayWithQualifierEvaluation(candidates, new EOSyncEntityFilter() );
        return (result.isEmpty() ? null : result);
    }

	@SuppressWarnings("unchecked")
    public void coordinateChanges(NSNotification notification)
	{
		NSDictionary userInfo = (NSDictionary)notification.userInfo();
		EOEditingContext ec = new EOEditingContext(new EOObjectStoreCoordinator());
		ec.lock();

		log.debug("Change Notification " + userInfo);
		try
		{
			NSArray deleted = syncFilteredGlobalId((NSArray)userInfo.objectForKey("deleted"));
			NSArray inserted = syncFilteredGlobalId((NSArray)userInfo.objectForKey("inserted"));
			NSArray updated = syncFilteredGlobalId((NSArray)userInfo.objectForKey("updated"));

			if (deleted != null || inserted != null || updated != null)
			{
				ERSyncChangeset changeset = ERSyncChangeset.createERSyncChangeset(ec);
				
				if ( deleted != null && deleted.count() > 0 )
					processDeleteForSync(deleted, changeset, ec);

				if ( inserted != null && inserted.count() > 0 )
					processInsertForSync(inserted, changeset, ec);
				
				if ( updated != null && updated.count() > 0 )
					processUpdateForSync(updated, changeset, ec);
			}

			ec.saveChanges();
		}
		finally
		{
			ec.unlock();
		}
	}

	@SuppressWarnings("unchecked")
    public void processInsertForSync(NSArray gidArray, ERSyncChangeset changeset, EOEditingContext ec)
	{
		for ( Object id : gidArray )
		{
			EOKeyGlobalID gid = (EOKeyGlobalID)id;
			String dataToken = ERXSyncUtilities.syncGIDHashCode(gid);
			EOEnterpriseObject eo = ec.faultForGlobalID( gid, ec);
			EOEntity entity = EOModelGroup.defaultGroup().entityNamed( gid.entityName() );

			ERSyncEntity syncEntity = ERSyncEntity.findOrCreateForToken(ec, dataToken);
			syncEntity.setStatus(ERSyncEntity.INSERTED);
			syncEntity.setDataSourceToken( dataToken );
			
			NSArray<String> propertyNames = (NSArray<String>)entity.classPropertyNames();
			for ( String propName : propertyNames )
			{
				EOProperty attr = entity.propertyNamed(propName);
				if ( attr instanceof EOAttribute )
				{
					ERSyncChangeValue changeVal = ERSyncChangeValue.createERSyncChangeValue(ec, attr, syncEntity, changeset);
					changeVal.updateChangeValue( attr, eo);
				}
				else if ( attr instanceof EORelationship )
				{
					EORelationship rel = (EORelationship)attr;
		            if (syncAuthenticator.syncEntityNames().containsObject(rel.destinationEntity().name()) == true )
		            {
						ERSyncChangeValue changeVal = ERSyncChangeValue.createERSyncChangeValue(ec, attr, syncEntity, changeset);
						changeVal.updateChangeValue( attr, eo);
		            }
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
    public void processUpdateForSync(NSArray gidArray, ERSyncChangeset changeset, EOEditingContext ec)
	{
		for ( Object id : gidArray )
		{
			EOKeyGlobalID gid = (EOKeyGlobalID)id;
			String dataToken = ERXSyncUtilities.syncGIDHashCode(gid);
			EOEnterpriseObject eo = ec.faultForGlobalID( gid, ec);
			EOEntity entity = EOModelGroup.defaultGroup().entityNamed( gid.entityName() );

			ERSyncEntity syncEntity = ERSyncEntity.findOrCreateForToken(ec, dataToken);
			syncEntity.setStatus(ERSyncEntity.UPDATED);
			syncEntity.setUpdatedDate(new NSTimestamp());
			
			NSArray<String> attributeNames = entity.classPropertyNames();
			for ( String attrName : attributeNames )
			{
				EOProperty attr = entity.propertyNamed(attrName);
				if ( attr != null )
				{
					EOQualifier ownerKVQ = ERXQ.equals( ERSyncChangeValue.CHANGE_ENTITY_KEY, syncEntity ); 
					EOQualifier attrKVQ = ERXQ.equals( ERSyncChangeValue.ATTRIBUTE_NAME_KEY, ((EOProperty)attr).name() ); 
					ERSyncChangeValue changeVal = ERSyncChangeValue.fetchERSyncChangeValue(ec, ERXQ.and(ownerKVQ, attrKVQ));
					
					if ( attr instanceof EOAttribute )
					{
						if ( changeVal == null )
						{
							changeVal = ERSyncChangeValue.createERSyncChangeValue(ec, attr, syncEntity, changeset);
						}
						changeVal.updateChangeValue( attr, eo);

						// only track one change value per attribute, move it to the current changeset
						changeVal.updateChangeValue( attr, eo);
						if ( ERXSyncUtilities.hasChangesFromSnapshot(changeVal) == true )
							changeVal.setChangesetRelationship(changeset);
					}
					else
					{
						EORelationship rel = (EORelationship)attr;
			            if (syncAuthenticator.syncEntityNames().containsObject(rel.destinationEntity().name()) == true )
			            {
							if ( changeVal == null )
							{
								changeVal = ERSyncChangeValue.createERSyncChangeValue(ec, attr, syncEntity, changeset);
							}
							changeVal.updateChangeValue( attr, eo);

							// only track one change value per attribute, move it to the current changeset
							changeVal.updateChangeValue( attr, eo);
							if ( ERXSyncUtilities.hasChangesFromSnapshot(changeVal) == true )
								changeVal.setChangesetRelationship(changeset);
			            }
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
    public void processDeleteForSync(NSArray gidArray, ERSyncChangeset changeset, EOEditingContext ec)
	{
		for ( Object id : gidArray )
		{
			EOKeyGlobalID gid = (EOKeyGlobalID)id;
			ERSyncEntity syncEntity = ERSyncEntity.fetchERSyncEntity(ec, ERSyncEntity.DATA_SOURCE_TOKEN_KEY, ERXSyncUtilities.syncGIDHashCode(gid));
			if ( syncEntity != null )
			{
				syncEntity.setStatus(ERSyncEntity.DELETED);
				syncEntity.setUpdatedDate(new NSTimestamp());
				
				// remove previous changes
				for ( ERSyncChangeValue aVal : syncEntity.changeValues())
				{
					ec.deleteObject(aVal);
				}
			}
		}
	}
}
