package net.global_village.notes.model.migrations;

import net.global_village.notes.model.entities.Category;
import net.global_village.notes.model.entities.Note;
import net.global_village.notes.model.entities.User;

import com.webobjects.eoaccess.EOModel;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSTimestamp;

import er.extensions.migration.ERXMigrationDatabase;
import er.extensions.migration.ERXMigrationTable;
import er.extensions.migration.IERXPostMigration;
import er.extensions.migration.ERXMigrationDatabase.Migration;

public class NotesSyncModel0 extends Migration implements IERXPostMigration
{

	@Override
	public void downgrade(EOEditingContext arg0, ERXMigrationDatabase arg1)
	        throws Throwable
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void upgrade(EOEditingContext ec, ERXMigrationDatabase database) throws Throwable
	{
		ERXMigrationTable userTable = database.newTableNamed("N_User");
		userTable.newIntegerColumn("id", false);
		userTable.newStringColumn("password", 100, false);
		userTable.newStringColumn("username", 100, false);
		userTable.create();
	 	userTable.setPrimaryKey("id");

		ERXMigrationTable noteTable = database.newTableNamed("Note");
		noteTable.newStringColumn("content", 10000000, true);
		noteTable.newTimestampColumn("creationDate", false);
		noteTable.newIntegerColumn("id", false);
		noteTable.newIntegerColumn("userID", false);
		noteTable.newIntegerColumn("categoryID", false);
		noteTable.newStringColumn("subject", 50, true);
		noteTable.create();
	 	noteTable.setPrimaryKey("id");


		ERXMigrationTable categoryTable = database.newTableNamed("Category");
		categoryTable.newIntegerColumn("id", false);
		categoryTable.newStringColumn("name", 50, false);
		categoryTable.newIntegerColumn("userID", false);
		categoryTable.create();
	 	categoryTable.setPrimaryKey("id");

		noteTable.addForeignKey("categoryID", "Category", "id");
		categoryTable.addForeignKey("userID", "N_User", "id");
		noteTable.addForeignKey("userID", "N_User", "id");

	}

	public void postUpgrade(EOEditingContext ec, EOModel model) throws Throwable
	{
	    User defaultUser = User.createUser(ec, "tester", "admin");
	    Category catadmin = Category.createCategory(ec, "Admin", defaultUser);
	    Note defaultNote = Note.createNote(ec, new NSTimestamp(), defaultUser);
	    defaultNote.setContent("Welcome to Sync Notes service");
	    defaultNote.setSubject("Welcome");
	    defaultNote.setCategoryRelationship(catadmin);

	    User testUser = User.createUser(ec, "tester", "david");
	    
	    Category catOne = Category.createCategory(ec, "Home", testUser);
	    Category catTwo = Category.createCategory(ec, "Work", testUser);
	    Category catThree = Category.createCategory(ec, "Mobile", testUser);
	    
	    Note note1 = Note.createNote(ec, new NSTimestamp(), testUser);
	    note1.setContent("Welcome to Sync Notes service");
	    note1.setSubject("Welcome");
	    note1.setCategoryRelationship(catOne);

	    Note note2 = Note.createNote(ec, new NSTimestamp(), testUser);
	    note2.setContent("This note is for the use of employees only.");
	    note2.setSubject("Work confidential");
	    note2.setCategoryRelationship(catTwo);

	    Note note3 = Note.createNote(ec, new NSTimestamp(), testUser);
	    note3.setContent("Notes are great for on the go action");
	    note3.setSubject("Action Note");
	    note3.setCategoryRelationship(catThree);

	    Note note4 = Note.createNote(ec, new NSTimestamp(), testUser);
	    note4.setContent("Pretend this was made by my phone");
	    note4.setSubject("Made by Migration");
	    note4.setCategoryRelationship(catThree);
	    
	}

}
