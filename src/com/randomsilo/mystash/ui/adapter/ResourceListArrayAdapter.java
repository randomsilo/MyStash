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
import com.randomsilo.mystash.db.pojo.Resource;

public class ResourceListArrayAdapter extends ArrayAdapter<Resource> {
	Context context;
	int layoutResourceId;
	List<Resource> data = null;

	public ResourceListArrayAdapter(Context context, int layoutResourceId, List<Resource> data) {
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

		Resource resource = data.get(position);
		TextView resourceTag = (TextView) convertView.findViewById(R.id.ResourceTag);
		resourceTag.setText(resource.getTag());
		
		TextView resourceDescription = (TextView) convertView.findViewById(R.id.ResourceDescription);
		resourceDescription.setText(resource.getDescription());
		
		TextView resourceId = (TextView) convertView.findViewById(R.id.ResourceId);
		resourceId.setText(resource.getId().toString());
		
		return convertView;
	}
	
}
