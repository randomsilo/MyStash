package com.randomsilo.mystash.ui.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.randomsilo.mystash.R;
import com.randomsilo.mystash.model.ThingWhatModel;

public class ThingWhatModelListArrayAdapter extends ArrayAdapter<ThingWhatModel> {
	Context context;
	int layoutResourceId;
	List<ThingWhatModel> data = null;

	public ThingWhatModelListArrayAdapter(Context context, int layoutResourceId, List<ThingWhatModel> data) {
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

		ThingWhatModel model = data.get(position);
		
		TextView id = (TextView) convertView.findViewById(R.id.Id);
		TextView thingId = (TextView) convertView.findViewById(R.id.ThingId);
		TextView resourceId = (TextView) convertView.findViewById(R.id.WhatId);
		
		TextView resourceTag = (TextView) convertView.findViewById(R.id.WhatTag);
		TextView resourceDetails = (TextView) convertView.findViewById(R.id.WhatDetails);
		
		id.setText(model.getId()+"");
		thingId.setText(model.getThingId()+"");
		resourceId.setText(model.getWhatId()+"");
		
		resourceTag.setText(model.getWhat().getTag());
		resourceDetails.setText(model.getWhatDetails());

		return convertView;
	}
	
}
