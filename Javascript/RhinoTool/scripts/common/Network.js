
/*
	Networking functions
	$Id: $ 
*/

var ETL = {
    port: 22,
    host: "orange.lab.ticoon.com",
    dest: "/home/aspinalld/etl",
    username: "daspinall",
    password: "sam2242",
    knownhosts: "knownhosts.txt"
};

importPackage(java.io, com.jcraft.jsch);

function sshCreateChannel( /*ssh session*/ session,  /* string */ command )
{
    var channel = session.openChannel("exec");
    channel.setCommand(command);
    return channel;
}

function checkAck(/* InputStream */ sshIn )
{
    var b = sshIn.read();
    
    // b may be
    // 0 for success,
    // 1 for error,
    // 2 for fatal error,
    
    if (b != 0)
    {
        var msg = "";
        var c;

        do
        {
            c = new Packages.java.lang.Character(sshIn.read());
            print( c );
            msg += c;
        }
        while (c != '\n');

        if (b == 1)
        {
            message("ack: " + b + " scp failed with an error - reason: " + msg);
        }
        else if (b == 2)
        {
            message("ack: " + b + " scp failed with a fatal error - reason: " + msg );
        }
        else
        {
            message("ack: " + b + " scp failed with an unknown error - reason: " + msg );
        }
    }
    return b;
}

function sendAck( /*OutputStream*/ sshOut)
{
    sshOut.write(0);
    sshOut.flush();
}

function ssh_upload(/* string */ filename)
{
    var session = null;
    var channel = null;
    var fis = null;
    var sshIn = null;
    var sshOut = null;

    var file = new File( filename );
    var remotename = filename;

    if (ETL.dest != null)
    {
        var separator = File.separator;
        
        if ( filename.lastIndexOf(separator) > 0 )
        {
            remotename = ETL.dest + separator + filename.substring(filename.lastIndexOf(separator) + 1, filename.length());
        }
        else
        {
            remotename = ETL.dest + separator + filename;
        }
    }
    
    var command = "scp -p -t " + remotename;
    try
    {
        var jsch = new JSch();
        jsch.setKnownHosts(ETL.knownhosts);
        
        session = jsch.getSession(ETL.username, ETL.host, ETL.port);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setPassword( ETL.password );
        session.connect();
        
        message( "Open channel: " + command );
        channel = sshCreateChannel( session, command );

        message( "opening files" );
        
        fis = new FileInputStream(filename);
        sshOut = new OutputStreamWriter(channel.getOutputStream());
        sshIn = channel.getInputStream();

        message( "Connecting .." );
        channel.connect();

        if (checkAck(sshIn) != 0)
        {
            /* error */
            message( "Error" );
        }

        // command remote name must not contain path information
        command = "C0644 " + file.length() + " " + remotename.substring(remotename.lastIndexOf(separator) + 1) + "\n";
        
        message( "Command: " + command );
        
        sshOut.write(command, 0, command.length);
        sshOut.flush();

        if (checkAck(sshIn) != 0)
        {
            /* error */
            message( "Error" );
        }

        /* send the file */
        var bytes;
        while ((bytes = fis.read()) > -1) 
        {
            sshOut.write( bytes );
        }
        sendAck( sshOut );
        
        if (checkAck(sshIn) != 0)
        {
            /* error */
            message( "Error" );
        }
    } 
    catch( error )
    {
        message( "caught error " + error );
    }
    finally
    {
        if ( sshOut )   sshOut.close();
        if ( sshIn )    sshIn.close();
        if ( fis )      fis.close()
        if ( channel )  channel.disconnect();
        if ( session )  session.disconnect();
    }
}

