/*
 * ERXSyncAuthenticator.java
 *
 * This class is public domain software - that is, you can do whatever you want
 * with it, and include it software that is licensed under the BSD license
 *
 * If you make modifications to this code that you think would benefit the
 * wider community, please send me a copy and I'll post it on my site.
 *
 */

package er.sync.api;

import com.webobjects.eoaccess.EOEntity;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOGlobalID;
import com.webobjects.eocontrol.EOKeyGlobalID;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableDictionary;

/**
 * Global Village Consulting
 * @author	David Aspinall
 */
public interface ERXSyncAuthenticator
{
	public ERXSyncUser userForCredentials(String username, String password, EOEditingContext ec);

	public NSArray<String> syncEntityNames();
	
	public NSArray<EOKeyGlobalID> syncObjectsForEntityUser(String entityName, ERXSyncUser usr, EOEditingContext ec);

	public EOEnterpriseObject syncInsertObject(EOEditingContext editingContext,
            EOEntity eoEntity, NSMutableDictionary dict, ERXSyncUser user);

	public void syncUpdateObject(EOEnterpriseObject eo,
            NSMutableDictionary dict, ERXSyncUser user);

	public void syncDeleteObject(EOEnterpriseObject eo, ERXSyncUser user);
	
}
