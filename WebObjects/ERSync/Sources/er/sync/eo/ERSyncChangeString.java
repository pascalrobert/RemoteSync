package er.sync.eo;

import org.apache.log4j.Logger;

import com.webobjects.eoaccess.EOAttribute;
import com.webobjects.eocontrol.EOEditingContext;

public class ERSyncChangeString extends _ERSyncChangeString 
{
	private static Logger log = Logger.getLogger(ERSyncChangeString.class);
	
	public void awakeFromInsertion( EOEditingContext ec ) 
	{
		super.awakeFromInsertion( ec );
		this.setValueType(String.valueOf(EOAttribute._VTString));
	}

	public Object changeValue()
	{
		Object cv = stringValue();
		return cv;
	}
}
