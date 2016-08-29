package com.randomsilo.mystash.dialog;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.randomsilo.mystash.R;
import com.randomsilo.mystash.ThingPropertiesActivity;
import com.randomsilo.mystash.db.pojo.Property;
import com.randomsilo.mystash.db.pojo.ThingProperty;
import com.randomsilo.mystash.model.ThingModel;
import com.randomsilo.mystash.model.ThingPropertyModel;
import com.randomsilo.mystash.session.MyStashSession;
import com.randomsilo.mystash.ui.adapter.PropertySpinnerAdapter;

public class SaveThingPropertyDialog {

	public static void SetPropertySpinnerItemByPropertyid(Spinner spnr, Long id)
	{
		PropertySpinnerAdapter adapter = (PropertySpinnerAdapter) spnr.getAdapter();
	    for (int position = 0; position < adapter.getCount(); position++)
	    {
	    	Property p = (Property)adapter.getItem(position);
	        if(p.getId() == id)
	        {
	            spnr.setSelection(position);
	            return;
	        }
	    }
	}
	
	public static void show(final Activity activity) {
		final Dialog dialog = new Dialog(activity, R.style.DialogMyStash);
		dialog.setContentView(R.layout.dialog_save_thing_property);
		dialog.setTitle(R.string.dialog_save_thing_property_title);
		
		final TextView Id = (TextView) dialog.findViewById(R.id.Id);
		final TextView ThingId = (TextView) dialog.findViewById(R.id.ThingId);
		final TextView PropertyId = (TextView) dialog.findViewById(R.id.PropertyId);
		final TextView PropertyValue = (TextView) dialog.findViewById(R.id.PropertyValue);
		final Spinner PropertySpinner = (Spinner) dialog.findViewById(R.id.PropertySpinner);
		
		ThingPropertyModel tpm = MyStashSession.getInstance().getActivePropertyModel();
		ThingModel m = MyStashSession.getInstance().getActiveThing();
		
		// resource list
		List<Property> propertyList = MyStashSession.getInstance().getThingService().getPropertyTags();
		if( propertyList.size() < 1) {
			Toast.makeText(activity, activity.getResources().getString(R.string.dialog_need_to_define_properties), Toast.LENGTH_LONG).show();
			return;
		}
		
		// setup spinner
		PropertySpinner.setAdapter( new PropertySpinnerAdapter(activity, propertyList));
		PropertySpinner.setSelection( 0);
		Property p = propertyList.get( 0);
		
		// leave if thing not selected
		if( m == null) {
			Toast.makeText(activity, activity.getResources().getString(R.string.dialog_need_a_thing), Toast.LENGTH_LONG).show();
			dialog.dismiss();
		}
		
		// set default model
		if( tpm == null) {
			tpm = new ThingPropertyModel();
			tpm.setId(0L);
			tpm.setThingId(m.getThingId());
			tpm.setPropertyId(p.getId());
			tpm.setValue("");
			tpm.setProperty(p);

			
		} else {
			SetPropertySpinnerItemByPropertyid(PropertySpinner, tpm.getPropertyId());
			p = (Property)PropertySpinner.getSelectedItem();
		}
		
		// dialog controls
		Id.setText(tpm.getId()+"");
		ThingId.setText(tpm.getThingId()+"");
		PropertyId.setText(tpm.getPropertyId()+"");
		PropertyValue.setText(tpm.getValue()+"");

		// buttons
		Button saveBtn = (Button) dialog.findViewById(R.id.SaveBtn);
		saveBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// Get Edited Values
				Property p = (Property)PropertySpinner.getSelectedItem();
				
				ThingProperty tp = new ThingProperty(); 
				tp.setId(Long.parseLong(Id.getText()+""));
				tp.setThingId(Long.parseLong(ThingId.getText()+""));
				tp.setPropertyId(p.getId());
				tp.setValue(PropertyValue.getText()+"");
				
				// Save ThingProperty
				MyStashSession.getInstance().getThingService().getThingPropertyDao().save(tp);
				
				// Refresh Activity
				((ThingPropertiesActivity)activity).RefreshPropertyList();
				
				// Tell User
				Toast.makeText(activity, activity.getResources().getString(R.string.dialog_save_btn_msg), Toast.LENGTH_LONG).show();
				
				// Go Back
				dialog.dismiss();
			}
		});
		
		Button removeBtn = (Button) dialog.findViewById(R.id.RemoveBtn);
		if(tpm.getId() == 0) {
			removeBtn.setEnabled(false);
		} else {
			removeBtn.setEnabled(true);
		}
		removeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				// Get Edited Values
				ThingProperty tp = new ThingProperty(); 
				tp.setId(Long.parseLong(Id.getText()+""));
				tp.setThingId(Long.parseLong(ThingId.getText()+""));
				tp.setPropertyId(Long.parseLong(PropertyId.getText()+""));
				
				// Remove ThingProperty
				MyStashSession.getInstance().getThingService().getThingPropertyDao().delete(tp);
				
				// Refresh Activity
				((ThingPropertiesActivity)activity).RefreshPropertyList();
				
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
