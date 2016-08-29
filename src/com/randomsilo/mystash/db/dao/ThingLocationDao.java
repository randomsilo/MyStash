package com.randomsilo.mystash.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;

import com.randomsilo.mystash.db.MySQLiteHelper;
import com.randomsilo.mystash.db.pojo.BasePojo;
import com.randomsilo.mystash.db.pojo.ThingLocation;

public class ThingLocationDao extends BaseDaoThingRelated {

	public ThingLocationDao(MySQLiteHelper dbHelper) {
		super(dbHelper);
	}

	@Override
	public String getTableName() {
		return "thing_location";
	}

	@Override
	public String[] getAllColumns() {
		String[] c = new String[] {
				getIdColumn()
				, getThingIdColumn()
				, "address"
				, "city_state"
				, "latitude"
				, "longitude"
		};
		return c;
	}

	@Override
	public String[] getCreateStatements() {
		String[] s = new String[] {
			"CREATE TABLE IF NOT EXISTS " + getTableName()
			+ "   (" + getIdColumn() + " " + COLUMN_ID_TYPE + " "
			+ "   , " + getThingIdColumn() + " integer "
			+ "   , address text "
			+ "   , city_state text "
			+ "   , latitude text "
			+ "   , longitude text "
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
		ThingLocation o = (ThingLocation)pojo;
		ContentValues values = new ContentValues();
		
		if(o.getId() != null) { values.put(getIdColumn(), o.getId()); }
		values.put(getThingIdColumn(), o.getThingId());
		values.put("address", o.getAddress());
		values.put("city_state", o.getCityState());
		values.put("latitude", o.getLatitude());
		values.put("longitude", o.getLongitude());
		
		return values;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BasePojo> T cursorToPojo(Cursor cursor) {
		int column = 0;
		ThingLocation o = new ThingLocation();
		
		o.setId(cursor.getLong(column++));
		o.setThingId(cursor.getLong(column++));
		o.setAddress(cursor.getString(column++));
		o.setCityState(cursor.getString(column++));
		o.setLatitude(cursor.getString(column++));
		o.setLongitude(cursor.getString(column++));
		
		return (T) o;
	}
	
	@SuppressWarnings("unchecked")
	public List<ThingLocation> findAllByThingId(Long id) {
		List<ThingLocation> list = new ArrayList<ThingLocation>();

		List<BasePojo> pojoList = super.findAllByThingId(id);
		for(BasePojo p : pojoList) {
			list.add((ThingLocation)p);
		}
		
		return list;
	}
	
	public ThingLocation findThingLocation(Long id) {
		List<ThingLocation> list = new ArrayList<ThingLocation>();

		List<BasePojo> pojoList = super.findAllByThingId(id);
		for(BasePojo p : pojoList) {
			list.add((ThingLocation)p);
		}
		
		ThingLocation location = new ThingLocation();
		if(list.size() > 0) {
			location = list.get(0);
		} else {
			location.setThingId(id);
		}
	
		return location;
	}

}
