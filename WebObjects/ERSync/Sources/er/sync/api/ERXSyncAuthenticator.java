package er.sync.api;

import com.webobjects.eoaccess.EOEntity;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOGlobalID;
import com.webobjects.eocontrol.EOKeyGlobalID;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSMutableDictionary;

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
