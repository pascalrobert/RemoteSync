importPackage(
	org.apache.http.impl.client,
	org.apache.http.client.methods,
    org.apache.commons.httpclient.methods,
	org.apache.http.client.entity,
	org.apache.http.client.utils,
	org.apache.http.message,
	org.apache.http.protocol,
    org.apache.http.params,
	org.apache.http.util
);
importPackage(
    java.util,
    java.io
);

// read in the properties
var properties = LoadProperties( "scripts/sync", "ersync.properties" );
var principal = properties.getProperty("principalUUID");
var lastSync = properties.getProperty("lastSync");
var protocol = properties.getProperty("protocol");
var host = properties.getProperty("host");
var port = properties.getProperty("port");
var uri = properties.getProperty("uri");
var verbose = (properties.getProperty("verbose") == 'true');;

if ( verbose ) println( "working with " + principal + " on last sync date " + lastSync);

var source = ReadFile( "scripts/sync/", "sync5.xml" );
source = source.replace( "#PUID#", principal );
source = source.replace( "#lastSync#", lastSync );
if ( verbose ) println( source );

var httpclient = new DefaultHttpClient();
var url = URIUtils.createURI( protocol, host, port, uri + "/sync/fast/full", null, null);
var authpost = new HttpPost(url);

authpost.setEntity(new org.apache.http.entity.StringEntity(source, "text/xml", "UTF-8") );

response = httpclient.execute(authpost);

var str = EntityUtils.toString(response.getEntity());
if ( verbose ) println(str);

var doc = ParseXMLString(str);

var nodeLst = doc.getElementsByTagName("lastSync");
if ( nodeLst.getLength() == 1 )
{
	var lastSyncNode = nodeLst.item(0);
	var lastSyncChildren = lastSyncNode.getChildNodes();

	if ( lastSyncChildren.item(0) != null )
	{
		properties.setProperty( "lastSync", lastSyncChildren.item(0).getNodeValue() );
		if ( verbose ) println("lastSync " + lastSyncChildren.item(0).getNodeValue() );
		StoreProperties(properties, "scripts/sync", "ersync.properties" );
	}
}

