package com.randomsilo.mystash.ui.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.randomsilo.mystash.BrowseActivity;
import com.randomsilo.mystash.R;
import com.randomsilo.mystash.model.ThingModel;
import com.randomsilo.mystash.service.ThingDeleteResponse;
import com.randomsilo.mystash.session.MyStashSession;

public class ThingListArrayAdapter extends ArrayAdapter<ThingModel> {
	Context context;
	int layoutResourceId;
	List<ThingModel> data = null;

	public ThingListArrayAdapter(Context context, int layoutResourceId,
			List<ThingModel> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			convertView = inflater.inflate(layoutResourceId, parent, false);
		}

		final Context context = convertView.getContext();
		final ThingModel thing = data.get(position);
		final TextView thingIdTextView = (TextView) convertView.findViewById(R.id.thing_id);
		final TextView thingTag = (TextView) convertView.findViewById(R.id.thing_tag);
		final ImageButton removeThingBtn = (ImageButton)convertView.findViewById(R.id.removeThingBtn);
		
		if(thing != null && thing.getThingId() != null) {
			thingIdTextView.setText(thing.getThingId().toString());
			thingTag.setText(thing.getThing().getTag());
			
			thingTag.setOnClickListener(new OnClickListener() 
	        {
	
	            @Override
	            public void onClick(View v) 
	            {
	                Long thingId = Long.parseLong(thingIdTextView.getText().toString());
	                MyStashSession.getInstance().setActiveThing(
	                		MyStashSession.getInstance().getThingService().get(thingId)
	        		);
	                
	                Intent intent = new Intent(context, BrowseActivity.class);
	        		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);        
	        		context.startActivity(intent);
	        		((Activity) context).overridePendingTransition(R.anim.pull_out_top, R.anim.pull_in_bottom);
	            }
	        });
			
			removeThingBtn.setOnClickListener(new OnClickListener() 
	        {
	
	            @Override
	            public void onClick(View v) 
	            {
	                ThingDeleteResponse response = MyStashSession.getInstance().getThingService().delete(thing);
	                
	                if(response.wasDeleted()) {
	                	MyStashSession.getInstance().setActiveThing(
		                		MyStashSession.getInstance().getThingService().get(response.getParentId())
		        		);
		                
	                	((BrowseActivity)context).RefreshList();
	                	
	                	// Toast Success Message
	                	Toast.makeText(context, context.getResources().getString(R.string.thing_remove_success), Toast.LENGTH_LONG).show();
	                } else {
	                	// Toast Fail Message
	                	Toast.makeText(context, context.getResources().getString(R.string.thing_remove_fail), Toast.LENGTH_LONG).show();
	                }
	            }
	        });
			
		} else {
			thingIdTextView.setVisibility(View.GONE);
			thingTag.setVisibility(View.GONE);
			removeThingBtn.setVisibility(View.GONE);
		}
		
		return convertView;
	}
	
}
