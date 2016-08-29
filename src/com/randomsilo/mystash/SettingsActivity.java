package com.randomsilo.mystash;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.randomsilo.mystash.session.MyStashSession;

public class SettingsActivity extends Activity {

	Button DbExportBtn;
	Button DbImportBtn;
	Button DbClearBtn;
	Button DbSampleBtn;
	ProgressBar SetupBtnProgress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		if(MyStashSession.getInstance() == null || MyStashSession.getInstance().getThingService() == null) {
    		MyStashSession.getInstance().Init(this.getApplication());
    	}
		
		DbExportBtn = (Button)findViewById(R.id.DbExportBtn);
		DbImportBtn = (Button)findViewById(R.id.DbImportBtn);
		DbClearBtn = (Button)findViewById(R.id.DbClearBtn);
		DbSampleBtn = (Button)findViewById(R.id.DbSampleBtn);
		SetupBtnProgress = (ProgressBar)findViewById(R.id.SetupBtnProgress);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.up_only, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		
		if (id == R.id.action_up) {
			finish();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	public void databaseExport(View view) {
		workStarted();
		MyStashSession.getInstance().getThingService().exportDatabase();
		workEnded();
	}
	
	public void databaseImport(View view) {
		workStarted();
		MyStashSession.getInstance().getThingService().importDatabase();
		workEnded();
	}
	
	public void databaseClear(View view) {
		workStarted();
		MyStashSession.getInstance().getThingService().clearDatabase();
		workEnded();
	}
	
	public void databaseLoadSample(View view) {
		workStarted();
		MyStashSession.getInstance().getThingService().loadDefaultDatabase();
		workEnded();
	}
	
	private void workStarted() {
		DbExportBtn.setEnabled(false);
		DbImportBtn.setEnabled(false);
		DbClearBtn.setEnabled(false);
		DbSampleBtn.setEnabled(false);
		SetupBtnProgress.setVisibility(View.VISIBLE);
	}
	
	private void workEnded() {
		DbExportBtn.setEnabled(true);
		DbImportBtn.setEnabled(true);
		DbClearBtn.setEnabled(true);
		DbSampleBtn.setEnabled(true);
		SetupBtnProgress.setVisibility(View.GONE);
	}
}
