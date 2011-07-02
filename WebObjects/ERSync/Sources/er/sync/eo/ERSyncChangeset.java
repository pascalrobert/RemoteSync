package er.sync.eo;

import java.util.UUID;

import org.apache.log4j.Logger;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSTimestamp;

import er.extensions.eof.ERXQ;

public class ERSyncChangeset extends _ERSyncChangeset
{
	/**
     * 
     */
    private static final long serialVersionUID = -9026010259535090795L;

    private static Logger log = Logger.getLogger(ERSyncChangeset.class);
  
	public void awakeFromInsertion( EOEditingContext ec ) 
	{
		super.awakeFromInsertion( ec );
		setUuid( UUID.randomUUID().toString() );
		setUpdatedDate(new NSTimestamp());
	}
	
	public NSArray<ERSyncEntity> allEntitiesInChangeset()
	{
		return (NSArray<ERSyncEntity>) this.valueForKeyPath( ERXQ.keyPath(ERSyncChangeset.CHANGE_VALUES_KEY, ERSyncChangeValue.CHANGE_ENTITY_KEY) );
	}

}
