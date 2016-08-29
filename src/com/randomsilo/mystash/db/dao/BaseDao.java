package com.randomsilo.mystash.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.randomsilo.mystash.db.MySQLiteHelper;
import com.randomsilo.mystash.db.pojo.BasePojo;

public abstract class BaseDao {
	protected SQLiteDatabase database;
	private MySQLiteHelper dbHelper;

	public static String COLUMN_ID_TYPE = "integer primary key autoincrement";
	
	public abstract String getTableName();
	
	public abstract String[] getAllColumns();
	public abstract String[] getCreateStatements();
	public abstract <T extends BasePojo> ContentValues makeContentValues(T pojo);
	public abstract <T extends BasePojo> T cursorToPojo(Cursor cursor);
	
	protected String getOrderBy() {
		return null;
	}
	
		
	public BaseDao(MySQLiteHelper dbHelper) {
		this.dbHelper = dbHelper;
	}
	
	public void Clear() {
		dbHelper.onClear(dbHelper.getWritableDatabase());
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}
	
	public String getIdColumn() {
		return "id";
	}

	public <T extends BasePojo> int saveAll( List<T> items) {
		int count = 0;
		for( T item : items) {
			if( this.save(item) != null) {
				count++;
			}
		}
		
		return count;
	}
	
	public <T extends BasePojo> T save(T pojo) {
		if(pojo.getId() == null || pojo.getId() < 1) {
			pojo.setId(null);
			return this.create(pojo);
		} else {
			return this.update(pojo);
		}
	}
	
	public <T extends BasePojo> T create(T pojo) {
		ContentValues values = makeContentValues(pojo);
		T object = null;
		Cursor cursor = null;
		
		try {
			long insertId = database.insert(getTableName(), null, values);
			if(insertId != -1) {
				String where = getIdColumn() + " = " + insertId;
				String[] columns = getAllColumns();
				
				cursor = database.query(getTableName(), columns, where, null, null, null, null);
				cursor.moveToFirst();
				if(!cursor.isAfterLast()) {
					object = cursorToPojo(cursor);
				 	cursor.close();
				}
				
			} else {
				Log.e("MyStash", "BaseDao:create - bad insertId returned");
			}
		} catch( Exception e) {
			Log.e("MyStash", "BaseDao:create", e);
		} finally {
			if (cursor != null) {
	            cursor.close();
			}
	    }
		
		return object;
	}
	
	public <T extends BasePojo> T update(T pojo) {
		ContentValues values = makeContentValues(pojo);
		T object = null;
		Cursor cursor = null;
		
		try {
			int rows = database.update(getTableName(), values, getIdColumn() + " = " + pojo.getId(), null);
			if(rows != -1) {
				
				cursor = database.query(getTableName(), getAllColumns(), getIdColumn() + " = " + pojo.getId(), null, null, null, null);
				cursor.moveToFirst();
				if(!cursor.isAfterLast()) {
					object = cursorToPojo(cursor);
					cursor.close();
				}
				
			} else {
				Log.e("MyStash", "BaseDao:create - bad insertId returned");
			}
		} catch( Exception e) {
			Log.e("MyStash", "BaseDao:create", e);
		} finally {
			if (cursor != null) {
	            cursor.close();
			}
	    }
		
		return object;
	}

	public <T extends BasePojo> void delete(T object) {
		long id = object.getId();
		database.delete(getTableName(), getIdColumn() + " = " + id, null);
	}

	public <T extends BasePojo> List<T> findAll() {
		List<T> list = new ArrayList<T>();

		Cursor cursor = null;

		try {
			cursor = database.query(getTableName(), getAllColumns(), null, null, null, null, getOrderBy());

			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				T object = null;
				try {
					object = cursorToPojo(cursor);
					list.add(object);
				} catch(Exception e) {
					Log.e("MyStash", "BaseDao:findAll", e);
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
	
	public <T extends BasePojo> T findById(Long id) {
		T object = null;

		Cursor cursor = null;

		try {
			cursor = database.query(getTableName(), getAllColumns(), getIdColumn() + " = " + id, null, null, null, null);
	
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				try {
					object = cursorToPojo(cursor);
				} catch(Exception e) {
					Log.e("MyStash", "BaseDao:findById", e);
				}
				
				cursor.moveToNext();
			}

		} finally {
			if (cursor != null) {
	            cursor.close();
			}
	    }
		return object;
	}
	
	public String[] getDropStatements() {
		String[] s = new String[] {
			"DROP TABLE IF EXISTS " + getTableName()	
		};
		return s;
	}

	public MySQLiteHelper getDbHelper() {
		return dbHelper;
	}

	public void setDbHelper(MySQLiteHelper dbHelper) {
		this.dbHelper = dbHelper;
	}
	
	
}