package com.randomsilo.mystash.ui.listener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.randomsilo.mystash.BrowseActivity;
import com.randomsilo.mystash.R;
import com.randomsilo.mystash.session.MyStashSession;

public class OnClickExpiredListItem implements OnItemClickListener {

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		Context context = view.getContext();
        TextView thingIdTextView = ((TextView) view.findViewById(R.id.ThingId));
        
        Long thingId = Long.parseLong(thingIdTextView.getText().toString());
        MyStashSession.getInstance().setActiveThing(
        		MyStashSession.getInstance().getThingService().get(thingId)
		);
        
        Intent intent = new Intent(view.getContext(), BrowseActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);        
		context.startActivity(intent);
		((Activity) context).overridePendingTransition(R.anim.pull_out_top, R.anim.pull_in_bottom);
        
	}

}
