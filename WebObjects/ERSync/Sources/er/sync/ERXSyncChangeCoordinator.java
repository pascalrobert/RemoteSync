/*
 * ERXSyncChangeCoordinator.java
 *
 * This class is public domain software - that is, you can do whatever you want
 * with it, and include it software that is licensed under the BSD license
 *
 * If you make modifications to this code that you think would benefit the
 * wider community, please send me a copy and I'll post it on my site.
 *
 */

package er.sync;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOObjectStoreCoordinator;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSLog;
import com.webobjects.foundation.NSNotification;
import com.webobjects.foundation.NSNotificationCenter;
import com.webobjects.foundation.NSSelector;

/**
 * Global Village Consulting
 * @author	David Aspinall
 */
public class ERXSyncChangeCoordinator
{
	/** singleton, there should be only one */
	private static final ERXSyncChangeCoordinator coordinator = new ERXSyncChangeCoordinator();
	
	public static ERXSyncChangeCoordinator coordinator()
	{
        return coordinator;
	}

	@SuppressWarnings("unchecked")
    private ERXSyncChangeCoordinator()
	{
		super();
		
		NSLog.debug.appendln("Starting Sync Change Coordinator");
		
//		/** register for EO changes notification */
//		NSSelector selector = new NSSelector("coordinateChanges", new Class[] { NSNotification.class } );
//		NSNotificationCenter.defaultCenter().addObserver( this, selector, EOObjectStoreCoordinator.ObjectsChangedInStoreNotification, EOObjectStoreCoordinator.defaultCoordinator());
	}

	@SuppressWarnings("unchecked")
    public void coordinateChanges(NSNotification notification)
	{
		NSDictionary userInfo = (NSDictionary)notification.userInfo();
		EOEditingContext ec = new EOEditingContext();
		
		NSArray inserted = (NSArray)userInfo.objectForKey("inserted");
		NSArray updated = (NSArray)userInfo.objectForKey("updated");
		NSArray deleted = (NSArray)userInfo.objectForKey("deleted");

	}

}
