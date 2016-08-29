package com.randomsilo.mystash;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.randomsilo.mystash.dialog.SaveThingAttentionDateDialog;
import com.randomsilo.mystash.model.ThingAttentionDateModel;
import com.randomsilo.mystash.model.ThingModel;
import com.randomsilo.mystash.session.MyStashSession;
import com.randomsilo.mystash.ui.adapter.ThingAttentionDateModelListArrayAdapter;
import com.randomsilo.mystash.ui.listener.OnClickThingAttentionDateListItem;

public class ThingScheduleActivity extends BaseThingNavigationActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thing_schedule);
	}
	
	@Override
	protected void onResume() {
	    super.onResume();
	    
	    RefreshLists();
	}
	
	public void RefreshLists() {
		RefreshAttentionDateList();
	}
	
	public void RefreshAttentionDateList() {
		List<ThingAttentionDateModel> attentionDates = new ArrayList<ThingAttentionDateModel>();
		
		ThingModel m = MyStashSession.getInstance().getActiveThing();
		if(m != null) { 
			attentionDates = MyStashSession.getInstance().getThingService().getAttentionDatesList(m.getThingId());
		}
		
		ListView attentionDatesListView = (ListView)findViewById(R.id.AttentionDateListView);
		ThingAttentionDateModelListArrayAdapter attentionDatesAdapter = new ThingAttentionDateModelListArrayAdapter(this, R.layout.list_thing_attention_dates, attentionDates);
		attentionDatesListView.setAdapter(attentionDatesAdapter);
		attentionDatesListView.setOnItemClickListener(new OnClickThingAttentionDateListItem());
	}

	public void addAttentionDate(View v) {
		MyStashSession.getInstance().setActiveAttentionDateModel(null);
		SaveThingAttentionDateDialog.show(this);
	}
	

}
