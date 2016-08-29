package com.randomsilo.mystash.ui.listener;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.randomsilo.mystash.R;
import com.randomsilo.mystash.dialog.SaveThingAttentionDateDialog;
import com.randomsilo.mystash.model.ThingAttentionDateModel;
import com.randomsilo.mystash.session.MyStashSession;

public class OnClickThingAttentionDateListItem implements OnItemClickListener {

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		Context context = view.getContext();
        TextView IdTextView = ((TextView) view.findViewById(R.id.Id));
        
        Long Id = Long.parseLong(IdTextView.getText().toString());
        ThingAttentionDateModel m = MyStashSession.getInstance().getThingService().getThingAttentionDate(Id);
        if( m != null) {
        	MyStashSession.getInstance().setActiveAttentionDateModel(m);
        }
        
        SaveThingAttentionDateDialog.show((Activity)context);
	}
	
	

}
