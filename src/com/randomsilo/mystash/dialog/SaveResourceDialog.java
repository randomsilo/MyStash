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
import com.randomsilo.mystash.db.pojo.Resource;
import com.randomsilo.mystash.session.MyStashSession;

public class SaveResourceDialog {

	public static void show(final Activity activity) {
		final Dialog dialog = new Dialog(activity, R.style.DialogMyStash);
		dialog.setContentView(R.layout.dialog_save_resource);
		dialog.setTitle(R.string.dialog_save_resource_title);
		
		final TextView ResourceId = (TextView) dialog.findViewById(R.id.ResourceId);
		final EditText ResourceName = (EditText) dialog.findViewById(R.id.ResourceName);
		final EditText ResourceDescription = (EditText) dialog.findViewById(R.id.ResourceDescription);
		
		Resource r = MyStashSession.getInstance().getActiveResource();
		if( r == null) {
			r = new Resource();
			r.setId(0L);
			r.setTag(activity.getResources().getString(R.string.dialog_save_resource_new_tag));
			r.setDescription(activity.getResources().getString(R.string.dialog_save_resource_new_description));
		}
		
		ResourceId.setText(r.getId()+"");
		ResourceName.setText(r.getTag());
		ResourceDescription.setText(r.getDescription());

		Button saveBtn = (Button) dialog.findViewById(R.id.SaveBtn);
		saveBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Get Edited Values
				Resource r = new Resource();
				r.setId(Long.parseLong(ResourceId.getText().toString()));
				r.setTag(ResourceName.getText().toString());
				r.setDescription(ResourceDescription.getText().toString());
				
				// Save Resource
				MyStashSession.getInstance().getThingService().getResourceDao().save(r);
				
				// Refresh Activity
				((EditListActivity)activity).refresh();
				
				// Tell User
				Toast.makeText(activity, ResourceName.getText() + " " + activity.getResources().getString(R.string.dialog_save_btn_msg), Toast.LENGTH_LONG).show();
				
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
				Resource r = new Resource();
				r.setId(Long.parseLong(ResourceId.getText().toString()));
				r.setTag(ResourceName.getText().toString());
				r.setDescription(ResourceDescription.getText().toString());
				
				// Remove Resource
				MyStashSession.getInstance().getThingService().getResourceDao().delete(r);
				
				// Refresh Activity
				((EditListActivity)activity).refresh();
				
				// Tell User
				Toast.makeText(activity, ResourceName.getText() + " " + activity.getResources().getString(R.string.dialog_remove_btn_msg), Toast.LENGTH_LONG).show();
				
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
