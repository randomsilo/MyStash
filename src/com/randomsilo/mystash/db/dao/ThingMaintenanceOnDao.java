package com.randomsilo.mystash.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.randomsilo.mystash.db.MySQLiteHelper;
import com.randomsilo.mystash.db.pojo.BasePojo;
import com.randomsilo.mystash.db.pojo.ThingMaintenanceOn;

public class ThingMaintenanceOnDao extends BaseDaoThingRelated {
	
	public ThingMaintenanceOnDao(MySQLiteHelper dbHelper) {
		super(dbHelper);
	}

	public String getLastMaintDate() {
		return "last_maint_date";
	}
	
	@Override
	public String getTableName() {
		return "thing_maintenance_on";
	}

	@Override
	public String[] getAllColumns() {
		String[] c = new String[] {
				getIdColumn()
				, getThingIdColumn()
				, "last_maint_date"
				, "duration_days"
				, "notify_days_before"
				, "maint_details"
		};
		return c;
	}

	@Override
	public String[] getCreateStatements() {
		String[] s = new String[] {
			"CREATE TABLE IF NOT EXISTS " + getTableName()
			+ "   (" + getIdColumn() + " " + COLUMN_ID_TYPE + " "
			+ "   , " + getThingIdColumn() + " integer "
			+ "   , " + getLastMaintDate() + " integer not null "
			+ "   , duration_days integer not null "
			+ "   , notify_days_before integer "
			+ "   , maint_details text "
			+ "   , FOREIGN KEY(" + getThingIdColumn() + ") REFERENCES things(id) "
			+ "   )"
			, "CREATE INDEX IF NOT EXISTS idx_" + getTableName() + "__" + getLastMaintDate() + " ON " + getTableName() + "(" + getLastMaintDate() + ")"
		};
		return s;
	}
	
	@Override
	public String[] getDropStatements() {
		String[] s = new String[] {
			"DROP TABLE IF EXISTS " + getTableName()
			, "DROP INDEX IF EXISTS idx_" + getTableName() + "__" + getLastMaintDate() + ""
		};
		return s;
	}

	@Override
	public <T extends BasePojo> ContentValues makeContentValues(T pojo) {
		ThingMaintenanceOn o = (ThingMaintenanceOn)pojo;
		ContentValues values = new ContentValues();
		
		if(o.getId() != null) { values.put(getIdColumn(), o.getId()); }
		values.put(getThingIdColumn(), o.getThingId());
		values.put(getLastMaintDate(), o.getLastMaintDate());
		values.put("duration_days", o.getDurationDays());
		values.put("notify_days_before", o.getNotifyDaysBefore());
		values.put("maint_details", o.getMaintDetails());
		
		return values;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BasePojo> T cursorToPojo(Cursor cursor) {
		int column = 0;
		ThingMaintenanceOn o = new ThingMaintenanceOn();
		
		o.setId(cursor.getLong(column++));
		o.setThingId(cursor.getLong(column++));
		o.setLastMaintDate(cursor.getLong(column++));
		o.setDurationDays(cursor.getInt(column++));
		o.setNotifyDaysBefore(cursor.getInt(column++));
		o.setMaintDetails(cursor.getString(column++));
		
		return (T) o;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends BasePojo> List<T> findAllMaintenaceRequired(Long date) {
		List<T> list = new ArrayList<T>();
		Cursor cursor = null;

		try {

			cursor = database.query(getTableName(), getAllColumns(), "(" + getLastMaintDate() + " + (duration_days*86400)) < " + date, null, null, null, null);
	
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				T object = null;
				try {
					object = (T) cursorToPojo(cursor);
					list.add(object);
				} catch(Exception e) {
					Log.e("MyStash", "ThingMaintenanceOnDao:findAllMaintenaceRequired", e);
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
