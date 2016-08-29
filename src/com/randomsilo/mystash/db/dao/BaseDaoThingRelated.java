package com.randomsilo.mystash.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.util.Log;

import com.randomsilo.mystash.db.MySQLiteHelper;
import com.randomsilo.mystash.db.pojo.BasePojo;

public abstract class BaseDaoThingRelated extends BaseDao {

	public BaseDaoThingRelated(MySQLiteHelper dbHelper) {
		super(dbHelper);
	}
	
	public String getThingIdColumn() {
		return "thing_id";
	}

	@SuppressWarnings("unchecked")
	public <T extends BasePojo> List<T> findAllByThingId(Long id) {
		List<T> list = new ArrayList<T>();
		Cursor cursor = null;

		try {
			cursor = database.query(getTableName(), getAllColumns(), getThingIdColumn() + " = " + id, null, null, null, getOrderBy());
	
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				T object = null;
				try {
					object = (T) cursorToPojo(cursor);
					list.add(object);
				} catch(Exception e) {
					Log.e("findAllByThingId", e.getMessage());
				}
				
				cursor.moveToNext();
			}
		} catch( Exception e) {
			Log.e("MyStash", "findAllByThingId", e);
		} finally {
			if (cursor != null) {
	            cursor.close();
			}
	    }
		
		return list;
	}
	
}