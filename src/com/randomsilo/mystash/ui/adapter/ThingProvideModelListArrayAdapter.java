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
import com.randomsilo.mystash.model.ThingProvideModel;

public class ThingProvideModelListArrayAdapter extends ArrayAdapter<ThingProvideModel> {
	Context context;
	int layoutResourceId;
	List<ThingProvideModel> data = null;

	public ThingProvideModelListArrayAdapter(Context context, int layoutResourceId, List<ThingProvideModel> data) {
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

		ThingProvideModel model = data.get(position);
		
		TextView id = (TextView) convertView.findViewById(R.id.Id);
		TextView thingId = (TextView) convertView.findViewById(R.id.ThingId);
		TextView resourceId = (TextView) convertView.findViewById(R.id.ResourceId);
		
		TextView resourceTag = (TextView) convertView.findViewById(R.id.ResourceTag);
		TextView resourceQuantity = (TextView) convertView.findViewById(R.id.ResourceQuantity);
		TextView resourceDetails = (TextView) convertView.findViewById(R.id.ResourceDetails);
		
		id.setText(model.getId()+"");
		thingId.setText(model.getThingId()+"");
		resourceId.setText(model.getResourceId()+"");
		
		resourceTag.setText(model.getResource().getTag());
		resourceQuantity.setText(model.getResourceQuantity().toString());
		resourceDetails.setText(model.getResourceDetails());

		return convertView;
	}
	
}
