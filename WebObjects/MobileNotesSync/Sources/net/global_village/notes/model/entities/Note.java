package net.global_village.notes.model.entities;

import java.util.Random;

import net.global_village.notes.Util;

import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSTimestamp;

import er.sync.api.ERXSyncObject;
import er.sync.api.ERXSyncUser;


@SuppressWarnings("serial")
public class Note extends _Note implements ERXSyncObject
{
	//private static Logger log = Logger.getLogger(Note.class);
	
	public static final String CONTENT_PREVIEW_KEY = "contentPreview";
	
	static NSArray<String> randomStrings = new NSArray<String>( new String[] {
			"Thomson routers suck!",
			"Chuck loves twitter.",
			"xGPS is the best iPhone app on the App Store! Go get it NOW!",
			"Apple campus is awesome.",
			"Must buy chocolate.",
			"Dark chocolate."
	});
  
	@Override
	public void awakeFromInsertion( EOEditingContext ec ) {
		super.awakeFromInsertion( ec );
	
		setCreationDate( new NSTimestamp() );
	}

	public static Note randomNote( EOEditingContext ec ) {
		Random random = new Random();
		Note note = (Note) EOUtilities.createAndInsertInstance( ec, Note.ENTITY_NAME );
		note.setContent( randomStrings.objectAtIndex( random.nextInt( randomStrings.count() ) ) );
		return note;
	}
	
	public String contentPreview() {
		return Util.croppedString( content(), 25 );
	}

}
