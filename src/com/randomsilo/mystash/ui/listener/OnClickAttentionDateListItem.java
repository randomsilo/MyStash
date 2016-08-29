package com.randomsilo.mystash.ui.listener;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.randomsilo.mystash.R;
import com.randomsilo.mystash.db.pojo.AttentionDate;
import com.randomsilo.mystash.dialog.SaveAttentionDateDialog;
import com.randomsilo.mystash.session.MyStashSession;

public class OnClickAttentionDateListItem implements OnItemClickListener {

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		Context context = view.getContext();
        TextView AttentionDateIdTextView = ((TextView) view.findViewById(R.id.AttentionDateId));
        
        Long AttentionDateId = Long.parseLong(AttentionDateIdTextView.getText().toString());
        AttentionDate p = MyStashSession.getInstance().getThingService().getAttentionDateDao().findById(AttentionDateId);
        if(p != null) {
        	MyStashSession.getInstance().setActiveAttentionDate(p);
        }
        
        SaveAttentionDateDialog.show((Activity)context);
	}

}
