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
import com.randomsilo.mystash.db.pojo.Property;

public class PropertyListArrayAdapter extends ArrayAdapter<Property> {
	Context context;
	int layoutPropertyId;
	List<Property> data = null;

	public PropertyListArrayAdapter(Context context, int layoutPropertyId, List<Property> data) {
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

		Property property = data.get(position);
		TextView propertyTag = (TextView) convertView.findViewById(R.id.PropertyTag);
		propertyTag.setText(property.getTag());
		
		TextView propertyDescription = (TextView) convertView.findViewById(R.id.PropertyDescription);
		propertyDescription.setText(property.getDescription());
		
		TextView propertyId = (TextView) convertView.findViewById(R.id.PropertyId);
		propertyId.setText(property.getId().toString());
		
		return convertView;
	}
	
}
