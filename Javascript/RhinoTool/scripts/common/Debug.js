
/*
	Debugging functions
	$Id: Debug.js 11272 2009-08-07 19:38:06Z daspinall $ 
*/

function message(msg)
{
	message( msg, null );
}

function message( msg, title)
{
	var bar = ""; //"**********\n";

	var foo = bar 
	if ( title != null )
		foo += "** " + title + bar + "\n";

	foo += msg + bar;

	println( foo );
}

function typeOf(obj)
{
	type = typeof obj;
	return type === "object" && !obj ? "null" : type;
}

function introspect (name, obj, indent, levels) 
{
	indent = indent || "";
	if (this.typeOf(levels) !== "number")
		levels = 1;

	var objType = this.typeOf(obj);
	var result = [indent, name, " ", objType, " :"].join('');

	if (objType === "object") 
	{
		if (levels > 0) 
		{
			indent = [indent, "\t"].join('');
			for (prop in obj) 
			{
				var prop = this.introspect(prop, obj[prop], indent, levels - 1);
				result = [result, "\n", prop].join('');
			}
			return result;
		}
		else 
		{
			return [result, " ..."].join('');
		}
	}
	else if (objType === "null") 
	{
		return [result, " null"].join('');
	}
	else if (objType === "function") 
	{
		return result;
	}
	return [result, "= ", obj].join('');
}


function logHttpCookies( /* writer */ writer, /* httpclient */ httpclient )
{
    var cookies = httpclient.getCookieStore().getCookies();
    if (cookies.isEmpty() == false) 
    {
        for (i = 0; i < cookies.size(); i++) 
        {
            Appendln(writer, "- " + cookies.get(i).toString() );
        }
    }
    else
        Appendln(writer, "- No Cookies" );
}

function logHttpResponse( /* writer */ writer, /* httpresponse */ response )
{
    var entity = response.getEntity();
    var stream = entity.getContent();

    var bytes;
    while ((bytes = stream.read()) > -1) 
    {
        writer.write( bytes );
    }
}