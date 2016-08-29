package com.randomsilo.mystash.db.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;

import com.randomsilo.mystash.db.MySQLiteHelper;
import com.randomsilo.mystash.db.pojo.BasePojo;
import com.randomsilo.mystash.db.pojo.ThingImage;

public class ThingImageDao extends BaseDaoThingRelated {

	public ThingImageDao(MySQLiteHelper dbHelper) {
		super(dbHelper);
	}

	@Override
	public String getTableName() {
		return "thing_image";
	}

	@Override
	public String[] getAllColumns() {
		String[] c = new String[] {
				getIdColumn()
				, getThingIdColumn()
				, "item_image"
				, "rotation"
				, "height"
				, "width"
		};
		return c;
	}

	@Override
	public String[] getCreateStatements() {
		String[] s = new String[] {
			"CREATE TABLE IF NOT EXISTS " + getTableName()
			+ "   (" + getIdColumn() + " " + COLUMN_ID_TYPE + " "
			+ "   , " + getThingIdColumn() + " integer "
			+ "   , item_image blob "
			+ "   , rotation integer "
			+ "   , height integer "
			+ "   , width integer "
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
		ThingImage o = (ThingImage)pojo;
		ContentValues values = new ContentValues();
		
		if(o.getId() != null) { values.put(getIdColumn(), o.getId()); }
		values.put(getThingIdColumn(), o.getThingId());
		values.put("item_image", o.getItemImage());
		values.put("rotation", o.getRotation());
		values.put("height", o.getHeight());
		values.put("width", o.getWidth());
		
		return values;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BasePojo> T cursorToPojo(Cursor cursor) {
		int column = 0;
		ThingImage o = new ThingImage();
		
		o.setId(cursor.getLong(column++));
		o.setThingId(cursor.getLong(column++));
		o.setItemImage(cursor.getBlob(column++));
		o.setRotation(cursor.getInt(column++));
		o.setHeight(cursor.getInt(column++));
		o.setWidth(cursor.getInt(column++));
		
		return (T) o;
	}
	
	@SuppressWarnings("unchecked")
	public List<ThingImage> findAllByThingId(Long id) {
		List<ThingImage> list = new ArrayList<ThingImage>();

		List<BasePojo> pojoList = super.findAllByThingId(id);
		for(BasePojo p : pojoList) {
			list.add((ThingImage)p);
		}
		
		return list;
	}
	
	public ThingImage findThingImage(Long id) {
		List<ThingImage> list = new ArrayList<ThingImage>();

		List<BasePojo> pojoList = super.findAllByThingId(id);
		for(BasePojo p : pojoList) {
			list.add((ThingImage)p);
		}
		
		ThingImage image = new ThingImage();
		if(list.size() > 0) {
			image = list.get(0);
		} else {
			image.setThingId(id);
		}
	
		return image;
	}

}
