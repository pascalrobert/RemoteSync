
/*
	File functions
	$Id: $ 
*/
importPackage(java.io, java.net, java.util);

function LoadProperties( /* string path */ dir, /* name */ filename )
{
	var prop = new Properties();
	prop.load( CreateFileinputStream( dir, filename ));
	return prop;
}

function StoreProperties( /* Properties */ prop, /* string path */ dir, /* name */ filename )
{
	prop.store( CreateFileoutputStream( dir, filename), null);
}

function CreateFileinputStream( /* string path */ dir, /* name */ filename)
{
    var file = CreateFile( dir, filename );
	return new FileInputStream(file);
}

function ReadFile( /* string path */ dir, /* string */ filename)
{
	var file = CreateFile( dir, filename );
    var reader = new BufferedReader( new FileReader (file));
    var string  = "";
    while( ( line = reader.readLine() ) != null ) 
	{
        string += line;
		string += "\n";
    }
    return string;
}

function CreateFile( /* string path */ dir, /* name */ filename)
{
    if (dir == null)
    {
        var outname = filename;
    }
    else
    {
        var separator = File.separator;
        var outname = dir + separator + filename.substring(filename.lastIndexOf(separator), filename.length);
    }
    return new Packages.java.io.File(outname);
}

function CreateFileoutputStream( /* string path */ dir, /* name */ filename)
{
    var file = CreateFile( dir, filename );
	return new FileOutputStream(file);
}

function CreateFileWriter( /* string path */ dir, /* name */ filename)
{
    var file = CreateFile( dir, filename );
	return new FileWriter(file);
}

function Append( /*Writer*/ writer, /*string*/ str )
{
    if ( str instanceof java.lang.String )
        writer.write( str, 0, str.length() );
    else
        writer.write( str, 0, str.length );
    writer.flush();
}

function Appendln( /*Writer*/ writer, /*string*/ str )
{
    Append( writer, str + "\n" );
}

function WriteStringToFile( /* string path */ dir, /* name */ filename, str)
{
    var fileio = CreateFileWriter( dir, filename );
    fileio.write( str, 0, str.length );
    fileio.flush();
    fileio.close();
}

function WriteDateToFile( /* string path */ dir, /* name */ filename, data)
{
    var fileio = CreateFileoutputStream( dir, filename );
    fileio.write( data );
    fileio.flush();
    fileio.close();
}

function WriteStreamToFile( /* string path */ dir, /* name */ filename, stream)
{
    var destfile = CreateFile( dir, filename );
    var fileio = CreateFileoutputStream( dir, filename );
    var bytes;
    while ((bytes = stream.read()) > -1) 
    {
        fileio.write( bytes );
    }

    fileio.flush();
    fileio.close();
    return destfile;
}
