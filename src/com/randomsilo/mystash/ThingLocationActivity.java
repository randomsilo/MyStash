package com.randomsilo.mystash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.randomsilo.mystash.model.ThingModel;
import com.randomsilo.mystash.session.MyStashSession;
import com.randomsilo.mystash.util.FastGps;

public class ThingLocationActivity extends BaseThingNavigationActivity {
	private EditText ThingAddress;
	private EditText ThingCityState;
	private EditText ThingLatitude;
	private EditText ThingLongitude;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thing_location);
		
		SetupComponents();
	}
	
	private void SetupComponents() {
		ThingAddress = (EditText)findViewById(R.id.ThingAddress);
		ThingCityState = (EditText)findViewById(R.id.ThingCityState);
		ThingLatitude = (EditText)findViewById(R.id.ThingLatitude);
		ThingLongitude = (EditText)findViewById(R.id.ThingLongitude);
		
		ThingModel m = MyStashSession.getInstance().getActiveThing();
		ThingAddress.setText(m.getThingLocation().getAddress());
		ThingCityState.setText(m.getThingLocation().getCityState());
		ThingLatitude.setText(m.getThingLocation().getLatitude());
		ThingLongitude.setText(m.getThingLocation().getLongitude());
	}

	public void saveLocation(View view) {
		ThingModel m = MyStashSession.getInstance().getActiveThing();
		m.getThingLocation().setAddress(ThingAddress.getText().toString());
		m.getThingLocation().setCityState(ThingCityState.getText().toString());
		m.getThingLocation().setLatitude(ThingLatitude.getText().toString());
		m.getThingLocation().setLongitude(ThingLongitude.getText().toString());
		
		
		boolean success = MyStashSession.getInstance().save();
		if(success) {
			Toast.makeText(this, getResources().getString(R.string.thing_save_sucess), Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, getResources().getString(R.string.thing_save_failure), Toast.LENGTH_LONG).show();
		}
		
	}
	
	@SuppressLint("DefaultLocale")
	public void gotoMap(View view) {
		String address = ThingAddress.getText().toString();
		String cityState = ThingCityState.getText().toString();
		String latitude = ThingLatitude.getText().toString();
		String longitude = ThingLongitude.getText().toString();
		
		double lat = 0;
		try {
			lat = Double.parseDouble(latitude);
		} catch(NumberFormatException n1) {
		}
		
		double lng = 0;
		try {
			lng = Double.parseDouble(longitude);
		} catch(NumberFormatException n2) {
		}

		if( (address.length()>0 && cityState.length()>0) || (lat!=0 && lng!=0)) {
			
			ThingModel m = MyStashSession.getInstance().getActiveThing();
			String query = String.format("geo:%f,%f?q=%f,%f (%s)", lat, lng, lat, lng, m.getThing().getTag());
			if( address.length()>0 && cityState.length()>0) {
				query = String.format("geo:%f,%f?q=%s, %s", lat, lng, address, cityState);
			}
			
			Uri gmmIntentUri = Uri.parse(query);
			Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
			mapIntent.setPackage("com.google.android.apps.maps");
			if (mapIntent.resolveActivity(getPackageManager()) != null) {
			    startActivity(mapIntent);
			} else {
				Toast.makeText(this, getResources().getString(R.string.maps_not_found), Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this, getResources().getString(R.string.maps_enter_more_data), Toast.LENGTH_LONG).show();
		}
	}
	
	public void setCurrentPosition(View view) {
		
		FastGps g = new FastGps(this);
		g.getMyLocation();
		
		if(g.getLatitude() != 0.0 && g.getLongitude() != 0) {
			ThingLatitude.setText(g.getLatitude()+"");
			ThingLongitude.setText(g.getLongitude()+"");
			Toast.makeText(this, getResources().getString(R.string.maps_gps_set), Toast.LENGTH_LONG).show();
			Toast.makeText(this, getResources().getString(R.string.maps_save_reminder), Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, getResources().getString(R.string.maps_gps_not_available), Toast.LENGTH_LONG).show();
		}
	}

}
