package com.randomsilo.mystash;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.randomsilo.mystash.model.ThingModel;
import com.randomsilo.mystash.session.MyStashSession;
import com.randomsilo.mystash.ui.AdvancedGestureListener;
import com.randomsilo.mystash.ui.ThingNavigation;

public class BaseThingNavigationActivity extends Activity {
	private GestureDetector detector;
	private ThingNavigation thingNavigation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SetupGestureDetector();
		setThingTitle();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if(MyStashSession.getInstance().isThingSelected()) {
			getMenuInflater().inflate(R.menu.thing_sub_activity, menu);
			
			MenuItem i = menu.findItem(R.id.action_image);
			i.setVisible(false);
			i.setEnabled(false);
			if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
				i.setVisible(true);
				i.setEnabled(true);
			}
		} else {
			getMenuInflater().inflate(R.menu.up_only, menu);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		
		if (id == R.id.action_up) {
			goBack();
			return true;
		}
		
		if (id == R.id.action_home) {
			goHome();
			return true;
		}
		
		if (id == R.id.action_hierarchy) {
			gotoScreen(BrowseActivity.class);
			return true;
		}
		
		if (id == R.id.action_details) {
			gotoScreen(ThingDetailsActivity.class);
			return true;
		}
		
		if (id == R.id.action_property) {
			gotoScreen(ThingPropertiesActivity.class);
			return true;
		}
		
		if (id == R.id.action_image) {
			gotoScreen(ThingImagesActivity.class);
			return true;
		}
		
		if (id == R.id.action_location) {
			gotoScreen(ThingLocationActivity.class);
			return true;
		}
		
		if (id == R.id.action_resource) {
			gotoScreen(ThingResourceActivity.class);
			return true;
		}
		
		if (id == R.id.action_schedule) {
			gotoScreen(ThingScheduleActivity.class);
			return true;
		}
		
		if (id == R.id.action_metadata) {
			gotoScreen(ThingMetadataActivity.class);
			return true;
		}
		
		/*
		if (id == R.id.action_help) {
			Toast.makeText(this, getResources().getString(R.string.under_construction), Toast.LENGTH_SHORT).show();
			return true;
		}
		*/
				
		return super.onOptionsItemSelected(item);
	}
	
	@Override 
	public boolean onTouchEvent(MotionEvent event) { 
		return detector.onTouchEvent(event); 
	} 
	
	protected void SetupGestureDetector() {
		final Activity a = this;
		thingNavigation = new ThingNavigation(a.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA));
		thingNavigation.setCurrentActivity(a.getClass());
		
		detector = new GestureDetector(getApplicationContext(), new AdvancedGestureListener() {
			
			@Override
			public void onSwipeLeft() {
				if(MyStashSession.getInstance().isThingSelected()) {
					Intent intent  = new Intent(a, thingNavigation.getNextActivity());
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					overridePendingTransition(R.anim.pull_in_right, R.anim.pull_out_left);
				}
		    }
			
			@Override
			public void onSwipeRight() {
				if(MyStashSession.getInstance().isThingSelected()) {
					Intent intent = new Intent(a, thingNavigation.getPreviousActivity());
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);        
					startActivity(intent);
					overridePendingTransition(R.anim.pull_in_left, R.anim.pull_out_right);
				}
		    }
			
			@Override
			public void onSwipeTop() {
				if(MyStashSession.getInstance().isThingSelected()) {
					//Toast.makeText(getApplicationContext(), "Top", Toast.LENGTH_SHORT).show();
				}
		    }
			
			@Override
			public void onSwipeBottom() {
				goBack();
			}
			
		});
	}
	
	public void setThingTitle() {
		if(MyStashSession.getInstance().isThingSelected()) {
			setTitle(MyStashSession.getInstance().getActiveThing().getThing().getTag());
		} else {
			setTitle(getResources().getString(R.string.app_name));
		}
	}
	
	public void goBack() {
		
		if(MyStashSession.getInstance().isThingSelected()) {
			
			ThingModel m = MyStashSession.getInstance().getActiveThing();	
			if(m != null && m.getThingBelongsTo().getParentId() != null	&& m.getThingBelongsTo().getParentId() > 0) {
				
				// Has Parent
				MyStashSession.getInstance().setActiveThing(
	        		MyStashSession.getInstance().getThingService().get(m.getThingBelongsTo().getParentId())
				);
			} else {
				
				// At Root
				MyStashSession.getInstance().setActiveThing(null);
			}
			
			Intent intent = new Intent(this, BrowseActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);        
			startActivity(intent);
			overridePendingTransition(R.anim.pull_in_top, R.anim.pull_out_bottom);
			
		} else {
			// Back to Landing
			finish();
		}
		
	}
	
	public void goHome() {
		MyStashSession.getInstance().setActiveThing(null);
		Intent intent = new Intent(this, LandingActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);        
		startActivity(intent);
		overridePendingTransition(R.anim.pull_in_top, R.anim.pull_out_bottom);
	}
	
	public void gotoScreen(Class<?> activity) {
		Intent intent = new Intent(this, activity);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);        
		startActivity(intent);
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}
	
	public void onBackPressed() {
		goBack();
	}
	
}
