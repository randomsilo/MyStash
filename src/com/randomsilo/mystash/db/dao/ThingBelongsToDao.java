package com.randomsilo.mystash.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.randomsilo.mystash.db.MySQLiteHelper;
import com.randomsilo.mystash.db.pojo.BasePojo;
import com.randomsilo.mystash.db.pojo.ThingBelongsTo;

public class ThingBelongsToDao extends BaseDaoThingRelated {

	public ThingBelongsToDao(MySQLiteHelper dbHelper) {
		super(dbHelper);
	}

	@Override
	public String getTableName() {
		return "thing_belongs_to";
	}
	
	public String getParentIdColumn() {
		return "parent_id";
	}

	@Override
	public String[] getAllColumns() {
		String[] c = new String[] {
				getIdColumn()
				, getThingIdColumn()
				, getParentIdColumn()
		};
		return c;
	}

	@Override
	public String[] getCreateStatements() {
		String[] s = new String[] {
			"CREATE TABLE IF NOT EXISTS " + getTableName()
			+ "   (" + getIdColumn() + " " + COLUMN_ID_TYPE + " "
			+ "   , " + getThingIdColumn() + " integer "
			+ "   , " + getParentIdColumn() + " integer "
			+ "   , FOREIGN KEY(" + getThingIdColumn() + ") REFERENCES thing(id) "
			+ "   )"
		};
		return s;
	}
	
	@Override
	public String[] getDropStatements() {
		String[] s = new String[] {
			"DROP TABLE IF EXISTS " + getTableName()
		};
		return s;
	}

	@Override
	public <T extends BasePojo> ContentValues makeContentValues(T pojo) {
		ThingBelongsTo o = (ThingBelongsTo)pojo;
		ContentValues values = new ContentValues();
		
		if(o.getId() != null) { values.put(getIdColumn(), o.getId()); }
		values.put(getThingIdColumn(), o.getThingId());
		values.put(getParentIdColumn(), o.getParentId());
		
		return values;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BasePojo> T cursorToPojo(Cursor cursor) {
		int column = 0;
		ThingBelongsTo o = new ThingBelongsTo();
		
		o.setId(cursor.getLong(column++));
		o.setThingId(cursor.getLong(column++));
		o.setParentId(cursor.getLong(column++));
		
		return (T) o;
	}

	public List<ThingBelongsTo> findAllByParentId(Long id) {
		List<ThingBelongsTo> list = new ArrayList<ThingBelongsTo>();
		Cursor cursor = null;
		
		try {

			String whereClause = getParentIdColumn() + " IS NULL OR " + getParentIdColumn() + " = 0";
			if(id != null && id != 0) {
				whereClause = getParentIdColumn() + " = " + id;
			}
			cursor = database.query(getTableName(), getAllColumns(), whereClause, null, null, null, null);
	
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				ThingBelongsTo object = null;
				try {
					object = (ThingBelongsTo) cursorToPojo(cursor);
					list.add(object);
				} catch(Exception e) {
					Log.e("MyStash", "ThingBelongsToDao:findAllByParentId", e);
				}
				
				cursor.moveToNext();
			}
		} catch( Exception e) {
			Log.e("MyStash", "findAllByParentId", e);
		} finally {
			if (cursor != null) {
	            cursor.close();
			}
	    }
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<ThingBelongsTo> findAllByThingId(Long id) {
		List<ThingBelongsTo> list = new ArrayList<ThingBelongsTo>();

		List<BasePojo> pojoList = super.findAllByThingId(id);
		for(BasePojo p : pojoList) {
			list.add((ThingBelongsTo)p);
		}
		
		return list;
	}
	
	public ThingBelongsTo getParent(Long id) {
		List<ThingBelongsTo> list = new ArrayList<ThingBelongsTo>();

		List<BasePojo> pojoList = super.findAllByThingId(id);
		for(BasePojo p : pojoList) {
			list.add((ThingBelongsTo)p);
		}
		
		ThingBelongsTo p = new ThingBelongsTo();
		if(list.size() > 0) {
			p = list.get(0);
		}
		
		return p;
	}
}
