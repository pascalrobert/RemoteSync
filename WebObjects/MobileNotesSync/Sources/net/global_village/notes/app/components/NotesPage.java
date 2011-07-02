package net.global_village.notes.app.components;



import net.global_village.notes.Session;
import net.global_village.notes.model.entities.Category;
import net.global_village.notes.model.entities.Note;

import com.webobjects.appserver.WOContext;
import com.webobjects.appserver.WOComponent;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSTimestamp;

public class NotesPage extends WOComponent 
{
	public Note noteItem = null;
	public Category categoryItem = null;
	
	public Note selectedNote = null;
		
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotesPage(WOContext context) 
    {
        super(context);
    }
	
	@SuppressWarnings("unchecked")
	public NSArray noteArray()
	{
		return ((Session)session()).user().notes();
	}

	@SuppressWarnings("unchecked")
	public NSArray categoryArray()
	{
		return ((Session)session()).user().categories();
	}

	public WOComponent selectNoteAction()
	{
		selectedNote = noteItem;

		return null;
	}
	
	public boolean editMode()
	{
		return selectedNote != null;
	}
	
	public WOComponent addNoteAction()
	{
		Note welcomeNote = Note.createNote(session().defaultEditingContext(), new NSTimestamp(), ((Session)session()).user());
    	welcomeNote.setSubject("New Note");
    	welcomeNote.setContent("This note has no content :)");
    	if ((categoryArray() != null) && (categoryArray().count() > 0))
    		welcomeNote.setCategoryRelationship( (Category)categoryArray().objectAtIndex(0) );
    	
    	selectedNote = welcomeNote;
    	
    	return null;
	}
	
	public WOComponent doCancel()
	{
    	session().defaultEditingContext().revert();
    	selectedNote = null;
    	
    	return null;
	}

	public WOComponent doSave()
	{
		selectedNote.setCreationDate(new NSTimestamp());
    	session().defaultEditingContext().saveChanges();
    	selectedNote = null;
    	
    	return null;
	}

	public WOComponent doDelete()
	{
    	session().defaultEditingContext().deleteObject(selectedNote);
    	session().defaultEditingContext().saveChanges();
    	selectedNote = null;
    	
    	return null;
	}

}