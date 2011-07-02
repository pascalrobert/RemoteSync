
message("This is 01_test.js calling Debug.js function message()");
message("Calling Debug.js function message() with title '01_test.js'", "01_test.js");

message( "Creating output file /tmp/ 01_test.txt" );


var content = "<HTML><HEADER><TITLE>File Index - directory</TITLE><BODY>"; 
var fileio = CreateFileWriter("/tmp", "01_test.txt" );
fileio.write( content, 0, content.length );
fileio.close();

WriteStringToFile("/tmp", "01_test_str.txt", content );

