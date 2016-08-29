package com.randomsilo.mystash.ui.listener;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.randomsilo.mystash.R;
import com.randomsilo.mystash.db.pojo.What;
import com.randomsilo.mystash.dialog.SaveWhatDialog;
import com.randomsilo.mystash.session.MyStashSession;

public class OnClickWhatListItem implements OnItemClickListener {

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		Context context = view.getContext();
        TextView WhatIdTextView = ((TextView) view.findViewById(R.id.WhatId));
        
        Long WhatId = Long.parseLong(WhatIdTextView.getText().toString());
        What m = MyStashSession.getInstance().getThingService().getWhatDao().findById(WhatId);
        if(m != null) {
        	MyStashSession.getInstance().setActiveWhat(m);
        }
        
        SaveWhatDialog.show((Activity)context);
	}

}
