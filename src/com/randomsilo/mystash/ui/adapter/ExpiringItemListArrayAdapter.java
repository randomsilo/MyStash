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
import com.randomsilo.mystash.db.pojo.ExpiringItem;

public class ExpiringItemListArrayAdapter extends ArrayAdapter<ExpiringItem> {
	Context context;
	int layoutResourceId;
	List<ExpiringItem> data = null;

	public ExpiringItemListArrayAdapter(Context context, int layoutResourceId, List<ExpiringItem> data) {
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

		ExpiringItem model = data.get(position);
		
		TextView thingId = (TextView) convertView.findViewById(R.id.ThingId);
		TextView expiringItemTitle = (TextView) convertView.findViewById(R.id.ExpiringItemTitle);
		TextView expiringItemDescr = (TextView) convertView.findViewById(R.id.ExpiringItemDescr);
		
		String contentText = context.getResources().getString(R.string.notification_date_reached, model.getDateType(), model.getDateDifference());
		
		thingId.setText(model.getThingId()+"");
		expiringItemTitle.setText( model.getThingName());
		expiringItemDescr.setText( contentText);
		

		return convertView;
	}
	
}
