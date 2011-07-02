package er.sync.eo;

import java.util.UUID;

import org.apache.log4j.Logger;

import com.webobjects.eoaccess.EOAttribute;
import com.webobjects.eocontrol.EOEditingContext;

public class ERSyncChangeBoolean extends _ERSyncChangeBoolean 
{
	private static Logger log = Logger.getLogger(ERSyncChangeBoolean.class);
	
	public void awakeFromInsertion( EOEditingContext ec ) 
	{
		super.awakeFromInsertion( ec );
		this.setValueType(String.valueOf(EOAttribute._VTBoolean));
	}

	public Object changeValue()
	{
		return booleanValue();
	}
}
