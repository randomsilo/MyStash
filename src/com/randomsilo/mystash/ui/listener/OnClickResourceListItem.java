package com.randomsilo.mystash.ui.listener;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.randomsilo.mystash.R;
import com.randomsilo.mystash.db.pojo.Resource;
import com.randomsilo.mystash.dialog.SaveResourceDialog;
import com.randomsilo.mystash.session.MyStashSession;

public class OnClickResourceListItem implements OnItemClickListener {

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		Context context = view.getContext();
        TextView ResourceIdTextView = ((TextView) view.findViewById(R.id.ResourceId));
        
        Long ResourceId = Long.parseLong(ResourceIdTextView.getText().toString());
        Resource r = MyStashSession.getInstance().getThingService().getResourceDao().findById(ResourceId);
        if(r != null) {
        	MyStashSession.getInstance().setActiveResource(r);
        }
        
        SaveResourceDialog.show((Activity)context);
	}

}
