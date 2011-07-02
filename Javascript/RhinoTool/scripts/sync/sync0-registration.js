
// http://192.168.0.100:2010/cgi-bin/WebObjects/MobileNotesSync.woa
var SVC = {
    protocol: "http",
    port: 2010,
    host: "localhost",
	uri: "/cgi-bin/WebObjects/MobileNotesSync.woa/sync/register/new",
	junk: "register/check"
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


var httpclient = new DefaultHttpClient();
var url = URIUtils.createURI( SVC.protocol, SVC.host, SVC.port, SVC.uri, null, null);
var authpost = new HttpPost(url);

// authpost.addHeader("Content-type", "text/xml; charset=ISO-8859-1");

var input = new File("scripts/sync/sync0.xml");

authpost.setEntity(new org.apache.http.entity.FileEntity(input, "text/xml; charset=UTF-8") );

response = httpclient.execute(authpost);

entity = response.getEntity();

var str = EntityUtils.toString(entity);
println(str);

  var doc = ParseXMLString(str);

  //doc.getDocumentElement().normalize();
  println("Root element " + doc.getDocumentElement().getNodeName());
  var nodeLst = doc.getElementsByTagName("principalUUID");

  if ( nodeLst.getLength() == 1 )
  {
	var principalNode = nodeLst.item(0);
	var children = principalNode.getChildNodes();

	var prop = new Properties();
	prop.setProperty( "principalUUID", children.item(0).getNodeValue() );

	StoreProperties(prop, "scripts/sync", "ersync.properties" );
  }

