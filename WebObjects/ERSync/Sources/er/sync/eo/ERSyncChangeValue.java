package er.sync.eo;

import org.apache.log4j.Logger;

import com.webobjects.eoaccess.EOAttribute;
import com.webobjects.eoaccess.EOProperty;
import com.webobjects.eoaccess.EORelationship;
import com.webobjects.eocontrol.EOEditingContext;
import com.webobjects.eocontrol.EOEnterpriseObject;
import com.webobjects.foundation.NSArray;
import com.webobjects.foundation.NSTimestamp;

public abstract class ERSyncChangeValue extends _ERSyncChangeValue 
{
	/**
     * 
     */
    private static final long serialVersionUID = 923820500623539249L;
	private static Logger log = Logger.getLogger(ERSyncChangeValue.class);
	
	public abstract Object changeValue();
	
	public static ERSyncChangeValue createERSyncChangeValue(EOEditingContext ec, EOProperty attr, ERSyncEntity syncEntity, ERSyncChangeset changeSet)
	{
		ERSyncChangeValue changeEO = null;
		
		if ( attr instanceof EOAttribute )
		{
			switch ( ((EOAttribute)attr).adaptorValueType())
			{
				case EOAttribute.AdaptorBytesType:
					break;
				case EOAttribute.AdaptorDateType:
					changeEO = ERSyncChangeTimestamp.createERSyncChangeTimestamp(ec, attr.name(), syncEntity, changeSet);
					break;
				case EOAttribute.AdaptorNumberType:
					char c = ((EOAttribute)attr).valueType().charAt( 0 );
					switch (c)
					{
						case EOAttribute._VTByte:
						case EOAttribute._VTShort:
						case EOAttribute._VTInteger:
							changeEO = ERSyncChangeInteger.createERSyncChangeInteger(ec, attr.name(), syncEntity, changeSet);
							break;

						case EOAttribute._VTBoolean:
							changeEO = ERSyncChangeBoolean.createERSyncChangeBoolean(ec, attr.name(), syncEntity, changeSet);
							break;
					}
					break;
				case EOAttribute.AdaptorCharactersType:
					changeEO = ERSyncChangeString.createERSyncChangeString(ec, attr.name(), syncEntity, changeSet );
					break;
			}
		}
		else if ( attr instanceof EORelationship )
		{
			if ( ((EORelationship)attr).isToMany() == false )
			{
				changeEO = ERSyncChangeToOne.createERSyncChangeToOne(ec, attr.name(), syncEntity, changeSet);
			}
			else
			{
				changeEO = ERSyncChangeToMany.createERSyncChangeToMany(ec, attr.name(), syncEntity, changeSet);
			}
		}
		
		return changeEO;
	}
	
	public void updateChangeValue( EOProperty attr, EOEnterpriseObject eo )
	{
		if ( attr instanceof EOAttribute )
		{
			switch (((EOAttribute)attr).adaptorValueType())
			{
				case EOAttribute.AdaptorBytesType:
					break;
				case EOAttribute.AdaptorDateType:
					((ERSyncChangeTimestamp)this).setTimestampValue((NSTimestamp)eo.valueForKey(attr.name()));
					break;
				case EOAttribute.AdaptorNumberType:
					char c = ((EOAttribute)attr).valueType().charAt( 0 );
					switch (c)
					{
						case EOAttribute._VTByte:
						case EOAttribute._VTShort:
						case EOAttribute._VTInteger:
							((ERSyncChangeInteger)this).setIntValue((Integer)eo.valueForKey(attr.name()));
							break;
					}
					break;
				case EOAttribute.AdaptorCharactersType:
				default: // default is string
					((ERSyncChangeString)this).setStringValue((String)eo.valueForKey(attr.name()).toString());
					break;
			}
		}
		else if ( attr instanceof EORelationship )
		{
			if ( ((EORelationship)attr).isToMany() == false )
			{
				EOEnterpriseObject dest = (EOEnterpriseObject)eo.valueForKey( attr.name() );
				ERSyncEntity destEntity = ERSyncEntity.findOrCreateForEO(eo.editingContext(), dest);
				((ERSyncChangeToOne)this).setToOneValueRelationship(destEntity);
			}
			else
			{
				NSArray list = (NSArray)eo.valueForKey( attr.name() );
				NSArray<ERSyncEntity> syncEntityList = ERSyncEntity.findOrCreateForEOArray(eo.editingContext(), list);
				NSArray<ERSyncEntity> existing = ((ERSyncChangeToMany)this).toManyEntities();
				
				// remove any existing that are not in the update list
				for ( ERSyncEntity item : existing )
				{
					if ( syncEntityList.containsObject(item) == false )
					{
						item.editingContext().deleteObject(item);
					}
				}

				// add any from the syncEntityList to the relation
				for ( ERSyncEntity item : syncEntityList )
				{
					if ( ((ERSyncChangeToMany)this).toManyEntities().containsObject(item) == false )
					{
						((ERSyncChangeToMany)this).addToToManyEntitiesRelationship(item);
					}
				}
			}
		}
	}
}
