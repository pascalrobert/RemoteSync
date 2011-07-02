package er.sync.eo;

import org.apache.log4j.Logger;

import com.webobjects.eoaccess.EOAttribute;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSDictionary;

public class ERSyncChangeToMany extends _ERSyncChangeToMany 
{
	/**
     * 
     */
    private static final long serialVersionUID = -4187111358007003420L;
	private static Logger log = Logger.getLogger(ERSyncChangeToMany.class);

	public void awakeFromInsertion( EOEditingContext ec ) 
	{
		super.awakeFromInsertion( ec );
		this.setValueType("M");
	}

	@Override
    public Object changeValue()
    {
	    return toManyEntities();
    }
	
}
