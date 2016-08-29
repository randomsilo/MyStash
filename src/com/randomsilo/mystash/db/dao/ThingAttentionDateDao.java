package com.randomsilo.mystash.db.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.randomsilo.mystash.db.MySQLiteHelper;
import com.randomsilo.mystash.db.pojo.BasePojo;
import com.randomsilo.mystash.db.pojo.ThingAttentionDate;

public class ThingAttentionDateDao extends BaseDaoThingRelated {

	public ThingAttentionDateDao(MySQLiteHelper dbHelper) {
		super(dbHelper);
	}

	@Override
	public String getTableName() {
		return "thing_attention_dates";
	}

	@Override
	public String[] getAllColumns() {
		String[] c = new String[] {
				getIdColumn()
				, getThingIdColumn()
				, "attentionDateId"
				, "value"
				, "valueDate"
		};
		return c;
	}

	@Override
	public String[] getCreateStatements() {
		String[] s = new String[] {
			"CREATE TABLE IF NOT EXISTS " + getTableName()
			+ "   (" + getIdColumn() + " " + COLUMN_ID_TYPE + " "
			+ "   , " + getThingIdColumn() + " integer "
			+ "   , attentionDateId integer not null "
			+ "   , value text "
			+ "   , valueDate text "
			+ "   , FOREIGN KEY(" + getThingIdColumn() + ") REFERENCES thing(id) "
			+ "   , FOREIGN KEY(attentionDateId) REFERENCES attention_date(id) "
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
		ThingAttentionDate o = (ThingAttentionDate)pojo;
		ContentValues values = new ContentValues();
		
		if(o.getId() != null) { values.put(getIdColumn(), o.getId()); }
		values.put(getThingIdColumn(), o.getThingId());
		values.put("attentionDateId", o.getAttentionDateId());
		values.put("value", o.getValue());
		values.put("valueDate", o.getValueDate());
		
		return values;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BasePojo> T cursorToPojo(Cursor cursor) {
		int column = 0;
		ThingAttentionDate o = new ThingAttentionDate();
		
		o.setId(cursor.getLong(column++));
		o.setThingId(cursor.getLong(column++));
		o.setAttentionDateId(cursor.getLong(column++));
		o.setValue(cursor.getString(column++));
		o.setValueDate(cursor.getString(column++));
		
		return (T) o;
	}

}
