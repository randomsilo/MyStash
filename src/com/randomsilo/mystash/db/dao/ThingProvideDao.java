package com.randomsilo.mystash.db.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.randomsilo.mystash.db.MySQLiteHelper;
import com.randomsilo.mystash.db.pojo.BasePojo;
import com.randomsilo.mystash.db.pojo.ThingProvide;

public class ThingProvideDao extends BaseDaoThingRelated {

	public ThingProvideDao(MySQLiteHelper dbHelper) {
		super(dbHelper);
	}

	@Override
	public String getTableName() {
		return "thing_provide";
	}

	@Override
	public String[] getAllColumns() {
		String[] c = new String[] {
				getIdColumn()
				, getThingIdColumn()
				, "resource_id"
				, "resource_details"
				, "resource_quantity"
		};
		return c;
	}
	
	@Override
	protected String getOrderBy() {
		return "resource_quantity";
	}

	@Override
	public String[] getCreateStatements() {
		String[] s = new String[] {
			"CREATE TABLE IF NOT EXISTS " + getTableName()
			+ "   (" + getIdColumn() + " " + COLUMN_ID_TYPE + " "
			+ "   , " + getThingIdColumn() + " integer "
			+ "   , resource_id integer not null "
			+ "   , resource_details text "
			+ "   , resource_quantity integer "
			+ "   , FOREIGN KEY(thing_id) REFERENCES things(id) "
			+ "   , FOREIGN KEY(resource_id) REFERENCES resource(id) "
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
		ThingProvide o = (ThingProvide)pojo;
		ContentValues values = new ContentValues();
		
		if(o.getId() != null) { values.put(getIdColumn(), o.getId()); }
		values.put(getThingIdColumn(), o.getThingId());
		values.put("resource_id", o.getResourceId());
		values.put("resource_details", o.getResourceDetails());
		values.put("resource_quantity", o.getResourceQuantity());
		
		return values;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BasePojo> T cursorToPojo(Cursor cursor) {
		int column = 0;
		ThingProvide o = new ThingProvide();
		
		o.setId(cursor.getLong(column++));
		o.setThingId(cursor.getLong(column++));
		o.setResourceId(cursor.getLong(column++));
		o.setResourceDetails(cursor.getString(column++));
		o.setResourceQuantity(cursor.getInt(column++));
		
		return (T) o;
	}

}
