package com.randomsilo.mystash.db;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.randomsilo.mystash.R;
import com.randomsilo.mystash.db.dao.BaseDao;
import com.randomsilo.mystash.util.FileIO;

public class MySQLiteHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "mystash.db";
	private static final int DATABASE_VERSION = 2;
	private List<Object> daos;
	private Context context;
	
	String privateDatabasePath = ""; 
	String publicDatabasePath =  Environment.getExternalStorageDirectory().getPath(); 
	
	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
		daos = new ArrayList<Object>();
		
		privateDatabasePath = context.getDatabasePath(DATABASE_NAME).toString();
		publicDatabasePath =  Environment.getExternalStorageDirectory().getPath() + File.separator + DATABASE_NAME;
	}
	
	public void setDaos(List<Object> daos) {
		this.daos = daos;
	}
	
	public <T extends BaseDao> void addDao(T dao) {
		daos.add(dao);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		for(Object dao : daos) {
			if(dao instanceof BaseDao) {
				executeStatements(database, ((BaseDao)dao).getCreateStatements());	
			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(
				MySQLiteHelper.class.getName()
				,"Upgrading database from version " + oldVersion + " to " + newVersion + "."
		);
		
		if(oldVersion == 1 && newVersion == 2) {
			executeStatements(db, new String[] {
					"ALTER TABLE thing_image ADD COLUMN rotation integer"
					, "ALTER TABLE thing_image ADD COLUMN height integer"
					, "ALTER TABLE thing_image ADD COLUMN width integer"
			});
		} else {
			onCreate(db);	
		}
		
		
	}
	
	public void onClear(SQLiteDatabase database) {
		for(Object dao : daos) {
			if(dao instanceof BaseDao) {
				executeStatements(database, ((BaseDao)dao).getDropStatements());	
			}
		}
	}
	
	public void openDaos() {
		for(Object dao : daos) {
			if(dao instanceof BaseDao) {
				((BaseDao) dao).open();	
			}
		}
	}
	
	public void closeDaos() {
		for(Object dao : daos) {
			if(dao instanceof BaseDao) {
				((BaseDao) dao).close();	
			}
		}
	}
	
	public void executeStatements(SQLiteDatabase database, String[] stmts) {
		for(String s : stmts) {
			database.execSQL(s);
		}
	}
	
	public void exportDatabase() {
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			File src = new File(db.getPath());
			File dst = new File(
					db.getPath()
						.replace(privateDatabasePath, publicDatabasePath)
					);
			FileIO.copy(src, dst);
			Toast.makeText(context, context.getResources().getString(R.string.database_export), Toast.LENGTH_LONG).show();
		} catch(Exception e) {
			Log.e("MyStash", "exportDatabase", e);
			Toast.makeText(context, context.getResources().getString(R.string.error_database_export_exception), Toast.LENGTH_LONG).show();
		}
	}
	
	public void importDatabase() {
		try {
			closeDaos();
			
			SQLiteDatabase db = this.getWritableDatabase();
			File dst = new File(db.getPath());
			File src = new File(
					db.getPath()
						.replace(privateDatabasePath, publicDatabasePath)
					);
			if( src.exists()) {
				FileIO.copy(src, dst);
				Toast.makeText(context, context.getResources().getString(R.string.database_import), Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(context, context.getResources().getString(R.string.error_file_not_found), Toast.LENGTH_LONG).show();
			}
			
			openDaos();
		} catch(Exception e) {
			Log.e("MyStash", "importDatabase", e);
			Toast.makeText(context, context.getResources().getString(R.string.error_database_import_exception), Toast.LENGTH_LONG).show();
		}
	}
	
	public void clearDatabase() {
		try {
			closeDaos();
			
			if( context.deleteDatabase(DATABASE_NAME)) {
				Toast.makeText(context, context.getResources().getString(R.string.database_clear), Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(context, context.getResources().getString(R.string.error_file_not_found), Toast.LENGTH_LONG).show();
			}
			
		} catch(Exception e) {
			Log.e("MyStash", "clearDatabase", e);
			Toast.makeText(context, context.getResources().getString(R.string.error_database_clear_exception), Toast.LENGTH_LONG).show();
		}
	}
		
}
