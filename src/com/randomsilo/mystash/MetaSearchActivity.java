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
import android.widget.Toast;

import com.randomsilo.mystash.db.pojo.FoundItem;
import com.randomsilo.mystash.db.pojo.What;
import com.randomsilo.mystash.db.pojo.Why;
import com.randomsilo.mystash.session.MyStashSession;
import com.randomsilo.mystash.ui.adapter.FoundItemListArrayAdapter;
import com.randomsilo.mystash.ui.adapter.SourceSpinnerAdapter;
import com.randomsilo.mystash.ui.adapter.WhatSpinnerAdapter;
import com.randomsilo.mystash.ui.adapter.WhySpinnerAdapter;
import com.randomsilo.mystash.ui.listener.OnClickFoundListItem;

public class MetaSearchActivity extends Activity {
	private Spinner MetaListSpinner;
	private Spinner MetaValueSpinner;
	private ListView ItemListView;
	private TextView FoundItemListHeader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meta_search);
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
	
	@Override
	protected void onResume() {
	    super.onResume();
	    refresh();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	public void refresh() {
		final Activity base = this;
		
		MetaListSpinner = (Spinner)findViewById(R.id.MetaListSpinner);
		MetaValueSpinner = (Spinner)findViewById(R.id.MetaValueSpinner);
		ItemListView = (ListView)findViewById(R.id.ItemListView);
		FoundItemListHeader = (TextView)findViewById(R.id.FoundItemListHeader);
		
		try {
			
			List<String> metaDataListNames = MyStashSession.getInstance().getThingService().getMetaDataListNames();
			MetaListSpinner.setAdapter( new SourceSpinnerAdapter(this, metaDataListNames));
			MetaListSpinner.setSelection( MyStashSession.getInstance().getLastSelectedMetaData());
			MetaListSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			    @Override
			    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
			    	if(selectedItemView == null) {
			    		return;
			    	}
			    	
			    	MyStashSession.getInstance().setLastSelectedMetaData(position);
			    	 
			    	TextView resourceName = ((TextView) selectedItemView.findViewById(R.id.ResourceName));
			    	
			    	String selectedType = resourceName.getText().toString();
			    	
			    	if(selectedType.equals(getString(R.string.list_what_name))) {
			    		
			    		List<What> objectList = MyStashSession.getInstance().getThingService().getWhatTags();
			    		if( objectList.size() < 1) {
			    			Toast.makeText(base, base.getResources().getString(R.string.dialog_need_to_define_what), Toast.LENGTH_LONG).show();
			    			return;
			    		}
			    		
			    		MetaValueSpinner.setAdapter( new WhatSpinnerAdapter(base, objectList));
			    		MetaValueSpinner.setSelection( MyStashSession.getInstance().getLastSelectedMetaDataWhat());
			    		MetaValueSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
						    @Override
						    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
						    	MyStashSession.getInstance().setLastSelectedMetaDataWhat(position);
						    	
						    	List<FoundItem> objects = MyStashSession.getInstance().getThingService().findItemsByWhat((What)MetaValueSpinner.getSelectedItem());
						    	ItemListView = (ListView)findViewById(R.id.ItemListView);
								FoundItemListArrayAdapter adapter = new FoundItemListArrayAdapter(base, R.layout.list_found_items, objects);
								ItemListView.setAdapter(adapter);
								ItemListView.setOnItemClickListener(new OnClickFoundListItem());
								FoundItemListHeader.setText( base.getResources().getString(R.string.header_found_list_with_count, objects.size()));
						    }
						    
						    @Override
							public void onNothingSelected(AdapterView<?> parent) {
								
							}
			    		});
			    		
			    		
			    	}
			    	else if(selectedType.equals(getString(R.string.list_why_name))) {
			    		
			    		List<Why> objectList = MyStashSession.getInstance().getThingService().getWhyTags();
			    		if( objectList.size() < 1) {
			    			Toast.makeText(base, base.getResources().getString(R.string.dialog_need_to_define_why), Toast.LENGTH_LONG).show();
			    			return;
			    		}
			    		
			    		MetaValueSpinner.setAdapter( new WhySpinnerAdapter(base, objectList));
			    		MetaValueSpinner.setSelection( MyStashSession.getInstance().getLastSelectedMetaDataWhy());
			    		MetaValueSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
						    @Override
						    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
						    	MyStashSession.getInstance().setLastSelectedMetaDataWhy(position);
						    	
						    	List<FoundItem> objects = MyStashSession.getInstance().getThingService().findItemsByWhy((Why)MetaValueSpinner.getSelectedItem());
						    	ItemListView = (ListView)findViewById(R.id.ItemListView);
								FoundItemListArrayAdapter adapter = new FoundItemListArrayAdapter(base, R.layout.list_found_items, objects);
								ItemListView.setAdapter(adapter);
								ItemListView.setOnItemClickListener(new OnClickFoundListItem());
								FoundItemListHeader.setText( base.getResources().getString(R.string.header_found_list_with_count, objects.size()));
						    }
						    
						    @Override
							public void onNothingSelected(AdapterView<?> parent) {
								
							}
			    		});

			    	}
			    }

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					
				}
			});
		
			
		} catch( Exception e) {
			Toast.makeText(this, this.getResources().getString(R.string.error_finding_items), Toast.LENGTH_LONG).show();
		}
		
	}
	
	
}
