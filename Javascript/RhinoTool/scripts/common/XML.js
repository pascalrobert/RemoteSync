
/*
	Networking functions
	$Id: $ 
*/

importPackage(
	javax.xml.parsers,
	org.w3c.dom
);

/* return XML Document */ 
function ParseXMLString( /* string */ xml )
{
  var dbf = DocumentBuilderFactory.newInstance();
  var db = dbf.newDocumentBuilder();
  return db.parse( new ByteArrayInputStream(xml.getBytes()) );
}