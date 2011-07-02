package er.sync.eo;

import org.apache.log4j.Logger;

import com.webobjects.eocontrol.EOEditingContext;

public class ERSyncClientDevice extends _ERSyncClientDevice 
{
	/**
     * 
     */
    private static final long serialVersionUID = 2872814723839222792L;

    @SuppressWarnings("unused")
    private static Logger log = Logger.getLogger(ERSyncClientDevice.class);

	public void awakeFromInsertion( EOEditingContext ec ) 
	{
		super.awakeFromInsertion( ec );
		setType( TYPE_ENUM.X.name() );
	}

}
