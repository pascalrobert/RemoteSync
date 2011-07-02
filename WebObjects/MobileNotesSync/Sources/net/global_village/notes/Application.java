package net.global_village.notes;

import net.global_village.notes.sync.SyncAuthenticationProvider;

import com.webobjects.eoaccess.ERXEntityDependencyOrderingDelegate;

import er.extensions.appserver.ERXApplication;
import er.extensions.eof.ERXDatabaseContextMulticastingDelegate;
import er.sync.ERXSyncHandler;

public class Application extends ERXApplication {
	public static void main(String[] argv) {
		ERXApplication.main(argv, Application.class);
	}

	public Application() {
		ERXApplication.log.info("Welcome to " + name() + " !");
		/* ** put your initialization code in here ** */
        ERXDatabaseContextMulticastingDelegate.addDefaultDelegate(new ERXEntityDependencyOrderingDelegate());

		ERXSyncHandler syncHandler = new ERXSyncHandler();
		syncHandler.setSyncAuthenticator(new SyncAuthenticationProvider());

		ERXSyncHandler.register(syncHandler);
	}
}
