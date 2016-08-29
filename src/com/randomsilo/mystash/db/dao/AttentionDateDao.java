package com.randomsilo.mystash.db.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.randomsilo.mystash.db.MySQLiteHelper;
import com.randomsilo.mystash.db.pojo.AttentionDate;
import com.randomsilo.mystash.db.pojo.BasePojo;

public class AttentionDateDao extends BaseDao {

	public AttentionDateDao(MySQLiteHelper dbHelper) {
		super(dbHelper);
	}

	@Override
	public String getTableName() {
		return "attention_date";
	}

	@Override
	public String[] getAllColumns() {
		String[] c = new String[] {
				getIdColumn()
				, "tag"
				, "description"
		};
		return c;
	}
	
	@Override
	protected String getOrderBy() {
		return "tag";
	}

	@Override
	public String[] getCreateStatements() {
		String[] s = new String[] {
			"CREATE TABLE IF NOT EXISTS " + getTableName()
			+ "   (" + getIdColumn() + " " + COLUMN_ID_TYPE + " "
			+ "   , tag text not null "
			+ "   , description text "
			+ "   )"
			, "CREATE INDEX IF NOT EXISTS idx_" + getTableName() +"__tag ON " + getTableName() +"(tag)"
		};
		return s;
	}
	
	@Override
	public String[] getDropStatements() {
		String[] s = new String[] {
			"DROP TABLE IF EXISTS " + getTableName()
			, "DROP INDEX IF EXISTS idx_" + getTableName() + "__tag"
		};
		return s;
	}

	@Override
	public <T extends BasePojo> ContentValues makeContentValues(T pojo) {
		AttentionDate o = (AttentionDate)pojo;
		ContentValues values = new ContentValues();
		
		if(o.getId() != null) { values.put(getIdColumn(), o.getId()); }
		values.put("tag", o.getTag());
		values.put("description", o.getDescription());
		
		return values;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BasePojo> T cursorToPojo(Cursor cursor) {
		int column = 0;
		AttentionDate o = new AttentionDate();
		
		o.setId(cursor.getLong(column++));
		o.setTag(cursor.getString(column++));
		o.setDescription(cursor.getString(column++));
		
		return (T) o;
	}

}
