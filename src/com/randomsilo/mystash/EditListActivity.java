package com.randomsilo.mystash;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.randomsilo.mystash.db.pojo.AttentionDate;
import com.randomsilo.mystash.db.pojo.Property;
import com.randomsilo.mystash.db.pojo.Resource;
import com.randomsilo.mystash.db.pojo.What;
import com.randomsilo.mystash.db.pojo.Why;
import com.randomsilo.mystash.dialog.SaveAttentionDateDialog;
import com.randomsilo.mystash.dialog.SavePropertyDialog;
import com.randomsilo.mystash.dialog.SaveResourceDialog;
import com.randomsilo.mystash.dialog.SaveWhatDialog;
import com.randomsilo.mystash.dialog.SaveWhyDialog;
import com.randomsilo.mystash.session.MyStashSession;
import com.randomsilo.mystash.ui.adapter.AttentionDateListArrayAdapter;
import com.randomsilo.mystash.ui.adapter.PropertyListArrayAdapter;
import com.randomsilo.mystash.ui.adapter.ResourceListArrayAdapter;
import com.randomsilo.mystash.ui.adapter.SourceSpinnerAdapter;
import com.randomsilo.mystash.ui.adapter.WhatListArrayAdapter;
import com.randomsilo.mystash.ui.adapter.WhyListArrayAdapter;
import com.randomsilo.mystash.ui.listener.OnClickAttentionDateListItem;
import com.randomsilo.mystash.ui.listener.OnClickPropertyListItem;
import com.randomsilo.mystash.ui.listener.OnClickResourceListItem;
import com.randomsilo.mystash.ui.listener.OnClickWhatListItem;
import com.randomsilo.mystash.ui.listener.OnClickWhyListItem;

public class EditListActivity extends Activity {

	private Spinner sourceSpinner;
	private ListView tagListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_list);
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	    refresh();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
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
	
	public void addListItem(View v) {
		TextView resourceName = ((TextView) findViewById(R.id.ResourceName));
		String selectedType = resourceName.getText().toString();
		
		if(selectedType.equals(getString(R.string.list_resource_name))) {
			MyStashSession.getInstance().setActiveResource(null);
			SaveResourceDialog.show(this);
			
		} else if(selectedType.equals(getString(R.string.list_what_name))) {
			MyStashSession.getInstance().setActiveWhat(null);
			SaveWhatDialog.show(this);
			
		} else if(selectedType.equals(getString(R.string.list_why_name))) {
			MyStashSession.getInstance().setActiveWhy(null);
			SaveWhyDialog.show(this);
			
		} else if(selectedType.equals(getString(R.string.list_property_name))) {
			MyStashSession.getInstance().setActiveProperty(null);
			SavePropertyDialog.show(this);
			
		} else if(selectedType.equals(getString(R.string.list_attention_date_name))) {
			MyStashSession.getInstance().setActiveAttentionDate(null);
			SaveAttentionDateDialog.show(this);
		}
	}
	
	public void refresh() {
		final Activity base = this;
		
		sourceSpinner = (Spinner)findViewById(R.id.SourceSpinner);
		tagListView = (ListView)findViewById(R.id.TagListView);
		
		List<String> sources = MyStashSession.getInstance().getThingService().getListNames();
		sourceSpinner.setAdapter( new SourceSpinnerAdapter(this, sources));
		sourceSpinner.setSelection( MyStashSession.getInstance().getLastSelectedResourceType());
		sourceSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		    	
		    	MyStashSession.getInstance().setLastSelectedResourceType(position);
		    	 
		    	TextView resourceName = ((TextView) selectedItemView.findViewById(R.id.ResourceName));
		    	String selectedType = resourceName.getText().toString();
		    	
		    	if(selectedType.equals(getString(R.string.list_resource_name))) {
		    		List<Resource> resources = MyStashSession.getInstance().getThingService().getResourceTags();
		    		ResourceListArrayAdapter adapter = new ResourceListArrayAdapter(base, R.layout.list_resources, resources);
		    		tagListView.setAdapter(adapter);
		    		tagListView.setOnItemClickListener( new OnClickResourceListItem());
		    	}
		    	else if(selectedType.equals(getString(R.string.list_what_name))) {
		    		List<What> whats = MyStashSession.getInstance().getThingService().getWhatTags();
		    		WhatListArrayAdapter adapter = new WhatListArrayAdapter(base, R.layout.list_what, whats);
		    		tagListView.setAdapter(adapter);
		    		tagListView.setOnItemClickListener( new OnClickWhatListItem());
		    	}
		    	else if(selectedType.equals(getString(R.string.list_why_name))) {
		    		List<Why> whys = MyStashSession.getInstance().getThingService().getWhyTags();
		    		WhyListArrayAdapter adapter = new WhyListArrayAdapter(base, R.layout.list_why, whys);
		    		tagListView.setAdapter(adapter);
		    		tagListView.setOnItemClickListener( new OnClickWhyListItem());
		    	}
		    	else if(selectedType.equals(getString(R.string.list_property_name))) {
		    		List<Property> propertys = MyStashSession.getInstance().getThingService().getPropertyTags();
		    		PropertyListArrayAdapter adapter = new PropertyListArrayAdapter(base, R.layout.list_property, propertys);
		    		tagListView.setAdapter(adapter);
		    		tagListView.setOnItemClickListener( new OnClickPropertyListItem());
		    	}
		    	else if(selectedType.equals(getString(R.string.list_attention_date_name))) {
		    		List<AttentionDate> attentionDates = MyStashSession.getInstance().getThingService().getAttentionDateTags();
		    		AttentionDateListArrayAdapter adapter = new AttentionDateListArrayAdapter(base, R.layout.list_attention_date, attentionDates);
		    		tagListView.setAdapter(adapter);
		    		tagListView.setOnItemClickListener( new OnClickAttentionDateListItem());
		    	}
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		    	
		    }

		});
		
	}
	
}
