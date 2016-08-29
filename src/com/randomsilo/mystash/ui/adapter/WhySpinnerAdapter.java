package com.randomsilo.mystash.ui.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.randomsilo.mystash.R;
import com.randomsilo.mystash.db.pojo.Why;

public class WhySpinnerAdapter extends BaseAdapter implements SpinnerAdapter{
	Activity activity;
	List<Why> data = null;

	public WhySpinnerAdapter(Activity activity, List<Why> data) {
		this.activity = activity;
		this.data = data;
	}
	
	public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return data.get(position);
    }

	@SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup parent) {
		View spinView;
	    if( convertView == null ){
	        LayoutInflater inflater = activity.getLayoutInflater();
	        spinView = inflater.inflate(R.layout.spinner_why, null);
	    } else {
	         spinView = convertView;
	    }
	    
	    TextView WhyTag = (TextView) spinView.findViewById(R.id.WhyTag);
	    WhyTag.setText(data.get(position).getTag());
	    return spinView;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
