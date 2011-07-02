/*
 * InitialController.java
 *
 * This class is public domain software - that is, you can do whatever you want
 * with it, and include it software that is licensed under the BSD license
 *
 * If you make modifications to this code that you think would benefit the
 * wider community, please send me a copy and I'll post it on my site.
 *
 */

package er.sync.controllers;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WORequest;
import com.webobjects.eoaccess.EOEntity;
import com.webobjects.eoaccess.EOModelGroup;
import com.webobjects.eocontrol.EOKeyGlobalID;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSTimestamp;

import er.rest.ERXRestRequestNode;
import er.sync.ERXSyncConstants;
import er.sync.eo.ERSyncEntity;

/**
 * Global Village Consulting
 * @author	David Aspinall
 */
public class InitialController extends GenericSyncController
{

	public InitialController(WORequest request)
    {
	    super(request);
    }

	/** initial sync has not client data to push, just a dump of the existing records, all records will look like insert to client */
	public WOActionResults fullAction() throws Throwable
	{
		ERXRestRequestNode fullSyncList = new ERXRestRequestNode(ERXSyncConstants.SYNC, null, true);
		ERXRestRequestNode dataSyncList = new ERXRestRequestNode(ERXSyncConstants.DATA, null, false);

		NSArray<String> syncEntityNames = authorizer().syncEntityNames();
		for ( String entityName : syncEntityNames )
		{
			NSArray<EOKeyGlobalID> gidArray = authorizer().syncObjectsForEntityUser(entityName, principal().authReference().user(), editingContext());
			NSArray<ERSyncEntity> syncList = updateSyncEntityArray(gidArray);
			EOEntity entity = EOModelGroup.defaultGroup().entityNamed( entityName );

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

	
	@Override
    public WOActionResults indexAction() throws Throwable
    {
		ERXRestRequestNode fullSyncList = new ERXRestRequestNode(ERXSyncConstants.SYNC, null, true);
		ERXRestRequestNode dataSyncList = new ERXRestRequestNode(ERXSyncConstants.DATA, null, false);

		NSArray<String> syncEntityNames = authorizer().syncEntityNames();
		for ( String entityName : syncEntityNames )
		{
			NSArray<EOKeyGlobalID> gidArray = authorizer().syncObjectsForEntityUser(entityName, principal().authReference().user(), editingContext());
			NSArray<ERSyncEntity> syncList = updateSyncEntityArray(gidArray);
			
			for ( ERSyncEntity syncEntity : syncList )
			{
				ERXRestRequestNode dataNode = syncIndexNodeForEntity( syncEntity ); 
				dataSyncList.addChild(dataNode);
			}
		}

		fullSyncList.addChild(syncStatusForPrincipal());
		fullSyncList.addChild(dataSyncList);

		return response( format(), fullSyncList);
    }

}
