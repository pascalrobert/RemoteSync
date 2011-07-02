package er.sync.eo;

import org.apache.log4j.Logger;

import com.webobjects.eocontrol.EOEditingContext;

public class ERSyncClientDeveloper extends _ERSyncClientDeveloper
{
	/**
     * 
     */
    private static final long serialVersionUID = -8509857190302847268L;
    
	@SuppressWarnings("unused")
    private static Logger log = Logger.getLogger(ERSyncClientDeveloper.class);
  
	public void awakeFromInsertion( EOEditingContext ec ) 
	{
		super.awakeFromInsertion( ec );
		setType( TYPE_ENUM.D.name() );
	}

}
