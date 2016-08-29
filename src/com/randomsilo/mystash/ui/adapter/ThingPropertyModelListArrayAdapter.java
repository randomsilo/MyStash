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
import com.randomsilo.mystash.model.ThingPropertyModel;

public class ThingPropertyModelListArrayAdapter extends ArrayAdapter<ThingPropertyModel> {
	Context context;
	int layoutResourceId;
	List<ThingPropertyModel> data = null;

	public ThingPropertyModelListArrayAdapter(Context context, int layoutResourceId, List<ThingPropertyModel> data) {
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

		ThingPropertyModel model = data.get(position);
		
		TextView id = (TextView) convertView.findViewById(R.id.Id);
		TextView thingId = (TextView) convertView.findViewById(R.id.ThingId);
		TextView propertyId = (TextView) convertView.findViewById(R.id.PropertyId);
		
		TextView propertyTag = (TextView) convertView.findViewById(R.id.PropertyTag);
		TextView propertyValue = (TextView) convertView.findViewById(R.id.PropertyValue);
		
		id.setText(model.getId()+"");
		thingId.setText(model.getThingId()+"");
		propertyId.setText(model.getPropertyId()+"");
		if(model.getProperty() != null) {
			propertyTag.setText(model.getProperty().getTag());
		} else {
			propertyTag.setText("");
		}
		propertyValue.setText(model.getValue());

		return convertView;
	}
	
}
