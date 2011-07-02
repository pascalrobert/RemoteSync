package er.sync.eo;

import org.apache.log4j.Logger;

import com.webobjects.eocontrol.EOEditingContext;

public class ERSyncClientApp extends _ERSyncClientApp {
  /**
     * 
     */
    private static final long serialVersionUID = 8648973008626094515L;

    @SuppressWarnings("unused")
    private static Logger log = Logger.getLogger(ERSyncClientApp.class);
  
	public void awakeFromInsertion( EOEditingContext ec ) 
	{
		super.awakeFromInsertion( ec );
		setType( TYPE_ENUM.A.name() );
	}
}
