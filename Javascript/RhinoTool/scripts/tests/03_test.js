
var SVC = {
    protocol: "http",
    port: 8082,
    host: "ts0010.lab.ticoon.com",
    uri: "/"
};

var AUTH = {
    user: "aspinalld",
    pword: "Gr3iSTtK"
};

importPackage(
	org.apache.http.impl.client, 
	org.apache.http.client.methods, 
	org.apache.http.client.entity,
	org.apache.http.client.utils, 
	org.apache.http.message,
	org.apache.http.protocol
);
importPackage(java.util);

var httpclient = new DefaultHttpClient();
var url = URIUtils.createURI( SVC.protocol, SVC.host, SVC.port, SVC.uri, null, null);

var method = new HttpGet( url );

message( "httpclient = " + url );

var response = httpclient.execute(method);

var log = CreateFileWriter( "log", "login.html" );
if (isStatusOk(response) == true)
{
	var entity = response.getEntity();
	
	/* we dont care about the login screen */
	// entity.consumeContent();
	//logHttpResponse( log, response );
	printForms( response );
	
	var cookies = httpclient.getCookieStore().getCookies();
	if (cookies.isEmpty() == false) 
	{
	    for (i = 0; i < cookies.size(); i++) 
	    {
	        Appendln(log, "- " + cookies.get(i).toString() );
	    }
	}
	else
	    Appendln(log, "No Cookies" );
	
	url = URIUtils.createURI( SVC.protocol, SVC.host, SVC.port, "login.action", null, null);
	message( "httpclient = " + url );
	
	var authpost = new HttpPost(url);
	var authdata = new ArrayList();
	authdata.add(new BasicNameValuePair("os_username", AUTH.user));
	authdata.add(new BasicNameValuePair("os_password", AUTH.pword));
	
	authpost.setEntity(new UrlEncodedFormEntity(authdata, HTTP.UTF_8));
	
	response = httpclient.execute(authpost);
	if (isStatusOk(response) == true && findElement(response, "form", "action", "login.action" ) == null)
	{
		println( "Login succeed" );
	}
	else
	{
		println( "Login failed" );
	}
}


log.close();
