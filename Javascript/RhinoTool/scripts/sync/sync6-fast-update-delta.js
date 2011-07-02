
// http://192.168.0.100:2010/cgi-bin/WebObjects/MobileNotesSync.woa
var SVC = {
    protocol: "http",
    port: 2010,
    host: "localhost",
	uri: "/cgi-bin/WebObjects/MobileNotesSync.woa/sync/fast/full",
};

var AUTH = {
    user: "",
    pword: ""
};

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

var properties = LoadProperties( "scripts/sync", "ersync.properties" );
var principal = properties.getProperty("principalUUID");
var lastSync = properties.getProperty("lastSync");

println( "working with " + principal );

var source = ReadFile( "scripts/sync/", "sync6.xml" );
source = source.replace( "#PUID#", principal );
source = source.replace( "#lastSync#", lastSync );

var httpclient = new DefaultHttpClient();
var url = URIUtils.createURI( SVC.protocol, SVC.host, SVC.port, SVC.uri, null, null);
var authpost = new HttpPost(url);

authpost.setEntity(new org.apache.http.entity.StringEntity(source, "text/xml", "UTF-8") );

response = httpclient.execute(authpost);

entity = response.getEntity();
var str = EntityUtils.toString(entity);
println(str);

  var doc = ParseXMLString(str);

  //doc.getDocumentElement().normalize();
  println("Root element " + doc.getDocumentElement().getNodeName());
  var nodeLst = doc.getElementsByTagName("lastSync");

  if ( nodeLst.getLength() == 1 )
  {
	var principalNode = nodeLst.item(0);
	var children = principalNode.getChildNodes();

	properties.setProperty( "lastSync", children.item(0).getNodeValue() );

	StoreProperties(properties, "scripts/sync", "ersync.properties" );
  }


