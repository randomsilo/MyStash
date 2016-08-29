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
import com.randomsilo.mystash.model.ThingAttentionDateModel;

public class ThingAttentionDateModelListArrayAdapter extends ArrayAdapter<ThingAttentionDateModel> {
	Context context;
	int layoutResourceId;
	List<ThingAttentionDateModel> data = null;

	public ThingAttentionDateModelListArrayAdapter(Context context, int layoutResourceId, List<ThingAttentionDateModel> data) {
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

		ThingAttentionDateModel model = data.get(position);
		
		TextView id = (TextView) convertView.findViewById(R.id.Id);
		TextView thingId = (TextView) convertView.findViewById(R.id.ThingId);
		TextView attentionDateId = (TextView) convertView.findViewById(R.id.AttentionDateId);
		
		TextView attentionDateTag = (TextView) convertView.findViewById(R.id.AttentionDateTag);
		TextView attentionDateValue = (TextView) convertView.findViewById(R.id.AttentionDateValue);
		
		id.setText(model.getId()+"");
		thingId.setText(model.getThingId()+"");
		attentionDateId.setText(model.getAttentionDateId()+"");
		if(model.getAttentionDate() != null) {
			attentionDateTag.setText(model.getAttentionDate().getTag());
		} else {
			attentionDateTag.setText("");
		}
		attentionDateValue.setText(model.getValue());

		return convertView;
	}
	
}
