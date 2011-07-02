/*
 * ERXSyncUtilities.java
 *
 * This class is public domain software - that is, you can do whatever you want
 * with it, and include it software that is licensed under the BSD license
 *
 * If you make modifications to this code that you think would benefit the
 * wider community, please send me a copy and I'll post it on my site.
 *
 */

package er.sync;

import java.math.BigDecimal;

import com.webobjects.eoaccess.EOAttribute;
import com.webobjects.eoaccess.EOEntity;
import com.webobjects.eoaccess.EOModelGroup;
import com.webobjects.eoaccess.EOUtilities;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.eocontrol.EOFetchSpecification;
import com.webobjects.eocontrol.EOGlobalID;
import com.webobjects.eocontrol.EOKeyGlobalID;
import com.webobjects.eocontrol.EOQualifier;
import com.webobjects.eocontrol.EOQualifierEvaluation;
import com.webobjects.eocontrol.EOSortOrdering;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSComparator;
import com.webobjects.foundation.NSDictionary;
import com.webobjects.foundation.NSLog;
import com.webobjects.foundation.NSMutableArray;
import com.webobjects.foundation.NSMutableDictionary;
import com.webobjects.foundation.NSSelector;

/**
 * Global Village Consulting
 * @author	David Aspinall
 */
public class ERXSyncUtilities
{
	public static final String UI_HASH_SEPARATOR = "-";
	public static final String OWNER_HASH_SEPARATOR = ":";

    /**
     * <p>Sort an array of key-value objects.</p>
     * 
     * @param arrayToSort Array to sort
     * @param keyPath keypath to sort the array
     * @param direction Array to sort, defaults to CompareCaseInsensitiveAscending
     * @return a sorted array or the original array if an error occurs 
     */
    @SuppressWarnings("unchecked")
    public static NSArray SortedArrayForKeypath( NSArray arrayToSort, String keypath, NSSelector direction)
	{
        NSArray orderedArray = arrayToSort;
        if ((arrayToSort != null) && (arrayToSort.count() > 0))
        {
            if (keypath != null)
            {
				if ( direction == null )
				{
					direction = EOSortOrdering.CompareCaseInsensitiveAscending;
				}
                EOSortOrdering so = EOSortOrdering.sortOrderingWithKey(keypath, direction);

                orderedArray = SortedArrayForOrder( arrayToSort, new NSArray( so ));
            }
        }

        return orderedArray;
	}

    /**
     * <p>Sort an array using EOSortOrdering</p>
     * 
     * @param arrayToSort Array to sort
     * @param sortOrder Array of EOSortOrder
     * @return a sorted array or the original array if an error occurs 
     */
	@SuppressWarnings("unchecked")
    public static NSArray SortedArrayForOrder( NSArray arrayToSort, NSArray sortOrder )
    {
        NSArray orderedArray = arrayToSort;

        if ((arrayToSort != null) && (arrayToSort.count() > 1))
        {
			if ((sortOrder != null) && (sortOrder.count() > 0))
			{
				orderedArray = EOSortOrdering.sortedArrayUsingKeyOrderArray( arrayToSort, sortOrder );
			}
        }
        return orderedArray;
    }

    /**
     * <p>Sort an array of strings.</p>
     * 
     * @param stringArray Array to sort
     * @return a sorted array or the original array if an error occurs 
     */
	@SuppressWarnings("unchecked")
    public static NSArray SortedStrings( NSArray stringArray )
	{
		NSArray sorted = stringArray;
		if ((stringArray != null ) && (stringArray.count() > 1))
		{
			try
			{
				NSArray ss = stringArray.sortedArrayUsingComparator( NSComparator.AscendingCaseInsensitiveStringComparator );

				/** make sure the return value is not nuke'd by an exception */
				sorted = ss;
			}
			catch ( Exception e )
			{
				NSLog.debug.appendln(e);
			}
		}
		return sorted;
	}

	@SuppressWarnings("unchecked")
    private static NSDictionary _makePrimaryKeyDictionary( NSArray values, NSArray pkAttrs )
	{
		NSMutableDictionary	pkDict = new NSMutableDictionary();

		NSArray sortedAttr = SortedArrayForKeypath( pkAttrs, "name", null);
		if (values.count() != sortedAttr.count() )
		{
			throw new RuntimeException( "Cannot recontruct PK dictionary when value count != key count" );
		}

		int cnt = values.count();
		for ( int i = 0; i < cnt; i++ )
		{
			EOAttribute att = (EOAttribute)sortedAttr.objectAtIndex( i );
			String key = att.name();
			char c = att.valueType().charAt( 0 );
			Object pkValue = values.objectAtIndex( i );
			
			if ( pkValue != null )
				pkValue = ((String)pkValue).trim();
			
			switch (c)
			{
				case 99: // 'c'
				case 105: // 'i'
					pkValue = Integer.valueOf( (String)pkValue );
					break;

				case 115: // 's'
					pkValue = Short.valueOf( (String)pkValue );
					break;

				case 108: // 'l'
					pkValue = Long.valueOf( (String)pkValue );
					break;

				case 102: // 'f'
					pkValue = Float.valueOf( (String)pkValue );
					break;

				case 100: // 'd'
					pkValue = Double.valueOf( (String)pkValue );
					break;

				case 66: // 'B'
					pkValue = new BigDecimal( (String)pkValue );
					break;

				default: // default is string
					break;
			}

			pkDict.setObjectForKey( pkValue, key );
		}
		return pkDict;
	}

	public static String entityNameForOwnerHashCode(String owner)
	{
		String eName = null;
		if ((owner == null) || (owner.length() < 3 ))
		{
			throw new RuntimeException( "Owner Code is required." );
		}

		int index = owner.indexOf( OWNER_HASH_SEPARATOR );
		if ((index > 0) && (index + 1 < owner.length()))
		{
			eName = owner.substring( 0, index );
		}
		else
		{
			throw new RuntimeException( "Invalid Owner Code." );
		}

		return eName;
	}
	
	@SuppressWarnings("unchecked")
    public static EOEnterpriseObject FaultForOwnerHashCode(EOEditingContext ec, String owner)
	{
		EOEnterpriseObject fault = null;
		if ( ec == null )
		{
			throw new RuntimeException( "Editing Context is required." );
		}

		EOModelGroup group = EOModelGroup.defaultGroup();
		EOEntity entity = group.entityNamed( entityNameForOwnerHashCode(owner) );

		if ( entity != null )
		{
			int index = owner.indexOf( OWNER_HASH_SEPARATOR );
			NSArray pkValues = NSArray.componentsSeparatedByString( owner.substring( index + 1 ),  UI_HASH_SEPARATOR );
			NSDictionary pk = _makePrimaryKeyDictionary( pkValues, entity.primaryKeyAttributes());

			try
			{
				fault = EOUtilities.faultWithPrimaryKey( ec, entity.name(), pk );
			}
			catch ( Exception e )
			{
				NSLog.debug.appendln(e);
			}
		}
		
		return fault;
	}

	
	public static EOEntity entity(EOKeyGlobalID gid)
    {
        return EOModelGroup.defaultGroup().entityNamed(gid.entityName());
    }

    public static EOGlobalID globalID(EOEnterpriseObject eo)
    {
        EOGlobalID gid = null;
        if ( eo.editingContext() != null )
        {
            gid = (EOGlobalID)eo.editingContext().globalIDForObject( eo );
        }
        return gid;
    }

    @SuppressWarnings("unchecked")
    public static NSDictionary primaryKey(EOGlobalID gid)
    {
        NSDictionary pk = null;
        
        if ((gid != null) && (gid.isTemporary() == false))
        {
            EOKeyGlobalID key_gid = (EOKeyGlobalID)gid;
            pk = entity(key_gid).primaryKeyForGlobalID( key_gid );
        }
        return pk;
    }

    /**
     * <p>Generates a string consisting of the primary key values as a string.  Multikey primary keys are always in keyname sorted order.</p>
     * 
     * @return a string representing the primary key 
     */
	public static String syncHashCodeFromGID(EOGlobalID gid)
	{
		/** use this for temporary objects */
		String eoHashCoded = Integer.toString(gid.hashCode());
		
		NSDictionary pk = primaryKey(gid);
		if ( pk != null )
		{
			if (pk.count() == 1 )
			{
				eoHashCoded = pk.allValues().lastObject().toString();
			}
			else if ( pk.count() > 1 )
			{
				StringBuffer buf = new StringBuffer();
				
				/** sort them so we are predictable */
				NSArray keys = SortedStrings(pk.allKeys());
				for ( int i = 0; i < keys.count(); i++ )
				{
					String item = (String)keys.objectAtIndex(i);
					Object value = pk.objectForKey( item );

					buf.append( value.toString() );
					
					if ( i+1 < keys.count() )
						buf.append( UI_HASH_SEPARATOR );
				}

				eoHashCoded = buf.toString();
			}
		}
		return eoHashCoded;
	}

    /**
     * <p>The entity name and primary key encoded into a simple string.</p>
     * 
     * @return The entity name and primary key encoded into a simple string. 
     */
	public static String syncEOHashCode(EOEnterpriseObject eo)
	{
		return eo.entityName() + OWNER_HASH_SEPARATOR + syncHashCodeFromGID(globalID(eo));
	}

    /**
     * <p>The entity name and primary key encoded into a simple string.</p>
     * 
     * @return The entity name and primary key encoded into a simple string. 
     */
	public static String syncGIDHashCode(EOKeyGlobalID gid)
	{
		return gid.entityName() + OWNER_HASH_SEPARATOR + syncHashCodeFromGID(gid);
	}

	/** returns true if the current object is different from the database context snapshot */
	@SuppressWarnings("unchecked")
    public static boolean hasChangesFromSnapshot(EOEnterpriseObject eo)
	{
		NSDictionary snapshot = eo.editingContext().committedSnapshotForObject(eo);
		NSDictionary delta = eo.changesFromSnapshot(snapshot);
		return (delta != null) && (delta.count() > 0);
	}

	public static NSArray<EOKeyGlobalID> globalIDForQualifier( EOEditingContext ec, EOEntity entity, EOQualifier qualifier, boolean usesDistinct )
	{
		return globalIDForPKArray( entity, primaryKeysForQualifier( ec, entity, qualifier, usesDistinct ));
	}

	/** fetches a raw array of primary key dictionary records for the specified entity and qualifier.  Will also fetch additional attributes by name */
	public static NSArray primaryKeysForQualifier( EOEditingContext ec, EOEntity entity, EOQualifier qualifier, boolean usesDistinct ) 
	{
		if ( entity == null )
		{
			throw new RuntimeException( "please specify an entity" );
		}
		if ( ec == null )
		{
			throw new RuntimeException( "Please provide an EOEditingContext" );
		}
		
		EOFetchSpecification fs = new EOFetchSpecification(entity.name(), qualifier, null);
        fs.setFetchesRawRows( true );
		fs.setUsesDistinct( usesDistinct );
        fs.setRawRowKeyPaths(entity.primaryKeyAttributeNames());
		
		return ec.objectsWithFetchSpecification( fs );
	}

	/** converts an array of pk rows into an array of EOKeyGlobalID */
	public static NSArray<EOKeyGlobalID> globalIDForPKArray( EOEntity entity, NSArray pkArray ) 
	{
		if ( entity == null )
		{
			throw new RuntimeException( "please specify an entity" );
		}
		NSMutableArray<EOKeyGlobalID> gidArray = new NSMutableArray<EOKeyGlobalID>();
		if ( pkArray != null )
		{
			for ( int i = 0; i < pkArray.count(); i++ )
			{
			    NSDictionary pk = (NSDictionary)pkArray.objectAtIndex(i);
			    gidArray.addObject( (EOKeyGlobalID)entity.globalIDForRow( pk ) );
			}
		}
		return gidArray;
	}
	
	public static class EOEntityFilter implements EOQualifierEvaluation
	{
		private String entityName;
		public EOEntityFilter(String entityName)
		{
			super();
			this.entityName = entityName;
		}
		
        public boolean evaluateWithObject(Object object) 
        {
        	if ( object instanceof EOEnterpriseObject )
        	{
        		return ((EOEnterpriseObject)object).entityName().equals(entityName);
        	}
            return false;
        }
	}

}
