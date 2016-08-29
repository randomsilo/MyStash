package com.randomsilo.mystash;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.randomsilo.mystash.dialog.SaveThingPropertyDialog;
import com.randomsilo.mystash.model.ThingModel;
import com.randomsilo.mystash.model.ThingPropertyModel;
import com.randomsilo.mystash.session.MyStashSession;
import com.randomsilo.mystash.ui.adapter.ThingPropertyModelListArrayAdapter;
import com.randomsilo.mystash.ui.listener.OnClickThingPropertyListItem;

public class ThingPropertiesActivity extends BaseThingNavigationActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thing_properties);
		
	}

	@Override
	protected void onResume() {
	    super.onResume();
	    
	    RefreshLists();
	}
	
	public void RefreshLists() {
		RefreshPropertyList();
	}
	
	public void RefreshPropertyList() {
		List<ThingPropertyModel> properties = new ArrayList<ThingPropertyModel>();
		
		ThingModel m = MyStashSession.getInstance().getActiveThing();
		if(m != null) { 
			properties = MyStashSession.getInstance().getThingService().getPropertiesList(m.getThingId());
		}
		
		ListView propertiesListView = (ListView)findViewById(R.id.PropertyListView);
		ThingPropertyModelListArrayAdapter propertiesAdapter = new ThingPropertyModelListArrayAdapter(this, R.layout.list_thing_properties, properties);
		propertiesListView.setAdapter(propertiesAdapter);
		propertiesListView.setOnItemClickListener(new OnClickThingPropertyListItem());
	}

	public void addProperty(View v) {
		MyStashSession.getInstance().setActivePropertyModel(null);
		SaveThingPropertyDialog.show(this);
	}

}
