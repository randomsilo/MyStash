package com.randomsilo.mystash.dialog;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.randomsilo.mystash.R;
import com.randomsilo.mystash.ThingMetadataActivity;
import com.randomsilo.mystash.db.pojo.ThingWhat;
import com.randomsilo.mystash.db.pojo.What;
import com.randomsilo.mystash.model.ThingModel;
import com.randomsilo.mystash.model.ThingWhatModel;
import com.randomsilo.mystash.session.MyStashSession;
import com.randomsilo.mystash.ui.adapter.WhatSpinnerAdapter;

public class SaveThingWhatDialog {

	public static void SetWhatSpinnerItemByWhatid(Spinner spnr, Long id)
	{
		WhatSpinnerAdapter adapter = (WhatSpinnerAdapter) spnr.getAdapter();
	    for (int position = 0; position < adapter.getCount(); position++)
	    {
	    	What r = (What)adapter.getItem(position);
	        if(r.getId() == id)
	        {
	            spnr.setSelection(position);
	            return;
	        }
	    }
	}
	
	public static void show(final Activity activity) {
		final Dialog dialog = new Dialog(activity, R.style.DialogMyStash);
		dialog.setContentView(R.layout.dialog_save_thing_what);
		dialog.setTitle(R.string.dialog_save_thing_what_title);
		
		final TextView Id = (TextView) dialog.findViewById(R.id.Id);
		final TextView ThingId = (TextView) dialog.findViewById(R.id.ThingId);
		final TextView WhatId = (TextView) dialog.findViewById(R.id.WhatId);
		final Spinner WhatSpinner = (Spinner) dialog.findViewById(R.id.WhatSpinner);
		final EditText WhatDetails = (EditText) dialog.findViewById(R.id.WhatDetails);
		
		ThingWhatModel c = MyStashSession.getInstance().getActiveWhatModel();
		ThingModel m = MyStashSession.getInstance().getActiveThing();
		
		// resource list
		List<What> objectList = MyStashSession.getInstance().getThingService().getWhatTags();
		if( objectList.size() < 1) {
			Toast.makeText(activity, activity.getResources().getString(R.string.dialog_need_to_define_resources), Toast.LENGTH_LONG).show();
			return;
		}
		
		// setup spinner
		WhatSpinner.setAdapter( new WhatSpinnerAdapter(activity, objectList));
		WhatSpinner.setSelection( 0);
		What r = objectList.get( 0);
		
		// leave if thing not selected
		if( m == null) {
			Toast.makeText(activity, activity.getResources().getString(R.string.dialog_need_a_thing), Toast.LENGTH_LONG).show();
			dialog.dismiss();
		}
		
		// set default model
		if( c == null) {
			c = new ThingWhatModel();
			c.setId(0L);
			c.setThingId(m.getThingId());
			c.setWhatId(r.getId());
			c.setWhat(r);
			c.setWhatDetails("");
			
		} else {
			SetWhatSpinnerItemByWhatid(WhatSpinner, c.getWhatId());
			r = (What)WhatSpinner.getSelectedItem();
		}
		
		// dialog controls
		Id.setText(c.getId()+"");
		ThingId.setText(c.getThingId()+"");
		WhatId.setText(c.getWhatId()+"");
		WhatDetails.setText(c.getWhatDetails());

		// buttons
		Button saveBtn = (Button) dialog.findViewById(R.id.SaveBtn);
		saveBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// Get Edited Values
				What o = (What)WhatSpinner.getSelectedItem();
				
				ThingWhat to = new ThingWhat(); 
				to.setId(Long.parseLong(Id.getText()+""));
				to.setThingId(Long.parseLong(ThingId.getText()+""));
				to.setWhatId(o.getId());
				to.setWhatDetails(WhatDetails.getText()+"");
				
				// Save ThingWhat
				MyStashSession.getInstance().getThingService().getThingWhatDao().save(to);
				
				// Refresh Activity
				((ThingMetadataActivity)activity).RefreshWhatList();
				
				// Tell User
				Toast.makeText(activity, activity.getResources().getString(R.string.dialog_save_btn_msg), Toast.LENGTH_LONG).show();
				
				// Go Back
				dialog.dismiss();
			}
		});
		
		Button removeBtn = (Button) dialog.findViewById(R.id.RemoveBtn);
		if(c.getId() == 0) {
			removeBtn.setEnabled(false);
		} else {
			removeBtn.setEnabled(true);
		}
		removeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				// Get Edited Values
				ThingWhat tc = new ThingWhat(); 
				tc.setId(Long.parseLong(Id.getText()+""));
				tc.setThingId(Long.parseLong(ThingId.getText()+""));
				tc.setWhatId(Long.parseLong(WhatId.getText()+""));
				tc.setWhatDetails(WhatDetails.getText()+"");
				
				// Remove ThingWhat
				MyStashSession.getInstance().getThingService().getThingWhatDao().delete(tc);
				
				// Refresh Activity
				((ThingMetadataActivity)activity).RefreshWhatList();
				
				// Tell User
				Toast.makeText(activity, activity.getResources().getString(R.string.dialog_remove_btn_msg), Toast.LENGTH_LONG).show();
				
				// Go Back
				dialog.dismiss();
			}
		});
		
		Button backBtn = (Button) dialog.findViewById(R.id.BackBtn);
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		dialog.show();
		
		return;
	}
}
