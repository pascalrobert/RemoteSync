package er.sync.eo;

import org.apache.log4j.Logger;

import com.webobjects.eoaccess.EOAttribute;
import com.webobjects.eocontrol.EOEditingContext;

public class ERSyncChangeInteger extends _ERSyncChangeInteger 
{
	private static Logger log = Logger.getLogger(ERSyncChangeInteger.class);
	public void awakeFromInsertion( EOEditingContext ec ) 
	{
		super.awakeFromInsertion( ec );
		this.setValueType(String.valueOf(EOAttribute._VTInteger));
	}

	public Object changeValue()
	{
		return intValue();
	}
}
