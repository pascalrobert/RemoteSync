
/*
	HTML functions
	$Id: Debug.js 11272 2009-08-07 19:38:06Z daspinall $ 
*/

var FORM_ATTRIBUTES = new Array("id", "lang", "style", "title", "target", "action", "enctype", "accept-charset", "accept", "name");

importPackage(net.htmlparser.jericho);
importPackage(org.apache.http.impl.client, org.apache.http.client.methods, org.apache.http.client.utils);

function isStatusOk(/* httpresponse */ response)
{
	var status = response.getStatusLine();
	if (status != null)
	{
		if (status.getStatusCode() >= 200 && status.getStatusCode() < 300)
		{
			println("Good status: " + response.getStatusLine().toString() );
			return true;
		}
		else
		{
			println("Bad status: " + response.getStatusLine().toString() );
		}
	}
	else
		println( "no status from response" );
	return false;
}

function findElement(/* httpresponse */ response, 
	/* html element name */htmlname, 
	/* attribute */ attr, 
	/* attribute value */ attrval )
{
    var entity = response.getEntity();
    var stream = entity.getContent();

    return findElementFromSource(new Source( stream ));
}

function findElementFromSource(/* Source */ source, 
	/* html element name */htmlname, 
	/* attribute */ attr, 
	/* attribute value */ attrval )
{
    var elementArray = source.getAllElements( htmlname );
    if (elementArray.size() > 0) 
    {
        for (i = 0; i < elementArray.size(); i++) 
        {
        	var aElement = elementArray.get(i);
        	var elementAttrValue = aElement.getAttributeValue( attr );
        	if ( elementAttrValue == attrval )
        	{
        		return aElement;
        	}
        }
    }
    return null;
}

function printForms(/* httpresponse */ response)
{
    var log = CreateFileWriter( "log", "test.txt" );

    var entity = response.getEntity();
    var stream = entity.getContent();

    printFormsFromSource(new Source( stream ));
}

function printFormsFromSource(/* Source */ source )
{
    message( "Encoding " + source.getPreliminaryEncodingInfo() );
    message( "getEncodingSpecificationInfo " +  source.getEncodingSpecificationInfo() );
    message( "getEncoding " +  source.getEncoding() );
    
    var forms = source.getAllElements(HTMLElementName.FORM); /**/
    message( "Forms: " + forms );
    if (forms.size() > 0) 
    {
        for (i = 0; i < forms.size(); i++) 
        {
        	var aForm = forms.get(i);
        	println ("\n* * * * * * [" + aForm.getName() + "] " + i );
        	
        	if ( aForm.getName() == "form" )
        	{
	        	var formAttr = aForm.getAttributes();
	
				for ( fa = 0; fa < FORM_ATTRIBUTES.length; fa++)
				{
					var faValue = aForm.getAttributeValue( FORM_ATTRIBUTES[fa] );
					if ( faValue != null )
					{
						println( "\t" + FORM_ATTRIBUTES[fa] + " = '" + faValue + "'" )
					}
				}
	        	
			    var formFields = aForm.getFormFields();
			    var labels = formFields.getColumnLabels();
	
			    if (labels.length > 0) 
			    {
			        for (j = 0; j < labels.length; j++) 
			        {
			            var ff = formFields.get(labels[j]);
			            var fc = ff.getFormControl();
			            var fct = fc.getFormControlType();
			            			            
			            print( "\t[" + fct.getElementName() + "] " + fct.name() );
			            print( "\t'" + fc.getName() + "' = '" + fc.getValues() + "'"  );
			            println( "" );
//			            println( "\t" + fc.getDebugInfo() );
			        }
			    }
			    else
			        Appendln(log, "No labels" );
        	}
        }
    }
}