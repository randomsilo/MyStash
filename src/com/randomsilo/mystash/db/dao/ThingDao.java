package com.randomsilo.mystash.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.randomsilo.mystash.db.MySQLiteHelper;
import com.randomsilo.mystash.db.pojo.BasePojo;
import com.randomsilo.mystash.db.pojo.ExpiringItem;
import com.randomsilo.mystash.db.pojo.FoundItem;
import com.randomsilo.mystash.db.pojo.Resource;
import com.randomsilo.mystash.db.pojo.Thing;
import com.randomsilo.mystash.db.pojo.ThingTotalCost;
import com.randomsilo.mystash.db.pojo.What;
import com.randomsilo.mystash.db.pojo.Why;

public class ThingDao extends BaseDaoDescriptor {

	public ThingDao(MySQLiteHelper dbHelper) {
		super(dbHelper);
	}

	@Override
	public String getTableName() {
		return "thing";
	}

	@Override
	public String[] getAllColumns() {
		String[] c = new String[] {
				getIdColumn()
				, "tag"
				, "description"
				, "actual_cost"
				, "replacement_cost"
		};
		return c;
	}

	@Override
	public String[] getCreateStatements() {
		String[] s = new String[] {
			"CREATE TABLE IF NOT EXISTS " + getTableName()
			+ "   (" + getIdColumn() + " " + COLUMN_ID_TYPE + " "
			+ "   , tag text not null "
			+ "   , description text "
			+ "   , actual_cost real "
			+ "   , replacement_cost real "
			+ "   )"
			, "CREATE INDEX IF NOT EXISTS idx_" + getTableName() +"__tag ON " + getTableName() +"(tag)"
			, "CREATE INDEX IF NOT EXISTS idx_" + getTableName() +"__description ON " + getTableName() +"(description)"
		};
		return s;
	}
	
	@Override
	public String[] getDropStatements() {
		String[] s = new String[] {
			"DROP TABLE IF EXISTS " + getTableName()
			, "DROP INDEX IF EXISTS idx_" + getTableName() + "__tag"
			, "DROP INDEX IF EXISTS idx_" + getTableName() + "__description"
		};
		return s;
	}

	@Override
	public <T extends BasePojo> ContentValues makeContentValues(T pojo) {
		Thing o = (Thing)pojo;
		ContentValues values = new ContentValues();
		
		if(o.getId() != null) { values.put(getIdColumn(), o.getId()); }
		values.put("tag", o.getTag());
		values.put("description", o.getDescription());
		values.put("actual_cost", o.getActualCost());
		values.put("replacement_cost", o.getReplacementCost());
		
		return values;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BasePojo> T cursorToPojo(Cursor cursor) {
		int column = 0;
		Thing o = new Thing();
		
		o.setId(cursor.getLong(column++));
		o.setTag(cursor.getString(column++));
		o.setDescription(cursor.getString(column++));
		o.setActualCost(cursor.getDouble(column++));
		o.setReplacementCost(cursor.getDouble(column++));
		
		return (T) o;
	}

	public Thing getThing(Long id) {
		Thing t = new Thing();
		
		t = (Thing)super.findById(id);
		
		return t;
	}
	
	public List<ExpiringItem> getExpiredItems( String containerName, String inPhrase, Integer daysBeforeNow) {
		List<ExpiringItem> list = new ArrayList<ExpiringItem>();
		
		Cursor cursor = null;

		try {

			String sql = this.getExpiredItemsSql( containerName, inPhrase, daysBeforeNow);
			cursor = database.rawQuery(sql, new String[]{});
	
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				ExpiringItem o = null;
				int column = 0;
				
				
				o = new ExpiringItem();
				o.setDateType(cursor.getString(column++));
				o.setIsoDate(cursor.getString(column++));
				o.setDateDifference(cursor.getLong(column++));
				o.setThingId(cursor.getLong(column++));
				o.setThingName(cursor.getString(column++));
				o.setParentName(cursor.getString(column++));
				o.setUserMessage(cursor.getString(column++));
				list.add(o);
				
				
				cursor.moveToNext();
			}
		} catch( Exception e) {
			Log.e("MyStash", "getExpiredItems", e);
		} finally {
			if (cursor != null) {
	            cursor.close();
			}
	    }

		return list;
	}
	
	private String getExpiredItemsSql(  String containerName, String inPhrase, Integer daysBeforeNow) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("select ");
		sb.append("    attention_date.tag as date_type ");
		sb.append("   , thing_attention_dates.valueDate as iso_date ");
		sb.append("   , cast( julianday(thing_attention_dates.valueDate) - julianday('now') as int) as date_difference ");
		sb.append("   , thing.id as thing_id ");
		sb.append("   , thing.tag as thing_name ");
		sb.append("   , IFNULL(parentThing.tag, '"+containerName+"') as parent_thing_name ");
		sb.append("   , thing.tag || ' "+inPhrase+" ' || IFNULL(parentThing.tag, '"+containerName+"') as user_message ");
		sb.append("from ");
		sb.append("   thing_attention_dates ");
		sb.append("   join attention_date on attention_date.id = thing_attention_dates.attentionDateId ");
		sb.append("   join thing on thing.id = thing_attention_dates.thing_id ");
		sb.append("   left join thing_belongs_to parentLink on parentLink.thing_id = thing.id ");
		sb.append("   left join thing parentThing on parentThing.id = parentLink.parent_id ");
		sb.append("where cast( julianday(thing_attention_dates.valueDate) - julianday('now') as int) < " + daysBeforeNow + " ");
		sb.append("order by cast( julianday(thing_attention_dates.valueDate) - julianday('now') as int) ");
		
		return sb.toString();
	}
	
	private String getFindItemsSql( String searchText, String containerName, String inPhrase) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("select ");
		sb.append("   thing.id as thing_id ");
		sb.append("   , thing.tag as thing_name ");
		sb.append("   , IFNULL(parentThing.tag, '"+containerName+"') as parent_thing_name ");
		sb.append("   , '"+inPhrase+" ' || IFNULL(parentThing.tag, '"+containerName+"') as user_message ");
		sb.append("from ");
		sb.append("   thing ");
		sb.append("   left join thing_belongs_to parentLink on parentLink.thing_id = thing.id ");
		sb.append("   left join thing parentThing on parentThing.id = parentLink.parent_id ");
		sb.append("where ");
		sb.append("   upper(thing.tag) like '%" + searchText + "%' ");
		sb.append("order by thing.tag ");
		
		return sb.toString();
	}
	
	private String getFindItemsByWhatSql( What what, String containerName, String inPhrase) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("select ");
		sb.append("   thing.id as thing_id ");
		sb.append("   , thing.tag as thing_name ");
		sb.append("   , IFNULL(parentThing.tag, '"+containerName+"') as parent_thing_name ");
		sb.append("   , '"+inPhrase+" ' || IFNULL(parentThing.tag, '"+containerName+"') as user_message ");
		sb.append("from ");
		sb.append("   thing ");
		sb.append("   join thing_what on thing_what.thing_id = thing.id ");
		sb.append("   left join thing_belongs_to parentLink on parentLink.thing_id = thing.id ");
		sb.append("   left join thing parentThing on parentThing.id = parentLink.parent_id ");
		sb.append("where ");
		sb.append("   thing_what.what_id = " + what.getId() + " ");
		sb.append("order by thing.tag ");
		
		return sb.toString();
	}
	
	private String getFindItemsByWhySql( Why why, String containerName, String inPhrase) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("select ");
		sb.append("   thing.id as thing_id ");
		sb.append("   , thing.tag as thing_name ");
		sb.append("   , IFNULL(parentThing.tag, '"+containerName+"') as parent_thing_name ");
		sb.append("   , '"+inPhrase+" ' || IFNULL(parentThing.tag, '"+containerName+"') as user_message ");
		sb.append("from ");
		sb.append("   thing ");
		sb.append("   join thing_why on thing_why.thing_id = thing.id ");
		sb.append("   left join thing_belongs_to parentLink on parentLink.thing_id = thing.id ");
		sb.append("   left join thing parentThing on parentThing.id = parentLink.parent_id ");
		sb.append("where ");
		sb.append("   thing_why.why_id = " + why.getId() + " ");
		sb.append("order by thing.tag ");
		
		return sb.toString();
	}
	
	private String getFindItemsByConsumeSql( Resource consume, String containerName, String inPhrase) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("select ");
		sb.append("   thing.id as thing_id ");
		sb.append("   , thing.tag || ' [' || thing_consume.resource_quantity || ']' as thing_name  ");
		sb.append("   , IFNULL(parentThing.tag, '"+containerName+"') as parent_thing_name ");
		sb.append("   , '"+inPhrase+" ' || IFNULL(parentThing.tag, '"+containerName+"') as user_message ");
		sb.append("   , thing_consume.resource_quantity as resource_count ");
		sb.append("from ");
		sb.append("   thing ");
		sb.append("   join thing_consume on thing_consume.thing_id = thing.id ");
		sb.append("   left join thing_belongs_to parentLink on parentLink.thing_id = thing.id ");
		sb.append("   left join thing parentThing on parentThing.id = parentLink.parent_id ");
		sb.append("where ");
		sb.append("   thing_consume.resource_id = " + consume.getId() + " ");
		sb.append("order by thing.tag ");
		
		return sb.toString();
	}
	
	private String getFindItemsByProvideSql( Resource provide, String containerName, String inPhrase) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("select ");
		sb.append("   thing.id as thing_id ");
		sb.append("   , thing.tag || ' [' || thing_provide.resource_quantity || ']' as thing_name  ");
		sb.append("   , IFNULL(parentThing.tag, '"+containerName+"') as parent_thing_name ");
		sb.append("   , '"+inPhrase+" ' || IFNULL(parentThing.tag, '"+containerName+"') as user_message ");
		sb.append("   , thing_provide.resource_quantity as resource_count ");
		sb.append("from ");
		sb.append("   thing ");
		sb.append("   join thing_provide on thing_provide.thing_id = thing.id ");
		sb.append("   left join thing_belongs_to parentLink on parentLink.thing_id = thing.id ");
		sb.append("   left join thing parentThing on parentThing.id = parentLink.parent_id ");
		sb.append("where ");
		sb.append("   thing_provide.resource_id = " + provide.getId() + " ");
		sb.append("order by thing.tag ");
		
		return sb.toString();
	}
	
	public ThingTotalCost getTotalCosts(String csvThingIds) {
		ThingTotalCost ttc = new ThingTotalCost();
		ttc.setTotalActualCost((double) 0);
		ttc.setTotalReplacementCost((double) 0);
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT ");
		sql.append("   SUM( actual_cost) ");
		sql.append("   , SUM(replacement_cost) ");
		sql.append("FROM ");
		sql.append("   thing ");
		sql.append("WHERE ");
		sql.append("   id IN (");
		sql.append(csvThingIds);
		sql.append(") ");
		
		Cursor cursor = null;

		try {

			cursor = database.rawQuery(sql.toString(), new String[]{});
	
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				int column = 0;
				
				try {
					ttc.setTotalActualCost(cursor.getDouble(column++));
					ttc.setTotalReplacementCost(cursor.getDouble(column++));
	
				} catch(Exception e) {
					// eat error
				}
				
				cursor.moveToNext();
			}

		} catch( Exception e) {
			Log.e("MyStash", "", e);
		} finally {
			if (cursor != null) {
	            cursor.close();
			}
	    }
		
		return ttc;
	}

	
	public List<FoundItem> findByName(String searchText, String containerName, String inPhrase) {
		List<FoundItem> list = new ArrayList<FoundItem>();
		Cursor cursor = null;

		try {

			String sql = this.getFindItemsSql( searchText, containerName, inPhrase);
			cursor = database.rawQuery(sql, new String[]{});
	
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				FoundItem o = null;
				int column = 0;
				
				try {
					o = new FoundItem();
					o.setThingId(cursor.getLong(column++));
					o.setThingName(cursor.getString(column++));
					o.setParentName(cursor.getString(column++));
					o.setUserMessage(cursor.getString(column++));
					
					list.add(o);
				} catch(Exception e) {
					// log error
				}
				
				cursor.moveToNext();
			}

		} catch( Exception e) {
			Log.e("MyStash", "", e);
		} finally {
			if (cursor != null) {
	            cursor.close();
			}
	    }
		return list;
	}

	public List<FoundItem> findByWhat(What what, String containerName, String inPhrase) {
		List<FoundItem> list = new ArrayList<FoundItem>();
		Cursor cursor = null;
		
		try {

			String sql = this.getFindItemsByWhatSql( what, containerName, inPhrase);
			cursor = database.rawQuery(sql, new String[]{});
	
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				FoundItem o = null;
				int column = 0;
				
				try {
					o = new FoundItem();
					o.setThingId(cursor.getLong(column++));
					o.setThingName(cursor.getString(column++));
					o.setParentName(cursor.getString(column++));
					o.setUserMessage(cursor.getString(column++));
					
					list.add(o);
				} catch(Exception e) {
					// log error
				}
				
				cursor.moveToNext();
			}

		} catch( Exception e) {
			Log.e("MyStash", "", e);
		} finally {
			if (cursor != null) {
	            cursor.close();
			}
	    }
		return list;
	}
	
	public List<FoundItem> findByWhy(Why why, String containerName, String inPhrase) {
		List<FoundItem> list = new ArrayList<FoundItem>();
		Cursor cursor = null;

		try {

			String sql = this.getFindItemsByWhySql( why, containerName, inPhrase);
			cursor = database.rawQuery(sql, new String[]{});
	
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				FoundItem o = null;
				int column = 0;
				
				try {
					o = new FoundItem();
					o.setThingId(cursor.getLong(column++));
					o.setThingName(cursor.getString(column++));
					o.setParentName(cursor.getString(column++));
					o.setUserMessage(cursor.getString(column++));
					
					list.add(o);
				} catch(Exception e) {
					// log error
				}
				
				cursor.moveToNext();
			}

		} catch( Exception e) {
			Log.e("MyStash", "", e);
		} finally {
			if (cursor != null) {
	            cursor.close();
			}
	    }
		
		return list;
	}
	
	
	public List<FoundItem> findByConsumes(Resource consume, String containerName, String inPhrase) {
		List<FoundItem> list = new ArrayList<FoundItem>();
		Cursor cursor = null;
		
		try {

			String sql = this.getFindItemsByConsumeSql( consume, containerName, inPhrase);
			cursor = database.rawQuery(sql, new String[]{});
	
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				FoundItem o = null;
				int column = 0;
				
				try {
					o = new FoundItem();
					o.setThingId(cursor.getLong(column++));
					o.setThingName(cursor.getString(column++));
					o.setParentName(cursor.getString(column++));
					o.setUserMessage(cursor.getString(column++));
					o.setResourceCount(cursor.getLong(column++));
					
					list.add(o);
				} catch(Exception e) {
					// log error
				}
				
				cursor.moveToNext();
			}

		} catch( Exception e) {
			Log.e("MyStash", "", e);
		} finally {
			if (cursor != null) {
	            cursor.close();
			}
	    }
		return list;
	}
	
	public List<FoundItem> findByProvides(Resource provide, String containerName, String inPhrase) {
		List<FoundItem> list = new ArrayList<FoundItem>();
		Cursor cursor = null;
		
		try {

			String sql = this.getFindItemsByProvideSql( provide, containerName, inPhrase);
			cursor = database.rawQuery(sql, new String[]{});
	
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				FoundItem o = null;
				int column = 0;
				
				try {
					o = new FoundItem();
					o.setThingId(cursor.getLong(column++));
					o.setThingName(cursor.getString(column++));
					o.setParentName(cursor.getString(column++));
					o.setUserMessage(cursor.getString(column++));
					o.setResourceCount(cursor.getLong(column++));
					
					list.add(o);
				} catch(Exception e) {
					// log error
				}
				
				cursor.moveToNext();
			}

		} catch( Exception e) {
			Log.e("MyStash", "", e);
		} finally {
			if (cursor != null) {
	            cursor.close();
			}
	    }
		
		return list;
	}
	
}
