package com.randomsilo.mystash.db.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.randomsilo.mystash.db.MySQLiteHelper;
import com.randomsilo.mystash.db.pojo.BasePojo;
import com.randomsilo.mystash.db.pojo.ThingWhy;

public class ThingWhyDao extends BaseDaoThingRelated {

	public ThingWhyDao(MySQLiteHelper dbHelper) {
		super(dbHelper);
	}

	@Override
	public String getTableName() {
		return "thing_why";
	}

	@Override
	public String[] getAllColumns() {
		String[] c = new String[] {
				getIdColumn()
				, getThingIdColumn()
				, "why_id"
				, "why_details"
		};
		return c;
	}

	@Override
	public String[] getCreateStatements() {
		String[] s = new String[] {
			"CREATE TABLE IF NOT EXISTS " + getTableName()
			+ "   (" + getIdColumn() + " " + COLUMN_ID_TYPE + " "
			+ "   , " + getThingIdColumn() + " integer "
			+ "   , why_id integer not null "
			+ "   , why_details text "
			+ "   , FOREIGN KEY(thing_id) REFERENCES things(id) "
			+ "   , FOREIGN KEY(why_id) REFERENCES why(id) "
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
		ThingWhy o = (ThingWhy)pojo;
		ContentValues values = new ContentValues();
		
		if(o.getId() != null) { values.put(getIdColumn(), o.getId()); }
		values.put(getThingIdColumn(), o.getThingId());
		values.put("why_id", o.getWhyId());
		values.put("why_details", o.getWhyDetails());
		
		return values;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BasePojo> T cursorToPojo(Cursor cursor) {
		int column = 0;
		ThingWhy o = new ThingWhy();
		
		o.setId(cursor.getLong(column++));
		o.setThingId(cursor.getLong(column++));
		o.setWhyId(cursor.getLong(column++));
		o.setWhyDetails(cursor.getString(column++));
		
		return (T) o;
	}

}
