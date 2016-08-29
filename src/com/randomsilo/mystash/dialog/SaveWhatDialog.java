package com.randomsilo.mystash.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.randomsilo.mystash.EditListActivity;
import com.randomsilo.mystash.R;
import com.randomsilo.mystash.db.pojo.What;
import com.randomsilo.mystash.session.MyStashSession;

public class SaveWhatDialog {

	public static void show(final Activity activity) {
		final Dialog dialog = new Dialog(activity, R.style.DialogMyStash);
		dialog.setContentView(R.layout.dialog_save_what);
		dialog.setTitle(R.string.dialog_save_what_title);
		
		final TextView WhatId = (TextView) dialog.findViewById(R.id.WhatId);
		final EditText WhatName = (EditText) dialog.findViewById(R.id.WhatName);
		final EditText WhatDescription = (EditText) dialog.findViewById(R.id.WhatDescription);
		
		What r = MyStashSession.getInstance().getActiveWhat();
		if( r == null) {
			r = new What();
			r.setId(0L);
			r.setTag(activity.getResources().getString(R.string.dialog_save_what_new_tag));
			r.setDescription(activity.getResources().getString(R.string.dialog_save_what_new_description));
		}
		
		WhatId.setText(r.getId()+"");
		WhatName.setText(r.getTag());
		WhatDescription.setText(r.getDescription());

		Button saveBtn = (Button) dialog.findViewById(R.id.SaveBtn);
		saveBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Get Edited Values
				What r = new What();
				r.setId(Long.parseLong(WhatId.getText().toString()));
				r.setTag(WhatName.getText().toString());
				r.setDescription(WhatDescription.getText().toString());
				
				// Save What
				MyStashSession.getInstance().getThingService().getWhatDao().save(r);
				
				// Refresh Activity
				((EditListActivity)activity).refresh();
				
				// Tell User
				Toast.makeText(activity, WhatName.getText() + " " + activity.getResources().getString(R.string.dialog_save_btn_msg), Toast.LENGTH_LONG).show();
				
				// Go Back
				dialog.dismiss();
			}
		});
		
		Button removeBtn = (Button) dialog.findViewById(R.id.RemoveBtn);
		if(r.getId() == 0) {
			removeBtn.setEnabled(false);
		} else {
			removeBtn.setEnabled(true);
		}
		removeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Get Edited Values
				What r = new What();
				r.setId(Long.parseLong(WhatId.getText().toString()));
				r.setTag(WhatName.getText().toString());
				r.setDescription(WhatDescription.getText().toString());
				
				// Remove What
				MyStashSession.getInstance().getThingService().getWhatDao().delete(r);
				
				// Refresh Activity
				((EditListActivity)activity).refresh();
				
				// Tell User
				Toast.makeText(activity, WhatName.getText() + " " + activity.getResources().getString(R.string.dialog_remove_btn_msg), Toast.LENGTH_LONG).show();
				
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
