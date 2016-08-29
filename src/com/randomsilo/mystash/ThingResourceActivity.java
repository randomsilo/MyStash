package com.randomsilo.mystash;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.randomsilo.mystash.dialog.SaveThingConsumeDialog;
import com.randomsilo.mystash.dialog.SaveThingProvideDialog;
import com.randomsilo.mystash.model.ThingConsumeModel;
import com.randomsilo.mystash.model.ThingModel;
import com.randomsilo.mystash.model.ThingProvideModel;
import com.randomsilo.mystash.session.MyStashSession;
import com.randomsilo.mystash.ui.adapter.ThingConsumeModelListArrayAdapter;
import com.randomsilo.mystash.ui.adapter.ThingProvideModelListArrayAdapter;
import com.randomsilo.mystash.ui.listener.OnClickThingConsumeListItem;
import com.randomsilo.mystash.ui.listener.OnClickThingProvideListItem;

public class ThingResourceActivity  extends BaseThingNavigationActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thing_resource);

	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	    
	    RefreshLists();
	}
	
	public void RefreshLists() {
		RefreshConsumeList();
		RefreshProvideList();
	}
	
	public void RefreshConsumeList() {
		List<ThingConsumeModel> consumes = new ArrayList<ThingConsumeModel>();
		
		ThingModel m = MyStashSession.getInstance().getActiveThing();
		if(m != null) { 
			consumes = MyStashSession.getInstance().getThingService().getConsumesList(m.getThingId());
		}
		
		ListView consumesListView = (ListView)findViewById(R.id.ConsumesListView);
		ThingConsumeModelListArrayAdapter consumesAdapter = new ThingConsumeModelListArrayAdapter(this, R.layout.list_thing_consumes, consumes);
		consumesListView.setAdapter(consumesAdapter);
		consumesListView.setOnItemClickListener(new OnClickThingConsumeListItem());
	}
	
	public void RefreshProvideList() {
		List<ThingProvideModel> provides = new ArrayList<ThingProvideModel>();
		
		ThingModel m = MyStashSession.getInstance().getActiveThing();
		if(m != null) { 
			provides = MyStashSession.getInstance().getThingService().getProvidesList(m.getThingId());
		}
		
		ListView providesListView = (ListView)findViewById(R.id.ProvidesListView);
		ThingProvideModelListArrayAdapter providesAdapter = new ThingProvideModelListArrayAdapter(this, R.layout.list_thing_provides, provides);
		providesListView.setAdapter(providesAdapter);
		providesListView.setOnItemClickListener(new OnClickThingProvideListItem());
	}
	
	public void addConsume(View v) {
		MyStashSession.getInstance().setActiveConsumeModel(null);
		SaveThingConsumeDialog.show(this);
	}
	
	public void addProvide(View v) {
		MyStashSession.getInstance().setActiveProvideModel(null);
		SaveThingProvideDialog.show(this);
	}

}
