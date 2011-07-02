package er.sync.eo;

import org.apache.log4j.Logger;

import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.foundation.NSDictionary;

public class ERSyncChangeToOne extends _ERSyncChangeToOne 
{
	/**
     * 
     */
    private static final long serialVersionUID = -4573067921510491080L;
	private static Logger log = Logger.getLogger(ERSyncChangeToOne.class);

	public void awakeFromInsertion( EOEditingContext ec ) 
	{
		super.awakeFromInsertion( ec );
		this.setValueType("R");
	}

	@Override
    public Object changeValue()
    {
	    return this.toOneValue();
    }
}
