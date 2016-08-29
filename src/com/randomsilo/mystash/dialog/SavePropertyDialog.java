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
import com.randomsilo.mystash.db.pojo.Property;
import com.randomsilo.mystash.session.MyStashSession;

public class SavePropertyDialog {

	public static void show(final Activity activity) {
		final Dialog dialog = new Dialog(activity, R.style.DialogMyStash);
		dialog.setContentView(R.layout.dialog_save_property);
		dialog.setTitle(R.string.dialog_save_property_title);
		
		final TextView PropertyId = (TextView) dialog.findViewById(R.id.PropertyId);
		final EditText PropertyName = (EditText) dialog.findViewById(R.id.PropertyName);
		final EditText PropertyDescription = (EditText) dialog.findViewById(R.id.PropertyDescription);
		
		Property r = MyStashSession.getInstance().getActiveProperty();
		if( r == null) {
			r = new Property();
			r.setId(0L);
			r.setTag(activity.getResources().getString(R.string.dialog_save_property_new_tag));
			r.setDescription(activity.getResources().getString(R.string.dialog_save_property_new_description));
		}
		
		PropertyId.setText(r.getId()+"");
		PropertyName.setText(r.getTag());
		PropertyDescription.setText(r.getDescription());

		Button saveBtn = (Button) dialog.findViewById(R.id.SaveBtn);
		saveBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Get Edited Values
				Property r = new Property();
				r.setId(Long.parseLong(PropertyId.getText().toString()));
				r.setTag(PropertyName.getText().toString());
				r.setDescription(PropertyDescription.getText().toString());
				
				// Save Property
				MyStashSession.getInstance().getThingService().getPropertyDao().save(r);
				
				// Refresh Activity
				((EditListActivity)activity).refresh();
				
				// Tell User
				Toast.makeText(activity, PropertyName.getText() + " " + activity.getResources().getString(R.string.dialog_save_btn_msg), Toast.LENGTH_LONG).show();
				
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
				Property r = new Property();
				r.setId(Long.parseLong(PropertyId.getText().toString()));
				r.setTag(PropertyName.getText().toString());
				r.setDescription(PropertyDescription.getText().toString());
				
				// Remove Property
				MyStashSession.getInstance().getThingService().getPropertyDao().delete(r);
				
				// Refresh Activity
				((EditListActivity)activity).refresh();
				
				// Tell User
				Toast.makeText(activity, PropertyName.getText() + " " + activity.getResources().getString(R.string.dialog_remove_btn_msg), Toast.LENGTH_LONG).show();
				
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
