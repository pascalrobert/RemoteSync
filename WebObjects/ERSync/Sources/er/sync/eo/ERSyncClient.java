package er.sync.eo;

import java.util.UUID;

import org.apache.log4j.Logger;

import com.webobjects.eocontrol.EOEditingContext;

public class ERSyncClient extends _ERSyncClient 
{
    /**
     * 
     */
    private static final long serialVersionUID = -6914430567996275180L;
    
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(ERSyncClient.class);
	
	public enum TYPE_ENUM 
	{
		A,
		D,
		X
	}

  
	public void awakeFromInsertion( EOEditingContext ec ) 
	{
		super.awakeFromInsertion( ec );
		
		setUuid( UUID.randomUUID().toString() );
	}

}
