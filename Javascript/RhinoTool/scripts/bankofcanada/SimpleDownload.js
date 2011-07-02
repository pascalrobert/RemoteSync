
var BOC = {
    protocol: "http",
    port: 80,
    host: "www.bankofcanada.ca",
    uri: "/en/markets/csv/exchange_eng.csv",
    username: null,
    password: null
};

importPackage(org.apache.http.impl.client, org.apache.http.client.methods, org.apache.http.client.utils);

var httpclient = new DefaultHttpClient();
var url = URIUtils.createURI( BOC.protocol, BOC.host, BOC.port, BOC.uri, null, null);

var method = new HttpGet( url );

message( "httpclient = " + httpclient );

var response = httpclient.execute(method);
var entity = response.getEntity();

if (entity != null) 
{
    var local = WriteStreamToFile( "/tmp", BOC.uri, entity.getContent() );
    ssh_upload( local.getAbsolutePath() );
}

