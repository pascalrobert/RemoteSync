package net.global_village;

import java.io.*;

import net.global_village.Arguments;
import net.global_village.Arguments.*;

import javax.script.*;
import com.sun.script.javascript.*;


public class RhinoTool
{   
	boolean verboseTool = false;

    public static void main(String[] args) throws Exception 
	{
		Arguments arguments = new Arguments();
		
		arguments.addOption( "-verbose", " verbose output", Constraint.OPTIONAL, Params.NONE );
		arguments.addOption( "-script", " path to javascript document", Constraint.REQUIRED, Params.ONE );
		arguments.addOption( "-library", " path to javascript library directory", Constraint.OPTIONAL, Params.ONE );
		
		arguments.parse(args);

		boolean verbose = false;
		Boolean[] verboseArgs = (Boolean[])arguments.optionForKey( "-verbose" );
		if (verboseArgs != null && verboseArgs.length == 1 )
			verbose = verboseArgs[0].booleanValue();

		File[] library = null;
		Object[] libargs = arguments.optionForKey( "-library" );
		if (libargs != null && libargs.length == 1 && libargs[0] instanceof String )
		{
			String path = (String)libargs[0];
			File lib = new File( path );
			
			if ((lib.exists() == true) && ( lib.isDirectory() == true))
			{
				FilenameFilter filter = new FilenameFilter() 
				{
					public boolean accept(File dir, String name) 
					{
						return name.endsWith( ".js" );
					}
				};
				
				library = lib.listFiles(filter);
			}
			else 
			{
				throw new RuntimeException( "Library path is not a directory [" + path + "]" );
			}
		}
		
		Object[] scriptargs = arguments.optionForKey( "-script" );
		File script = null;
		if (scriptargs != null && scriptargs.length == 1 && scriptargs[0] instanceof String )
		{
			script = new File((String)scriptargs[0]);
		}
		else 
		{
			throw new RuntimeException( "Script path is not a provided" );
		}

		RhinoTool rhino = new RhinoTool(verbose);
		try
		{
			rhino.process(script, library);
        }
//		catch (RhinoException rex) 
//		{
//			System.err.println( rex.message() );
//        }
		catch (VirtualMachineError ex) 
		{
            // Treat StackOverflow and OutOfMemory as runtime errors
            ex.printStackTrace();
			System.err.println( ex.toString() );
        }
		catch (Exception e)
		{
			System.err.println( e.toString() );
		}
		finally 
		{
			// cleanup?
		}
	}
	
	public RhinoTool(boolean v)
	{
		super();
		verboseTool = v;
	}
	
	public void process(File script, File[] library ) throws Exception
	{		
        // create a JavaScript engine
		ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");

		if ( library != null && library.length > 0)
		{
			for (int i = 0; i != library.length; ++i) 
			{
				if ( verboseTool == true ) System.out.println( "Reading library script [" + library[i].getAbsolutePath() + "]" );
				readScript(engine, library[i] );
			}
		}
		
		if ( verboseTool == true ) System.out.println( "" );
		System.out.println( "Running main script [" + script + "]\n" );
        readScript( engine, script );
    }
	
	private void readScript(ScriptEngine engine, File file ) throws Exception
	{
		if (file.isFile() == false)
		{
			throw new RuntimeException( "	Error, not a file [" + file.getAbsolutePath() + "]" );
        }
		
		InputStreamReader r = new InputStreamReader(new FileInputStream(file));
		engine.put(ScriptEngine.FILENAME, file.getName() );
		engine.eval(r);
		r.close();
	}
}



