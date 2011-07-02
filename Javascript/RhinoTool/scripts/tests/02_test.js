
message("This is 02_test.js calling Debug.js function message()");

importPackage(java.io, java.net);

var urlStrs = ["http://static.flickr.com/119/292857754_1b153997bc_m.jpg", "http://www.bankofcanada.ca/en/markets/csv/exchange_eng.csv"];


for (var i=0; i < urlStrs.length; i++) 
{
    var urlStr = urlStrs[i];
    var url = new URL(urlStr);
    var r = url.openStream();

    var filename = "imgs" + urlStr.substring(urlStr.lastIndexOf("/"));
    var w = CreateFileoutputStream( "/tmp", urlStr.substring(urlStr.lastIndexOf("/")) );
    
    var b;
    while ((b = r.read()) > -1) 
    {
        w.write(b);
    }
    w.flush();
    w.close();
}

