package com.randomsilo.mystash.ui.listener;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.randomsilo.mystash.R;
import com.randomsilo.mystash.db.pojo.Property;
import com.randomsilo.mystash.dialog.SavePropertyDialog;
import com.randomsilo.mystash.session.MyStashSession;

public class OnClickPropertyListItem implements OnItemClickListener {

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		Context context = view.getContext();
        TextView PropertyIdTextView = ((TextView) view.findViewById(R.id.PropertyId));
        
        Long PropertyId = Long.parseLong(PropertyIdTextView.getText().toString());
        Property p = MyStashSession.getInstance().getThingService().getPropertyDao().findById(PropertyId);
        if(p != null) {
        	MyStashSession.getInstance().setActiveProperty(p);
        }
        
        SavePropertyDialog.show((Activity)context);
	}

}
