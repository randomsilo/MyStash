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
import com.randomsilo.mystash.db.pojo.Why;
import com.randomsilo.mystash.session.MyStashSession;

public class SaveWhyDialog {

	public static void show(final Activity activity) {
		final Dialog dialog = new Dialog(activity, R.style.DialogMyStash);
		dialog.setContentView(R.layout.dialog_save_why);
		dialog.setTitle(R.string.dialog_save_why_title);
		
		final TextView WhyId = (TextView) dialog.findViewById(R.id.WhyId);
		final EditText WhyName = (EditText) dialog.findViewById(R.id.WhyName);
		final EditText WhyDescription = (EditText) dialog.findViewById(R.id.WhyDescription);
		
		Why r = MyStashSession.getInstance().getActiveWhy();
		if( r == null) {
			r = new Why();
			r.setId(0L);
			r.setTag(activity.getResources().getString(R.string.dialog_save_why_new_tag));
			r.setDescription(activity.getResources().getString(R.string.dialog_save_why_new_description));
		}
		
		WhyId.setText(r.getId()+"");
		WhyName.setText(r.getTag());
		WhyDescription.setText(r.getDescription());

		Button saveBtn = (Button) dialog.findViewById(R.id.SaveBtn);
		saveBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Get Edited Values
				Why r = new Why();
				r.setId(Long.parseLong(WhyId.getText().toString()));
				r.setTag(WhyName.getText().toString());
				r.setDescription(WhyDescription.getText().toString());
				
				// Save Why
				MyStashSession.getInstance().getThingService().getWhyDao().save(r);
				
				// Refresh Activity
				((EditListActivity)activity).refresh();
				
				// Tell User
				Toast.makeText(activity, WhyName.getText() + " " + activity.getResources().getString(R.string.dialog_save_btn_msg), Toast.LENGTH_LONG).show();
				
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
				Why r = new Why();
				r.setId(Long.parseLong(WhyId.getText().toString()));
				r.setTag(WhyName.getText().toString());
				r.setDescription(WhyDescription.getText().toString());
				
				// Remove Why
				MyStashSession.getInstance().getThingService().getWhyDao().delete(r);
				
				// Refresh Activity
				((EditListActivity)activity).refresh();
				
				// Tell User
				Toast.makeText(activity, WhyName.getText() + " " + activity.getResources().getString(R.string.dialog_remove_btn_msg), Toast.LENGTH_LONG).show();
				
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
