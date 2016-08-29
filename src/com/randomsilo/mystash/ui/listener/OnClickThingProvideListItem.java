package com.randomsilo.mystash.ui.listener;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.randomsilo.mystash.R;
import com.randomsilo.mystash.dialog.SaveThingProvideDialog;
import com.randomsilo.mystash.model.ThingProvideModel;
import com.randomsilo.mystash.session.MyStashSession;

public class OnClickThingProvideListItem implements OnItemClickListener {

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		Context context = view.getContext();
        TextView IdTextView = ((TextView) view.findViewById(R.id.Id));
        
        Long Id = Long.parseLong(IdTextView.getText().toString());
        ThingProvideModel m = MyStashSession.getInstance().getThingService().getThingProvides(Id);
        if( m != null) {
        	MyStashSession.getInstance().setActiveProvideModel(m);
        }
        
        SaveThingProvideDialog.show((Activity)context);
	}
	
	

}
