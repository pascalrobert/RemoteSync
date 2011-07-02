package er.sync.controllers;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WORequest;
import com.webobjects.eoaccess.EOAttribute;
import com.webobjects.eoaccess.EOEntity;
import com.webobjects.eoaccess.EOModelGroup;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOKeyGlobalID;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSTimestamp;

import er.extensions.eof.ERXQ;
import er.rest.ERXRestRequestNode;
import er.rest.ERXRestUtils;
import er.sync.ERXSyncConstants;
import er.sync.eo.ERSyncChangeValue;
import er.sync.eo.ERSyncChangeset;
import er.sync.eo.ERSyncEntity;
import er.sync.eo.ERSyncPrincipal;

public class FastController extends SlowController
{

	public FastController(WORequest request)
    {
	    super(request);
    }

	/** initial sync has not client data to push, just a dump of the existing records */
	public WOActionResults fullAction() throws Throwable
	{
		ERXRestRequestNode dataNode = requestNode().childNamed(ERXSyncConstants.DATA);
		if ((dataNode != null) && (dataNode.children() != null))
		{
			int count = processDataNodes(dataNode.children());
		}

		// generate the delta sync response
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
			EOQualifier dateQualifier = ERXQ.greaterThanOrEqualTo(ERSyncEntity.UPDATED_DATE_KEY, principal().lastSync());
			
			NSArray<ERSyncEntity> syncList = ERSyncEntity.refreshERSyncEntities(editingContext(), ERXQ.and(userQualifier, dataQualifier, dateQualifier), null);
			for ( ERSyncEntity syncEntity : syncList )
			{
				ERXRestRequestNode deltaNode = syncDeltaDataNodeForEntity(entity, syncEntity, principal().lastSync());
				if ( deltaNode != null )
					dataSyncList.addChild(deltaNode);
			}
		}
		
		principal().setLastSync(new NSTimestamp());
		editingContext().saveChanges();

		fullSyncList.addChild(syncStatusForPrincipal());
		fullSyncList.addChild(dataSyncList);
		return response( format(), fullSyncList);
	}
}
