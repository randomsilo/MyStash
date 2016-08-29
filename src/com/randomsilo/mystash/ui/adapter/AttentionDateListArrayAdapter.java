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
import com.randomsilo.mystash.db.pojo.AttentionDate;

public class AttentionDateListArrayAdapter extends ArrayAdapter<AttentionDate> {
	Context context;
	int layoutPropertyId;
	List<AttentionDate> data = null;

	public AttentionDateListArrayAdapter(Context context, int layoutPropertyId, List<AttentionDate> data) {
		super(context, layoutPropertyId, data);
		this.layoutPropertyId = layoutPropertyId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			convertView = inflater.inflate(layoutPropertyId, parent, false);
		}

		AttentionDate attentionDate = data.get(position);
		TextView attentionDateTag = (TextView) convertView.findViewById(R.id.AttentionDateTag);
		attentionDateTag.setText(attentionDate.getTag());
		
		TextView attentionDateDescription = (TextView) convertView.findViewById(R.id.AttentionDateDescription);
		attentionDateDescription.setText(attentionDate.getDescription());
		
		TextView attentionDateId = (TextView) convertView.findViewById(R.id.AttentionDateId);
		attentionDateId.setText(attentionDate.getId().toString());
		
		return convertView;
	}
	
}
