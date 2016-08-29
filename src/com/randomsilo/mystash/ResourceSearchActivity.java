package com.randomsilo.mystash;

import java.util.ArrayList;
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
import com.randomsilo.mystash.db.pojo.Resource;
import com.randomsilo.mystash.session.MyStashSession;
import com.randomsilo.mystash.ui.adapter.FoundItemListArrayAdapter;
import com.randomsilo.mystash.ui.adapter.ResourceSpinnerAdapter;
import com.randomsilo.mystash.ui.adapter.SourceSpinnerAdapter;
import com.randomsilo.mystash.ui.listener.OnClickFoundListItem;

public class ResourceSearchActivity extends Activity {

	private Spinner ResourceTypeSpinner;
	private Spinner ResourceListSpinner;
	private ListView ItemListView;
	private TextView FoundItemListHeader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resource_search);
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
		
		ResourceTypeSpinner = (Spinner)findViewById(R.id.ResourceTypeSpinner);
		ResourceListSpinner = (Spinner)findViewById(R.id.ResourceListSpinner);
		ItemListView = (ListView)findViewById(R.id.ItemListView);
		FoundItemListHeader = (TextView)findViewById(R.id.FoundItemListHeader);
		
		try {
			
			List<String> resourceSearchTypeNames = MyStashSession.getInstance().getThingService().getResourceSearchTypeNames();
			ResourceTypeSpinner.setAdapter( new SourceSpinnerAdapter(this, resourceSearchTypeNames));
			ResourceTypeSpinner.setSelection( MyStashSession.getInstance().getLastSelectedResourceSearchType());
			ResourceTypeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			    @Override
			    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
			    	if(selectedItemView == null) {
			    		return;
			    	}
			    	
			    	MyStashSession.getInstance().setLastSelectedResourceSearchType(position);
			    	TextView resourceName = ((TextView) selectedItemView.findViewById(R.id.ResourceName));
			    	final String selectedType = resourceName.getText().toString();
			    		
		    		List<Resource> objectList = MyStashSession.getInstance().getThingService().getResourceTags();
		    		if( objectList.size() < 1) {
		    			Toast.makeText(base, base.getResources().getString(R.string.dialog_need_to_define_resources), Toast.LENGTH_LONG).show();
		    			return;
		    		}
		    		
		    		
		    		ResourceListSpinner.setAdapter( new ResourceSpinnerAdapter(base, objectList));
		    		if(selectedType.equals(getString(R.string.list_consumes_name))) {
		    			ResourceListSpinner.setSelection( MyStashSession.getInstance().getLastSelectedResourceConsumes());	
		    		} else {
		    			ResourceListSpinner.setSelection( MyStashSession.getInstance().getLastSelectedResourceProvides());
		    		}
		    		
		    		ResourceListSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
					    @Override
					    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
					    	MyStashSession.getInstance().setLastSelectedMetaDataWhat(position);
					    	
					    	List<FoundItem> objects = new ArrayList<FoundItem>();
					    	if(selectedType.equals(getString(R.string.list_consumes_name))) {
				    			MyStashSession.getInstance().setLastSelectedResourceConsumes(position);
				    			objects = MyStashSession.getInstance().getThingService().findItemsByConsumes((Resource)ResourceListSpinner.getSelectedItem());
				    		} else {
				    			MyStashSession.getInstance().setLastSelectedResourceProvides(position);
				    			objects = MyStashSession.getInstance().getThingService().findItemsByProvides((Resource)ResourceListSpinner.getSelectedItem());
				    		}
					    	
					    	ItemListView = (ListView)findViewById(R.id.ItemListView);
							FoundItemListArrayAdapter adapter = new FoundItemListArrayAdapter(base, R.layout.list_found_items, objects);
							ItemListView.setAdapter(adapter);
							ItemListView.setOnItemClickListener(new OnClickFoundListItem());
							
							int objectCount = objects.size();
							Long totalResourceCount = 0L;
							for(FoundItem item : objects) {
								totalResourceCount += item.getResourceCount();
							}
							
							FoundItemListHeader.setText( base.getResources().getString(R.string.header_found_list_with_details, objectCount, totalResourceCount));
							
					    }
					    
					    @Override
						public void onNothingSelected(AdapterView<?> parent) {
							
						}
		    		});
		    		
		    		
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