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
import com.randomsilo.mystash.db.pojo.AttentionDate;
import com.randomsilo.mystash.session.MyStashSession;

public class SaveAttentionDateDialog {

	public static void show(final Activity activity) {
		final Dialog dialog = new Dialog(activity, R.style.DialogMyStash);
		dialog.setContentView(R.layout.dialog_save_attention_date);
		dialog.setTitle(R.string.dialog_save_attention_date_title);
		
		final TextView AttentionDateId = (TextView) dialog.findViewById(R.id.AttentionDateId);
		final EditText AttentionDateName = (EditText) dialog.findViewById(R.id.AttentionDateName);
		final EditText AttentionDateDescription = (EditText) dialog.findViewById(R.id.AttentionDateDescription);
		
		AttentionDate r = MyStashSession.getInstance().getActiveAttentionDate();
		if( r == null) {
			r = new AttentionDate();
			r.setId(0L);
			r.setTag(activity.getResources().getString(R.string.dialog_save_attention_date_new_tag));
			r.setDescription(activity.getResources().getString(R.string.dialog_save_attention_date_new_description));
		}
		
		AttentionDateId.setText(r.getId()+"");
		AttentionDateName.setText(r.getTag());
		AttentionDateDescription.setText(r.getDescription());

		Button saveBtn = (Button) dialog.findViewById(R.id.SaveBtn);
		saveBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Get Edited Values
				AttentionDate r = new AttentionDate();
				r.setId(Long.parseLong(AttentionDateId.getText().toString()));
				r.setTag(AttentionDateName.getText().toString());
				r.setDescription(AttentionDateDescription.getText().toString());
				
				// Save AttentionDate
				MyStashSession.getInstance().getThingService().getAttentionDateDao().save(r);
				
				// Refresh Activity
				((EditListActivity)activity).refresh();
				
				// Tell User
				Toast.makeText(activity, AttentionDateName.getText() + " " + activity.getResources().getString(R.string.dialog_save_btn_msg), Toast.LENGTH_LONG).show();
				
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
				AttentionDate r = new AttentionDate();
				r.setId(Long.parseLong(AttentionDateId.getText().toString()));
				r.setTag(AttentionDateName.getText().toString());
				r.setDescription(AttentionDateDescription.getText().toString());
				
				// Remove AttentionDate
				MyStashSession.getInstance().getThingService().getAttentionDateDao().delete(r);
				
				// Refresh Activity
				((EditListActivity)activity).refresh();
				
				// Tell User
				Toast.makeText(activity, AttentionDateName.getText() + " " + activity.getResources().getString(R.string.dialog_remove_btn_msg), Toast.LENGTH_LONG).show();
				
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
