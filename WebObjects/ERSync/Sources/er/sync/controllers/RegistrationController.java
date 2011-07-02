/*
 * RegistrationController.java
 *
 * This class is public domain software - that is, you can do whatever you want
 * with it, and include it software that is licensed under the BSD license
 *
 * If you make modifications to this code that you think would benefit the
 * wider community, please send me a copy and I'll post it on my site.
 *
 */

package er.sync.controllers;

import java.util.UUID;

import com.webobjects.appserver.WOActionResults;
import com.webobjects.appserver.WORequest;
import com.webobjects.eocontrol.EOKeyGlobalID;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSTimestamp;

import er.extensions.eof.ERXKey;
import er.extensions.eof.ERXKeyFilter;
import er.rest.ERXRestRequestNode;
import er.rest.routes.jsr311.POST;
import er.sync.ERXSyncUtilities;
import er.sync.ERXSyncConstants;
import er.sync.ERXSyncHandler;
import er.sync.api.ERXSyncAuthenticator;
import er.sync.api.ERXSyncUser;
import er.sync.eo.ERSyncAuthReference;
import er.sync.eo.ERSyncClientApp;
import er.sync.eo.ERSyncEntity;
import er.sync.eo.ERSyncPrincipal;

/**
 * Global Village Consulting
 * @author	David Aspinall
 */
public class RegistrationController extends GenericSyncController 
{

	public RegistrationController(WORequest request)
	{
		super(request);
	}
	
	@Override
	protected void checkAccess() throws SecurityException 
	{
	}


	@Override
	public WOActionResults createAction() throws Throwable 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WOActionResults destroyAction() throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WOActionResults indexAction() throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WOActionResults showAction() throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WOActionResults updateAction() throws Throwable {
		// TODO Auto-generated method stub
		return null;
	}

	@POST
	@Override
	public WOActionResults newAction() throws Throwable 
	{
		ERXRestRequestNode fullSyncList = new ERXRestRequestNode(ERXSyncConstants.SYNC, null, true);
		String apikey = (String) requestNode().childNamed("appid").value();
		ERSyncClientApp app = ERSyncClientApp.fetchRequiredERSyncClientApp(editingContext(), ERSyncClientApp.API_KEY_KEY, apikey);
		if ( app == null )
		{
			throw new SecurityException();
		}

		String deviceUUID = (String) requestNode().childNamed("device").value();
		if ( deviceUUID == null )
		{
			throw new SecurityException();
		}

		String username = (String) requestNode().childNamed("user").value();
		String password = (String) requestNode().childNamed("password").value();
		
		ERXSyncUser user = authorizer().userForCredentials(username, password, editingContext());
		if ( user == null )
		{
			throw new SecurityException();
		}
		
		ERSyncAuthReference authReference = ERSyncAuthReference.findOrCreateAuthReference( user, editingContext() );
		if ( authReference == null )
		{
			throw new SecurityException();
		}
		
		ERSyncPrincipal principal = ERSyncPrincipal.principalForUserWithAppidAndDevice(authReference, app, deviceUUID, editingContext());
		if ( principal == null )
		{
			principal = ERSyncPrincipal.createERSyncPrincipal(editingContext(), deviceUUID, UUID.randomUUID().toString(), app, authReference);
		}
		
		NSArray<String> syncEntityNames = authorizer().syncEntityNames();
		for ( String entityName : syncEntityNames )
		{
			NSArray<EOKeyGlobalID> gidArray = authorizer().syncObjectsForEntityUser(entityName, user, editingContext());
			for ( EOKeyGlobalID gid : gidArray )
			{
				String dataToken = ERXSyncUtilities.syncGIDHashCode(gid);
				ERSyncEntity syncEntity = ERSyncEntity.fetchERSyncEntity(editingContext(), ERSyncEntity.DATA_SOURCE_TOKEN_KEY, dataToken);
				if ( syncEntity == null )
				{
					syncEntity = ERSyncEntity.createERSyncEntity(editingContext(), ERSyncEntity.VIRGIN, new NSTimestamp());
					syncEntity.setDataSourceToken( dataToken );
					syncEntity.setUuid( dataToken );
				}
				
				if ( syncEntity.authReferences().containsObject(authReference) == false )
				{
					syncEntity.addToAuthReferencesRelationship(authReference);
				}
			}
		}
		
		editingContext().saveChanges();
		
		fullSyncList.addChild( new ERXRestRequestNode(ERSyncPrincipal.PRINCIPAL_UUID_KEY, principal.principalUUID(), false) );
		fullSyncList.addChild( new ERXRestRequestNode(ERSyncPrincipal.LAST_SYNC_KEY, principal.lastSync(), false) );

		return response( format(), fullSyncList);
	}
	

}
