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
import com.randomsilo.mystash.db.pojo.What;

public class WhatListArrayAdapter extends ArrayAdapter<What> {
	Context context;
	int layoutWhatId;
	List<What> data = null;

	public WhatListArrayAdapter(Context context, int layoutWhatId, List<What> data) {
		super(context, layoutWhatId, data);
		this.layoutWhatId = layoutWhatId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			convertView = inflater.inflate(layoutWhatId, parent, false);
		}

		What what = data.get(position);
		TextView whatTag = (TextView) convertView.findViewById(R.id.WhatTag);
		whatTag.setText(what.getTag());
		
		TextView whatDescription = (TextView) convertView.findViewById(R.id.WhatDescription);
		whatDescription.setText(what.getDescription());
		
		TextView whatId = (TextView) convertView.findViewById(R.id.WhatId);
		whatId.setText(what.getId().toString());
		
		return convertView;
	}
	
}
