package net.terminalapp.notes.model.migration;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;

import er.extensions.migration.ERXMigrationDatabase;
import er.extensions.migration.ERXMigrationTable;
import er.extensions.migration.ERXModelVersion;

public class NotesModel0 extends ERXMigrationDatabase.Migration {
	@Override
	public NSArray<ERXModelVersion> modelDependencies() {
		return null;
	}
  
	@Override
	public void downgrade(EOEditingContext editingContext, ERXMigrationDatabase database) throws Throwable {
		// DO NOTHING
	}

	@Override
	public void upgrade(EOEditingContext editingContext, ERXMigrationDatabase database) throws Throwable {
		ERXMigrationTable userTable = database.newTableNamed("N_User");
		userTable.newIntegerColumn("id", false);
		userTable.newStringColumn("password", 100, false);
		userTable.newStringColumn("username", 100, false);
		userTable.create();
	 	userTable.setPrimaryKey("id");
	 	userTable.addUniqueIndex( "username" );

		ERXMigrationTable noteTable = database.newTableNamed("Note");
		noteTable.newStringColumn("content", 10000, true);
		noteTable.newTimestampColumn("creationDate", false);
		noteTable.newIntegerColumn("id", false);
		noteTable.newIntegerColumn("userID", false);
		noteTable.create();
	 	noteTable.setPrimaryKey("id");
	 	
		noteTable.addForeignKey("userID", "N_User", "id");
	}
	
	
	
	
}