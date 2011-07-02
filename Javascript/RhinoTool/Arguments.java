/*
 * Arguments.java
 *
 * This class is public domain software - that is, you can do whatever you want
 * with it, and include it software that is licensed under the BSD license
 *
 * If you make modifications to this code that you think would benefit the
 * wider community, please send me a copy and I'll post it on my site.
 *
 */

package net.global_village;

import java.util.*;

/**
 * Global Village Consulting
 * @author	David Aspinall
 */
public class Arguments extends Object
{
	public enum Constraint { REQUIRED, OPTIONAL }
	public enum Params { NONE, ONE, MULTIPLE }

    public static final String OTHER = "other";
    
    Hashtable<String, Arg> optionDict = new Hashtable<String, Arg>();
    
    /** Standard Constructor */
    public Arguments()
    {
        super();
    }

    public void addOption( String key, String desc )
    {
        addOption( key, desc, Constraint.OPTIONAL, Params.MULTIPLE );
    }
    
    public void addOption( String key, String desc, Constraint required, Params opts )
    {
        if ( key == null )
        {
            throw new RuntimeException( "you must specity a flag for this option" );
        }

        Arg arg = new Arg( key, desc, required, opts );
        optionDict.put( key, arg );
    }

    public Object[] optionForKey( String key )
    {
        if ( key == null )
        {
            throw new RuntimeException( "you must specity a flag for this option" );
        }
        Arg arg = (Arg)optionDict.get(key);
        return (arg != null ? arg.values() : null);
    }
    
    public class Arg extends Object
    {
        public String flag = null;
        public ArrayList<Object> val = null;
        public String desc = null;
        public Constraint required = Constraint.OPTIONAL;
        public Params opts = Params.NONE;

        public Arg( String f, String d )
        {
            this(f, d, Constraint.OPTIONAL, Params.MULTIPLE);
        }

        public Arg( String f, String d, Constraint r, Params v )
        {
            super();
            flag = f;
            desc = d;
            required = r;
            opts = v;
        }
		
		public Object[] values()
		{
			return (val == null ? null : val.toArray() );
		}
		
		public void addValue(Object value)
		{
			if ( val == null )
			{
				val = new ArrayList<Object>();
				val.add( value );
			}
			else if ( opts == Params.MULTIPLE )
			{
				val.add( value );
			}
			else
			{
				System.err.println("Current value " + values()[0] + " and " + value);
				throw new RuntimeException( "Multiple values for " + flag );
			}
		}

		public String toString()
		{
			StringBuffer buf = new StringBuffer();
			
			buf.append("\t" + flag + "= " );
			if ( val != null )
			{
				buf.append( values().toString() );
			}
			
			return buf.toString();
		}
		
    }

    public String toString()
    {
        StringBuffer buf = new StringBuffer();

        Collection options = optionDict.values();
		Object[] args = options.toArray();
		
        for ( int i = 0; i < args.length; i++ )
        {
            Arg item = (Arg)args[i];

            buf.append("\t" + item.flag + "= " );
            if ( item.val != null )
            {
                buf.append( item.values().toString() );
            }
            buf.append( "\n" );
        }
        
        return buf.toString();
    }

	public void usage()
	{
		usage(null);
	}

    public void usage(String message)
    {
		if ( message != null )
			System.out.println( message );
			
        System.out.println("usage: " );

        Collection options = optionDict.values();
		Object[] args = options.toArray();

        for ( int i = 0; i < args.length; i++ )
        {
            Arg item = (Arg)args[i];
            System.out.println("\t " + item.flag + "\t\t" + item.desc + " (" + item.required + ")" );
        }
        System.out.println("\t " + "-h\t\tdisplays this help" );
        System.exit(-1);
    }

    public void parse(String[] arguments)
    {
		int count = arguments.length;
		int index = 0;
        while (index < count )
        {
			String key = arguments[index];

			if (key.equals( "-h" ) == true)
			{
				usage();
			}
			
			Arg item = (Arg)optionDict.get(key);
			if ( item != null )
			{
				index++;
				if ( item.opts == Params.NONE )
				{
					item.addValue(Boolean.TRUE);
				}
				else 
				{
					String par = arguments[index];
					while (optionDict.get(par) == null)
					{
						item.addValue( par );
						index++;
						if (index >= count)
							break;
						
						par = arguments[index];
					}
				}
			}
			else
			{
				usage( "Invalid option '" + key + "'" );
			}
        }
		
		// ensure all required arguments are present
        Collection options = optionDict.values();
		Object[] args = options.toArray();
		
        for ( int i = 0; i < args.length; i++ )
        {
            Arg item = (Arg)args[i];
			if ( item.required != Constraint.OPTIONAL )
			{
				if ( item.values() == null )
					usage( "Missing required argument '" + item.flag + "'" );
			}
        }
    }    
}
