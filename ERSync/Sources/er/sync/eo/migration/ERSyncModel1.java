package er.sync.eo.migration;

import com.webobjects.eoaccess.EOModel;
import com.webobjects.eocontrol.EOEditingContext;

import er.extensions.migration.ERXMigrationDatabase;
import er.extensions.migration.ERXMigrationTable;
import er.extensions.migration.IERXPostMigration;
import er.extensions.migration.ERXMigrationDatabase.Migration;
import er.sync.eo.ERSyncClient;
import er.sync.eo.ERSyncClientApp;
import er.sync.eo.ERSyncClientDeveloper;
import er.sync.eo.ERSyncClientDevice;

public class ERSyncModel1 extends Migration implements IERXPostMigration
{

	@Override
	public void downgrade(EOEditingContext arg0, ERXMigrationDatabase arg1)
	        throws Throwable
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void upgrade(EOEditingContext context, ERXMigrationDatabase database) throws Throwable
	{
		ERXMigrationTable eRSyncEntityTable = database.newTableNamed("ERSyncEntity");
		eRSyncEntityTable.newIntegerColumn("id", false);
		eRSyncEntityTable.newStringColumn("dataSourceToken", 50, true);
		eRSyncEntityTable.newStringColumn("status", 1, false);
		eRSyncEntityTable.newTimestampColumn("updatedDate", false);
		eRSyncEntityTable.newStringColumn("uuid", 50, true);
		eRSyncEntityTable.create();
	 	eRSyncEntityTable.setPrimaryKey("id");

		ERXMigrationTable eRSyncChangesetTable = database.newTableNamed("ERSyncChangeset");
		eRSyncChangesetTable.newIntegerColumn("authID", true);
		eRSyncChangesetTable.newIntegerColumn("id", false);
		eRSyncChangesetTable.newTimestampColumn("updatedDate", true);
		eRSyncChangesetTable.newStringColumn("uuid", 50, true);
		eRSyncChangesetTable.create();
	 	eRSyncChangesetTable.setPrimaryKey("id");

		ERXMigrationTable eRSyncChangeValueTable = database.newTableNamed("ERSyncChangeValue");
		eRSyncChangeValueTable.newIntegerColumn("id", false);
		eRSyncChangeValueTable.newIntegerColumn("entityID", false);
		eRSyncChangeValueTable.newIntegerColumn("changesetID", false);
		eRSyncChangeValueTable.newIntegerColumn("toOneEntityID", true);
		eRSyncChangeValueTable.newStringColumn("valueType", 1, false);
		eRSyncChangeValueTable.newStringColumn("attributeName", 50, false);
		eRSyncChangeValueTable.newIntBooleanColumn("booleanValue", true);
		eRSyncChangeValueTable.newIntegerColumn("intValue", true);
		eRSyncChangeValueTable.newTimestampColumn("timestampValue", true);
		eRSyncChangeValueTable.newStringColumn("stringValue", 10000000, true);
		eRSyncChangeValueTable.create();
		eRSyncChangeValueTable.setPrimaryKey("id");

		ERXMigrationTable eRSyncMMEntityAuthReferenceTable = database.newTableNamed("ERSyncMMEntityAuthReference");
		eRSyncMMEntityAuthReferenceTable.newIntegerColumn("eRSyncAuthReferenceId", false);
		eRSyncMMEntityAuthReferenceTable.newIntegerColumn("eRSyncEntityId", false);
		eRSyncMMEntityAuthReferenceTable.create();
	 	eRSyncMMEntityAuthReferenceTable.setPrimaryKey("eRSyncAuthReferenceId", "eRSyncEntityId");

		eRSyncChangeValueTable.addForeignKey("entityID", "ERSyncEntity", "id");
		eRSyncChangeValueTable.addForeignKey("changesetID", "ERSyncChangeset", "id");
		eRSyncChangesetTable.addForeignKey("authID", "ERSyncAuthReference", "id");
		eRSyncMMEntityAuthReferenceTable.addForeignKey("eRSyncAuthReferenceId", "ERSyncAuthReference", "id");
		eRSyncMMEntityAuthReferenceTable.addForeignKey("eRSyncEntityId", "ERSyncEntity", "id");
		
	}

	public void postUpgrade(EOEditingContext ec, EOModel model) throws Throwable
	{
	    ERSyncClientDeveloper developer = ERSyncClientDeveloper.fetchERSyncClientDeveloper(ec, ERSyncClientDeveloper.NAME_KEY, "Global Village Consulting");

		ERSyncClientApp defaultApp = ERSyncClientApp.createERSyncClientApp(ec, "0c424873-2f0d-4746-b2d5-1735f51d9f52", false, "ERSyncTest", developer);
		defaultApp.addToDevicesRelationship(ERSyncClientDevice.fetchERSyncClientDevice(ec, ERSyncClientDevice.NAME_KEY, "iPhone"));
		defaultApp.addToDevicesRelationship(ERSyncClientDevice.fetchERSyncClientDevice(ec, ERSyncClientDevice.NAME_KEY, "iPad"));
	}

}
