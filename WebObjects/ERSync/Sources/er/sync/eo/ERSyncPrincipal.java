package er.sync.eo;

import java.util.UUID;

import org.apache.log4j.Logger;

import com.webobjects.eocontrol.EOAndQualifier;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.foundation.NSArray;

import er.extensions.eof.ERXQ;

public class ERSyncPrincipal extends _ERSyncPrincipal 
{
	/**
     * 
     */
    private static final long serialVersionUID = 7302250842688617793L;
    
	@SuppressWarnings("unused")
    private static Logger log = Logger.getLogger(ERSyncPrincipal.class);
	
	public void awakeFromInsertion( EOEditingContext ec ) 
	{
		super.awakeFromInsertion( ec );
	}

	@SuppressWarnings("unchecked")
	public static ERSyncPrincipal principalForUUID( String uuid, EOEditingContext ec ) 
	{
		EOQualifier uuidQualifier = ERXQ.equals( ERSyncPrincipal.PRINCIPAL_UUID_KEY, uuid);
		EOFetchSpecification spec = new EOFetchSpecification( ERSyncPrincipal.ENTITY_NAME, uuidQualifier, null );
		NSArray<ERSyncPrincipal> result = ec.objectsWithFetchSpecification( spec );
		
		if ( result.count() == 1 ) 
		{
			return (ERSyncPrincipal)result.lastObject();
		}
		else if( result.count() > 1 ) 
		{
			throw new RuntimeException("Expected 0 or 1 objects, got " + result.count() );
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	public static ERSyncPrincipal principalForUserWithAppidAndDevice( ERSyncAuthReference user, ERSyncClientApp appid, String device, EOEditingContext ec ) 
	{
		EOQualifier userQualifier = ERXQ.equals( ERSyncPrincipal.AUTH_REFERENCE_KEY, user );
		EOQualifier appQualifier = ERXQ.equals( ERSyncPrincipal.APPLICATION_KEY, appid );
		EOQualifier deviceQualifier = ERXQ.equals( ERSyncPrincipal.DEVICE_UUID_KEY, device);
		EOAndQualifier andQualifier = ERXQ.and(userQualifier, appQualifier, deviceQualifier);
		EOFetchSpecification spec = new EOFetchSpecification( ERSyncPrincipal.ENTITY_NAME, andQualifier, null );
		NSArray<ERSyncPrincipal> result = ec.objectsWithFetchSpecification( spec );
		
		if ( result.count() == 1 ) 
		{
			return (ERSyncPrincipal)result.lastObject();
		}
		else if( result.count() > 1 ) 
		{
			throw new RuntimeException("Expected 0 or 1 objects, got " + result.count() );
		}
		
		return null;
	}

}
