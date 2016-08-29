package com.randomsilo.mystash;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.randomsilo.mystash.dialog.SaveThingWhatDialog;
import com.randomsilo.mystash.dialog.SaveThingWhyDialog;
import com.randomsilo.mystash.model.ThingModel;
import com.randomsilo.mystash.model.ThingWhatModel;
import com.randomsilo.mystash.model.ThingWhyModel;
import com.randomsilo.mystash.session.MyStashSession;
import com.randomsilo.mystash.ui.adapter.ThingWhatModelListArrayAdapter;
import com.randomsilo.mystash.ui.adapter.ThingWhyModelListArrayAdapter;
import com.randomsilo.mystash.ui.listener.OnClickThingWhatListItem;
import com.randomsilo.mystash.ui.listener.OnClickThingWhyListItem;

public class ThingMetadataActivity extends BaseThingNavigationActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thing_metadata);
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	    
	    RefreshLists();
	}
	
	public void RefreshLists() {
		RefreshWhatList();
		RefreshWhyList();
	}
	
	public void RefreshWhatList() {
		List<ThingWhatModel> whats = new ArrayList<ThingWhatModel>();
		
		ThingModel m = MyStashSession.getInstance().getActiveThing();
		if(m != null) { 
			whats = MyStashSession.getInstance().getThingService().getWhatsList(m.getThingId());
		}
		
		ListView whatsListView = (ListView)findViewById(R.id.WhatsListView);
		ThingWhatModelListArrayAdapter whatsAdapter = new ThingWhatModelListArrayAdapter(this, R.layout.list_thing_whats, whats);
		whatsListView.setAdapter(whatsAdapter);
		whatsListView.setOnItemClickListener(new OnClickThingWhatListItem());
	}
	
	public void RefreshWhyList() {
		List<ThingWhyModel> whys = new ArrayList<ThingWhyModel>();
		
		ThingModel m = MyStashSession.getInstance().getActiveThing();
		if(m != null) { 
			whys = MyStashSession.getInstance().getThingService().getWhysList(m.getThingId());
		}
		
		ListView whysListView = (ListView)findViewById(R.id.WhysListView);
		ThingWhyModelListArrayAdapter whysAdapter = new ThingWhyModelListArrayAdapter(this, R.layout.list_thing_whys, whys);
		whysListView.setAdapter(whysAdapter);
		whysListView.setOnItemClickListener(new OnClickThingWhyListItem());
	}
	
	public void addWhat(View v) {
		MyStashSession.getInstance().setActiveWhat(null);
		SaveThingWhatDialog.show(this);
	}
	
	public void addWhy(View v) {
		MyStashSession.getInstance().setActiveWhy(null);
		SaveThingWhyDialog.show(this);
	}
}
