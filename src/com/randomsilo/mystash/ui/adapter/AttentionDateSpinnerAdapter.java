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
import com.randomsilo.mystash.db.pojo.AttentionDate;

public class AttentionDateSpinnerAdapter extends BaseAdapter implements SpinnerAdapter{
	Activity activity;
	List<AttentionDate> data = null;

	public AttentionDateSpinnerAdapter(Activity activity, List<AttentionDate> data) {
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
	        spinView = inflater.inflate(R.layout.spinner_attention_date, null);
	    } else {
	         spinView = convertView;
	    }
	    
	    TextView ResourceTag = (TextView) spinView.findViewById(R.id.AttentionDateTag);
	    ResourceTag.setText(data.get(position).getTag());
	    return spinView;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
