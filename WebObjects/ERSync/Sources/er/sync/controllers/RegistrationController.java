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
import er.sync.eo.ERSyncClientDevice;
import er.sync.eo.ERSyncEntity;
import er.sync.eo.ERSyncPrincipal;

/**
 * The registration controller processes requests from clients.  
 * It expects to receive an identifier for the client application, 
 * the identifier for the device type, both of which must be known 
 * by the service already.  The client must also provide a unique 
 * identifier for the device and the user authentication.
 * 
 * 
 * 
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
		// the parent will deny access if the principal is not known, but 
		// the purpose of this controller is to create a Principal
	}

	@POST
	@Override
	public WOActionResults newAction() throws Throwable 
	{
		ERXRestRequestNode fullSyncList = new ERXRestRequestNode(ERXSyncConstants.SYNC, null, true);
		String apikey = (String) requestNode().childNamed(ERXSyncConstants.REGISTRATION_APPID).value();
		ERSyncClientApp app = ERSyncClientApp.fetchRequiredERSyncClientApp(editingContext(), ERSyncClientApp.API_KEY_KEY, apikey);
		if ( app == null )
		{
			throw new SecurityException();
		}
		if ( app.disable().booleanValue() == true )
		{
			throw new SecurityException("This application '" + app.name() + "' has been disabled" );
		}

		String deviceTypekey = (String) requestNode().childNamed(ERXSyncConstants.REGISTRATION_DEVICE_TYPE).value();
		ERSyncClientDevice type = ERSyncClientDevice.fetchERSyncClientDevice(editingContext(), ERSyncClientDevice.UUID_KEY, deviceTypekey);
		if ( type == null )
		{
			throw new SecurityException();
		}
		if ( type.disable().booleanValue() == true )
		{
			throw new SecurityException("This device type '" + type.name() + "' has been disabled" );
		}

		String deviceUUID = (String) requestNode().childNamed(ERXSyncConstants.REGISTRATION_DEVICE_UUID).value();
		if ( deviceUUID == null )
		{
			throw new SecurityException();
		}

		String username = (String) requestNode().childNamed(ERXSyncConstants.REGISTRATION_USER).value();
		String password = (String) requestNode().childNamed(ERXSyncConstants.REGISTRATION_PASSWORD).value();
		
		ERXSyncUser user = authorizer().userForCredentials(username, password, editingContext());
		if ( user == null )
		{
			throw new SecurityException("Unknwon user");
		}
		
		ERSyncAuthReference authReference = ERSyncAuthReference.findOrCreateAuthReference( user, editingContext() );
		if ( authReference == null )
		{
			throw new SecurityException();
		}
		
		// it is possible that the same app, on the same device (type and UUID) could be used by different users, but the same user

		
		ERSyncPrincipal principal = ERSyncPrincipal.principalForUserWithAppidAndDevice(authReference, app, deviceUUID, editingContext());
		if ( principal == null )
		{
			principal = ERSyncPrincipal.createERSyncPrincipal(editingContext(), deviceUUID, UUID.randomUUID().toString(), app, authReference, type);
		}
		setPrincipal(principal);
		
		NSArray<String> syncEntityNames = authorizer().syncEntityNames();
		for ( String entityName : syncEntityNames )
		{
			NSArray<EOKeyGlobalID> gidArray = authorizer().syncObjectsForEntityUser(entityName, user, editingContext());
			updateSyncEntityArray(gidArray);
		}
		
		editingContext().saveChanges();
		
		fullSyncList.addChild( new ERXRestRequestNode(ERSyncPrincipal.PRINCIPAL_UUID_KEY, principal.principalUUID(), false) );
		fullSyncList.addChild( new ERXRestRequestNode(ERSyncPrincipal.LAST_SYNC_KEY, principal.lastSync(), false) );

		return response( format(), fullSyncList);
	}
	

}
