package er.sync.eo;

import java.util.UUID;

import org.apache.log4j.Logger;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;

import er.extensions.eof.ERXQ;
import er.sync.ERXSyncUtilities;
import er.sync.api.ERXSyncUser;

public class ERSyncAuthReference extends _ERSyncAuthReference 
{
	/**
     * 
     */
    private static final long serialVersionUID = -8335595716470649574L;
    
    @SuppressWarnings("unused")
    private static Logger log = Logger.getLogger(ERSyncAuthReference.class);
  
	public void awakeFromInsertion( EOEditingContext ec ) 
	{
		super.awakeFromInsertion( ec );
		
		setUuid( UUID.randomUUID().toString() );
	}

	@SuppressWarnings("unchecked")
	public static ERSyncAuthReference authReferenceWithUser( ERXSyncUser user, EOEditingContext ec ) 
	{
		EOQualifier userQualifier = ERXQ.equals( ERSyncAuthReference.DATA_SOURCE_TOKEN_KEY, ERXSyncUtilities.syncEOHashCode( user ) );
		EOFetchSpecification spec = new EOFetchSpecification( ERSyncAuthReference.ENTITY_NAME, userQualifier, null );
		NSArray<ERSyncAuthReference> result = ec.objectsWithFetchSpecification( spec );
		
		if ( result.count() == 1 ) 
		{
			return (ERSyncAuthReference)result.lastObject();
		}
		else if( result.count() > 1 ) 
		{
			throw new RuntimeException("Expected 0 or 1 objects, got " + result.count() );
		}
		
		return null;
	}

	public static ERSyncAuthReference findOrCreateAuthReference( ERXSyncUser user, EOEditingContext editingContext )
    {
		ERSyncAuthReference ref = ERSyncAuthReference.authReferenceWithUser(user, editingContext);
		if ( ref == null )
		{
			ref = ERSyncAuthReference.createERSyncAuthReference(editingContext, ERXSyncUtilities.syncEOHashCode( user ), user.sync_userName());
		}
	    return ref;
    }

	public ERXSyncUser user()
	{
		return (ERXSyncUser) ERXSyncUtilities.FaultForOwnerHashCode(editingContext(), dataSourceToken());
	}
}
