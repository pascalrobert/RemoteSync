/*
 * ERSyncModel0.java
 *
 * This class is public domain software - that is, you can do whatever you want
 * with it, and include it software that is licensed under the BSD license
 *
 * If you make modifications to this code that you think would benefit the
 * wider community, please send me a copy and I'll post it on my site.
 *
 */

package er.sync.eo.migration;

import java.util.UUID;

import com.webobjects.eoaccess.EOModel;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSTimestamp;

import er.extensions.migration.ERXMigrationDatabase;
import er.extensions.migration.ERXMigrationTable;
import er.extensions.migration.IERXPostMigration;
import er.extensions.migration.ERXMigrationDatabase.Migration;
import er.sync.eo.ERSyncClientApp;
import er.sync.eo.ERSyncClientDeveloper;
import er.sync.eo.ERSyncClientDevice;

/**
 * Global Village Consulting
 * @author	David Aspinall
 */
public class ERSyncModel0 extends Migration implements IERXPostMigration
{

	@Override
	public void downgrade(EOEditingContext arg0, ERXMigrationDatabase arg1)
	        throws Throwable
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void upgrade(EOEditingContext editingContext, ERXMigrationDatabase database) throws Throwable 
	{
		ERXMigrationTable eRSyncPrincipalTable = database.newTableNamed("ERSyncPrincipal");
		eRSyncPrincipalTable.newIntegerColumn("id", false);
		eRSyncPrincipalTable.newIntegerColumn("authRefId", false);
		eRSyncPrincipalTable.newIntegerColumn("clientAppId", false);
		eRSyncPrincipalTable.newTimestampColumn("lastSync", true);
		eRSyncPrincipalTable.newStringColumn("deviceUUID", 50, false);
		eRSyncPrincipalTable.newStringColumn("principalUUID", 50, false);
		eRSyncPrincipalTable.create();
	 	eRSyncPrincipalTable.setPrimaryKey("id");
	 	eRSyncPrincipalTable.addUniqueIndex("ERSyncPrincipal_uniqueClientAuthDevice", 
	 			eRSyncPrincipalTable.existingColumnNamed("clientAppId"), 
	 			eRSyncPrincipalTable.existingColumnNamed("authRefId"), 
	 			eRSyncPrincipalTable.existingColumnNamed("deviceUUID"));

		ERXMigrationTable eRSyncCapabilitiesTable = database.newTableNamed("ERSyncCapabilities");
		eRSyncCapabilitiesTable.newIntegerColumn("id", false);
		eRSyncCapabilitiesTable.newBooleanColumn("large_objects", false);
		eRSyncCapabilitiesTable.newStringColumn("manufacturer", 100, false);
		eRSyncCapabilitiesTable.newStringColumn("model", 100, false);
		eRSyncCapabilitiesTable.create();
	 	eRSyncCapabilitiesTable.setPrimaryKey("id");

		ERXMigrationTable eRSyncAuthReferenceTable = database.newTableNamed("ERSyncAuthReference");
		eRSyncAuthReferenceTable.newStringColumn("dataSourceToken", 50, false);
		eRSyncAuthReferenceTable.newIntegerColumn("id", false);
		eRSyncAuthReferenceTable.newStringColumn("name", 50, false);
		eRSyncAuthReferenceTable.newStringColumn("uuid", 50, false);
		eRSyncAuthReferenceTable.create();
	 	eRSyncAuthReferenceTable.setPrimaryKey("id");

		ERXMigrationTable eRSyncClientTable = database.newTableNamed("ERSyncClient");
		eRSyncClientTable.newIntegerColumn("id", false);
		eRSyncClientTable.newIntegerColumn("capabilitiesId", true);
	 	eRSyncClientTable.newIntegerColumn("developerID", true);

		eRSyncClientTable.newBooleanColumn("disable", false);
		eRSyncClientTable.newStringColumn("name", 50, false);
		eRSyncClientTable.newStringColumn("type", 1, false);
		eRSyncClientTable.newStringColumn("uuid", 50, false);
		eRSyncClientTable.newStringColumn("apiKey", 50, true);
		
		eRSyncClientTable.create();
	 	eRSyncClientTable.setPrimaryKey("id");

		ERXMigrationTable eRSyncMMAppDevicesTable = database.newTableNamed("ERSyncMMAppDevices");
		eRSyncMMAppDevicesTable.newIntegerColumn("eRSyncClientAppId", false);
		eRSyncMMAppDevicesTable.newIntegerColumn("eRSyncClientDeviceId", false);
		eRSyncMMAppDevicesTable.create();
	 	eRSyncMMAppDevicesTable.setPrimaryKey("eRSyncClientAppId", "eRSyncClientDeviceId");

		eRSyncMMAppDevicesTable.addForeignKey("eRSyncClientAppId", "ERSyncClient", "id");
		eRSyncMMAppDevicesTable.addForeignKey("eRSyncClientDeviceId", "ERSyncClient", "id");

		eRSyncPrincipalTable.addForeignKey("clientAppId", "ERSyncClient", "id");
		eRSyncPrincipalTable.addForeignKey("authRefId", "ERSyncAuthReference", "id");
		eRSyncClientTable.addForeignKey("capabilitiesId", "ERSyncCapabilities", "id");
		eRSyncClientTable.addForeignKey("developerID", "ERSyncClient", "id");
	}

	public void postUpgrade(EOEditingContext ec, EOModel model)
	        throws Throwable
	{
	    ERSyncClientDevice iPhoneDevice = ERSyncClientDevice.createERSyncClientDevice(ec, false, "iPhone");
	    ERSyncClientDevice iPadDevice = ERSyncClientDevice.createERSyncClientDevice(ec, false, "iPad");
	    ERSyncClientDevice iPodDevice = ERSyncClientDevice.createERSyncClientDevice(ec, false, "iPod Touch");
	    
	    ERSyncClientDeveloper defaultDeveloper = ERSyncClientDeveloper.createERSyncClientDeveloper(ec, false, "Global Village Consulting");
	}

}
