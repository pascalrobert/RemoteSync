/*
 * ERSyncModel2.java
 *
 * This class is public domain software - that is, you can do whatever you want
 * with it, and include it software that is licensed under the BSD license
 *
 * If you make modifications to this code that you think would benefit the
 * wider community, please send me a copy and I'll post it on my site.
 *
 */

package er.sync.eo.migration;

import com.webobjects.eoaccess.EOModel;
import com.webobjects.eocontrol.EOEditingContext;

import er.extensions.migration.ERXMigrationDatabase;
import er.extensions.migration.ERXMigrationTable;
import er.extensions.migration.IERXPostMigration;
import er.extensions.migration.ERXMigrationDatabase.Migration;

/**
 * Global Village Consulting
 * @author	David Aspinall
 */
public class ERSyncModel2 extends Migration implements IERXPostMigration
{

	@Override
	public void downgrade(EOEditingContext arg0, ERXMigrationDatabase arg1)
	        throws Throwable
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void upgrade(EOEditingContext ec, ERXMigrationDatabase database)
	        throws Throwable
	{
		ERXMigrationTable eRSyncMMChangeToManyEntityTable = database.newTableNamed("ERSyncMMChangeToManyEntity");
		eRSyncMMChangeToManyEntityTable.newIntegerColumn("eRSyncChangeToManyId", false);
		eRSyncMMChangeToManyEntityTable.newIntegerColumn("eRSyncEntityId", false);
		eRSyncMMChangeToManyEntityTable.create();
	 	eRSyncMMChangeToManyEntityTable.setPrimaryKey("eRSyncChangeToManyId", "eRSyncEntityId");

		ERXMigrationTable eRSyncChangeToOneTable = database.existingTableNamed("ERSyncChangeValue");

		ERXMigrationTable eRSyncChangeToManyTable = database.existingTableNamed("ERSyncChangeValue");

		eRSyncMMChangeToManyEntityTable.addForeignKey("eRSyncChangeToManyId", "ERSyncChangeValue", "id");
		eRSyncMMChangeToManyEntityTable.addForeignKey("eRSyncEntityId", "ERSyncEntity", "id");
		eRSyncChangeToOneTable.addForeignKey("toOneEntityID", "ERSyncEntity", "id");
	}

	public void postUpgrade(EOEditingContext arg0, EOModel arg1)
	        throws Throwable
	{
		// TODO Auto-generated method stub

	}

}
