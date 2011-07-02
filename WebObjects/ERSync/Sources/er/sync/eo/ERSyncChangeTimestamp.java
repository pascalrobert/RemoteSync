package er.sync.eo;

import org.apache.log4j.Logger;

import com.webobjects.eoaccess.EOAttribute;
import com.webobjects.eocontrol.EOEditingContext;

public class ERSyncChangeTimestamp extends _ERSyncChangeTimestamp 
{
	private static Logger log = Logger.getLogger(ERSyncChangeTimestamp.class);

	public void awakeFromInsertion( EOEditingContext ec ) 
	{
		super.awakeFromInsertion( ec );
		this.setValueType(String.valueOf(EOAttribute._VTTimestamp));
	}
	
	public Object changeValue()
	{
		return timestampValue();
	}

}
