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
import com.randomsilo.mystash.db.pojo.FoundItem;

public class FoundItemListArrayAdapter extends ArrayAdapter<FoundItem> {
	Context context;
	int layoutResourceId;
	List<FoundItem> data = null;

	public FoundItemListArrayAdapter(Context context, int layoutResourceId, List<FoundItem> data) {
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

		FoundItem model = data.get(position);
		
		TextView thingId = (TextView) convertView.findViewById(R.id.ThingId);
		TextView itemTitle = (TextView) convertView.findViewById(R.id.ItemTitle);
		TextView itemDescr = (TextView) convertView.findViewById(R.id.ItemDescr);
		
		thingId.setText(model.getThingId()+"");
		itemTitle.setText( model.getThingName());
		itemDescr.setText( model.getUserMessage());

		return convertView;
	}
	
}
