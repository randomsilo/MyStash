package com.randomsilo.mystash.ui.listener;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.randomsilo.mystash.R;
import com.randomsilo.mystash.db.pojo.Why;
import com.randomsilo.mystash.dialog.SaveWhyDialog;
import com.randomsilo.mystash.session.MyStashSession;

public class OnClickWhyListItem implements OnItemClickListener {

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		Context context = view.getContext();
        TextView WhyIdTextView = ((TextView) view.findViewById(R.id.WhyId));
        
        Long WhyId = Long.parseLong(WhyIdTextView.getText().toString());
        Why r = MyStashSession.getInstance().getThingService().getWhyDao().findById(WhyId);
        if(r != null) {
        	MyStashSession.getInstance().setActiveWhy(r);
        }
        
        SaveWhyDialog.show((Activity)context);
	}

}
