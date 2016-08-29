package com.randomsilo.mystash.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

import com.randomsilo.mystash.db.MySQLiteHelper;
import com.randomsilo.mystash.db.pojo.BasePojo;

public abstract class BaseDaoDescriptor extends BaseDao {

	public BaseDaoDescriptor(MySQLiteHelper dbHelper) {
		super(dbHelper);
	}
	
	public String getTagColumn() {
		return "tag";
	}
	
	public String getDescriptionColumn() {
		return "description";
	}

	@SuppressWarnings("unchecked")
	public <T extends BasePojo> List<T> findAllByTag(String tag) {
		List<T> list = new ArrayList<T>();
		Cursor cursor = null;
		
		try {
			cursor = database.query(getTableName(), getAllColumns(), getTagColumn() + " like '%" + tag + "%'", null, null, null, null);
	
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				T object = null;
				try {
					object = (T) cursorToPojo(cursor);
					list.add(object);
				} catch(Exception e) {
					// log error
				}
				
				cursor.moveToNext();
			}
		} finally {
			if (cursor != null) {
	            cursor.close();
			}
	    }
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends BasePojo> List<T> findAllByDescription(String description) {
		List<T> list = new ArrayList<T>();

		Cursor cursor = database.query(getTableName(), getAllColumns(), getTagColumn() + " like '%" + description + "%'", null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			T object = null;
			try {
				object = (T) cursorToPojo(cursor);
				list.add(object);
			} catch(Exception e) {
				// log error
			}
			
			cursor.moveToNext();
		}

		cursor.close();
		return list;
	}

	
}