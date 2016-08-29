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
import com.randomsilo.mystash.db.pojo.Why;

public class WhyListArrayAdapter extends ArrayAdapter<Why> {
	Context context;
	int layoutWhyId;
	List<Why> data = null;

	public WhyListArrayAdapter(Context context, int layoutWhyId, List<Why> data) {
		super(context, layoutWhyId, data);
		this.layoutWhyId = layoutWhyId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			convertView = inflater.inflate(layoutWhyId, parent, false);
		}

		Why why = data.get(position);
		TextView whyTag = (TextView) convertView.findViewById(R.id.WhyTag);
		whyTag.setText(why.getTag());
		
		TextView whyDescription = (TextView) convertView.findViewById(R.id.WhyDescription);
		whyDescription.setText(why.getDescription());
		
		TextView whyId = (TextView) convertView.findViewById(R.id.WhyId);
		whyId.setText(why.getId().toString());
		
		return convertView;
	}
	
}
