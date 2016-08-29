package com.randomsilo.mystash.ui.listener;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.randomsilo.mystash.R;
import com.randomsilo.mystash.dialog.SaveThingPropertyDialog;
import com.randomsilo.mystash.model.ThingPropertyModel;
import com.randomsilo.mystash.session.MyStashSession;

public class OnClickThingPropertyListItem implements OnItemClickListener {

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		Context context = view.getContext();
        TextView IdTextView = ((TextView) view.findViewById(R.id.Id));
        
        Long Id = Long.parseLong(IdTextView.getText().toString());
        ThingPropertyModel m = MyStashSession.getInstance().getThingService().getThingProperty(Id);
        if( m != null) {
        	MyStashSession.getInstance().setActivePropertyModel(m);
        }
        
        SaveThingPropertyDialog.show((Activity)context);
	}
	
	

}
