package com.randomsilo.mystash.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.randomsilo.mystash.db.MySQLiteHelper;
import com.randomsilo.mystash.db.pojo.BasePojo;
import com.randomsilo.mystash.db.pojo.ThingExpiresOn;

public class ThingExpiresOnDao extends BaseDaoThingRelated {

	public ThingExpiresOnDao(MySQLiteHelper dbHelper) {
		super(dbHelper);
	}

	
	public String getExpirationDate() {
		return "expiration_date";
	}
	
	@Override
	public String getTableName() {
		return "thing_expires_on";
	}

	@Override
	public String[] getAllColumns() {
		String[] c = new String[] {
				getIdColumn()
				, getThingIdColumn()
				, "expiration_date"
				, "notify_days_before"
				, "expire_details"
		};
		return c;
	}

	@Override
	public String[] getCreateStatements() {
		String[] s = new String[] {
			"CREATE TABLE IF NOT EXISTS " + getTableName()
			+ "   (" + getIdColumn() + " " + COLUMN_ID_TYPE + " "
			+ "   , " + getThingIdColumn() + " integer "
			+ "   , " + getExpirationDate() + " integer not null "
			+ "   , notify_days_before integer "
			+ "   , expire_details text "
			+ "   , FOREIGN KEY(" + getThingIdColumn() + ") REFERENCES things(id) "
			+ "   )"
			, "CREATE INDEX idx_" + getTableName() + "__" + getExpirationDate() + " ON " + getTableName() + "(" + getExpirationDate() + ")"
		};
		return s;
	}
	
	@Override
	public String[] getDropStatements() {
		String[] s = new String[] {
			"DROP TABLE IF EXISTS " + getTableName()
			, "DROP INDEX IF EXISTS idx_" + getTableName() + "__" + getExpirationDate() + ""
		};
		return s;
	}

	@Override
	public <T extends BasePojo> ContentValues makeContentValues(T pojo) {
		ThingExpiresOn o = (ThingExpiresOn)pojo;
		ContentValues values = new ContentValues();
		
		if(o.getId() != null) { values.put(getIdColumn(), o.getId()); }
		values.put(getThingIdColumn(), o.getThingId());
		values.put(getExpirationDate(), o.getExpirationDate());
		values.put("notify_days_before", o.getNotifyDaysBefore());
		values.put("expire_details", o.getExpireDetails());
		
		return values;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BasePojo> T cursorToPojo(Cursor cursor) {
		int column = 0;
		ThingExpiresOn o = new ThingExpiresOn();
		
		o.setId(cursor.getLong(column++));
		o.setThingId(cursor.getLong(column++));
		o.setExpirationDate(cursor.getLong(column++));
		o.setNotifyDaysBefore(cursor.getInt(column++));
		o.setExpireDetails(cursor.getString(column++));
		
		return (T) o;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends BasePojo> List<T> findAllExpired(Long date) {
		List<T> list = new ArrayList<T>();

		Cursor cursor = database.query(getTableName(), getAllColumns(), getExpirationDate() + " < " + date, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			T object = null;
			try {
				object = (T) cursorToPojo(cursor);
				list.add(object);
			} catch(Exception e) {
				Log.e("MyStash", "ThingExpiresOnDao:findAllExpired", e);
			}
			
			cursor.moveToNext();
		}

		cursor.close();
		return list;
	}

}
