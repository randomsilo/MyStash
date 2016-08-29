package com.randomsilo.mystash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.randomsilo.mystash.session.MyStashSession;

public class LandingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_landing);
		
		onNewIntent(getIntent());
	}
	
	@Override
	public void onNewIntent(Intent intent){
	    Bundle extras = intent.getExtras();
	    if(extras != null){
	        if(extras.containsKey("thingId")) {
	        	
	        	if(MyStashSession.getInstance() == null || MyStashSession.getInstance().getThingService() == null) {
	        		MyStashSession.getInstance().Init(this.getApplication());
	        	}
	        	
	        	try {
	        		String thingIdStr = extras.getString("thingId");
		            Long thingId = Long.parseLong(thingIdStr);
		            MyStashSession.getInstance().setActiveThing(
		            		MyStashSession.getInstance().getThingService().get(thingId)
		    		);
		            
		            Intent browse = new Intent(this, BrowseActivity.class);
		            browse.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		            browse.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);        
		    		startActivity(browse);
		    		overridePendingTransition(R.anim.pull_in_bottom, R.anim.pull_out_top);
	        	} catch(Exception e) {
	        		if(e != null && e.getMessage() != null) {
	        			Log.e("MyStash", e.getMessage());
	        		}
	        		else {
	        			Log.e("MyStash", "Null Exception Message");
	        		}
	        	}
	            
	        }
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.landing, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		
		if (id == R.id.action_settings) {
			Intent intent = new Intent(this, SettingsActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);        
			startActivity(intent);
			overridePendingTransition(R.anim.pull_in_bottom, R.anim.pull_out_top);
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	public void navigateBrowse(View view) {
		Intent intent = new Intent(this, BrowseActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);        
		startActivity(intent);
		overridePendingTransition(R.anim.pull_in_bottom, R.anim.pull_out_top);
	}
	
	public void navigateSearch(View view) {
		Intent intent = new Intent(this, SearchSelectionActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		overridePendingTransition(R.anim.pull_in_bottom, R.anim.pull_out_top);
	}

	public void navigateExpired(View view) {
		notifyExpiredItems();
	}
	
	public void navigateLists(View view) {
		Intent intent = new Intent(this, EditListActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		overridePendingTransition(R.anim.pull_in_bottom, R.anim.pull_out_top);
	}
	
	private void notifyExpiredItems() {
		Intent intent = new Intent(this, NotificationActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		overridePendingTransition(R.anim.pull_in_bottom, R.anim.pull_out_top);
	}
}
